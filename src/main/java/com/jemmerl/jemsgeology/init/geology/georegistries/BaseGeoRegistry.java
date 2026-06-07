package com.jemmerl.jemsgeology.init.geology.georegistries;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import com.jemmerl.jemsgeology.init.geology.OreBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseGeoRegistry {

    private final GeoType geoType;
    private final RegistryObject<Block> baseGeoBlock;
    private final LinkedHashMap<OreType, OreBlockRegistry> oreRegistry;

    public BaseGeoRegistry(GeoType geoType) {
        this.geoType = geoType;

        baseGeoBlock = geoType.getGeoGroup().isRegolith() ?
                ModBlocks.registerRegolithGeoBlock(geoType) : ModBlocks.registerStoneGeoBlock(geoType);

        this.oreRegistry =  fillOreRegistry(geoType);
    }

//            ResourceLocation baseStoneRL;
//            ResourceLocation cobblestoneRL;
//            if (geoRegistry instanceof SoftStoneGeoRegistry) {
//                SoftStoneGeoRegistry softStoneGeoRegistry = (SoftStoneGeoRegistry) geoRegistry;
//
//            } else {
//                continue;
//            }
//
//            if (geoRegistry instanceof HardStoneGeoRegistry) {
//                HardStoneGeoRegistry hardStoneGeoRegistry = (HardStoneGeoRegistry) geoRegistry;
//
//            }


    //////////////////////////////
    //          GETTERS         //
    //////////////////////////////

    public GeoType getGeoType() { return geoType; }

    // TODO at the end of time (development), if base detritus blocks are never used (because sand-detritus with no
    //  ore is literally sand), then use a SWITCH statement in getBaseStone to remove them and return vanilla blocks.
    //  until then, its literally 6 more blocks out of like 20000, so who cares.
    public Block getBaseGeoBlock() { return baseGeoBlock.get(); }
    public BlockState getBaseState() { return getBaseGeoBlock().getDefaultState(); } // Makes some stuff cleaner :)

    public Block getOreVariant(OreType oreType) {
        return getOreVariant(oreType, OreGrade.NORMAL);
    }

    public Block getOreVariant(OreType oreType, OreGrade oreGrade) {
        if (oreType.hasOre() && oreType.getGeoPredicate().generatesIn(geoType)) {
            switch (oreGrade) {
                case NORMAL:
                    return oreRegistry.get(oreType).getNormalOreBlock().get();
                case POOR:
                    return oreRegistry.get(oreType).getPoorOreBlock().get();
                case NONE:
                default:
            }
        }
        return baseGeoBlock.get();
    }

    // It is expected that methods calling these do appropriate checks to avoid null returns
    // GeoTypes with no cobble use their base stone as their own regolith


    public Item getMainDropItem() {
        if (geoType.getGeoLoot().hasPresetDrop()) {
            return geoType.getGeoLoot().getPresetDropItem();
        } else {
            return baseGeoBlock.get().asItem();
        }
    }

    //////////////////////////////
    //          SETTERS         //
    //////////////////////////////

    private LinkedHashMap<OreType, OreBlockRegistry> fillOreRegistry(GeoType geoType) {
        LinkedHashMap<OreType, OreBlockRegistry> oreMap = new LinkedHashMap<>();
        for (OreType oreType : ModGeoOres.getModOreTypes()) {
            if(oreType.getGeoPredicate().generatesIn(geoType)) {
                oreMap.put(oreType, new OreBlockRegistry(geoType, oreType));
            }
        }
        return oreMap;
    }

    //////////////////////////////////////////////
    //          DATA-GEN / LIST BUILDERS        //
    //////////////////////////////////////////////

    // THESE ARE MAINLY TO BE USED DURING INITIALIZATION AND DATA GENERATION
    // The sheer amount of items generated would be excessive during any other stage
    // <!> These all assume proper checks are being done to ensure no null returns for cobble-less blocks! <!>

    // Get all geo-blocks
    public List<Block> getAllGeoBlocks() {
        List<Block> stoneGeoBlocks = new ArrayList<>(getAllOreGeoBlocks());
        stoneGeoBlocks.add(getBaseGeoBlock());
        return stoneGeoBlocks;
    }

    // Get all ore-bearing geo-blocks
    public List<Block> getAllOreGeoBlocks() {
        List<Block> allOreBlocks = new ArrayList<>();
        for (OreBlockRegistry oreRegistry: oreRegistry.values()) {
            allOreBlocks.addAll(oreRegistry.getAllGradedOreBlocks());
        }
        return allOreBlocks;
    }

    public Map<OreType, OreBlockRegistry> getOreRegistry() { return oreRegistry; }

    public List<Block> getDecorBlocks() {
        List<Block> allDecorBlocks = new ArrayList<>();
        return allDecorBlocks;
    }

}
