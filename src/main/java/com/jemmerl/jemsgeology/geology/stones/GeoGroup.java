package com.jemmerl.jemsgeology.geology.stones;

public enum GeoGroup {
    SEDIMENTARY("sedimentary"),
    EVAPORITE("evaporite"),
    HYDROTHERMAL("hydrothermal"),
    EXTRUSIVE("extrusive"),
    INTRUSIVE("intrusive"),
    METAMORPHIC("metamorphic"),
    DETRITUS("detritus");

    private final String name;

    GeoGroup(String name) {
        this.name = name;
    }

}
