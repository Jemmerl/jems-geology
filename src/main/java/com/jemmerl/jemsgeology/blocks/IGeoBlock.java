package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import net.minecraft.util.ResourceLocation;

public interface IGeoBlock {

    // Return ore state of block
    OreType getOreType();

    // Return grade state of block
    Grade getGrade();

    // Return the stone type of the block
    GeoType getGeoType();

    // All GeoBlocks are blocks, but I cannot call a block method from the interface without including here
    ResourceLocation getRegistryName();
}
