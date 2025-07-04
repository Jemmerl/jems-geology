package com.jemmerl.jemsgeology.geology;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ClimateProvider {



    // values range from 0f to 1f, pulled from biome values by default then mapped to that range based on min and max
    // possible values

    // the provider or methods should be overridable for compat into other worldgen mods

    // convolution should be done to blend values

    // avg temp
    public float getAvgTemp(IWorld world, BlockPos pos) {
        return biomeTemp(world, pos);
    }


    // avg rainfall
    public float getAvgRainfall(IWorld world, BlockPos pos) {
        return biomeRainfall(world, pos);
    }

    // Vanilla getters

    // TODO use biome average, or use local value? Leaning towards average, will experiment after implementation done
    // Returns -0.5f to 2f
    private float biomeTemp(IWorld world, BlockPos pos) {
        return world.getBiome(pos).getTemperature();
    }

    // Returns 0f-1f
    private float biomeRainfall(IWorld world, BlockPos pos) {
        return world.getBiome(pos).getDownfall();
    }
}
