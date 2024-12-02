package com.jemmerl.jemsgeology.geology.stones;

public enum GeoType {
    // surface weathering, subsurface weathering

    // Sedimentary
    CHALK("chalk", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    LIMESTONE("limestone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    DOLOMITE("dolomite", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    CALCITE("calcite", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    MARLSTONE("marlstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SHALE("shale", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    LIMY_SHALE("limy_shale", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SANDSTONE("sandstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    RED_SANDSTONE("red_sandstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    ARKOSE("arkose", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    GREYWACKE("greywacke", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    MUDSTONE("mudstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    CLAYSTONE("claystone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SILTSTONE("siltstone", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    CONGLOMERATE("conglomerate", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    LIGNITE_COAL("lignite_coal", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false),
    SUBBITUMINOUS_COAL("sub-bituminous_coal", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false),
    BITUMINOUS_COAL("bituminous_coal", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false),
    ANTHRACITE_COAL("anthracite_coal", GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false),

    // Hydrothermal
    QUARTZ("quartz", GeoGroup.HYDROTHERMAL, WeatheringType.REGOLITH, WeatheringType.NONE, true),

    // Evaporites
    ROCKSALT("rocksalt", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false),
    GYPSUM("gypsum", GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false),

    // Extrusive Igneous
    GRAY_RHYOLITE("gray_rhyolite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    PINK_RHYOLITE("pink_rhyolite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    DACITE("dacite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    ANDESITE("andesite", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    TRACHYTE("trachyte", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    BASALT("basalt", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    PAHOEHOE("pahoehoe", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, false), //todo keep?? also edit block model to have side textures on bottom
    SCORIA("scoria", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.NONE, false),
    RHYOLITIC_TUFF("rhyolitic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    TRACHYTIC_TUFF("trachytic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    ANDESITIC_TUFF("andesitic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    BASALTIC_TUFF("basaltic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    ULTRAMAFIC_TUFF("ultramafic_tuff", GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),

    // Intrusive Igneous
    DIORITE("diorite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    GRANODIORITE("granodiorite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    GRANITE("granite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SYENITE("syenite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    GABBRO("gabbro", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    ANORTHOSITE("anorthosite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    DIABASE("diabase", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    PERIDOTITE("peridotite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    KIMBERLITE("kimberlite", GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE("quartzite", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SERPENTINE("serpentine", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SCHIST("schist", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    PHYLLITE("phyllite", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    SLATE("slate", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    GNEISS("gneiss", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    AMPHIBOLITE("amphibolite", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    MARBLE("marble", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    PELITIC_HORNFELS("pelitic_hornfels", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    CARBONATE_HORNFELS("carbonate_hornfels", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    MAFIC_HORNFELS("mafic_hornfels", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    METACONGLOMERATE("metaconglomerate", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),
    GREISEN("greisen", GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true),

    // Stable Detritus
    DIRT("dirt", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false),
    COARSE_DIRT("coarse_dirt", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false),
    CLAY("clay", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false),
    LATERITE("laterite", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false),

    // Falling Detritus
    SAND("sand", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false),
    RED_SAND("red_sand", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false),
    GRAVEL("gravel", GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false);

    private final String name;
    private final GeoGroup geoGroup;
    private final WeatheringType surfaceWeathering;
    private final WeatheringType subSurfaceWeathering;
    private final boolean hasCobble;

    GeoType(String name, GeoGroup geoGroup, WeatheringType surfaceWeathering, WeatheringType subSurfaceWeathering, boolean hasCobble) {
        this.name = name;
        this.geoGroup = geoGroup;
        this.surfaceWeathering = surfaceWeathering;
        this.subSurfaceWeathering = subSurfaceWeathering;
        this.hasCobble = hasCobble;
    }

    //////////////////////////////////////
    //              GETTERS             //
    //////////////////////////////////////

    public String getName() {
        return name;
    }

    public GeoGroup getGeoGroup() {
        return geoGroup;
    }

    public WeatheringType getSurfaceWeathering() {
        return surfaceWeathering;
    }

    public WeatheringType getSubSurfaceWeathering() {
        return subSurfaceWeathering;
    }

    public boolean hasCobble() {
        return hasCobble;
    }

}
