package com.jemmerl.jemsgeology.geology.stones;

import net.minecraft.block.material.MaterialColor;

import java.util.Collections;
import java.util.List;

public enum GeoType {
    // surface weathering, subsurface weathering

    // Sedimentary
    CHALK("chalk",                              GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    LIMESTONE("limestone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    DOLOMITE("dolomite",                        GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    CALCITE("calcite",                          GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    MARLSTONE("marlstone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SHALE("shale",                              GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    LIMY_SHALE("limy_shale",                    GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SANDSTONE("sandstone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    RED_SANDSTONE("red_sandstone",              GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    ARKOSE("arkose",                            GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    GREYWACKE("greywacke",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    MUDSTONE("mudstone",                        GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    CLAYSTONE("claystone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SILTSTONE("siltstone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    CONGLOMERATE("conglomerate",                GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    LIGNITE_COAL("lignite_coal",                GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),
    SUBBITUMINOUS_COAL("sub-bituminous_coal",   GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),
    BITUMINOUS_COAL("bituminous_coal",          GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),
    ANTHRACITE_COAL("anthracite_coal",          GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),
    LATERITE("laterite",                        GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.DIRT),
    // Note: Laterites are most commonly clay-soils, but can be compacted into rocks, especially after exposure.
    //  Laterite rocks have even been used for construction, so it has been promoted.
    //  Laterite is registered as a sedimentary rock, but its primary generation form will be as its regolith.
    // TODO most laterites are, well, lateritic. But do not forget karst bauxite (also laterites kinda)
    //  also maybe rename laterite regolith manually to just laterite or laterite soil?

    // Hydrothermal
    QUARTZ("quartz",        GeoGroup.HYDROTHERMAL, WeatheringType.REGOLITH, WeatheringType.NONE, true, MaterialColor.QUARTZ),

    // Evaporites
    ROCKSALT("rocksalt",    GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),
    GYPSUM("gypsum",        GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),

    // Extrusive Igneous
    GRAY_RHYOLITE("gray_rhyolite",      GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    PINK_RHYOLITE("pink_rhyolite",      GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    DACITE("dacite",                    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    ANDESITE("andesite",                GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    TRACHYTE("trachyte",                GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    BASALT("basalt",                    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SCORIA("scoria",                    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.STONE),
    RHYOLITIC_TUFF("rhyolitic_tuff",    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    TRACHYTIC_TUFF("trachytic_tuff",    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    ANDESITIC_TUFF("andesitic_tuff",    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    BASALTIC_TUFF("basaltic_tuff",      GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    ULTRAMAFIC_TUFF("ultramafic_tuff",  GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),

    // Intrusive Igneous
    DIORITE("diorite",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    GRANODIORITE("granodiorite",    GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    GRANITE("granite",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SYENITE("syenite",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    GABBRO("gabbro",                GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    ANORTHOSITE("anorthosite",      GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    DIABASE("diabase",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    PERIDOTITE("peridotite",        GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    KIMBERLITE("kimberlite",        GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE("quartzite",                      GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SERPENTINE("serpentine",                    GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SCHIST("schist",                            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    PHYLLITE("phyllite",                        GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    SLATE("slate",                              GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    GNEISS("gneiss",                            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    AMPHIBOLITE("amphibolite",                  GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    MARBLE("marble",                            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    PELITIC_HORNFELS("pelitic_hornfels",        GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    CARBONATE_HORNFELS("carbonate_hornfels",    GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    MAFIC_HORNFELS("mafic_hornfels",            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    METACONGLOMERATE("metaconglomerate",        GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),
    GREISEN("greisen",                          GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, MaterialColor.STONE),

    // Stable Detritus
    DIRT("dirt",                GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.DIRT),
    COARSE_DIRT("coarse_dirt",  GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.DIRT),
    CLAY("clay",                GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.DIRT),

    // Falling Detritus
    SAND("sand",            GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.DIRT),
    RED_SAND("red_sand",    GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.DIRT),
    GRAVEL("gravel",        GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, MaterialColor.DIRT);

    private final String name;
    private final GeoGroup geoGroup;
    private final WeatheringType surfaceWeathering;
    private final WeatheringType subSurfaceWeathering;
    private final boolean hasCobble;
    private final MaterialColor materialColor;

    GeoType(String name, GeoGroup geoGroup, WeatheringType surfaceWeathering, WeatheringType subSurfaceWeathering, boolean hasCobble, MaterialColor materialColor) {
        this.name = name;
        this.geoGroup = geoGroup;
        this.surfaceWeathering = surfaceWeathering;
        this.subSurfaceWeathering = subSurfaceWeathering;
        this.hasCobble = hasCobble;
        this.materialColor = materialColor;
    }

    //TODO note EnumSet is very efficient for enums smile

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

    public MaterialColor getMaterialColor() {
        return materialColor;
    }

    public float getHardnessAdj() {
        return 0F;
    }

    public float getResistAdj() {
        return 0F;
    }


    //TODO may move to its own class down the line. or maybe remove all together, if stones become API'd like ores
    ////////////////////////////////////
    //              LISTS             //
    ////////////////////////////////////

    // List of blocks with a different side texture
    public static List<GeoType> SIDE_TEXTURE_MODELS = Collections.emptyList();
}
