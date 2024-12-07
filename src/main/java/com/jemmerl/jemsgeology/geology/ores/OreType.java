package com.jemmerl.jemsgeology.geology.ores;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Locale;
import java.util.Objects;

public class OreType /*implements IStringSerializable*/ {

    private final String name;
    private final String source;
    private final boolean hasPoorOre;

    private final boolean hasPresetOre;
    private final boolean hasPresetPoorOre;
    private final Item presetOreItem;
    private final Item presetPoorOreItem;

    public OreType(String name, String source, boolean hasPoorOre) {
        this(name, source, hasPoorOre, false, Items.AIR, false, Items.AIR);
    }

    public OreType(String name, String source, boolean hasPoorOre, boolean hasPresetOre, Item presetOreItem) {
        this(name, source, hasPoorOre, hasPresetOre, presetOreItem, false, Items.AIR);
    }

    public OreType(String name, String source, boolean hasPoorOre, boolean hasPresetOre, Item presetOreItem, boolean hasPresetPoorOre, Item presetPoorOreItem) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.source = source;
        this.hasPoorOre = hasPoorOre;

        this.hasPresetOre = hasPresetOre;
        this.presetOreItem = presetOreItem;
        this.hasPresetPoorOre = hasPresetPoorOre;
        this.presetPoorOreItem = presetPoorOreItem;

        if (!hasPoorOre && hasPresetPoorOre) {
            JemsGeology.LOGGER.warn("OreType \"" + name + "\" from source \"" + source + "\" has a pre-set poor ore item without enabling poor ore.");
        }
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public boolean hasPoorOre() {
        return hasPoorOre;
    }

    public boolean hasOre() {
        return !Objects.equals(name, "none");
    }

    public boolean hasPresetOre() {
        return hasPresetOre;
    }

    public boolean hasPresetPoorOre() {
        return hasPresetPoorOre;
    }

    public Item getPresetOreItem() {
        return presetOreItem;
    }

    public Item getPresetPoorOreItem() {
        return presetPoorOreItem;
    }

    public Item getOredItem(boolean poorOre) {
        if (poorOre) {
            return hasPresetPoorOre ? presetPoorOreItem : ModItems.ORE_ITEMS.get(this).getOreItem(true);
        }
        return hasPresetOre ? presetOreItem : ModItems.ORE_ITEMS.get(this).getOreItem(false);
    }

    public Item getOreItem(boolean poorOre) {
        if (poorOre) {
            if (hasPresetPoorOre) return presetPoorOreItem;
        } else {
            if (hasPresetOre) return presetOreItem;
        }
        return ModItems.ORE_ITEMS.get(this).getOreItem(poorOre);
    }

//    public Item getPoorOreItem() {
//        return hasPresetPoorOre ? presetPoorOreItem : ModItems.ORE_ITEMS.get(this).getPoorOreItem();
//    }
}
