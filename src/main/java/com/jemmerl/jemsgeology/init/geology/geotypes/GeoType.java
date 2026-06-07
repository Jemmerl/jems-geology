package com.jemmerl.jemsgeology.init.geology.geotypes;

import com.google.common.collect.Sets;
import com.jemmerl.jemsgeology.init.geology.geotypes.props.GeoProp;
import com.jemmerl.jemsgeology.init.geology.geotypes.props.Hardness;
import com.jemmerl.jemsgeology.init.geology.ores.GeoLoot;
import net.minecraft.block.material.MaterialColor;

import java.util.*;

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

    // Empty -- NOTE: Defaults to Gray Rhyolite when applied to world
    EMPTY(GeoGroup.EMPTY, GeoProperties.EMPTY),

    // Sedimentary
    CHALK(                  GeoGroup.SEDIMENTARY, GeoProperties.CHALK),
    LIMESTONE(              GeoGroup.SED_EVAPORITE, GeoProperties.LIMESTONE),
    DOLOMITE(               GeoGroup.SED_EVAP_HYDRO, GeoProperties.DOLOMITE),
    CALCITE(                GeoGroup.SED_EVAP_HYDRO, GeoProperties.CALCITE),
    ROCKSALT(               GeoGroup.SED_EVAPORITE, GeoProperties.ROCKSALT),
    GYPSUM(                 GeoGroup.SED_EVAPORITE, GeoProperties.GYPSUM),
    MARLSTONE(              GeoGroup.SEDIMENTARY, GeoProperties.MARLSTONE),
    SHALE(                  GeoGroup.SEDIMENTARY, GeoProperties.SHALE),
    LIMY_SHALE(             GeoGroup.SEDIMENTARY, GeoProperties.LIMY_SHALE),
    SANDSTONE(              GeoGroup.SEDIMENTARY, GeoProperties.SANDSTONE),
    RED_SANDSTONE(          GeoGroup.SEDIMENTARY, GeoProperties.RED_SANDSTONE),
    ARKOSE(                 GeoGroup.SEDIMENTARY, GeoProperties.ARKOSE),
    GREYWACKE(              GeoGroup.SEDIMENTARY, GeoProperties.GREYWACKE),
    MUDSTONE(               GeoGroup.SEDIMENTARY, GeoProperties.MUDSTONE),
    CLAYSTONE(              GeoGroup.SEDIMENTARY, GeoProperties.CLAYSTONE),
    SILTSTONE(              GeoGroup.SEDIMENTARY, GeoProperties.SILTSTONE),
    CONGLOMERATE(           GeoGroup.SEDIMENTARY, GeoProperties.CONGLOMERATE),
    LIGNITE_COAL(           GeoGroup.SEDIMENTARY, GeoProperties.LIGNITE_COAL),
    SUB$BITUMINOUS_COAL(    GeoGroup.SEDIMENTARY, GeoProperties.SUBBITUMINOUS_COAL),
    BITUMINOUS_COAL(        GeoGroup.SEDIMENTARY, GeoProperties.BITUMINOUS_COAL),
    ANTHRACITE_COAL(        GeoGroup.SEDIMENTARY, GeoProperties.ANTHRACITE_COAL),

    // Hydrothermal
    QUARTZ(                 GeoGroup.HYDROTHERMAL, GeoProperties.QUARTZ),

    // Extrusive Igneous
    GRAY_RHYOLITE(          GeoGroup.IGN_EXTRUSIVE, GeoProperties.GRAY_RHYOLITE),
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    PINK_RHYOLITE(          GeoGroup.IGN_EXTRUSIVE, GeoProperties.PINK_RHYOLITE),
    DACITE(                 GeoGroup.IGN_EXTRUSIVE, GeoProperties.DACITE),
    ANDESITE(               GeoGroup.IGN_EXTRUSIVE, GeoProperties.ANDESITE),
    TRACHYTE(               GeoGroup.IGN_EXTRUSIVE, GeoProperties.TRACHYTE),
    BASALT(                 GeoGroup.IGN_EXTRUSIVE, GeoProperties.BASALT),
    SCORIA(                 GeoGroup.IGN_EJECTA, GeoProperties.SCORIA),
    RHYOLITIC_TUFF(         GeoGroup.IGN_EJECTA, GeoProperties.RHYOLITIC_TUFF),
    TRACHYTIC_TUFF(         GeoGroup.IGN_EJECTA, GeoProperties.TRACHYTIC_TUFF),
    ANDESITIC_TUFF(         GeoGroup.IGN_EJECTA, GeoProperties.ANDESITIC_TUFF),
    BASALTIC_TUFF(          GeoGroup.IGN_EJECTA, GeoProperties.BASALTIC_TUFF),
    ULTRAMAFIC_TUFF(        GeoGroup.IGN_EJECTA, GeoProperties.ULTRAMAFIC_TUFF),

    // Intrusive Igneous
    DIORITE(                GeoGroup.IGN_INTRUSIVE, GeoProperties.DIORITE),
    GRANODIORITE(           GeoGroup.IGN_INTRUSIVE, GeoProperties.GRANODIORITE),
    PINK_GRANITE(           GeoGroup.IGN_INTRUSIVE, GeoProperties.GRANITE),
    GRAY_GRANITE(           GeoGroup.IGN_INTRUSIVE, GeoProperties.GRANITE),
    WHITE_GRANITE(          GeoGroup.IGN_INTRUSIVE, GeoProperties.GRANITE),
    SYENITE(                GeoGroup.IGN_INTRUSIVE, GeoProperties.SYENITE),
    GABBRO(                 GeoGroup.IGN_INTRUSIVE, GeoProperties.GABBRO),
    ANORTHOSITE(            GeoGroup.IGN_INTRUSIVE, GeoProperties.ANORTHOSITE),
    DIABASE(                GeoGroup.IGN_INTRUSIVE, GeoProperties.DIABASE),
    PERIDOTITE(             GeoGroup.IGN_INTRUSIVE, GeoProperties.PERIDOTITE),
    KIMBERLITE(             GeoGroup.IGN_INTRUSIVE, GeoProperties.KIMBERLITE),

    // Igneous Extrusive AND Intrusive
    CARBONATITE(            GeoGroup.IGN_BOTH, GeoProperties.CARBONATITE),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE(              GeoGroup.METAMORPHIC, GeoProperties.QUARTZITE),
    SERPENTINITE(           GeoGroup.METAMORPHIC, GeoProperties.SERPENTINITE),
    GREENSCHIST(            GeoGroup.METAMORPHIC, GeoProperties.GREENSCHIST),
    SCHIST(                 GeoGroup.METAMORPHIC, GeoProperties.SCHIST),
    PHYLLITE(               GeoGroup.METAMORPHIC, GeoProperties.PHYLLITE),
    SLATE(                  GeoGroup.METAMORPHIC, GeoProperties.SLATE),
    GNEISS(                 GeoGroup.METAMORPHIC, GeoProperties.GNEISS),
    AMPHIBOLITE(            GeoGroup.METAMORPHIC, GeoProperties.AMPHIBOLITE),
    MARBLE(                 GeoGroup.METAMORPHIC, GeoProperties.MARBLE),
    PELITIC_HORNFELS(       GeoGroup.METAMORPHIC, GeoProperties.PELITIC_HORNFELS),
    CARBONATE_HORNFELS(     GeoGroup.METAMORPHIC, GeoProperties.CARBONATE_HORNFELS),
    MAFIC_HORNFELS(         GeoGroup.METAMORPHIC, GeoProperties.MAFIC_HORNFELS),
    METACONGLOMERATE(       GeoGroup.METAMORPHIC, GeoProperties.METACONGLOMERATE),
    GREISEN(                GeoGroup.METAMORPHIC, GeoProperties.GREISEN),
    BLUESCHIST(             GeoGroup.METAMORPHIC, GeoProperties.BLUESCHIST),
    ECLOGITE(               GeoGroup.METAMORPHIC, GeoProperties.ECLOGITE),

    // Stable Regolith
    DIRT(           GeoGroup.REGO_SOIL, GeoProperties.DIRT),
    COARSE_DIRT(    GeoGroup.REGO_SOIL, GeoProperties.COARSE_DIRT),
    CLAY(           GeoGroup.REGO_CLAY, GeoProperties.CLAY),
    LATERITE(       GeoGroup.REGO_SOIL, GeoProperties.LATERITE),

    // Falling Regolith
    SAND(           GeoGroup.REGO_SANDY, GeoProperties.SAND),
    RED_SAND(       GeoGroup.REGO_SANDY, GeoProperties.RED_SAND),
    PINK_SAND(      GeoGroup.REGO_SANDY, GeoProperties.PINK_SAND),
    BLACK_SAND(     GeoGroup.REGO_SANDY, GeoProperties.BLACK_SAND),
    WHITE_SAND(     GeoGroup.REGO_SANDY, GeoProperties.WHITE_SAND),
    GRAVEL(         GeoGroup.REGO_GRAVEL, GeoProperties.GRAVEL),
    DUNE_SAND(      GeoGroup.DESERT_SAND, GeoProperties.DUNE_SAND),
    GYPSUM_SAND(    GeoGroup.DESERT_SAND, GeoProperties.GYPSUM_SAND);

    /*
o
•	Bright white sand (calcite/aragonite) + limey gravels
•	Yellow sand (Quartz) + Sandy gravels
o

     */

    // Use this instead of values to avoid EMPTY
    public static final Set<GeoType> ALL_GEOTYPES = Sets.immutableEnumSet(EnumSet.complementOf(EnumSet.of(GeoType.EMPTY)));
    public static final Set<GeoType> ALL_STONE_GEOTYPES = Sets.immutableEnumSet(GeoGroup.getAllGeoTypesIn(GeoGroup.STONES));

    private final GeoGroup geoGroup;
    private final GeoProp geoProp;

    GeoType(GeoGroup geoGroup, GeoProp geoProp) {
        this.geoGroup = geoGroup;
        this.geoProp = geoProp;
    }

    public static GeoType fromString(String name) {
        return valueOf(name.toUpperCase(Locale.ENGLISH).replace('-', '$'));
    }

    //////////////////////////////////////
    //              GETTERS             //
    //////////////////////////////////////

    public String getName() { return toString().toLowerCase(Locale.ENGLISH).replace('$', '-'); }
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