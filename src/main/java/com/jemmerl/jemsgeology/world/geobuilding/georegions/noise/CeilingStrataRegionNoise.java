package com.jemmerl.jemsgeology.world.geobuilding.georegions.noise;

import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

// Used to generate the overarching unique strata formation areas
public class CeilingStrataRegionNoise {


//    public static int getBasementRegionSeed(int x, int z) {
//        return NoiseInit.getWorldSeed() + Math.round(getRawBasementRegionNoise(x, z, false) * 100000);
//    }
//
//    public static float getBasementRegionDist(int x, int z) {
//        return getRawBasementRegionNoise(x, z, true);
//    }
//
//    private static float getRawBasementRegionNoise(int x, int z, boolean dist) {
//        FastNoiseLite.Vector2 v2 = new FastNoiseLite.Vector2(x, z);
//        basementWarp.DomainWarp(v2);
//        return (dist ? basementDistNoise : basementCellNoise).GetNoise(v2.x, v2.y);
//    }
    

    public static void initNoise(long seed) {

    }
}
