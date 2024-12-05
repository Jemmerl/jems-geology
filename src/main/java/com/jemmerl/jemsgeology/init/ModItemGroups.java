package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geologyinit.ModGeoOres;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup JEMGEO_BASE_STONE_GROUP = new ItemGroup("jemsgeo_base_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT).getBaseStone().asItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMSGEO_DECOR_STONE_GROUP = new ItemGroup("jemsgeo_decor_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT).getRawStairs().asItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMGEO_COBBLE_GROUP = new ItemGroup("jemsgeo_cobbles_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT).getRockItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMGEO_ORES_GROUP = new ItemGroup("jemsgeo_ores_tab") {
        @Override
        public ItemStack createIcon() {
            return ModItems.ORE_ITEMS.get(ModGeoOres.MAGNETITE).getOreItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMGEO_MISC_GROUP = new ItemGroup("jemsgeo_misc_tab") {
        @Override
        public ItemStack createIcon() {
            return ModItems.IRON_QUARRY_TOOL.get().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMGEO_ORE_BLOCK_GROUP = new ItemGroup("jemsgeo_ore_blocks_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT)
                    .getStoneOre(ModGeoOres.DIAMOND, Grade.NORMAL).asItem().getDefaultInstance();
        }
    };

}