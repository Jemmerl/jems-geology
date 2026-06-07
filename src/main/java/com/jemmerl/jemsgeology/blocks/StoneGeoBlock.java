package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.ServerConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class StoneGeoBlock extends BaseGeoBlock {
    public StoneGeoBlock(Properties properties, GeoType geoType, OreType oreType, OreGrade oreGrade) {
        super(properties, geoType, oreType, oreGrade);
    }

    // Stone block hardness can be affected by both a global multiplier and depth-based scale
    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos) * ServerConfig.STONE_HARD_MULT.get();
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        if (!getGeoType().getGeoGroup().isRegolith()) {
            int y = pos.getY();
            int y0 = ServerConfig.STONE_DEPTH_MULT_START.get();
            if (y <= y0) { // Increases linearly starting at y = y0
                f *= (1 + (ServerConfig.STONE_DEPTH_MULT.get() - 1) * (((float)y0 - y) / y0));
            }
        }
        return player.getDigSpeed(state, pos) / f / (float)i;
    }

//    @Override
//    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
//        return checkIfIsolated(currentPos, worldIn) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
//    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);

        // todo add config option to disable?
        //  kinda! option to do this instead of COLLAPSING
        //  maybe roll into "enable collapses" option
        if (checkIfIsolated(pos, worldIn)) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState()); //todo sound effects when breaking?
            ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(state.getBlock(), 1));
            worldIn.addEntity(itemEntity);
        }
    }


    //TODO isSolidSide instead of isSolid?
    //  isSideSolid(faceState, worldIn, offsetPos, face.getOpposite()))
    private boolean checkIfIsolated(BlockPos pos, IWorld world) {
        if (world.getBlockState(pos.down()).isSolid()) return false;
        if (world.getBlockState(pos.up()).isSolid()) return false;
        if (world.getBlockState(pos.north()).isSolid()) return false;
        if (world.getBlockState(pos.south()).isSolid()) return false;
        if (world.getBlockState(pos.east()).isSolid()) return false;
        return !world.getBlockState(pos.west()).isSolid();
    }
}
