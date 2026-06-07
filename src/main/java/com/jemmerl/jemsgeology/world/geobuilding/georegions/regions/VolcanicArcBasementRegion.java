package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;

// Rocks subjected to volcanic-arc style metamorphism
public class VolcanicArcBasementRegion extends AbstractBasementRegion {
    public VolcanicArcBasementRegion(int seed) {
        this(seed, false);
    }

    public VolcanicArcBasementRegion(int seed, boolean useRandomSeed) {
        super(seed, useRandomSeed);
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        return null;
    }
}
