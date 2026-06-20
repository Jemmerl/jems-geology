package com.jemmerl.jemsgeology.world.geobuilding.georegions.noise;

import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.Pair;
import com.jemmerl.jemsgeology.util.noise.FastNoiseLite;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;

import java.util.HashMap;

// Used to generate the actual strata themselves
public class InstStrataNoise {

    // Seed-Age Caching
    private static final int STRATA_AGE_CACHE_SIZE = 5; // TODO size? 3 - 5 entries?
    private final Int2IntLinkedOpenHashMap strataAgeLRU;



    // TODO note: purge for each different "region" used to generate strata, in the rare event of duplicate
    //  cell value. "PREVIOUS" list testing shows strata values are not duplicated, at least only extremely rarely.
    private final HashMap<Float, Integer> cachedCenters = new HashMap<>();

    private FastNoiseLite ageCellIDNoise = new FastNoiseLite();
    private FastNoiseLite ageCellDistNoise = new FastNoiseLite();
    private FastNoiseLite ageDomainWarp = new FastNoiseLite();

    private final int seed;
    private final int minAge;
    private final int maxAge;

    public InstStrataNoise(int seed, int minAge, int maxAge) {
        this.seed = seed;
        this.minAge = minAge;
        this.maxAge = maxAge;
        instInitNoise(seed);
        strataAgeLRU = initStrataAgeCache();
    }


    // Returns the individual Strata Seed and Age
    public Pair<Integer, Integer> getStrataIdentifier(int x, int y, int z) {
        int strataID = Math.round(getStrataNoise(x, y, z, false)) - seed;

        int age = strataAgeLRU.getAndMoveToFirst(strataID);
        if (age == -1) {
            age = getStrataAge(x, y, z);
            // get the region from the seed, since  not in LRU
            put(strataID, age);
        }
        return new Pair<>(strataID, age);
    }


    private int getStrataAge(int x, int y, int z) {
        float noise = getStrataNoise(y, false);
        int cellCenter = (cachedCenters.containsKey(noise)) ?
                cachedCenters.get(noise): getCellularCenter(y, noise);

        //////////////////////////////////////////////////////////
        //                   AGE CALCULATION                    //
        //////////////////////////////////////////////////////////

        // Todo BOUNDED??? AgE ALGORITHMA AAAAAAA

//        float agePerc = Math.max(0f, Math.min(1f, 1f - (cellularCenter / )); // can make this nonlinear for more variation too

        float ageCoarse =  (cellCenter + (1.25f * (float)Math.sin(cellCenter*0.75f)));
        float agePerc = 1f - (ageCoarse / (float)NoiseInit.BUILD_LIMIT);
        return (int)Math.floor(minAge + (agePerc * (maxAge-minAge)));
    }


