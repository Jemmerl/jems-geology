package com.jemmerl.jemsgeology.geology.geoblocks;

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
    CHALK("chalk",                              GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    LIMESTONE("limestone",                      GeoGroup.SED_EVAPORITE, true, MaterialColor.STONE),
    DOLOMITE("dolomite",                        GeoGroup.SED_EVAP_HYDRO, true, MaterialColor.STONE),
    CALCITE("calcite",                          GeoGroup.SED_EVAP_HYDRO, true, MaterialColor.STONE),
    ROCKSALT("rocksalt",                        GeoGroup.SED_EVAPORITE, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,5), ModItems.ROCKSALT)),
    GYPSUM("gypsum",                            GeoGroup.SED_EVAPORITE, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,5), ModItems.GYPSUM)),
    MARLSTONE("marlstone",                      GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    SHALE("shale",                              GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    LIMY_SHALE("limy_shale",                    GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    SANDSTONE("sandstone",                      GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    RED_SANDSTONE("red_sandstone",              GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    ARKOSE("arkose",                            GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    GREYWACKE("greywacke",                      GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    MUDSTONE("mudstone",                        GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    CLAYSTONE("claystone",                      GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    SILTSTONE("siltstone",                      GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    CONGLOMERATE("conglomerate",                GeoGroup.SEDIMENTARY, true, MaterialColor.STONE),
    LIGNITE_COAL("lignite_coal",                GeoGroup.SEDIMENTARY, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(1,3), ModItems.LIGNITE_COAL)),
    SUBBITUMINOUS_COAL("sub-bituminous_coal",   GeoGroup.SEDIMENTARY, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,4), ModItems.SUBBITUMINOUS_COAL)),
    BITUMINOUS_COAL("bituminous_coal",          GeoGroup.SEDIMENTARY, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(3,5), ModItems.BITUMINOUS_COAL)),
    ANTHRACITE_COAL("anthracite_coal",          GeoGroup.SEDIMENTARY, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(3,5), ModItems.ANTHRACITE_COAL)),

    // Hydrothermal
    QUARTZ("quartz",                            GeoGroup.HYDROTHERMAL, true, MaterialColor.QUARTZ),

    // Extrusive Igneous
    GRAY_RHYOLITE("gray_rhyolite",              GeoGroup.IGN_EXTRUSIVE, true, MaterialColor.STONE),
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    PINK_RHYOLITE("pink_rhyolite",              GeoGroup.IGN_EXTRUSIVE, true, MaterialColor.STONE),
    DACITE("dacite",                            GeoGroup.IGN_EXTRUSIVE, true, MaterialColor.STONE),
    ANDESITE("andesite",                        GeoGroup.IGN_EXTRUSIVE, true, MaterialColor.STONE),
    TRACHYTE("trachyte",                        GeoGroup.IGN_EXTRUSIVE, true, MaterialColor.STONE),
    BASALT("basalt",                            GeoGroup.IGN_EXTRUSIVE, true, MaterialColor.STONE),
    SCORIA("scoria",                            GeoGroup.IGN_EJECTA, false, MaterialColor.STONE),
    RHYOLITIC_TUFF("rhyolitic_tuff",            GeoGroup.IGN_EJECTA, true, MaterialColor.STONE),
    TRACHYTIC_TUFF("trachytic_tuff",            GeoGroup.IGN_EJECTA, true, MaterialColor.STONE),
    ANDESITIC_TUFF("andesitic_tuff",            GeoGroup.IGN_EJECTA, true, MaterialColor.STONE),
    BASALTIC_TUFF("basaltic_tuff",              GeoGroup.IGN_EJECTA, true, MaterialColor.STONE),
    ULTRAMAFIC_TUFF("ultramafic_tuff",          GeoGroup.IGN_EJECTA, true, MaterialColor.STONE),

    // Intrusive Igneous
    DIORITE("diorite",                          GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    GRANODIORITE("granodiorite",                GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    GRANITE("granite",                          GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    SYENITE("syenite",                          GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    GABBRO("gabbro",                            GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    ANORTHOSITE("anorthosite",                  GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    DIABASE("diabase",                          GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    PERIDOTITE("peridotite",                    GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),
    KIMBERLITE("kimberlite",                    GeoGroup.IGN_INTRUSIVE, true, MaterialColor.STONE),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE("quartzite",                      GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    SERPENTINITE("serpentinite",                GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    SCHIST("schist",                            GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    PHYLLITE("phyllite",                        GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    SLATE("slate",                              GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    GNEISS("gneiss",                            GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    AMPHIBOLITE("amphibolite",                  GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    MARBLE("marble",                            GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    PELITIC_HORNFELS("pelitic_hornfels",        GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    CARBONATE_HORNFELS("carbonate_hornfels",    GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    MAFIC_HORNFELS("mafic_hornfels",            GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    METACONGLOMERATE("metaconglomerate",        GeoGroup.METAMORPHIC, true, MaterialColor.STONE),
    GREISEN("greisen",                          GeoGroup.METAMORPHIC, true, MaterialColor.STONE),

    // Stable Regolith
    DIRT("dirt",                GeoGroup.REGO_SOIL, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.DIRT::asItem)),
    COARSE_DIRT("coarse_dirt",  GeoGroup.REGO_SOIL, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.COARSE_DIRT::asItem)),
    CLAY("clay",                GeoGroup.REGO_CLAY, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.CLAY::asItem)),
    LATERITE("laterite",        GeoGroup.REGO_SOIL, true, MaterialColor.DIRT), //TODO regolith needs overhaul (remove "Detritus")

    // Falling Regolith
    SAND("sand",                GeoGroup.REGO_SANDY, false, MaterialColor.SAND, new GeoLoot(ConstantRange.of(1), Blocks.SAND::asItem)),
    RED_SAND("red_sand",        GeoGroup.REGO_SANDY, false, MaterialColor.SAND, new GeoLoot(ConstantRange.of(1), Blocks.RED_SAND::asItem)),
    PINK_SAND("pink_sand",      GeoGroup.REGO_SANDY, false, MaterialColor.SAND),
    BLACK_SAND("black_sand",    GeoGroup.REGO_SANDY, false, MaterialColor.SAND),
    WHITE_SAND("white_sand",    GeoGroup.REGO_SANDY, false, MaterialColor.SAND),
    GRAVEL("gravel",            GeoGroup.REGO_GRAVEL, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.GRAVEL::asItem)),
    DUNE_SAND("dune_sand",      GeoGroup.DESERT_SAND, false, MaterialColor.SAND),
    GYPSUM_SAND("gypsum_sand",  GeoGroup.DESERT_SAND, false, MaterialColor.SAND);

    /*
o
•	Bright white sand (calcite/aragonite) + limey gravels
•	Yellow sand (Quartz) + Sandy gravels
o

     */

    private final String name;
    private final GeoGroup geoGroup;
    private final boolean hasCobble;
    private final MaterialColor materialColor;
    private final GeoLoot geoLoot;

    GeoType(String name, GeoGroup geoGroup, boolean hasCobble, MaterialColor materialColor) {
        this(name, geoGroup, hasCobble, materialColor, GeoLoot.BASIC_ROCKS);
    }

    GeoType(String name, GeoGroup geoGroup, boolean hasCobble, MaterialColor materialColor, GeoLoot geoLoot) {
        this.name = name;
        this.geoGroup = geoGroup;
        this.hasCobble = hasCobble;
        this.materialColor = materialColor;
        this.geoLoot = geoLoot;
    }

    public static GeoType fromString(String name) {
        return valueOf(name.toUpperCase(Locale.ENGLISH));
    }

    private static class Constants {

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

    public boolean hasCobble() {
        return hasCobble;
    }

    public GeoLoot getGeoLoot() {
        return geoLoot;
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