package com.jemmerl.jemsgeology.init.geologyinit;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class OreBlockRegistry {

    private final RegistryObject<Block> poorOreBlock;
    private final RegistryObject<Block> normalOreBlock;
//    private final RegistryObject<Block> richOreBlock;

    public OreBlockRegistry(GeoType geoType, OreType oreType, boolean isRegolith) {
        poorOreBlock = oreType.hasPoorOre() ? registerOreBlock(geoType, oreType, Grade.POOR, isRegolith) : null;
        normalOreBlock = registerOreBlock(geoType, oreType, Grade.NORMAL, isRegolith);
//        richOreBlock = registerOreBlock(geoType, oreType, GradeType.HIGHGRADE, blockType);
    }

    public RegistryObject<Block> getPoorOreBlock() {
        return poorOreBlock;
    }

    public RegistryObject<Block> getNormalOreBlock() {
        return normalOreBlock;
    }

//    // Build a list of all three graded ore blocks
//    public List<Block> getAllGradedOreBlocks() {
//        return Arrays.asList(poorOreBlock.get(), normalOreBlock.get());
////        return Arrays.asList(lowOreBlock.get(), midOreBlock.get(), richOreBlock.get());
//    }

    private RegistryObject<Block> registerOreBlock(GeoType geoType, OreType oreType, Grade grade, boolean isRegolith) {
        return isRegolith ?
                ModBlocks.registerStoneGeoBlock(geoType, oreType, grade) :
                ModBlocks.registerRegolithGeoBlock(geoType, oreType, grade);
    }
}