    private int getCellularCenter(int yIn, float noiseIn) {
        //////////////////////////////////////////////////////////////////////
        //      Determine direction and check immediate adjacent values     //
        //////////////////////////////////////////////////////////////////////
        // If current (+ve direction) is further away from input, or the wrong region, check the other direction.
        int dir = 1;
        float prevDist = getStrataNoise(yIn, true);
        float currDist = getStrataNoise(yIn+1, true);
        float currNoise = getStrataNoise(yIn+1, false);
        if ((prevDist < currDist) || (currNoise != noiseIn)) {
            // If new current (-ve direction) is further (or equal) from input, or the wrong region,
            // then current is center. The "or equal" part catches rare cases where the true center is
            // exactly between two integers.
            currDist = getStrataNoise(yIn-1, true);
            currNoise = getStrataNoise(yIn-1, false);
            if ((prevDist <= currDist) || (currNoise != noiseIn)) {
                cachedCenters.put(noiseIn, yIn);
                return yIn;
            }
            // Else, continue checking in the -ve direction
            dir = -1;
        }
        // Else, continue checking in the +ve direction

        //////////////////////////////////////////////////
        //      Rough search in chosen direction        //
        //////////////////////////////////////////////////
        final int ROUGH_DIST = 5;
        int prevCheckX = yIn+dir;
        prevDist = currDist;
        int currCheckY = prevCheckX + (ROUGH_DIST * dir);
        currDist = getStrataNoise(currCheckY, true);
        currNoise = getStrataNoise(currCheckY, false);
        // While the current distance is closer (and in region bounds) than the previous, keep checking that direction.
        while ((currDist < prevDist) && (currNoise == noiseIn)) {
            // Check for u-curve "rough overshot center but still better than prev" issue.
            // Before continuing with rough, check if the slope is actually decreasing in dir.
            if (currDist > getStrataNoise(currCheckY-dir, true)) {
                break;
            }

            prevDist = currDist;
            prevCheckX = currCheckY;
            currCheckY += (ROUGH_DIST * dir);
            currDist = getStrataNoise(currCheckY, true);
            currNoise = getStrataNoise(currCheckY, false);
        }
        // As soon as the loop ends, we know the center must lie within [currX-ROUGH_DIST, currX)
        // The value for "currX-ROUGH_DIST" is still stored in "prevDist", as the while loop did not execute.

        ////////////////////////////////////////////////////
        //      Fine search in determined interval        //
        ////////////////////////////////////////////////////
        currCheckY = prevCheckX+dir;
        currDist = getStrataNoise(currCheckY, true);
        currNoise = getStrataNoise(currCheckY, false);
        // While the current distance is closer (and in region bounds) than the previous, keep checking that direction.
        while ((currDist < prevDist) && (currNoise == noiseIn)) {
            prevDist = currDist;
            prevCheckX = currCheckY;
            currCheckY += dir;
            currDist = getStrataNoise(currCheckY, true);
            currNoise = getStrataNoise(currCheckY, false);
        }
        // When this loop fails to execute, then "currCheckY-dir" is the center point.

        cachedCenters.put(noiseIn, prevCheckX);
        return prevCheckX;
    }


    private float getStrataNoise(int y, boolean dist) { return getStrataNoise(0, y, 0, dist); }

    private float getStrataNoise(int x, int y, int z, boolean dist) {
        FastNoiseLite.Vector1 vector1 = new FastNoiseLite.Vector1(y);
        ageDomainWarp.DomainWarp(vector1);
        return dist ? ageCellDistNoise.GetNoise(vector1.x) : ageCellIDNoise.GetNoise(vector1.x);
    }

    private int getStrataSeed(int x, int y, int z) {
        return NoiseInit.getWorldSeed() + Math.round(getStrataNoise(x, y, z, true) * 100000);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////
    ///                                 INIT / CACHE METHODS                                ///
    ///////////////////////////////////////////////////////////////////////////////////////////

    private void put(int strataSeed, int strataAge) {
        if (strataAgeLRU.size() >= STRATA_AGE_CACHE_SIZE) {
            strataAgeLRU.removeLastInt();
        }
        strataAgeLRU.putAndMoveToFirst(strataSeed, strataAge);
    }

    private static Int2IntLinkedOpenHashMap initStrataAgeCache() {
        Int2IntLinkedOpenHashMap c = new Int2IntLinkedOpenHashMap(STRATA_AGE_CACHE_SIZE);
        c.defaultReturnValue(-1);
        return c;
    }

    public void instInitNoise(long seed) {
        ageCellIDNoise = new FastNoiseLite(((int)seed) - 542474);
        buildStrataNoise(ageCellIDNoise, true);

        ageCellDistNoise = new FastNoiseLite(((int)seed) + 4157);
        buildStrataNoise(ageCellDistNoise, false);

        ageDomainWarp = new FastNoiseLite(((int)seed) + 40312102);
        ageDomainWarp.SetDomainWarpType(FastNoiseLite.DomainWarpType.BasicGrid);
        ageDomainWarp.SetFrequency(0.03f);
        ageDomainWarp.SetDomainWarpAmp(20f);
    }

    // Used to propagate both cell noise objects with the same values, so I do not miss one when changing manually.
    private void buildStrataNoise(FastNoiseLite noiseIn, boolean returnCellVal) {
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
