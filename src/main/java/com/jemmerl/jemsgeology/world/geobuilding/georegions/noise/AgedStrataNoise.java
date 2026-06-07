package com.jemmerl.jemsgeology.world.geobuilding.georegions.noise;

import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;

public class AgedStrataNoise {
    private static FastNoiseLite ageCellValNoise = new FastNoiseLite();
    private static FastNoiseLite ageCellDistNoise = new FastNoiseLite();
    private static FastNoiseLite ageDomainWarp = new FastNoiseLite();


    private static float getStrataNoise(int x, int y, int z, boolean strataVal) {
        FastNoiseLite.Vector1 vector1 = new FastNoiseLite.Vector1(y);
        ageDomainWarp.DomainWarp(vector1);
        return strataVal ? ageCellDistNoise.GetNoise(vector1.x) : ageCellValNoise.GetNoise(vector1.x);
    }

    public static int getStrataSeed(int x, int y, int z) {
        return NoiseInit.getWorldSeed() + Math.round(getStrataNoise(x, y, z, true) * 100000);
    }

    public static void initNoise(long seed) {
        ageCellValNoise = new FastNoiseLite(((int)seed) - 542474);
        buildStrataNoise(ageCellValNoise, true);

        ageCellDistNoise = new FastNoiseLite(((int)seed) + 4157);
        buildStrataNoise(ageCellDistNoise, false);

        ageDomainWarp = new FastNoiseLite(((int)seed) + 40312102);
        ageDomainWarp.SetDomainWarpType(FastNoiseLite.DomainWarpType.BasicGrid);
        ageDomainWarp.SetFrequency(0.03f);
        ageDomainWarp.SetDomainWarpAmp(20f);
    }

    // Used to propagate both cell noise objects with the same values, so I do not miss one when changing manually.
    private static void buildStrataNoise(FastNoiseLite noiseIn, boolean returnCellVal) {
        noiseIn.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        noiseIn.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Euclidean);
        if (returnCellVal) {
            noiseIn.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
        } else {
            noiseIn.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance);
        }

        noiseIn.SetCellularJitter(0f);
        noiseIn.SetRotationType3D(FastNoiseLite.RotationType3D.None);
        noiseIn.SetFractalType(FastNoiseLite.FractalType.None);
        noiseIn.SetFrequency(0.06f);
    }
}
