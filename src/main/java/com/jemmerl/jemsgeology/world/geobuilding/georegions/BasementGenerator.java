package com.jemmerl.jemsgeology.world.geobuilding.georegions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.BasementRegionNoise;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.InstStrataNoise;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.AbstractBasementRegion;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.OrogenicBasementRegion;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.StrataRegion;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;

public class BasementGenerator {

    public static final int AVG_BASEMENT_Y = 25;

    private static final int CACHE_SIZE = 5; // TODO size? 3 - 5 entries?
    private static final Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> basementRegionLRU = initRegionCache();
    private static final Int2ObjectLinkedOpenHashMap<StrataRegion> basementStrataLRU = initStrataCache();

    // TODO make the strata for basement regions independant of the basement region noise. aka, its own noise
    //  gen. that way adjacent orogeny regions can influence each-other. ALSO ALSO make basement regions a bit larger?

    public static void buildBasement(UnbakedGeo[][][] wrapperArray, short[][][] deformArray, BlockPos cornerPos) {
            for (int x = 0; x < wrapperArray.length; x++) {
            for (int z = 0; z < wrapperArray[0].length; z++) {
                final int xP = cornerPos.getX() + x;
                final int zP = cornerPos.getZ() + z;

                int regionSeed = BasementRegionNoise.getBasementRegionSeed(xP, zP); // TODO regions are flat, fix??
                AbstractBasementRegion region = basementRegionLRU.getAndMoveToFirst(regionSeed);
                if (region == null) {
                    region = new OrogenicBasementRegion(regionSeed);
                    // get the region from the seed, since  not in LRU
                    putBasement(regionSeed, region);
                }

                region.buildDeformColumn(xP, zP, deformArray[x][z]);

                int basementLimit = Math.min(BasementRegionNoise.getBasementDepth(xP, zP), wrapperArray[0][0].length);

                for (int y = 0; y < basementLimit; y++) {
                    UnbakedGeo unbakedGeo = region.getUnbakedGeo(xP, y, zP);

                    if (unbakedGeo.isEmpty()) {
                        int strataSeed = BasementRegionNoise.getBasementStrataSeed(xP, y, zP);
                        StrataRegion strataRegion = basementStrataLRU.getAndMoveToFirst(strataSeed);
                        if (strataRegion == null) {
                            strataRegion = new StrataRegion(strataSeed);
                            // get the region from the seed, since  not in LRU
                            putStrata(strataSeed, strataRegion);
                        }

                        unbakedGeo.merge(strataRegion.getUnbakedGeo(xP, y, zP));
                    }

                    wrapperArray[x][z][y] = unbakedGeo;
                }

            }
        }
    }

    public static void buildBasementDeformOnly(short[][][] deformArray) {

    }

    private static AbstractBasementRegion regionSelector;


    //////////////////////////////////
    //          CACHE STUFF         //
    //////////////////////////////////

    private static void putBasement(int regionSeed, AbstractBasementRegion region) {
        if (basementRegionLRU.size() >= CACHE_SIZE) {
            basementRegionLRU.removeLast();
        }
        basementRegionLRU.putAndMoveToFirst(regionSeed, region);
    }

    private static void putStrata(int strataSeed, StrataRegion strataRegion) {
        if (basementStrataLRU.size() >= CACHE_SIZE) {
            basementStrataLRU.removeLast();
        }
        basementStrataLRU.putAndMoveToFirst(strataSeed, strataRegion);
    }

//    private static void invalidateCache() {
//        regionLRU.clear(); // Always the same size, can reuse object :)
//        reloadCache = true;
//    }
//
//    private static void reloadCacheMap() {
//        reloadCache = false;
//    }

    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> initRegionCache() {
        Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> c = new Int2ObjectLinkedOpenHashMap<>(CACHE_SIZE);
//        c.defaultReturnValue(-1);
        return c;
    }

    private static Int2ObjectLinkedOpenHashMap<StrataRegion> initStrataCache() {
        Int2ObjectLinkedOpenHashMap<StrataRegion> c = new Int2ObjectLinkedOpenHashMap<>(CACHE_SIZE);
//        c.defaultReturnValue(-1);
        return c;
    }

}
