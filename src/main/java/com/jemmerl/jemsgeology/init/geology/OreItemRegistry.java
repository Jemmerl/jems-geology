package com.jemmerl.jemsgeology.init.geology;

import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class OreItemRegistry {

    private final OreType oreType;
    private final RegistryObject<Item> oreItem;
    private final RegistryObject<Item> poorOreItem;

    public OreItemRegistry(OreType oreType) {
        this.oreType = oreType;
        this.oreItem = oreType.getGeoLoot().hasPresetDrop() ? null : ModItems.registerOreItem(oreType);
        this.poorOreItem = (oreType.getPoorGeoLoot().hasPresetDrop() || !oreType.hasPoorOre()) ? null : ModItems.registerPoorOreItem(oreType);
    }

    public Item getOreItem(boolean poorOre) {
        if (poorOre && oreType.hasPoorOre()) {
            return oreType.getPoorGeoLoot().hasPresetDrop() ? oreType.getPoorGeoLoot().getPresetDrop() : poorOreItem.get();
        }
        return oreType.getGeoLoot().hasPresetDrop() ? oreType.getGeoLoot().getPresetDrop() : oreItem.get();
    }

}
