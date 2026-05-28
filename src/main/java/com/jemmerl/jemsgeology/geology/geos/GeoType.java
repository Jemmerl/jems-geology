package com.jemmerl.jemsgeology.geology.geos;

import com.jemmerl.jemsgeology.geology.geos.props.GeoProp;
import com.jemmerl.jemsgeology.geology.geos.props.Hardness;
import com.jemmerl.jemsgeology.geology.ores.GeoLoot;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.RandomValueRange;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

// JEM'S NOTES:
//    * The name "dolomite" was picked over "dolostone", because the same block will be used for both widespread
//      "dolostone" rocks and hydrothermal veins of dolomite. Additionally, the term "dolostone" isn't fully adopted.
//      Hopefully, the dolostone-loving geologists can forgive this decision made in the name of optimization!
//      Let it be known that I am a personal fan of separating the terms dolostone and dolomite!
//
//      Unlike dolomite/stone, limestone is unquestionably separable from calcite.
//      While they are both mainly calcium carbonate (CO3), there is a recognized difference-- limestone being
//      both the minerals calcite and aragonite (and usually some magnesium) and frequently formed from fossils.
//      For the sake of optimization, it should be noted that aragonite is conveniently omitted in the mod.
//      I suppose such is the fate of an unstable polymorph!



// Note: Laterites are most commonly clay-soils, but can be compacted into rocks, especially after exposure.
//  Laterite is a softer rock/soil, but dries when exposed.
// TODO most laterites are, well, lateritic. But do not forget karst bauxite (also laterites kinda)
//  also maybe rename laterite regolith manually to just laterite or laterite soil?

public enum GeoType {

    // Sedimentary
    CHALK("chalk",                              GeoGroup.SEDIMENTARY, GeoProperties.CHALK),
    LIMESTONE("limestone",                      GeoGroup.SED_EVAPORITE, GeoProperties.LIMESTONE),
    DOLOMITE("dolomite",                        GeoGroup.SED_EVAP_HYDRO, GeoProperties.DOLOMITE),
    CALCITE("calcite",                          GeoGroup.SED_EVAP_HYDRO, GeoProperties.CALCITE),
    ROCKSALT("rocksalt",                        GeoGroup.SED_EVAPORITE, GeoProperties.ROCKSALT),
    GYPSUM("gypsum",                            GeoGroup.SED_EVAPORITE, GeoProperties.GYPSUM),
    MARLSTONE("marlstone",                      GeoGroup.SEDIMENTARY, GeoProperties.MARLSTONE),
    SHALE("shale",                              GeoGroup.SEDIMENTARY, GeoProperties.SHALE),
    LIMY_SHALE("limy_shale",                    GeoGroup.SEDIMENTARY, GeoProperties.LIMY_SHALE),
    SANDSTONE("sandstone",                      GeoGroup.SEDIMENTARY, GeoProperties.SANDSTONE),
    RED_SANDSTONE("red_sandstone",              GeoGroup.SEDIMENTARY, GeoProperties.RED_SANDSTONE),
    ARKOSE("arkose",                            GeoGroup.SEDIMENTARY, GeoProperties.ARKOSE),
    GREYWACKE("greywacke",                      GeoGroup.SEDIMENTARY, GeoProperties.GREYWACKE),
    MUDSTONE("mudstone",                        GeoGroup.SEDIMENTARY, GeoProperties.MUDSTONE),
    CLAYSTONE("claystone",                      GeoGroup.SEDIMENTARY, GeoProperties.CLAYSTONE),
    SILTSTONE("siltstone",                      GeoGroup.SEDIMENTARY, GeoProperties.SILTSTONE),
    CONGLOMERATE("conglomerate",                GeoGroup.SEDIMENTARY, GeoProperties.CONGLOMERATE),
    LIGNITE_COAL("lignite_coal",                GeoGroup.SEDIMENTARY, GeoProperties.LIGNITE_COAL),
    SUBBITUMINOUS_COAL("sub-bituminous_coal",   GeoGroup.SEDIMENTARY, GeoProperties.SUBBITUMINOUS_COAL),
    BITUMINOUS_COAL("bituminous_coal",          GeoGroup.SEDIMENTARY, GeoProperties.BITUMINOUS_COAL),
    ANTHRACITE_COAL("anthracite_coal",          GeoGroup.SEDIMENTARY, GeoProperties.ANTHRACITE_COAL),

