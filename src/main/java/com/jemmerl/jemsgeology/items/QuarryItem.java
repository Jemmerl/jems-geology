package com.jemmerl.jemsgeology.items;

import com.jemmerl.jemsgeology.init.ModItemGroups;
import net.minecraft.item.ItemTier;
import net.minecraft.item.TieredItem;

public class QuarryItem extends TieredItem {
    public QuarryItem(ItemTier tierIn) {
        super(tierIn, new Properties().group(ModItemGroups.JEMGEO_MISC_GROUP));
    }
}
