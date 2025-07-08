package com.jemmerl.jemsgeology.data.client;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import org.codehaus.plexus.util.StringUtils;

import java.util.Locale;
import java.util.Objects;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {

        // Misc items and stuff
        add("block." + JemsGeology.MOD_ID + ".lichen_block", "Lichen");
        add("block." + JemsGeology.MOD_ID + ".desert_varnish", "Desert Varnish");
        add("block." + JemsGeology.MOD_ID + ".salt_crust", "Salt Crust");
        add("item." + JemsGeology.MOD_ID + ".lime_mortar", "Lime Mortar");
        add("item." + JemsGeology.MOD_ID + ".wooden_quarry_tool", "Wooden Quarrying Chisel");
        add("item." + JemsGeology.MOD_ID + ".stone_quarry_tool", "Stone Quarrying Chisel");
        add("item." + JemsGeology.MOD_ID + ".iron_quarry_tool", "Iron Quarrying Chisel");
        add("item." + JemsGeology.MOD_ID + ".golden_quarry_tool", "Golden Quarrying Chisel");
        add("item." + JemsGeology.MOD_ID + ".diamond_quarry_tool", "Diamond Quarrying Chisel");
        add("item." + JemsGeology.MOD_ID + ".netherite_quarry_tool", "Netherite Quarrying Chisel");
        add("itemGroup.jemsgeo_base_stones_tab", "Jem's Geology: Stones");
        add("itemGroup.jemsgeo_decor_stones_tab", "Jem's Geology: Decor");
        add("itemGroup.jemsgeo_cobbles_tab", "Jem's Geology: Cobbles");
        add("itemGroup.jemsgeo_ores_tab", "Jem's Geology: Ore Items");
        add("itemGroup.jemsgeo_misc_tab", "Jem's Geology: Miscellaneous");
        add("itemGroup.jemsgeo_ore_blocks_tab", "Jem's Geology: Ore Blocks");
        add("tooltip.all." + JemsGeology.MOD_ID + ".shiftinfo", "\u00A77Hold\u00A7r \u00A7eSHIFT\u00A7r \u00A77for details...\u00A7r"); // Might be used again in future

        // Items
        for (RegistryObject<Item> itemReg: ModItems.ITEMS.getEntries()) {
            Item item = itemReg.get();
            if (item.getRegistryName().getPath().contains("poor_")){
                namePoorOre(item);
            } else if (item.getRegistryName().getPath().contains("_ore")) {
                nameNormalOre(item);
            } else if  (item.getRegistryName().getPath().contains("_rock")) {
                nameRockItem(item);
            }
        }

        // Blocks
        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            if (geoRegistry.getGeoType().getGeoGroup().isRegolith()) {
                for (Block block: geoRegistry.getAllGeoBlocks()) {
                    nameRegolithOreBlock(block);
                }
            } else {
                for (Block block: geoRegistry.getAllGeoBlocks()) {
                    nameGeoOreBlock(block);
                }
            }

            if (geoRegistry.hasCobble()) {
                nameCobblesBlock(geoRegistry.getCobbles());
                nameCobblestoneBlock(geoRegistry.getCobblestone());

                for (Block block: geoRegistry.getDecorBlocks()) {
                    nameStoneDecorBlock(block);
                }
            }
        }
    }


    //////////////////////
    // ENTRY GENERATORS //
    //////////////////////

    // Normal Ore Items
    private void nameNormalOre (Item item) {
        String path = Objects.requireNonNull(item.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String oreName = path.split("_ore", 2)[0].replace('_', ' ');
        String displayName = StringUtils.capitaliseAllWords(oreName);
        add(item, displayName);
    }

    // Poor Ore Items
    private void namePoorOre (Item item) {
        String path = Objects.requireNonNull(item.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String oreName = path.split("poor_", 2)[1].split("_ore", 2)[0].replace('_', ' ');
        String displayName = "Small " + StringUtils.capitaliseAllWords(oreName);
        add(item, displayName);
    }

    // Rock Items
    private void nameRockItem (Item item) {
        String path = Objects.requireNonNull(item.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String rockName = path.split("_rock", 2)[0].replace('_', ' ');
        String displayName = StringUtils.capitaliseAllWords(rockName) + " Cobble";
        add(item, displayName);
    }

    // Cobble Blocks
    private void nameCobblesBlock (Block block) {
        String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String displayName = "Loose " + StringUtils.capitaliseAllWords(path.replace('_', ' '));
        add(block, displayName);
    }

    // Cobblestone Blocks
    private void nameCobblestoneBlock (Block block) {
        String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String displayName = StringUtils.capitaliseAllWords(path.replace('_', ' '));
        add(block, displayName);
    }

    // Stone Blocks
    private void nameGeoOreBlock(Block block) {
        String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String displayName;
        if (path.contains("grade")) {
            String[] dividePath = path.split("/", 3);
            String stoneName = StringUtils.capitaliseAllWords(dividePath[0].split("_stone", 2)[0].replace('_', ' '));
            String oreName = StringUtils.capitaliseAllWords(dividePath[1].replace('_', ' '));
            if (dividePath[2].contains("low")) {
                displayName = "Poor " + oreName + " " + stoneName;
            } else {
                displayName = oreName + " " + stoneName;
            }
        } else {
            String[] dividePath = path.split("_stone", 2);
            displayName = StringUtils.capitaliseAllWords(dividePath[0].replace('_', ' '));
        }
        add(block, displayName);
    }

    // Detritus Blocks
    private void nameRegolithOreBlock(Block block) {
        String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String displayName;
        if (path.contains("grade")) {
            String[] dividePath = path.split("/", 3);
            String detritusName = StringUtils.capitaliseAllWords(dividePath[0].split("_rego", 2)[0].replace('_', ' '));
            String oreName = StringUtils.capitaliseAllWords(dividePath[1].replace('_', ' '));
            if (dividePath[2].contains("low")) {
                displayName = "Poor " + oreName + " " + detritusName;
            } else {
                displayName = oreName + " " + detritusName;
            }
        } else {
            String[] dividePath = path.split("_rego", 2);
            displayName = StringUtils.capitaliseAllWords(dividePath[0].replace('_', ' '));
        }
        add(block, displayName);
    }

    // Stone Decor Blocks
    private void nameStoneDecorBlock(Block block) {
        String path = Objects.requireNonNull(block.getRegistryName()).getPath().toLowerCase(Locale.ENGLISH);
        String displayName;
        if (path.contains("_stone")) {
            displayName = StringUtils.capitaliseAllWords(path.split("_stone", 2)[0].replace('_', ' '));
        } else {
            displayName = StringUtils.capitaliseAllWords(path.replace('_', ' '));
        }
        add(block, displayName);
    }

}
