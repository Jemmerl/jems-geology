package com.jemmerl.jemsgeology.world.geobuilding.georegions.noise;

import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

// Used to generate the regions basement geology can be in.
public class BasementRegionNoise {
    private static FastNoiseLite basementCellNoise = new FastNoiseLite();
    private static FastNoiseLite basementDistNoise = new FastNoiseLite();
    private static FastNoiseLite basementWarp = new FastNoiseLite();

    private static FastNoiseLite basementBoundaryRegional = new FastNoiseLite();
    private static FastNoiseLite basementBoundaryLocal = new FastNoiseLite();
    private static FastNoiseLite basementBoundaryLocalFluct = new FastNoiseLite();

    private static FastNoiseLite basementStrataRegionNoise = new FastNoiseLite();
    private static FastNoiseLite basementStrataWarp = new FastNoiseLite();

    private static final int BASEMENT_BASE_HEIGHT = 25;

    public static int getBasementRegionSeed(int x, int z) {
        return NoiseInit.getWorldSeed() + Math.round(getRawBasementRegionNoise(x, z, false) * 100000);
    }

    public static float getBasementRegionDist(int x, int z) {
        return getRawBasementRegionNoise(x, z, true);
    }

    private static float getRawBasementRegionNoise(int x, int z, boolean dist) {
        FastNoiseLite.Vector2 v2 = new FastNoiseLite.Vector2(x, z);
        basementWarp.DomainWarp(v2);
        return (dist ? basementDistNoise : basementCellNoise).GetNoise(v2.x, v2.y);
    }

    public static int getBasementDepth(int x, int z) {
        float localFluctNoise = (basementBoundaryLocalFluct.GetNoise(x, z) + 1f) * 0.5f;
        localFluctNoise *= localFluctNoise * localFluctNoise;
        return Math.round(BASEMENT_BASE_HEIGHT
                + (80 * basementBoundaryRegional.GetNoise(x, z))
                + (20 * basementBoundaryLocal.GetNoise(x, z) * localFluctNoise));
    }

    public static int getBasementStrataSeed(int x, int y, int z) {
        FastNoiseLite.Vector3 v3 = new FastNoiseLite.Vector3(x, y, z);
        basementStrataWarp.DomainWarp(v3);
        return NoiseInit.getWorldSeed() + Math.round(basementStrataRegionNoise.GetNoise(v3.x, v3.y, v3.z) * 1000000);
    }

    public static void initNoise(long seed) {
        // Basement type region noise
        basementCellNoise = new FastNoiseLite(((int)seed) + 436102);
        basementCellNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        basementCellNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
        basementCellNoise.SetFractalType(FastNoiseLite.FractalType.None);
        basementCellNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        basementCellNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        basementCellNoise.SetCellularJitter(0.85f);
        basementCellNoise.SetFrequency(0.002f);

        basementDistNoise = new FastNoiseLite(((int)seed) + 436102);
        basementDistNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        basementDistNoise.SetRotationType3D(FastNoiseLite.RotationType3D.None);
        basementDistNoise.SetFractalType(FastNoiseLite.FractalType.None);
        basementDistNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        basementDistNoise.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance);
        basementDistNoise.SetCellularJitter(0.85f);
        basementDistNoise.SetFrequency(0.002f);

        basementWarp = new FastNoiseLite(((int)seed) - 3449);
        basementWarp.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
        basementWarp.SetRotationType3D(FastNoiseLite.RotationType3D.None);
        basementWarp.SetDomainWarpAmp(80f);
        basementWarp.SetFrequency(0.007f);
        basementWarp.SetFractalType(FastNoiseLite.FractalType.None);

        // Basement weathering height noise
        basementBoundaryRegional = new FastNoiseLite((int)seed);
        basementBoundaryRegional.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
        basementBoundaryRegional.SetFrequency(0.0005f);

        basementBoundaryLocal = new FastNoiseLite((int)seed - 1088822);
        basementBoundaryLocal.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        basementBoundaryLocal.SetFrequency(0.0150f);

        basementBoundaryLocalFluct = new FastNoiseLite((int)seed + 10329);
        basementBoundaryLocalFluct.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
        basementBoundaryLocalFluct.SetFrequency(0.001f);

        // Basement strata region noise
        basementStrataRegionNoise = new FastNoiseLite((int)seed - 645357447);
        basementStrataWarp = new FastNoiseLite((int)seed + 64533484);
    }
}
