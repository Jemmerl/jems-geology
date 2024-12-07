package com.jemmerl.jemsgeology.init.geologyinit;

import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;

public class OreItemRegistry {

    private final OreType oreType;
    private final RegistryObject<Item> oreItem;
    private final RegistryObject<Item> poorOreItem;

    public OreItemRegistry(OreType oreType) {
        this.oreType = oreType;
        this.oreItem = oreType.hasPresetOre() ? null : ModItems.registerOreItem(oreType);
        this.poorOreItem = (oreType.hasPresetPoorOre() || !oreType.hasPoorOre()) ? null : ModItems.registerPoorOreItem(oreType);
    }

    public Item getOreItem(boolean poorOre) {
        if (poorOre && oreType.hasPoorOre()) {
            return oreType.hasPresetPoorOre() ? oreType.getPresetPoorOreItem() : poorOreItem.get();
        }
        return oreType.hasPresetOre() ? oreType.getPresetOreItem() : oreItem.get();
    }

}
