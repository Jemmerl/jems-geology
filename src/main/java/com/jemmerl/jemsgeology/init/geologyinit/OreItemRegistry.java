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
        this.oreItem = (oreType.getName() == "diamond") ? null : ModItems.registerOreItem(oreType);
        this.poorOreItem = oreType.hasPoorOre() ? ModItems.registerPoorOreItem(oreType) : null;
    }

    public Item getOreItem() {
        return (oreType.getName() == "diamond") ? Items.DIAMOND : oreItem.get();
    }
    public Item getPoorOreItem() {
        return oreType.hasPoorOre() ? poorOreItem.get() : oreItem.get();
    }

}
