package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.BasementRegionNoise;

public class NoiseInit {
    public static boolean configured = false;
    private static int WORLD_SEED = 0;
    public static final int BUILD_LIMIT = 256;

    public static int getWorldSeed() { return WORLD_SEED; }

    public static void initNoise(long seed) {
        WORLD_SEED = (int)seed;
        BasementRegionNoise.initNoise(WORLD_SEED);
        configured = true;
    }
}
