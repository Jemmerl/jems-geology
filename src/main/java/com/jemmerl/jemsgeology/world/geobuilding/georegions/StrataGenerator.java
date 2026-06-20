package com.jemmerl.jemsgeology.world.geobuilding.georegions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.BasementRegionNoise;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.InstStrataNoise;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.AbstractBasementRegion;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.regions.BatholithBasementRegion;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

// Used for generating rock strata with an age. Can be used for sedimentary and basement alike.
public class StrataGenerator {

    // TODO try to rotate strata noise (which only samplesy dir) using 2-angles for rotation? Rotate imput coords, then
    //  take y direction?

    // TODO temp
    private static final InstStrataNoise instNoise = new InstStrataNoise(8675309, 1, 100);

    // TODO if i reuse a lot of code exactly, do an abstract class. otherwise, there is no contract that needs held.
    //  these are all hardcoded built in after all

    // LRU cache with 3 - 5 entries
    private static final int CACHE_SIZE = 5; // TODO size?

    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> strataLRU = initCache();

    public static UnbakedGeo getGeoWrapper(int x, int y, int z) {
        int regionSeed = BasementRegionNoise.getBasementRegionSeed(x, z); // TODO obv
        // actually, this whole method here works differently.

        AbstractBasementRegion region = strataLRU.getAndMoveToFirst(regionSeed);
        if (region == null) {
            region = new BatholithBasementRegion(regionSeed);
            // get the region from the seed, since  not in LRU
            put(regionSeed, region);
        }

        return region.getUnbakedGeo(x, y, z);
    }

    public static UnbakedGeo getGeoWrapper(@Nonnull BlockPos blockPos) {
        return getGeoWrapper(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }








    private static void put(int regionSeed, AbstractBasementRegion region) {
        if (strataLRU.size() >= CACHE_SIZE) {
            strataLRU.removeLast();
        }
        strataLRU.putAndMoveToFirst(regionSeed, region);
    }

    private static Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> initCache() {
        Int2ObjectLinkedOpenHashMap<AbstractBasementRegion> c = new Int2ObjectLinkedOpenHashMap<>(CACHE_SIZE);
//        c.defaultReturnValue(-1);
        return c;
    }
}
