package com.jemmerl.jemsgeology.world.geobuilding.georegions.noise;

import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

// Used to generate the regions basement geology can be in.
public class BasementRegionNoise {
    private static FastNoiseLite basementNoise = new FastNoiseLite();
    private static FastNoiseLite basementWarp = new FastNoiseLite();

    public static float getRegionCellNoise(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        basementWarp.DomainWarp(v3);
        return basementNoise.GetNoise(v3.x, v3.y*0.28125f, v3.z);
    }

    public static int getRegionalSeed(int x, int y, int z) {
        return NoiseInit.getWorldSeed() + Math.round(getRegionCellNoise(x, y, z) * 10000);
    }

    public static void initNoise(long seed) {
        basementNoise = new FastNoiseLite(((int)seed) + 436102);
        basementNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        basementNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
        basementNoise.SetFractalType(FastNoiseLite.FractalType.None);
        basementNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        basementNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        basementNoise.SetCellularJitter(0.85f);
        basementNoise.SetFrequency(0.002f);

        basementWarp = new FastNoiseLite(((int)seed) - 3449);
        basementWarp.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2Reduced);
        basementWarp.SetRotationType3D(FastNoiseLite.RotationType3D.None);
        basementWarp.SetDomainWarpAmp(90f);
        basementWarp.SetFrequency(0.002f);
        basementWarp.SetFractalType(FastNoiseLite.FractalType.None);
    }
}
