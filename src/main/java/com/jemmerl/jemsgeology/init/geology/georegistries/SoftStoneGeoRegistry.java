package com.jemmerl.jemsgeology.init.geology.georegistries;

import com.jemmerl.jemsgeology.geology.geos.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SoftStoneGeoRegistry extends BaseGeoRegistry {

    //TODO add mossycobbles block? Not cobblestone.

    private final RegistryObject<Block> cobbles;
    private final Supplier<Item> rockItem;

    private final RegistryObject<Block> rawSlab;
    private final RegistryObject<Block> rawStairs;
    
    public SoftStoneGeoRegistry(GeoType geoType) {
        super(geoType);
        rockItem = ModItems.registerRockItem(geoType);
        cobbles = ModBlocks.registerCobblesBlock(geoType);

        rawSlab = ModBlocks.registerRawStoneSlab(geoType);
        rawStairs = ModBlocks.registerRawStoneStairs(geoType,
                () -> super.getBaseGeoBlock().getDefaultState());
    }

    public SoftStoneGeoRegistry(GeoType geoType, Supplier<Item> customRockItem) {
        super(geoType);
        rockItem = customRockItem;
        cobbles = ModBlocks.registerCobblesBlock(geoType);

        rawSlab = ModBlocks.registerRawStoneSlab(geoType);
        rawStairs = ModBlocks.registerRawStoneStairs(geoType,
                () -> super.getBaseGeoBlock().getDefaultState());
    }

    public Item getRockItem() { return rockItem.get(); }
    public Block getCobbles() { return cobbles.get(); }

    public Block getRawSlab() { return this.rawSlab.get(); }
    public Block getRawStairs() { return this.rawStairs.get(); }


    public Item getMainDropItem() {
        return getRockItem();
    }


    public List<Block> getDecorBlocks() {
        List<Block> allDecorBlocks = new ArrayList<>();
        allDecorBlocks.add(rawSlab.get());
        allDecorBlocks.add(rawStairs.get());

        return allDecorBlocks;
    }
}
