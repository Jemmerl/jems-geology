package com.jemmerl.jemsgeology.init.geology;

// Each level is more restrictive, excluding the ones above it. E.g. "regolith" excludes desert sands, sedimentary, and
// all stones. However, "desert sand" includes regolith.
public enum OreEnviromentTypes {
    STONES("stones"),
    SEDIMENTARY("sedimentary"),
    DESERT_SAND("desert_sand"),
    REGOLITH("regolith");

    private final String name;

    OreEnviromentTypes(String name) {
        this.name = name;
    }

}
