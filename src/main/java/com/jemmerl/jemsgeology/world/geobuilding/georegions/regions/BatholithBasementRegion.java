package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.util.RefWeightedProbPicker;
import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;

import java.util.HashMap;
import java.util.Random;

// Represents a weathered batholith.
public class BatholithBasementRegion extends AbstractBasementRegion {
    // TODO ore placement options
    // TODO this will eventually be data-created, including map / ores

    private static HashMap<GeoType, Integer> buildMap() {
        HashMap<GeoType, Integer> map = new HashMap<>();
        map.put(GeoType.DIORITE, 5);
        map.put(GeoType.GRANODIORITE, 8);
        map.put(GeoType.PINK_GRANITE, 23);
        map.put(GeoType.GRAY_GRANITE, 23);
        map.put(GeoType.WHITE_GRANITE, 23);
        map.put(GeoType.SYENITE, 7);
        map.put(GeoType.GABBRO, 1);
        map.put(GeoType.ANORTHOSITE, 10);
        return map;
    }

    private final GeoType baseRock;
    private final int age;
    private final float scalingPerc;

    public BatholithBasementRegion(int seed) {
        super(seed);
        this.baseRock = new RefWeightedProbPicker<>(buildMap(), GeoType.EMPTY)
                .pickRandom(new Random(seed + 816622703));
        this.age = 100;
        this.scalingPerc = .5f;
        // TODO random pick, age is all the same. Will be configurable with json built later,
        // but now and this use-case, should be older than the first ign intrusive step
    }

    @Override
    public int buildDeformColumn(int x, int z, short[] deformArrayColumn) {
        return 0;
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        //if within bound, baserock
        //if out bound but within contact, contact meta but empty,
        // else empty;


        return new UnbakedGeo(baseRock);
    }
}