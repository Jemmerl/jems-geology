package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreBlockType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;

public class GeoRegistry {

    private final GeologyType geologyType;
    private final boolean hasCobble;

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> regolith;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private final RegistryObject<Item> rockItem;

    private final RegistryObject<Block> rawSlab;
    private final RegistryObject<Block> rawStairs;
    private final RegistryObject<Block> rawWall;
    private final RegistryObject<Block> cobbleSlab;
    private final RegistryObject<Block> cobbleStairs;
    private final RegistryObject<Block> cobbleWall;
    private final RegistryObject<Block> polishedStone;
    private final RegistryObject<Block> polishedSlab;
    private final RegistryObject<Block> polishedStairs;

    private final Map<OreType, OreBlockRegistry> stoneOreRegistry;
    private final Map<OreType, OreBlockRegistry> regolithOreRegistry;

    // TODO add to respective tags
    public GeoRegistry(GeologyType geoType) {
        this.geologyType = geoType;
        this.hasCobble = geoType.hasCobble(); // Blocks without a cobble are also not solid enough for stone-working

        this.baseStone = ModBlocks.registerStoneGeoBlock(geoType);
        this.regolith = hasCobble ? ModBlocks.registerRegolithGeoBlock(geoType) : null;
        this.cobbles = hasCobble ? ModBlocks.registerCobblesBlock(geoType) : null;
        this.cobblestone = hasCobble ? ModBlocks.registerCobblestoneBlock(geoType) : null;
        this.rockItem = hasCobble ? ModItems.registerRockItem(geoType) : null;

        this.rawSlab = hasCobble ? ModBlocks.registerRawStoneSlab(geoType) : null;
        this.rawStairs = hasCobble ? ModBlocks.registerRawStoneStairs(geoType,
                () -> this.baseStone.get().getDefaultState()) : null;
        this.rawWall = hasCobble ? ModBlocks.registerRawStoneWall(geoType) : null;
        this.cobbleSlab = hasCobble ? ModBlocks.registerCobbleSlab(geoType) : null;
        this.cobbleStairs = hasCobble ? ModBlocks.registerCobbleStairs(geoType,
                () -> this.cobblestone.get().getDefaultState()) : null;
        this.cobbleWall = hasCobble ? ModBlocks.registerCobbleWall(geoType) : null;
        this.polishedStone = hasCobble ? ModBlocks.registerPolishedStoneBlock(geoType) : null;
        this.polishedSlab = hasCobble ? ModBlocks.registerPolishedSlab(geoType) : null;
        this.polishedStairs = hasCobble ? ModBlocks.registerPolishedStairs(geoType,
                () -> this.polishedStone.get().getDefaultState()) : null;

        this.stoneOreRegistry = geoType.isInStoneGroup(StoneGroupType.DETRITUS) ?
                fillOreRegistry(geoType, OreBlockType.DETRITUS) : fillOreRegistry(geoType, OreBlockType.STONE);
        this.regolithOreRegistry =  hasCobble ? fillOreRegistry(geoType, OreBlockType.REGOLITH) : Collections.emptyMap();
    }


    public GeologyType getGeoType() { return geologyType; }
    public boolean hasCobble() { return hasCobble; }


    //////////////////////////
    // SINGLE BLOCK GETTERS //
    //////////////////////////

    public Block getBaseStone() { return baseStone.get(); }
    public BlockState getBaseState() { return getBaseStone().getDefaultState(); } // Makes some hardcoded stuff cleaner

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

    public Block getStoneOre(OreType oreType, GradeType gradeType) {
        if (oreType.hasOre() && gradeType.hasGrade()) {
            return stoneOreRegistry.get(oreType).getGradeOre(gradeType).get();
        }
        return baseStone.get();
    }

    // GeoTypes with no cobble use their base stone as their own regolith
    public Block getRegolithOre(OreType oreType, GradeType gradeType) {
        if (oreType.hasOre() && gradeType.hasGrade()) {
            return  hasCobble ? regolithOreRegistry.get(oreType).getGradeOre(gradeType).get() : getStoneOre(oreType, gradeType);
        }
        return  hasCobble ? regolith.get() : baseStone.get();
    }


    ///////////////////
    // LIST BUILDERS //
    ///////////////////

    // THESE ARE MAINLY TO BE USED DURING INITIALIZATION AND DATA GENERATION
    // The sheer amount of items generated would be excessive during any other stage
    // These all assume proper checks are being done to ensure no null returns for cobble-less blocks!

    // Get all geo-blocks (aka not including cobbles and cobblestones)
    public List<Block> getAllGeoBlocks() {
        List<Block> allGeoBlocks = new ArrayList<>(getStoneGeoBlocks());
        if (hasCobble) allGeoBlocks.addAll(getRegolithGeoBlocks());
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
        if (hasCobble) allOreBlocks.addAll(getRegolithOreBlocks());
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
        allDecorBlocks.add(polishedStone.get());
        allDecorBlocks.add(polishedSlab.get());
        allDecorBlocks.add(polishedStairs.get());
        return allDecorBlocks;
    }

    ////////////////////////
    // Registration Utils //
    ////////////////////////

    private  Map<OreType, OreBlockRegistry> fillOreRegistry(GeologyType geoType, OreBlockType blockType) {
        Map<OreType, OreBlockRegistry> oreMap = new HashMap<>();
        for (OreType oreType: EnumSet.complementOf(EnumSet.of(OreType.NONE))) {
            oreMap.put(oreType, new OreBlockRegistry(geoType, oreType, blockType));
        }
        return oreMap;
    }
}
