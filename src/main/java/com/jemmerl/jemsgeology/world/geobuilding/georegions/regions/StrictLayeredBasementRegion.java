package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;

// Consists of layers with a strict order, such as Greenstone Belts
public class StrictLayeredBasementRegion extends AbstractBasementRegion {
    public StrictLayeredBasementRegion(int seed) {
        this(seed, false);
    }

    public StrictLayeredBasementRegion(int seed, boolean useRandomSeed) {
        super(seed, useRandomSeed);
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        return null;
    }
}
