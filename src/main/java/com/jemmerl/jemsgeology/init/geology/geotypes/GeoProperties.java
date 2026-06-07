package com.jemmerl.jemsgeology.init.geology.geotypes;

import com.jemmerl.jemsgeology.init.geology.geotypes.props.GeoProp;
import com.jemmerl.jemsgeology.init.geology.geotypes.props.Hardness;
import com.jemmerl.jemsgeology.init.geology.ores.GeoLoot;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.RandomValueRange;

public class GeoProperties {
    // contain all the enum property stuff here, for cleaner enum code
    // also, add a property for ore weathering? certain minerals will not survive saproite weathering, like garnet

    // Empty
    public static final GeoProp EMPTY = new GeoProp(Hardness.FRAGILE, MaterialColor.PURPLE, GeoLoot.EMPTY);

    // Sedimentary
    public static final GeoProp CHALK = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp LIMESTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp DOLOMITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp CALCITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ROCKSALT = new GeoProp(Hardness.SOFT, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,5), ModItems.ROCKSALT_ORE));
    public static final GeoProp GYPSUM = new GeoProp(Hardness.SOFT, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,5), ModItems.GYPSUM_ORE));
    public static final GeoProp MARLSTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SHALE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp LIMY_SHALE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SANDSTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp RED_SANDSTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ARKOSE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GREYWACKE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp MUDSTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp CLAYSTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SILTSTONE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp CONGLOMERATE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp LIGNITE_COAL = new GeoProp(Hardness.SOFT, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(1,3), ModItems.LIGNITE_COAL_ORE));
    public static final GeoProp SUBBITUMINOUS_COAL = new GeoProp(Hardness.SOFT, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(2,4), ModItems.SUBBITUMINOUS_COAL_ORE));
    public static final GeoProp BITUMINOUS_COAL = new GeoProp(Hardness.SOFT, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(3,5), ModItems.BITUMINOUS_COAL_ORE));
    public static final GeoProp ANTHRACITE_COAL = new GeoProp(Hardness.SOFT, MaterialColor.STONE, new GeoLoot(RandomValueRange.of(3,5), ModItems.ANTHRACITE_COAL_ORE));

    // Hydrothermal
    public static final GeoProp QUARTZ = new GeoProp(Hardness.HARD, MaterialColor.QUARTZ);

    // Extrusive Igneous
    public static final GeoProp GRAY_RHYOLITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    // TODO gray rhyolite will be the "minecraft stone" analog, used in structure block replacements
    public static final GeoProp PINK_RHYOLITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp DACITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ANDESITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp TRACHYTE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp BASALT = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SCORIA = new GeoProp(Hardness.SOFT, MaterialColor.STONE);
    public static final GeoProp RHYOLITIC_TUFF = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp TRACHYTIC_TUFF = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ANDESITIC_TUFF = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp BASALTIC_TUFF = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ULTRAMAFIC_TUFF = new GeoProp(Hardness.HARD, MaterialColor.STONE);

    // Intrusive Igneous
    public static final GeoProp DIORITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GRANODIORITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GRANITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SYENITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GABBRO = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ANORTHOSITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp DIABASE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp PERIDOTITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp KIMBERLITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);

    // Both Igneous
    public static final GeoProp CARBONATITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);

    // Metamorphic
    public static final GeoProp QUARTZITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SERPENTINITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GREENSCHIST = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SCHIST = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp PHYLLITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp SLATE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GNEISS = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp AMPHIBOLITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp MARBLE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp PELITIC_HORNFELS = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp CARBONATE_HORNFELS = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp MAFIC_HORNFELS = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp METACONGLOMERATE = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp GREISEN = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp BLUESCHIST = new GeoProp(Hardness.HARD, MaterialColor.STONE);
    public static final GeoProp ECLOGITE = new GeoProp(Hardness.HARD, MaterialColor.STONE);

    // Stable Regolith
    public static final GeoProp DIRT = new GeoProp(Hardness.FRAGILE, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.DIRT::asItem));
    public static final GeoProp COARSE_DIRT = new GeoProp(Hardness.FRAGILE, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.COARSE_DIRT::asItem));
    public static final GeoProp CLAY = new GeoProp(Hardness.FRAGILE, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.CLAY::asItem));
    public static final GeoProp LATERITE = new GeoProp(Hardness.HARD, MaterialColor.DIRT);

    // Falling Regolith
    public static final GeoProp SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND, new GeoLoot(ConstantRange.of(1), Blocks.SAND::asItem));
    public static final GeoProp RED_SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND, new GeoLoot(ConstantRange.of(1), Blocks.RED_SAND::asItem));
    public static final GeoProp PINK_SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND);
    public static final GeoProp BLACK_SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND);
    public static final GeoProp WHITE_SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND);
    public static final GeoProp GRAVEL = new GeoProp(Hardness.FRAGILE, MaterialColor.DIRT, new GeoLoot(ConstantRange.of(1), Blocks.GRAVEL::asItem));
    public static final GeoProp DUNE_SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND);
    public static final GeoProp GYPSUM_SAND = new GeoProp(Hardness.FRAGILE, MaterialColor.SAND);


}