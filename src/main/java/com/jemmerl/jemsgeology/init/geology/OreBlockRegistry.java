package com.jemmerl.jemsgeology.init.geology;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class OreBlockRegistry {

    private final boolean hasPoorOre;
    private final RegistryObject<Block> poorOreBlock;
    private final RegistryObject<Block> normalOreBlock;
//    private final RegistryObject<Block> richOreBlock;

    public OreBlockRegistry(GeoType geoType, OreType oreType) {
        hasPoorOre = oreType.hasPoorOre();
        poorOreBlock = hasPoorOre ? registerOreBlock(geoType, oreType, OreGrade.POOR) : null;
        normalOreBlock = registerOreBlock(geoType, oreType, OreGrade.NORMAL);
//        richOreBlock = hasRichOre ? registerOreBlock(geoType, oreType, GradeType.HIGHGRADE, blockType);
    }

    public RegistryObject<Block> getPoorOreBlock() {
        return (hasPoorOre ? poorOreBlock : normalOreBlock);
    }

    public RegistryObject<Block> getNormalOreBlock() {
        return normalOreBlock;
    }

    // Build a list of all graded ore blocks
    public List<Block> getAllGradedOreBlocks() {
        List<Block> gradedOreBlocks = new ArrayList<>();
        gradedOreBlocks.add(normalOreBlock.get());
        if (hasPoorOre) gradedOreBlocks.add(poorOreBlock.get());
        return gradedOreBlocks;
    }

    private RegistryObject<Block> registerOreBlock(GeoType geoType, OreType oreType, OreGrade oreGrade) {
        return geoType.getGeoGroup().isRegolith() ?
                ModBlocks.registerRegolithOreBlock(geoType, oreType, oreGrade) :
                ModBlocks.registerStoneOreBlock(geoType, oreType, oreGrade);
    }
}
