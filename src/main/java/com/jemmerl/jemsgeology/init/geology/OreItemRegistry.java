package com.jemmerl.jemsgeology.init.geology;

import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class OreItemRegistry {

    private final OreType oreType;
    private final Supplier<Item> oreItem;
    private final Supplier<Item> poorOreItem;

    public OreItemRegistry(OreType oreType) {
        this.oreType = oreType;
        this.oreItem = oreType.getGeoLoot().hasPresetDrop() ? oreType.getGeoLoot().getPresetDrop() : ModItems.registerOreItem(oreType);
        this.poorOreItem = (oreType.getPoorGeoLoot().hasPresetDrop() || !oreType.hasPoorOre())
                ? oreType.getPoorGeoLoot().getPresetDrop() : ModItems.registerPoorOreItem(oreType);
    }

    public Item getOreItem(boolean poorOre) {
        return ((poorOre && oreType.hasPoorOre()) ? poorOreItem : oreItem).get();
    }

}
