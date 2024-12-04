package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoGroup;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ServerConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class StoneGeoBlock extends BaseGeoBlock {
    public StoneGeoBlock(Properties properties, GeoType geoType, OreType oreType, Grade grade) {
        super(properties, geoType, oreType, grade);
    }

    // Stone block hardness can be affected by both a global multiplier and depth-based scale
    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos) * ServerConfig.STONE_HARD_MULT.get();
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        if (!getGeologyType().getGeoGroup().equals(GeoGroup.DETRITUS)) {
            int y = pos.getY();
            int y0 = ServerConfig.STONE_DEPTH_MULT_START.get();
            if (y <= y0) { // Increases linearly starting at y = y0
                f *= (1 + (ServerConfig.STONE_DEPTH_MULT.get() - 1) * (((float)y0 - y) / y0));
            }
        }
        return player.getDigSpeed(state, pos) / f / (float)i;
    }
}
