package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {

        // forge.tag stone (forge tag?)
        // forge.tag cobblestones

        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_STONE = createTag("stones");
        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_REGOLITH = createTag("regolith");
        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_COBBLES = createTag("cobbles");
        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_COBBLESTONE = createTag("cobblestone");
//        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_ORE = createTag("ore_blocks");
////        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_ORE_HIGH = createTag("highgrade");
//        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_ORE_MID = createTag("midgrade");
//        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_ORE_LOW = createTag("lowgrade");
        public static final Tags.IOptionalNamedTag<Block> JEMSGEO_NO_ORE = createTag("barren");

        public static final Tags.IOptionalNamedTag<Block> VANILLA_DETRITUS = createTag("vanilla_detritus"); // I am not sure this is even used anymore
        public static final Tags.IOptionalNamedTag<Block> COBBLES_CAN_BREAK = createTag("jemsgeo_cobbles_break"); // Cobbles cannot be supported by...
        public static final Tags.IOptionalNamedTag<Block> COBBLES_CAN_SMASH = createTag("jemsgeo_cobbles_smash"); // Cobbles cannot be STOPPED by...

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(JemsGeology.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        // Example
        // public static final Tags.IOptionalNamedTag<Item> AMETHYST = createForgeTag("gems/amethyst");

        public static final Tags.IOptionalNamedTag<Item> JEMSGEO_ROCKS = createTag("rocks");
//        public static final Tags.IOptionalNamedTag<Item> JEMSGEO_POOR_ORE = createTag("poor_ores");
//        public static final Tags.IOptionalNamedTag<Item> JEMSGEO_GOOD_ORE = createTag("good_ores");
//        public static final Tags.IOptionalNamedTag<Item> JEMSGEO_ALL_ORE = createTag("ores");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(JemsGeology.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}
