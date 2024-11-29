package com.jemmerl.jemsgeology.geology.stones;

import com.jemmerl.jemsgeology.geology.ores.OreBearing;

import java.util.HashSet;

public enum GeoStones {
    // surface weathering, subsurface weathering

    // Sedimentary
    CHALK("chalk", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    LIMESTONE("limestone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    DOLOMITE("dolomite", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    CALCITE("calcite", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    MARLSTONE("marlstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SHALE("shale", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    LIMY_SHALE("limy_shale", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SANDSTONE("sandstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    RED_SANDSTONE("red_sandstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    ARKOSE("arkose", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    GREYWACKE("greywacke", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    MUDSTONE("mudstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    CLAYSTONE("claystone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SILTSTONE("siltstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    CONGLOMERATE("conglomerate", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),

    // Hydrothermal
    QUARTZ("quartz", GeoGroup.HYDROTHERMAL, WeatheringType.NONE, WeatheringType.NONE, true, OreBearing.ANY),

    // Evaporites
    ROCKSALT("rocksalt", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.NONE),
    GYPSUM("gypsum", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.NONE),
    SYLVITE("sylvite", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.NONE),
    BORAX("borax", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.NONE),
    TRONA("trona", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.NONE),
    MAGNESITE("magnesite", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.NONE),
    // todo magnesite can be in regolith above serpentine bodies, make it drop from serp regolith? (similar to flint/gravel)

    // Extrusive Igneous
    RHYOLITE("rhyolite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    DACITE("dacite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    ANDESITE("andesite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    TRACHYTE("trachyte", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    BASALT("basalt", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    PAHOEHOE("pahoehoe", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, false, OreBearing.NONE), //todo keep??
    RHYOLITIC_TUFF("rhyolitic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.NONE),
    TRACHYTIC_TUFF("trachytic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.NONE),
    ANDESITIC_TUFF("andesitic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.NONE),
    BASALTIC_TUFF("basaltic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.NONE),
    ULTRAMAFIC_TUFF("ultramafic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.DIAMONDIFEROUS),

    // Intrusive Igneous
    DIORITE("diorite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    GRANODIORITE("granodiorite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    GRANITE("granite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SYENITE("syenite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    GABBRO("gabbro", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    ANORTHOSITE("anorthosite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    DIABASE("diabase", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    PERIDOTITE("peridotite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    KIMBERLITE("kimberlite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, false, OreBearing.DIAMONDIFEROUS),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE("quartzite", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SERPENTINE("serpentine", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SCHIST("schist", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    PHYLLITE("phyllite", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    SLATE("slate", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    GNEISS("gneiss", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    AMPHIBOLITE("amphibolite", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    MARBLE("marble", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    PELITIC_HORNFELS("pelitic_hornfels", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    CARBONATE_HORNFELS("carbonate_hornfels", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    MAFIC_HORNFELS("mafic_hornfels", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    METACONGLOMERATE("metaconglomerate", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),
    GREISEN("greisen", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, OreBearing.ANY),

    // Stable Detritus
    DIRT("dirt", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY),
    COARSE_DIRT("coarse_dirt", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY),
    CLAY("clay", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY),
    LATERITE("laterite", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY),

    // Falling Detritus
    SAND("sand", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY),
    RED_SAND("red_sand", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY),
    GRAVEL("gravel", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, OreBearing.ANY);


    GeoStones(String name, GeoGroup geoGroup, WeatheringType surfaceWeathering, WeatheringType subSurfaceWeathering, boolean hasCobble, OreBearing oreBearing) {

    }




}
