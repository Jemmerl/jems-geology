package com.jemmerl.jemsgeology.geology.stones;

// This is not a perfectly accurate grouping, as many rocks would fit into multiple categories.
// E.g. calcite can be sedimentary or evaporite, and tuff is well-arguably better described as sedimentary.
// Just a potentially useful tool.
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
