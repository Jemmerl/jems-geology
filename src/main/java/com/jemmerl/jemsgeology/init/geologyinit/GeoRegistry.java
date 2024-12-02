package com.jemmerl.jemsgeology.init.geologyinit;

import com.jemmerl.jemsgeology.geology.stones.GeoType;

public class GeoRegistry {

    private boolean hasCobble;
    private OreRegistry oreRegistry;
    private DecorRegistry decorRegistry;

    public GeoRegistry(GeoType geoType) {
        this.hasCobble = geoType.hasCobble();

        // important stuff
    }
}
