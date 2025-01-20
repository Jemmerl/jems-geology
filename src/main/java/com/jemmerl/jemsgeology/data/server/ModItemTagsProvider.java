package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Jem's Geo Item Tags";
    }

    @Override
    protected void registerTags() {
        Builder<Item> tagBuilderGeoRocks = this.getOrCreateBuilder(ModTags.Items.JEMSGEO_ROCKS);
//        Builder<Item> tagBuilderPoorOre = this.getOrCreateBuilder(ModTags.Items.JEMSGEO_POOR_ORE);
//        Builder<Item> tagBuilderGoodOre = this.getOrCreateBuilder(ModTags.Items.JEMSGEO_GOOD_ORE);
//        Builder<Item> tagBuilderAllOre = this.getOrCreateBuilder(ModTags.Items.JEMSGEO_ALL_ORE);

        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            if (geoRegistry.hasCobble()) {
                tagBuilderGeoRocks.add(geoRegistry.getRockItem());
            }
        }

//        for (RegistryObject<Item> regItem : ModItems.ITEMS.getEntries()) {
//            String path = regItem.getId().getPath();
//            if (path.contains("_ore")) {
//                if (path.contains("poor_")) {
//                    tagBuilderPoorOre.add(regItem.get());
//                } else {
//                    tagBuilderGoodOre.add(regItem.get());
//                }
//            }
//        }
//
//        tagBuilderAllOre.addTag(ModTags.Items.JEMSGEO_POOR_ORE);
//        tagBuilderAllOre.addTag(ModTags.Items.JEMSGEO_GOOD_ORE);
    }
}
