//package com.jemmerl.jemsgeology.world.geobuilding.georegions;
//
//import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
//import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.AgedStrataNoise;
//import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.AbstractBasementRegion;
//import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.SingleBasementRegion;
//import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
//import net.minecraft.util.math.BlockPos;
//
//import javax.annotation.Nonnull;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//
//// Used for generating rock strata with an age. Can be used for sedimentary and basement alike.
//public class StrataGenerator {
//
//    // TODO note: purge for each different "region" used to generate strata, in the rare event of duplicate
//    //  cell value. "PREVIOUS" list testing shows strata values are not duplicated, at least only extremely rarely.
//    private static final HashMap<Float, Integer> cachedCenters = new HashMap<>();
//
//    // TODO if i reuse a lot of code exactly, do an abstract class. otherwise, there is no contract that needs held.
//    //  these are all hardcoded built in after all
//
//    // LRU cache with 3 - 5 entries
//    private static final int CACHE_SIZE = 5; // TODO size?
//
//    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> strataLRU = initCache();
//
//    public static UnbakedGeo getGeoWrapper(int x, int y, int z) {
//        int regionSeed = AgedStrataNoise.getRegionalSeed(x, y, z); // TODO this needs instantiated more than basement does
//        // actually, this whole method here works differently.
//
//        AbstractBasementRegion region = strataLRU.getAndMoveToFirst(regionSeed);
//        if (region == null) {
//            region = new SingleBasementRegion(regionSeed);
//            // get the region from the seed, since  not in LRU
//            put(regionSeed, region);
//        }
//
//        return region.getUnbakedGeo(x, y, z);
//    }
//
//    public static UnbakedGeo getGeoWrapper(@Nonnull BlockPos blockPos) {
//        return getGeoWrapper(blockPos.getX(), blockPos.getY(), blockPos.getZ());
//    }
//
//
//
//
//
//
//
//
//    private static void put(int regionSeed, AbstractBasementRegion region) {
//        if (strataLRU.size() >= CACHE_SIZE) {
//            strataLRU.removeLast();
//        }
//        strataLRU.putAndMoveToFirst(regionSeed, region);
//    }
//
//    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> initCache() {
//        Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> c = new Int2ObjectLinkedOpenHashMap<>(CACHE_SIZE);
////        c.defaultReturnValue(-1);
//        return c;
//    }
//
//
//    @Override
//    public void strata(BufferedImage image, int currZ) {
//        int X_MIN = image.getMinX();
//        int X_MAX = image.getWidth();
//        int Y_MIN = image.getMinY();
//        int Y_MAX = image.getHeight();
//
//        boolean flag = true;
//        int currAge = 4001;
//
//        ArrayList<Float> PREVIOUS = new ArrayList<>();
//
//        HashMap<Integer, Combo> agePoints = new HashMap<>();
//        HashMap<Integer, Integer> centerPoints = new HashMap<>();
//        HashSet<Integer> checkCentPoints = new HashSet<>();
//
//        // Clear cached center values for new seed (or region)
//        cachedCenters.clear();
//
//        for (int iy = Y_MIN; iy < Y_MAX; iy++) {
//            int y = iy;
//
//            //////////////////////////////////////////////////////////
//            //                  GEN NOISE / CENTER                  //
//            //////////////////////////////////////////////////////////
//
////            float noise = ageNoise.GetNoise(y);
////            float cellularCenter = ageNoise.get1DCellularCenter(y);
//
//            float noise = getNoise(y, false);
//            int cellularCenter = (cachedCenters.containsKey(noise)) ?
//                    cachedCenters.get(noise): getCellularCenter(y, noise);
//
////            System.out.println(cellularCenter + ", unwarped: " + ageNoise.get1DCellularCenter(y));
////            {
////                FastNoiseLite.Vector1 v1 = new FastNoiseLite.Vector1(cellularCenter);
////                ageDomainWarp.DomainWarp(v1);
////                cellularCenter = v1.x;
////            }
////            System.out.println(cellularCenter);
//
//
//
//            //////////////////////////////////////////////////////////
//            //                   AGE CALCULATION                    //
//            //////////////////////////////////////////////////////////
//
//            float agePerc = Math.max(0f, Math.min(1f, 1f - (cellularCenter / (float)Y_MAX))); // can make this nonlinear for more variation too
//            int age = Math.round(agePerc * 4000);
////            System.out.println(cellularCenter);
//
//            if (flag) {
//                if (currAge > age) {
//                    currAge = age;
//                } else if (currAge < age) {
//                    flag = false;
//                    System.out.println("Failed");
//                    System.out.println("Prev: " + currAge + ", Curr: " + age);
//                }
//            }
//            float agePercInv = 1f - agePerc;
//
//
//
//
//            //////////////////////////////////////////////////////////
//            //                  COLOR / IMAGE CALC                  //
//            //////////////////////////////////////////////////////////
//
////                int color = UtilMethods.getColor(ageBucket).getRGB();
//            int color = UtilMethods.remapColor(true, (agePerc*2f)-1f).getRGB();
////            float noiseDist = getNoise(x, true);
//            color = UtilMethods.remapColor(true, noise).getRGB();
//
////            Integer centInt = Math.round(cellularCenter);
//////            agePoints.putIfAbsent(centInt, new Combo(y, agePerc));
//////            if (!checkCentPoints.contains(centInt)) {
//////                checkCentPoints.add(centInt);
//////                centerPoints.putIfAbsent(y, UtilMethods.remapColor(true, noise).getRGB());
//////            }
////
////            FastNoiseLite.Vector1 vector1 = new FastNoiseLite.Vector1(y);
////            ageDomainWarp.DomainWarp(vector1);
////            if (Math.round(vector1.x) == centInt) {
////                centerPoints.putIfAbsent(y, UtilMethods.remapColor(true, noise).getRGB());
////            }
//
//            for (int ix = X_MIN; ix < X_MAX; ix++) {
//                image.setRGB((X_MAX - ix - 1), (Y_MAX - iy - 1), color);
//                //UtilMethods.remapColor(true, noise).getRGB()
//            }
//        }
//        if (flag) {
//            System.out.println("Passed");
//        }
//
//        Graphics2D graphics2D = image.createGraphics();
//        for (Map.Entry<Float, Integer> combo : cachedCenters.entrySet()) {
//            int centPoint = Y_MAX - combo.getValue() - 1;
////            int centPoint = combo.getKey();
//            graphics2D.setColor(UtilMethods.remapColor(true, combo.getKey()));
//            graphics2D.fill(new Rectangle(256, centPoint, 3, 3));
//            graphics2D.setColor(Color.WHITE);
//            graphics2D.fill(new Rectangle(253, centPoint, 3, 3));
//            graphics2D.setColor(Color.BLACK);
//            graphics2D.fill(new Rectangle(259, centPoint, 3, 3));
//        }
//        graphics2D.dispose();
//    }
//
//
//    private int getCellularCenter(int xIn, float noiseIn) {
//        //////////////////////////////////////////////////////////////////////
//        //      Determine direction and check immediate adjacent values     //
//        //////////////////////////////////////////////////////////////////////
//        // If current (+ve direction) is further away from input, or the wrong region, check the other direction.
//        int dir = 1;
//        float prevDist = getNoise(xIn, true);
//        float currDist = getNoise(xIn+1, true);
//        float currNoise = getNoise(xIn+1, false);
//        if ((prevDist < currDist) || (currNoise != noiseIn)) {
//            // If new current (-ve direction) is further (or equal) from input, or the wrong region,
//            // then current is center. The "or equal" part catches rare cases where the true center is
//            // exactly between two integers.
//            currDist = getNoise(xIn-1, true);
//            currNoise = getNoise(xIn-1, false);
//            if ((prevDist <= currDist) || (currNoise != noiseIn)) {
//                cachedCenters.put(noiseIn, xIn);
//                return xIn;
//            }
//            // Else, continue checking in the -ve direction
//            dir = -1;
//        }
//        // Else, continue checking in the +ve direction
//
//        //////////////////////////////////////////////////
//        //      Rough search in chosen direction        //
//        //////////////////////////////////////////////////
//        final int ROUGH_DIST = 5;
//        int prevCheckX = xIn+dir;
//        prevDist = currDist;
//        int currCheckX = prevCheckX + (ROUGH_DIST * dir);
//        currDist = getNoise(currCheckX, true);
//        currNoise = getNoise(currCheckX, false);
//        // While the current distance is closer (and in region bounds) than the previous, keep checking that direction.
//        while ((currDist < prevDist) && (currNoise == noiseIn)) {
//            // Check for u-curve "rough overshot center but still better than prev" issue.
//            // Before continuing with rough, check if the slope is actually decreasing in dir.
//            if (currDist > getNoise(currCheckX-dir, true)) {
//                break;
//            }
//
//            prevDist = currDist;
//            prevCheckX = currCheckX;
//            currCheckX += (ROUGH_DIST * dir);
//            currDist = getNoise(currCheckX, true);
//            currNoise = getNoise(currCheckX, false);
//        }
//        // As soon as the loop ends, we know the center must lie within [currX-ROUGH_DIST, currX)
//        // The value for "currX-ROUGH_DIST" is still stored in "prevDist", as the while loop did not execute.
//
//        ////////////////////////////////////////////////////
//        //      Fine search in determined interval        //
//        ////////////////////////////////////////////////////
//        currCheckX = prevCheckX+dir;
//        currDist = getNoise(currCheckX, true);
//        currNoise = getNoise(currCheckX, false);
//        // While the current distance is closer (and in region bounds) than the previous, keep checking that direction.
//        while ((currDist < prevDist) && (currNoise == noiseIn)) {
//            prevDist = currDist;
//            prevCheckX = currCheckX;
//            currCheckX += dir;
//            currDist = getNoise(currCheckX, true);
//            currNoise = getNoise(currCheckX, false);
//        }
//        // When this loop fails to execute, then "currCheckX-dir" is the center point.
//
//        cachedCenters.put(noiseIn, prevCheckX);
//        return prevCheckX;
//    }
//
//
//
//
//
//    @Override
//    public void region(BufferedImage image, int currY) {
//        int X_MIN = image.getMinX();
//        int X_MAX = image.getWidth();
//        int Z_MIN = image.getMinY();
//        int Z_MAX = image.getHeight();
//
//        for (int ix = X_MIN; ix < X_MAX; ix++) {
//            for (int iz = Z_MIN; iz < Z_MAX; iz++) {
//                int[] shifts = {0, 0, 0};
//
//                int x = ix + shifts[0];
//                int y = currY + shifts[1];
//                int z = iz + shifts[2];
//
//
//                float noise = 0;
//
//                int color = UtilMethods.remapColor(true, noise).getRGB();
//                image.setRGB((X_MAX - ix - 1), (Z_MAX - iz - 1), color);
//            }
//        }
//    }
//
//
//    private class Combo {
//        public final Integer i;
//        public final Float f;
//
//        Combo(Integer i, Float f) {
//            this.i = i;
//            this.f = f;
//        }
//    }
//}
