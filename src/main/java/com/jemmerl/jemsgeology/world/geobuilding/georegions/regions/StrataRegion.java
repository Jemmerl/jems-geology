package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.InstStrataNoise;

public class StrataRegion {

    private final InstStrataNoise strataInstNoise;

    public StrataRegion(int seed) {
        strataInstNoise = new InstStrataNoise(seed - 113874636, 1, 100);
    }

    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        return new UnbakedGeo(GeoType.ANORTHOSITE);
    }
}
