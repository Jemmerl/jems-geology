package com.jemmerl.jemsgeology.geology.stones;

import com.jemmerl.jemsgeology.geology.ores.GeoLoot;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.RandomValueRange;

import java.util.Collections;
import java.util.List;

public enum GeoType {
    // surface weathering, subsurface weathering

    // Sedimentary
    CHALK("chalk",                              GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    LIMESTONE("limestone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    DOLOMITE("dolomite",                        GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    CALCITE("calcite",                          GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    MARLSTONE("marlstone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SHALE("shale",                              GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    LIMY_SHALE("limy_shale",                    GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SANDSTONE("sandstone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    RED_SANDSTONE("red_sandstone",              GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    ARKOSE("arkose",                            GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    GREYWACKE("greywacke",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    MUDSTONE("mudstone",                        GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    CLAYSTONE("claystone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SILTSTONE("siltstone",                      GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    CONGLOMERATE("conglomerate",                GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    LIGNITE_COAL("lignite_coal",                GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(1,3), ModItems.LIGNITE_COAL)),
    SUBBITUMINOUS_COAL("sub-bituminous_coal",   GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,4), ModItems.SUBBITUMINOUS_COAL)),
    BITUMINOUS_COAL("bituminous_coal",          GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(3,5), ModItems.BITUMINOUS_COAL)),
    ANTHRACITE_COAL("anthracite_coal",          GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(3,5), ModItems.ANTHRACITE_COAL)),
    LATERITE("laterite",                        GeoGroup.SEDIMENTARY, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.DIRT),
    // Note: Laterites are most commonly clay-soils, but can be compacted into rocks, especially after exposure.
    //  Laterite rocks have even been used for construction, so it has been promoted.
    //  Laterite is registered as a sedimentary rock, but its primary generation form will be as its regolith.
    // TODO most laterites are, well, lateritic. But do not forget karst bauxite (also laterites kinda)
    //  also maybe rename laterite regolith manually to just laterite or laterite soil?

    // Hydrothermal
    QUARTZ("quartz",        GeoGroup.HYDROTHERMAL, WeatheringType.REGOLITH, WeatheringType.NONE, true, true, MaterialColor.QUARTZ),

    // Evaporites
    ROCKSALT("rocksalt",    GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,5), ModItems.ROCKSALT)),
    GYPSUM("gypsum",        GeoGroup.EVAPORITE, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,5), ModItems.GYPSUM)),

    // Extrusive Igneous
    GRAY_RHYOLITE("gray_rhyolite",      GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    PINK_RHYOLITE("pink_rhyolite",      GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    DACITE("dacite",                    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    ANDESITE("andesite",                GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    TRACHYTE("trachyte",                GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    BASALT("basalt",                    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SCORIA("scoria",                    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.NONE, false, true, MaterialColor.STONE),
    RHYOLITIC_TUFF("rhyolitic_tuff",    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    TRACHYTIC_TUFF("trachytic_tuff",    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    ANDESITIC_TUFF("andesitic_tuff",    GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    BASALTIC_TUFF("basaltic_tuff",      GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    ULTRAMAFIC_TUFF("ultramafic_tuff",  GeoGroup.EXTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),

    // Intrusive Igneous
    DIORITE("diorite",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    GRANODIORITE("granodiorite",    GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    GRANITE("granite",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SYENITE("syenite",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    GABBRO("gabbro",                GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    ANORTHOSITE("anorthosite",      GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    DIABASE("diabase",              GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    PERIDOTITE("peridotite",        GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    KIMBERLITE("kimberlite",        GeoGroup.INTRUSIVE, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),

    //TODO when there are multiple kinds of meta. rock that can form from a source (marl can be hornfel or amphibolite)
    //  then use regional values to pick

    // Metamorphic
    QUARTZITE("quartzite",                      GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SERPENTINE("serpentine",                    GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SCHIST("schist",                            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    PHYLLITE("phyllite",                        GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    SLATE("slate",                              GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    GNEISS("gneiss",                            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    AMPHIBOLITE("amphibolite",                  GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    MARBLE("marble",                            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    PELITIC_HORNFELS("pelitic_hornfels",        GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    CARBONATE_HORNFELS("carbonate_hornfels",    GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    MAFIC_HORNFELS("mafic_hornfels",            GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    METACONGLOMERATE("metaconglomerate",        GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),
    GREISEN("greisen",                          GeoGroup.METAMORPHIC, WeatheringType.NONE, WeatheringType.REGOLITH, true, true, MaterialColor.STONE),

    // Stable Detritus
    DIRT("dirt",                GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.DIRT::asItem)),
    COARSE_DIRT("coarse_dirt",  GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.COARSE_DIRT::asItem)),
    CLAY("clay",                GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.CLAY::asItem)),

    // Falling Detritus
    SAND("sand",            GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.SAND::asItem)),
    RED_SAND("red_sand",    GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.RED_SAND::asItem)),
    GRAVEL("gravel",        GeoGroup.DETRITUS, WeatheringType.NONE, WeatheringType.NONE, false, false, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.GRAVEL::asItem));

    private final String name;
    private final GeoGroup geoGroup;
    private final WeatheringType surfaceWeathering;
    private final WeatheringType subSurfaceWeathering;
    private final boolean hasRegolith;
    private final boolean hasCobble;
    private final MaterialColor materialColor;
    private final GeoLoot geoLoot;

    GeoType(String name, GeoGroup geoGroup, WeatheringType surfaceWeathering, WeatheringType subSurfaceWeathering, boolean hasRegolith, boolean hasCobble, MaterialColor materialColor) {
        this(name, geoGroup, surfaceWeathering, subSurfaceWeathering, hasRegolith, hasCobble, materialColor, GeoLoot.BASIC_ROCKS);
    }

    GeoType(String name, GeoGroup geoGroup, WeatheringType surfaceWeathering, WeatheringType subSurfaceWeathering, boolean hasRegolith, boolean hasCobble, MaterialColor materialColor, GeoLoot geoLoot) {
        this.name = name;
        this.geoGroup = geoGroup;
        this.surfaceWeathering = surfaceWeathering;
        this.subSurfaceWeathering = subSurfaceWeathering;
        this.hasRegolith = hasRegolith;
        this.hasCobble = hasCobble;
        this.materialColor = materialColor;
        this.geoLoot = geoLoot;
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

    public boolean hasRegolith() {
        return hasRegolith;
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
