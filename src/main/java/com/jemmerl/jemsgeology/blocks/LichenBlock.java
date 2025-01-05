package com.jemmerl.jemsgeology.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LichenBlock extends Block implements IGrowable, IWaterLoggable {
    private static final VoxelShape UP_SHAPE = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape DOWN_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = SixWayBlock.FACING_TO_PROPERTY_MAP;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTIONS = Util.make(Maps.newEnumMap(Direction.class), (shapes) -> {
        shapes.put(Direction.NORTH, NORTH_SHAPE);
        shapes.put(Direction.EAST, EAST_SHAPE);
        shapes.put(Direction.SOUTH, SOUTH_SHAPE);
        shapes.put(Direction.WEST, WEST_SHAPE);
        shapes.put(Direction.UP, UP_SHAPE);
        shapes.put(Direction.DOWN, DOWN_SHAPE);
    });
    
    protected static final Direction[] DIRECTIONS = Direction.values();
    private final ImmutableMap<BlockState, VoxelShape> shapes;
    private final boolean hasAllHorizontalDirections;
    private final boolean canMirrorX;
    private final boolean canMirrorZ;

    public LichenBlock(Properties properties) {
        super(properties);
        this.setDefaultState(withAllDirections(this.getStateContainer()).with(WATERLOGGED, false));
        this.shapes = getShapesForStates(this, LichenBlock::getShapeForState);
        this.hasAllHorizontalDirections = Plane.HORIZONTAL.getDirectionValues().allMatch(this::canHaveDirection);
        this.canMirrorX = Plane.HORIZONTAL.getDirectionValues().filter(Axis.X).filter(this::canHaveDirection).count() % 2L == 0L;
        this.canMirrorZ = Plane.HORIZONTAL.getDirectionValues().filter(Axis.Z).filter(this::canHaveDirection).count() % 2L == 0L;
    }

    protected boolean canHaveDirection(Direction direction) {
        return true;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        for(Direction direction : DIRECTIONS) {
            if (this.canHaveDirection(direction)) {
                builder.add(getProperty(direction));
            }
        }
        builder.add(WATERLOGGED);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if (!hasAnyDirection(stateIn)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return (hasDirection(stateIn, facing) && !canAttachTo(worldIn, facing, facingPos, facingState))
                    ? disableDirection(stateIn, getProperty(facing)) : stateIn;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes.get(state);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        boolean isValidPosition = false;
        for(Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                BlockPos blockPos = pos.offset(direction);
                if (!canAttachTo(worldIn, direction, blockPos, worldIn.getBlockState(blockPos))) {
                    return false;
                }
                isValidPosition = true;
            }
        }
        return isValidPosition;
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        //return state.getMaterial().isReplaceable() && (useContext.getItem().isEmpty() || useContext.getItem().getItem() != this.asItem());
        return !useContext.getItem().getItem().equals(ModBlocks.LICHEN_BLOCK.get().asItem())
                || isNotFullBlock(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        return Arrays.stream(context.getNearestLookingDirections())
                .map(direction -> this.withDirection(state, world, pos, direction)).filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    public BlockState withDirection(BlockState state, IBlockReader worldIn, BlockPos pos, Direction direction) {
        if (!this.canHaveDirection(direction)) {
            return null;
        } else {
            BlockState blockState;
            if (state.getBlock() == this) {
                if (hasDirection(state, direction)) {
                    return null;
                }
                blockState = state;
            } else if (this.isWaterlogged() && isFluidEqualAndStill(state.getFluidState(), Fluids.WATER)) {
                blockState = this.getDefaultState().with(BlockStateProperties.WATERLOGGED, true);
            } else {
                blockState = this.getDefaultState();
            }

            BlockPos blockPos = pos.offset(direction);
            return canAttachTo(worldIn, direction, blockPos, worldIn.getBlockState(blockPos))
                    ? blockState.with(getProperty(direction), true) : null;
        }
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return !this.hasAllHorizontalDirections ? state : this.mirror(state, rot::rotate);
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        if (mirrorIn == Mirror.FRONT_BACK && !this.canMirrorX) {
            return state;
        } else {
            return mirrorIn == Mirror.LEFT_RIGHT && !this.canMirrorZ ? state : this.mirror(state, mirrorIn::mirror);
        }
    }

    private BlockState mirror(BlockState state, Function<Direction, Direction> mirror) {
        BlockState blockState = state;
        for(Direction direction : DIRECTIONS) {
            if (this.canHaveDirection(direction)) {
                blockState = blockState.with(getProperty(mirror.apply(direction)), state.get(getProperty(direction)));
            }
        }
        return blockState;
    }

    public boolean trySpreadRandomly(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        List<Direction> directions = Arrays.asList(DIRECTIONS);
        Collections.shuffle(directions, random);
        return directions.stream().filter(from -> hasDirection(state, from)).anyMatch(to -> this.trySpreadRandomly(state, world, pos, to, random, false));
    }

    public boolean trySpreadRandomly(BlockState state, IWorld worldIn, BlockPos pos, Direction from, Random random, boolean postProcess) {
        List<Direction> directions = Arrays.asList(DIRECTIONS);
        Collections.shuffle(directions, random);
        return directions.stream().anyMatch(direction -> this.trySpreadTo(state, worldIn, pos, from, direction, postProcess));
    }

    public boolean trySpreadTo(BlockState state, IWorld worldIn, BlockPos pos, Direction from, Direction to, boolean postProcess) {
        Optional<Pair<BlockPos, Direction>> spreadLocation = this.getSpreadLocation(state, worldIn, pos, from, to);
        if (spreadLocation.isPresent()) {
            Pair<BlockPos, Direction> location = spreadLocation.get();
            return this.addDirection(worldIn, location.getFirst(), location.getSecond(), postProcess);
        } else {
            return false;
        }
    }

    protected boolean canSpread(BlockState state, IBlockReader worldIn, BlockPos pos, Direction from) {
        return Stream.of(DIRECTIONS).anyMatch(to -> this.getSpreadLocation(state, worldIn, pos, from, to).isPresent());
    }

    private Optional<Pair<BlockPos, Direction>> getSpreadLocation(BlockState state, IBlockReader worldIn, BlockPos pos, Direction from, Direction to) {
        if (to.getAxis() == from.getAxis() || !hasDirection(state, from) || hasDirection(state, to)) {
            return Optional.empty();
        } else if (this.canSpreadTo(worldIn, pos, to)) {
            return Optional.of(Pair.of(pos, to));
        } else {
            BlockPos toPos = pos.offset(to);
            if (this.canSpreadTo(worldIn, toPos, from)) {
                return Optional.of(Pair.of(toPos, from));
            } else {
                BlockPos fromPos = toPos.offset(from);
                Direction direction = to.getOpposite();
                return this.canSpreadTo(worldIn, fromPos, direction) ? Optional.of(Pair.of(fromPos, direction)) : Optional.empty();
            }
        }
    }

    private boolean canSpreadTo(IBlockReader worldIn, BlockPos pos, Direction direction) {
        BlockState state = worldIn.getBlockState(pos);
        if (!this.canGrowIn(state)) {
            return false;
        } else {
            BlockState directionalState = this.withDirection(state, worldIn, pos, direction);
            return directionalState != null;
        }
    }

    private boolean addDirection(IWorld worldIn, BlockPos pos, Direction direction, boolean postProcess) {
        BlockState state = worldIn.getBlockState(pos);
        BlockState directionalState = this.withDirection(state, worldIn, pos, direction);
        if (directionalState != null) {
            if (postProcess) {
                worldIn.getChunk(pos).markBlockForPostprocessing(pos);
            }
            return worldIn.setBlockState(pos, directionalState, 2);
        } else {
            return false;
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return Stream.of(DIRECTIONS).anyMatch(direction -> this.canSpread(state, worldIn, pos, direction.getOpposite()));
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    private boolean canGrowIn(BlockState state) {
        return state.isAir() || (state.getBlock() == this) || (state.getBlock() == Blocks.WATER) && state.getFluidState().isSource();
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.trySpreadRandomly(state, worldIn, pos, rand);
    }

    private static boolean hasDirection(BlockState state, Direction direction) {
        BooleanProperty property = getProperty(direction);
        return state.hasProperty(property) && state.get(property);
    }

    private static boolean canAttachTo(IBlockReader worldIn, Direction direction, BlockPos pos, BlockState state) {
        return Block.doesSideFillSquare(state.getShape(worldIn, pos), direction.getOpposite());
    }

    private boolean isWaterlogged() {
        return this.getStateContainer().getProperties().contains(BlockStateProperties.WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    private static BlockState disableDirection(BlockState state, BooleanProperty direction) {
        BlockState blockState = state.with(direction, false);
        return hasAnyDirection(blockState) ? blockState : Blocks.AIR.getDefaultState();
    }

    public static BooleanProperty getProperty(Direction direction) {
        return FACING_PROPERTIES.get(direction);
    }

    private static BlockState withAllDirections(StateContainer<Block, BlockState> stateContainer) {
        BlockState state = stateContainer.getBaseState();
        for(BooleanProperty property : FACING_PROPERTIES.values()) {
            if (state.hasProperty(property)) {
                state = state.with(property, false);
            }
        }
        return state;
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape shape = VoxelShapes.empty();
        for(Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                shape = VoxelShapes.or(shape, SHAPES_FOR_DIRECTIONS.get(direction));
            }
        }
        return shape.isEmpty() ? VoxelShapes.fullCube() : shape;
    }

    protected static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch(direction -> hasDirection(state, direction));
    }

    private static boolean isNotFullBlock(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch(direction -> !hasDirection(state, direction));
    }

    // TODO not certain on "is source", but getFluid is right
    private static boolean isFluidEqualAndStill(FluidState state, Fluid fluid) {
        return state.getFluid() == fluid && state.getFluid().isSource(state);
    }

    private static ImmutableMap<BlockState, VoxelShape> getShapesForStates(Block block, Function<BlockState, VoxelShape> function) {
        return block.getStateContainer().getValidStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), function));
    }
}
