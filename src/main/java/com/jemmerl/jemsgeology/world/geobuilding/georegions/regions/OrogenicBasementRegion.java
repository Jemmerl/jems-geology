package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;

// Rocks subjected to orogenic style metamorphism
public class OrogenicBasementRegion extends AbstractBasementRegion {
    public OrogenicBasementRegion(int seed) {
        this(seed, false);
    }

    public OrogenicBasementRegion(int seed, boolean useRandomSeed) {
        super(seed, useRandomSeed);
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        return null;
    }
}



