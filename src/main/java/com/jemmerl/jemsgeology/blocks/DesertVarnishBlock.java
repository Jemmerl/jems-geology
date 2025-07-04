package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class DesertVarnishBlock extends BaseCoatingBlock {

    public DesertVarnishBlock(Properties properties) {
        super(properties);
        this.setDefaultState(withAllDirections(this.getStateContainer()));
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currPos, BlockPos facingPos) {
        if (!hasAnyDirection(stateIn)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return (hasDirection(stateIn, facing) && !canAttachTo(worldIn, facing, facingPos, facingState))
                    ? disableDirection(stateIn, getProperty(facing)) : stateIn;
        }
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        //return state.getMaterial().isReplaceable() && (useContext.getItem().isEmpty() || useContext.getItem().getItem() != this.asItem());
        return !useContext.getItem().getItem().equals(ModBlocks.DESERT_VARNISH_BLOCK.get().asItem())
                || isNotFullBlock(state);
    }
}
