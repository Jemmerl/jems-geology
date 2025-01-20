package com.jemmerl.jemsgeology.init.geology;

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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeoRegistry {

    private final GeoType geoType;
    private final boolean hasRegolith;
    private final boolean hasCobble;

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Item> rockItem;

    private final LinkedHashMap<OreType, OreBlockRegistry> stoneOreRegistry;
    private final LinkedHashMap<OreType, OreBlockRegistry> regolithOreRegistry;

    private final RegistryObject<Block> rawSlab;
    private final RegistryObject<Block> rawStairs;
    private final RegistryObject<Block> rawWall;

    private final RegistryObject<Block> cobblestone;
    private final RegistryObject<Block> cobbleSlab;
    private final RegistryObject<Block> cobbleStairs;
    private final RegistryObject<Block> cobbleWall;

    private final RegistryObject<Block> mossyCobblestone;
    private final RegistryObject<Block> mossyCobbleSlab;
    private final RegistryObject<Block> mossyCobbleStairs;
    private final RegistryObject<Block> mossyCobbleWall;

    private final RegistryObject<Block> polishedStone;
    private final RegistryObject<Block> polishedSlab;
    private final RegistryObject<Block> polishedStairs;
    private final RegistryObject<Block> polishedWall;

    private final RegistryObject<Block> bricks;
    private final RegistryObject<Block> brickSlab;
    private final RegistryObject<Block> brickStairs;
    private final RegistryObject<Block> brickWall;

    private final RegistryObject<Block> mossyBricks;
    private final RegistryObject<Block> mossyBrickSlab;
    private final RegistryObject<Block> mossyBrickStairs;
    private final RegistryObject<Block> mossyBrickWall;

    private final RegistryObject<Block> chiseled;
    private final RegistryObject<Block> cracked;
    private final RegistryObject<Block> pillar;
    private final RegistryObject<Block> button;
    private final RegistryObject<Block> pressureplate;


    public GeoRegistry(GeoType geoType) {
        this.geoType = geoType;
        hasRegolith = geoType.hasRegolith();
        hasCobble = geoType.hasCobble();

        baseStone = geoType.getGeoGroup().isDetritus() ?
                ModBlocks.registerDetritusGeoBlock(geoType) : ModBlocks.registerStoneGeoBlock(geoType);
        regolith = hasRegolith ? ModBlocks.registerRegolithGeoBlock(geoType) : null;

        this.stoneOreRegistry =  fillOreRegistry(geoType, false);
        this.regolithOreRegistry = hasRegolith ? fillOreRegistry(geoType, true) : Maps.newLinkedHashMap();

        rockItem = hasCobble ? ModItems.registerRockItem(geoType) : null;

        cobbles = hasCobble ? ModBlocks.registerCobblesBlock(geoType) : null;
        cobblestone = hasCobble ? ModBlocks.registerCobblestoneBlock(geoType) : null;
        cobbleSlab = hasCobble ? ModBlocks.registerCobbleSlab(geoType) : null;
        cobbleStairs = hasCobble ? ModBlocks.registerCobbleStairs(geoType,
                () -> cobblestone.get().getDefaultState()) : null;
        cobbleWall = hasCobble ? ModBlocks.registerCobbleWall(geoType) : null;

        mossyCobblestone = hasCobble ? ModBlocks.registerMossyCobblestoneBlock(geoType) : null;
        mossyCobbleSlab = hasCobble ? ModBlocks.registerMossyCobbleSlab(geoType) : null;
        mossyCobbleStairs = hasCobble ? ModBlocks.registerMossyCobbleStairs(geoType,
                () -> mossyCobblestone.get().getDefaultState()) : null;
        mossyCobbleWall = hasCobble ? ModBlocks.registerMossyCobbleWall(geoType) : null;

        rawSlab = hasCobble ? ModBlocks.registerRawStoneSlab(geoType) : null;
        rawStairs = hasCobble ? ModBlocks.registerRawStoneStairs(geoType,
                () -> baseStone.get().getDefaultState()) : null;
        rawWall = hasCobble ? ModBlocks.registerRawStoneWall(geoType) : null;

        polishedStone = hasCobble ? ModBlocks.registerPolishedStoneBlock(geoType) : null;
        polishedSlab = hasCobble ? ModBlocks.registerPolishedSlab(geoType) : null;
        polishedStairs = hasCobble ? ModBlocks.registerPolishedStairs(geoType,
                () -> polishedStone.get().getDefaultState()) : null;
        polishedWall = hasCobble ? ModBlocks.registerPolishedStoneWall(geoType) : null;

        bricks = hasCobble ? ModBlocks.registerBricksBlock(geoType) : null;
        brickSlab = hasCobble ? ModBlocks.registerBrickSlab(geoType) : null;
        brickStairs = hasCobble ? ModBlocks.registerBrickStairs(geoType,
                () -> bricks.get().getDefaultState()) : null;
        brickWall = hasCobble ? ModBlocks.registerBrickWall(geoType) : null;

        mossyBricks = hasCobble ? ModBlocks.registerMossyBricksBlock(geoType) : null;
        mossyBrickSlab = hasCobble ? ModBlocks.registerMossyBrickSlab(geoType) : null;
        mossyBrickStairs = hasCobble ? ModBlocks.registerMossyBrickStairs(geoType,
                () -> mossyBricks.get().getDefaultState()) : null;
        mossyBrickWall = hasCobble ? ModBlocks.registerMossyBrickWall(geoType) : null;

        chiseled = hasCobble ? ModBlocks.registerChiseledBlock(geoType) : null;
        cracked = hasCobble ? ModBlocks.registerCrackedBlock(geoType) : null;
        pillar = hasCobble ? ModBlocks.registerPillarBlock(geoType) : null;
        button = hasCobble ? ModBlocks.registerButtonBlock(geoType) : null;
        pressureplate = hasCobble ? ModBlocks.registerPressurePlateBlock(geoType) : null;
    }


    //////////////////////////////
    //          GETTERS         //
    //////////////////////////////

    public boolean hasCobble() { return hasCobble; }
    public boolean hasRegolith() { return hasRegolith; }
    public GeoType getGeoType() { return geoType; }

    // TODO at the end of time (development), if base detritus blocks are never used (because sand-detritus with no
    //  ore is literally sand), then use a SWITCH statement in getBaseStone to remove them and return vanilla blocks.
    //  until then, its literally 6 more blocks out of like 20000, so who cares.
    public Block getBaseStone() { return baseStone.get(); }
    public BlockState getBaseState() { return getBaseStone().getDefaultState(); } // Makes some stuff cleaner :)

    public Block getBaseOre(OreType oreType, Grade grade) {
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
        if (!hasRegolith) return getBaseOre(oreType, grade);

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

    // It is expected that methods calling these do appropriate checks to avoid null returns
    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolith() {
        return hasRegolith ? regolith.get() : baseStone.get();
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
    public Block getMossyCobblestone() { return mossyCobblestone.get(); }
    public Block getMossyCobbleSlab() { return this.mossyCobbleSlab.get(); }
    public Block getMossyCobbleStairs() { return this.mossyCobbleStairs.get(); }
    public Block getMossyCobbleWall() { return this.mossyCobbleWall.get(); }
    public Block getPolishedStone() { return this.polishedStone.get(); }
    public Block getPolishedSlab() { return this.polishedSlab.get(); }
    public Block getPolishedStairs() { return this.polishedStairs.get(); }
    public Block getPolishedWall() { return this.polishedWall.get(); }
    public Block getBricks() { return this.bricks.get(); }
    public Block getBrickSlab() { return this.brickSlab.get(); }
    public Block getBrickStairs() { return this.brickStairs.get(); }
    public Block getBrickWall() { return this.brickWall.get(); }
    public Block getMossyBricks() { return this.mossyBricks.get(); }
    public Block getMossyBrickSlab() { return this.mossyBrickSlab.get(); }
    public Block getMossyBrickStairs() { return this.mossyBrickStairs.get(); }
    public Block getMossyBrickWall() { return this.mossyBrickWall.get(); }

    public Block getChiseled() { return this.chiseled.get(); }
    public Block getCracked() { return this.cracked.get(); }
    public Block getPillar() { return this.pillar.get(); }
    public Block getButton() { return this.button.get(); } ////
    public Block getPressurePlate() { return this.pressureplate.get(); } ////

    public Item getDropItem() {
        if (hasCobble) {
            return getRockItem();
        } else if (geoType.getGeoLoot().hasPresetDrop()) {
            return geoType.getGeoLoot().getPresetDrop();
        } else {
            return baseStone.get().asItem();
        }
    }

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


    //////////////////////////////////////////////
    //          DATA-GEN / LIST BUILDERS        //
    //////////////////////////////////////////////

    // THESE ARE MAINLY TO BE USED DURING INITIALIZATION AND DATA GENERATION
    // The sheer amount of items generated would be excessive during any other stage
    // These all assume proper checks are being done to ensure no null returns for cobble-less blocks!

    // Get all geo-blocks (aka not including cobbles and cobblestones)
    public List<Block> getAllGeoBlocks() {
        List<Block> allGeoBlocks = new ArrayList<>(getStoneGeoBlocks());
        if (hasRegolith) allGeoBlocks.addAll(getRegolithGeoBlocks());
        return allGeoBlocks;
    }

    // Get all stone geo-blocks
    public List<Block> getStoneGeoBlocks() {
        List<Block> stoneGeoBlocks = new ArrayList<>(getStoneOreBlocks());
        stoneGeoBlocks.add(getBaseStone());
        return stoneGeoBlocks;
    }

    // Get all regolith geo-blocks
    public List<Block> getRegolithGeoBlocks() {
        List<Block> regolithGeoBlocks = new ArrayList<>(getRegolithOreBlocks());
        regolithGeoBlocks.add(getRegolith());
        return regolithGeoBlocks;
    }

    // Get all ore-bearing geo-blocks
    public List<Block> getAllOreBlocks() {
        List<Block> allOreBlocks = new ArrayList<>(getStoneOreBlocks());
        if (hasRegolith) allOreBlocks.addAll(getRegolithOreBlocks());
        return allOreBlocks;
    }

    // Get all ore-bearing base stone geo-blocks
    public List<Block> getStoneOreBlocks() {
        List<Block> allStoneOreBlocks = new ArrayList<>();
        for (OreBlockRegistry oreRegistry: stoneOreRegistry.values()) {
            allStoneOreBlocks.addAll(oreRegistry.getAllGradedOreBlocks());
        }
        return allStoneOreBlocks;
    }

    // Get all ore-bearing regolith geo-blocks
    public List<Block> getRegolithOreBlocks() {
        List<Block> allRegolithOreBlocks = new ArrayList<>();
        for (OreBlockRegistry oreRegistry: regolithOreRegistry.values()) {
            allRegolithOreBlocks.addAll(oreRegistry.getAllGradedOreBlocks());
        }
        return allRegolithOreBlocks;
    }

    public Map<OreType, OreBlockRegistry> getStoneOreRegistry() { return stoneOreRegistry; }
    public Map<OreType, OreBlockRegistry> getRegolithOreRegistry() { return regolithOreRegistry; }

    public List<Block> getDecorBlocks() {
        List<Block> allDecorBlocks = new ArrayList<>();
        allDecorBlocks.add(rawSlab.get());
        allDecorBlocks.add(rawStairs.get());
        allDecorBlocks.add(rawWall.get());
        allDecorBlocks.add(cobbleSlab.get());
        allDecorBlocks.add(cobbleStairs.get());
        allDecorBlocks.add(cobbleWall.get());
        allDecorBlocks.add(mossyCobblestone.get());
        allDecorBlocks.add(mossyCobbleSlab.get());
        allDecorBlocks.add(mossyCobbleStairs.get());
        allDecorBlocks.add(mossyCobbleWall.get());
        allDecorBlocks.add(polishedStone.get());
        allDecorBlocks.add(polishedSlab.get());
        allDecorBlocks.add(polishedStairs.get());
        allDecorBlocks.add(polishedWall.get());
        allDecorBlocks.add(bricks.get());
        allDecorBlocks.add(brickSlab.get());
        allDecorBlocks.add(brickStairs.get());
        allDecorBlocks.add(brickWall.get());
        allDecorBlocks.add(mossyBricks.get());
        allDecorBlocks.add(mossyBrickSlab.get());
        allDecorBlocks.add(mossyBrickStairs.get());
        allDecorBlocks.add(mossyBrickWall.get());
        allDecorBlocks.add(chiseled.get());
        allDecorBlocks.add(cracked.get());
        allDecorBlocks.add(pillar.get());
        allDecorBlocks.add(button.get());
        allDecorBlocks.add(pressureplate.get());

        return allDecorBlocks;
    }

}
