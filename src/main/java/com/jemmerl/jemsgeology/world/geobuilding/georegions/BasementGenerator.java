package com.jemmerl.jemsgeology.world.geobuilding.georegions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.BasementRegionNoise;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.AbstractBasementRegion;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.SingleBasementRegion;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class BasementGenerator {

    // TODO if i reuse a lot of code exactly, do an abstract class. otherwise, there is no contract that needs held.
    //  these are all hardcoded built in after all

    // LRU cache with 3 - 5 entries
    private static final int CACHE_SIZE = 5; // TODO size?

    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> regionLRU = initCache();
    // Int2ReferenceLinkedOpenHashMap???

    // do caching here, as well as weighted region gen selection based on the random seed noise

    public static UnbakedGeo getGeoWrapper(int x, int y, int z) {
        int regionSeed = BasementRegionNoise.getRegionalSeed(x, y, z);

        AbstractBasementRegion region = regionLRU.getAndMoveToFirst(regionSeed);
        if (region == null) {
            region = new SingleBasementRegion(regionSeed);
            // get the region from the seed, since  not in LRU
            put(regionSeed, region);
        }

        return region.getUnbakedGeo(x, y, z);
    }

    public static UnbakedGeo getGeoWrapper(@Nonnull BlockPos blockPos) {
        return getGeoWrapper(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    //
    //
    //

    private static void put(int regionSeed, AbstractBasementRegion region) {
        if (regionLRU.size() >= CACHE_SIZE) {
            regionLRU.removeLast();
        }
        regionLRU.putAndMoveToFirst(regionSeed, region);
    }

//    private static void invalidateCache() {
//        regionLRU.clear(); // Always the same size, can reuse object :)
//        reloadCache = true;
//    }
//
//    private static void reloadCacheMap() {
//        reloadCache = false;
//    }

    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> initCache() {
        Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> c = new Int2ObjectLinkedOpenHashMap<>(CACHE_SIZE);
//        c.defaultReturnValue(-1);
        return c;
    }

}
