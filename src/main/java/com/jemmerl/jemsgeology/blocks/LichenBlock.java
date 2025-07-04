package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LichenBlock extends BaseCoatingBlock implements IGrowable, IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public LichenBlock(Properties properties) {
        super(properties);
        this.setDefaultState(withAllDirections(this.getStateContainer()).with(WATERLOGGED, false));
    }

    @Override
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

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        //return state.getMaterial().isReplaceable() && (useContext.getItem().isEmpty() || useContext.getItem().getItem() != this.asItem());
        return !useContext.getItem().getItem().equals(ModBlocks.LICHEN_BLOCK.get().asItem())
                || isNotFullBlock(state);
    }

    @Override
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

    private boolean isWaterlogged() {
        return this.getStateContainer().getProperties().contains(BlockStateProperties.WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    // TODO not certain on "is source", but getFluid is right
    private static boolean isFluidEqualAndStill(FluidState state, Fluid fluid) {
        return state.getFluid() == fluid && state.getFluid().isSource(state);
    }
}
