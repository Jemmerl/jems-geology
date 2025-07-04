package com.jemmerl.jemsgeology.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class BaseCoatingBlock extends Block {
    private static final VoxelShape UP_SHAPE = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape DOWN_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = SixWayBlock.FACING_TO_PROPERTY_MAP;
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

    public BaseCoatingBlock(Properties properties) {
        super(properties);
        this.shapes = getShapesForStates(this, BaseCoatingBlock::getShapeForState);
        this.hasAllHorizontalDirections = Direction.Plane.HORIZONTAL.getDirectionValues().allMatch(this::canHaveDirection);
        this.canMirrorX = Direction.Plane.HORIZONTAL.getDirectionValues().filter(Direction.Axis.X).filter(this::canHaveDirection).count() % 2L == 0L;
        this.canMirrorZ = Direction.Plane.HORIZONTAL.getDirectionValues().filter(Direction.Axis.Z).filter(this::canHaveDirection).count() % 2L == 0L;
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
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes.get(state);
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

    protected boolean addDirection(IWorld worldIn, BlockPos pos, Direction direction, boolean postProcess) {
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

    protected static boolean hasDirection(BlockState state, Direction direction) {
        BooleanProperty property = getProperty(direction);
        return state.hasProperty(property) && state.get(property);
    }

    protected static boolean canAttachTo(IBlockReader worldIn, Direction direction, BlockPos pos, BlockState state) {
        return Block.doesSideFillSquare(state.getShape(worldIn, pos), direction.getOpposite());
    }

    protected static BlockState disableDirection(BlockState state, BooleanProperty direction) {
        BlockState blockState = state.with(direction, false);
        return hasAnyDirection(blockState) ? blockState : Blocks.AIR.getDefaultState();
    }

    public static BooleanProperty getProperty(Direction direction) {
        return FACING_PROPERTIES.get(direction);
    }

    protected static BlockState withAllDirections(StateContainer<Block, BlockState> stateContainer) {
        BlockState state = stateContainer.getBaseState();
        for(BooleanProperty property : FACING_PROPERTIES.values()) {
            if (state.hasProperty(property)) {
                state = state.with(property, false);
            }
        }
        return state;
    }

    protected static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch(direction -> hasDirection(state, direction));
    }

    protected static boolean isNotFullBlock(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch(direction -> !hasDirection(state, direction));
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

    private static ImmutableMap<BlockState, VoxelShape> getShapesForStates(Block block, Function<BlockState, VoxelShape> function) {
        return block.getStateContainer().getValidStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), function));
    }
}