    // Hydrothermal
    QUARTZ("quartz",                            GeoGroup.HYDROTHERMAL, GeoProperties.QUARTZ),

    // Extrusive Igneous
    GRAY_RHYOLITE("gray_rhyolite",              GeoGroup.IGN_EXTRUSIVE, GeoProperties.GRAY_RHYOLITE),
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    PINK_RHYOLITE("pink_rhyolite",              GeoGroup.IGN_EXTRUSIVE, GeoProperties.PINK_RHYOLITE),
    DACITE("dacite",                            GeoGroup.IGN_EXTRUSIVE, GeoProperties.DACITE),
    ANDESITE("andesite",                        GeoGroup.IGN_EXTRUSIVE, GeoProperties.ANDESITE),
    TRACHYTE("trachyte",                        GeoGroup.IGN_EXTRUSIVE, GeoProperties.TRACHYTE),
    BASALT("basalt",                            GeoGroup.IGN_EXTRUSIVE, GeoProperties.BASALT),
    SCORIA("scoria",                            GeoGroup.IGN_EJECTA, GeoProperties.SCORIA),
    RHYOLITIC_TUFF("rhyolitic_tuff",            GeoGroup.IGN_EJECTA, GeoProperties.RHYOLITIC_TUFF),
    TRACHYTIC_TUFF("trachytic_tuff",            GeoGroup.IGN_EJECTA, GeoProperties.TRACHYTIC_TUFF),
    ANDESITIC_TUFF("andesitic_tuff",            GeoGroup.IGN_EJECTA, GeoProperties.ANDESITIC_TUFF),
    BASALTIC_TUFF("basaltic_tuff",              GeoGroup.IGN_EJECTA, GeoProperties.BASALTIC_TUFF),
    ULTRAMAFIC_TUFF("ultramafic_tuff",          GeoGroup.IGN_EJECTA, GeoProperties.ULTRAMAFIC_TUFF),

    // Intrusive Igneous
    DIORITE("diorite",                          GeoGroup.IGN_INTRUSIVE, GeoProperties.DIORITE),
    GRANODIORITE("granodiorite",                GeoGroup.IGN_INTRUSIVE, GeoProperties.GRANODIORITE),
    GRANITE("granite",                          GeoGroup.IGN_INTRUSIVE, GeoProperties.GRANITE),
    SYENITE("syenite",                          GeoGroup.IGN_INTRUSIVE, GeoProperties.SYENITE),
    GABBRO("gabbro",                            GeoGroup.IGN_INTRUSIVE, GeoProperties.GABBRO),
    ANORTHOSITE("anorthosite",                  GeoGroup.IGN_INTRUSIVE, GeoProperties.ANORTHOSITE),
    DIABASE("diabase",                          GeoGroup.IGN_INTRUSIVE, GeoProperties.DIABASE),
    PERIDOTITE("peridotite",                    GeoGroup.IGN_INTRUSIVE, GeoProperties.PERIDOTITE),
    KIMBERLITE("kimberlite",                    GeoGroup.IGN_INTRUSIVE, GeoProperties.KIMBERLITE),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE("quartzite",                      GeoGroup.METAMORPHIC, GeoProperties.QUARTZITE),
    SERPENTINITE("serpentinite",                GeoGroup.METAMORPHIC, GeoProperties.SERPENTINITE),
    SCHIST("schist",                            GeoGroup.METAMORPHIC, GeoProperties.SCHIST),
    PHYLLITE("phyllite",                        GeoGroup.METAMORPHIC, GeoProperties.PHYLLITE),
    SLATE("slate",                              GeoGroup.METAMORPHIC, GeoProperties.SLATE),
    GNEISS("gneiss",                            GeoGroup.METAMORPHIC, GeoProperties.GNEISS),
    AMPHIBOLITE("amphibolite",                  GeoGroup.METAMORPHIC, GeoProperties.AMPHIBOLITE),
    MARBLE("marble",                            GeoGroup.METAMORPHIC, GeoProperties.MARBLE),
    PELITIC_HORNFELS("pelitic_hornfels",        GeoGroup.METAMORPHIC, GeoProperties.PELITIC_HORNFELS),
    CARBONATE_HORNFELS("carbonate_hornfels",    GeoGroup.METAMORPHIC, GeoProperties.CARBONATE_HORNFELS),
    MAFIC_HORNFELS("mafic_hornfels",            GeoGroup.METAMORPHIC, GeoProperties.MAFIC_HORNFELS),
    METACONGLOMERATE("metaconglomerate",        GeoGroup.METAMORPHIC, GeoProperties.METACONGLOMERATE),
    GREISEN("greisen",                          GeoGroup.METAMORPHIC, GeoProperties.GREISEN),

