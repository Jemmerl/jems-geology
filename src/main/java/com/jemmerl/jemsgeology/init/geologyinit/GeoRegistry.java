package com.jemmerl.jemsgeology.init.geologyinit;

import com.google.common.collect.Maps;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;

public class GeoRegistry {

    private final GeoType geoType;
    private final boolean hasCobble;

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private final RegistryObject<Item> rockItem;

    private final LinkedHashMap<OreType, OreBlockRegistry> stoneOreRegistry;
    private final LinkedHashMap<OreType, OreBlockRegistry> regolithOreRegistry;

    private final RegistryObject<Block> rawSlab;
    private final RegistryObject<Block> rawStairs;
    private final RegistryObject<Block> rawWall;
    private final RegistryObject<Block> cobbleSlab;
    private final RegistryObject<Block> cobbleStairs;
    private final RegistryObject<Block> cobbleWall;
    private final RegistryObject<Block> polishedStone;
    private final RegistryObject<Block> polishedSlab;
    private final RegistryObject<Block> polishedStairs;

    public GeoRegistry(GeoType geoType) {
        this.geoType = geoType;
        hasCobble = geoType.hasCobble();

        baseStone = ModBlocks.registerStoneGeoBlock(geoType);
        regolith = hasCobble ? ModBlocks.registerRegolithGeoBlock(geoType) : null;
        cobbles = hasCobble ? ModBlocks.registerCobblesBlock(geoType) : null;
        cobblestone = hasCobble ? ModBlocks.registerCobblestoneBlock(geoType) : null;
        rockItem = hasCobble ? ModItems.registerRockItem(geoType) : null;

        rawSlab = hasCobble ? ModBlocks.registerRawStoneSlab(geoType) : null;
        rawStairs = hasCobble ? ModBlocks.registerRawStoneStairs(geoType,
                () -> baseStone.get().getDefaultState()) : null;
        rawWall = hasCobble ? ModBlocks.registerRawStoneWall(geoType) : null;
        cobbleSlab = hasCobble ? ModBlocks.registerCobbleSlab(geoType) : null;
        cobbleStairs = hasCobble ? ModBlocks.registerCobbleStairs(geoType,
                () -> cobblestone.get().getDefaultState()) : null;
        cobbleWall = hasCobble ? ModBlocks.registerCobbleWall(geoType) : null;
        polishedStone = hasCobble ? ModBlocks.registerPolishedStoneBlock(geoType) : null;
        polishedSlab = hasCobble ? ModBlocks.registerPolishedSlab(geoType) : null;
        polishedStairs = hasCobble ? ModBlocks.registerPolishedStairs(geoType,
                () -> polishedStone.get().getDefaultState()) : null;

        this.stoneOreRegistry =  fillOreRegistry(geoType, false);
        this.regolithOreRegistry =  hasCobble ? fillOreRegistry(geoType, true) : Maps.newLinkedHashMap();
    }


    //////////////////////////////
    //          GETTERS         //
    //////////////////////////////

    public Block getBaseStone() { return baseStone.get(); }
    public BlockState getBaseState() { return getBaseStone().getDefaultState(); } // Makes some stuff cleaner :)

    public Block getStoneOre(OreType oreType, Grade grade) {
        if (oreType.hasOre()) {
            switch (grade) {
                case NORMAL:
                    return stoneOreRegistry.get(oreType).getNormalOreBlock().get();
                case POOR:
                    return stoneOreRegistry.get(oreType).getPoorOreBlock().get();
                case NONE:
                default:
            }
        }
        return baseStone.get();
    }

    public Block getRegolithOre(OreType oreType, Grade grade) {
        if (!hasCobble) return getStoneOre(oreType, grade);

        if (oreType.hasOre()) {
            switch (grade) {
                case NORMAL:
                    return regolithOreRegistry.get(oreType).getNormalOreBlock().get();
                case POOR:
                    return regolithOreRegistry.get(oreType).getPoorOreBlock().get();
                case NONE:
                default:
            }
        }
        return regolith.get();
    }

    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolith() {
        return hasCobble ? regolith.get() : baseStone.get();
    }
    public Block getCobbles() { return cobbles.get(); }
    public Block getCobblestone() { return cobblestone.get(); }
    public Item getRockItem() { return rockItem.get(); }

    public Block getRawSlab() { return this.rawSlab.get(); }
    public Block getRawStairs() { return this.rawStairs.get(); }
    public Block getRawWall() { return this.rawWall.get(); }
    public Block getCobbleSlab() { return this.cobbleSlab.get(); }
    public Block getCobbleStairs() { return this.cobbleStairs.get(); }
    public Block getCobbleWall() { return this.cobbleWall.get(); }
    public Block getPolishedStone() { return this.polishedStone.get(); }
    public Block getPolishedSlab() { return this.polishedSlab.get(); }
    public Block getPolishedStairs() { return this.polishedStairs.get(); }


    //////////////////////////////
    //          SETTERS         //
    //////////////////////////////

    private  LinkedHashMap<OreType, OreBlockRegistry> fillOreRegistry(GeoType geoType, boolean isRegolith) {
        LinkedHashMap<OreType, OreBlockRegistry> oreMap = new LinkedHashMap<>();
        for (OreType oreType : ModBlocks.REGISTERED_ORES.values()) {
            oreMap.put(oreType, new OreBlockRegistry(geoType, oreType, isRegolith));
        }
        return oreMap;
    }


    ///////////////////////////////
    //          DATA-GEN         //
    ///////////////////////////////

}
