package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup JEMSGEO_BASE_STONE_GROUP = new ItemGroup("jemsgeo_base_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT).getBaseGeoBlock().asItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMSGEO_COBBLE_GROUP = new ItemGroup("jemsgeo_cobbles_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT).getCobbles().asItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMSGEO_DECOR_STONE_GROUP = new ItemGroup("jemsgeo_decor_stones_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT).getRawStairs().asItem().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMSGEO_ORES_GROUP = new ItemGroup("jemsgeo_ores_tab") {
        @Override
        public ItemStack createIcon() {
            return ModItems.ORE_ITEMS.get(ModGeoOres.MAGNETITE).getOreItem(false).getDefaultInstance();
        }
    };

    public static final ItemGroup JEMSGEO_MISC_GROUP = new ItemGroup("jemsgeo_misc_tab") {
        @Override
        public ItemStack createIcon() {
            return ModItems.IRON_QUARRY_TOOL.get().getDefaultInstance();
        }
    };

    public static final ItemGroup JEMSGEO_ORE_BLOCK_GROUP = new ItemGroup("jemsgeo_ore_blocks_tab") {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.GEO_BLOCKS.get(GeoType.BASALT)
                    .getOreVariant(ModGeoOres.OLIVINE, Grade.NORMAL).asItem().getDefaultInstance();
        }
    };

}