    // Stable Regolith
    DIRT("dirt",                GeoGroup.REGO_SOIL, GeoProperties.DIRT),
    COARSE_DIRT("coarse_dirt",  GeoGroup.REGO_SOIL, GeoProperties.COARSE_DIRT),
    CLAY("clay",                GeoGroup.REGO_CLAY, GeoProperties.CLAY),
    LATERITE("laterite",        GeoGroup.REGO_SOIL, GeoProperties.LATERITE),

    // Falling Regolith
    SAND("sand",                GeoGroup.REGO_SANDY, GeoProperties.SAND),
    RED_SAND("red_sand",        GeoGroup.REGO_SANDY, GeoProperties.RED_SAND),
    PINK_SAND("pink_sand",      GeoGroup.REGO_SANDY, GeoProperties.PINK_SAND),
    BLACK_SAND("black_sand",    GeoGroup.REGO_SANDY, GeoProperties.BLACK_SAND),
    WHITE_SAND("white_sand",    GeoGroup.REGO_SANDY, GeoProperties.WHITE_SAND),
    GRAVEL("gravel",            GeoGroup.REGO_GRAVEL, GeoProperties.GRAVEL),
    DUNE_SAND("dune_sand",      GeoGroup.DESERT_SAND, GeoProperties.DUNE_SAND),
    GYPSUM_SAND("gypsum_sand",  GeoGroup.DESERT_SAND, GeoProperties.GYPSUM_SAND);

    /*
o
•	Bright white sand (calcite/aragonite) + limey gravels
•	Yellow sand (Quartz) + Sandy gravels
o

     */

    private final String name;
    private final GeoGroup geoGroup;
    private final GeoProp geoProp;

    GeoType(String name, GeoGroup geoGroup, GeoProp geoProp) {
        this.name = name;
        this.geoGroup = geoGroup;
        this.geoProp = geoProp;
    }

    public static GeoType fromString(String name) {
        return valueOf(name.toUpperCase(Locale.ENGLISH));
    }


    //TODO note EnumSet is very efficient for enums smile

    //////////////////////////////////////
    //              GETTERS             //
    //////////////////////////////////////

    public String getName() { return name; }
    public GeoGroup getGeoGroup() { return geoGroup; }
    public GeoProp getGeoProp() { return geoProp; }

    public Hardness getHardness() { return geoProp.getHardness(); }
    public MaterialColor getMaterialColor() { return geoProp.getMaterialColor(); }
    public GeoLoot getGeoLoot() { return geoProp.getGeoLoot(); }

    // TODO TEMP
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