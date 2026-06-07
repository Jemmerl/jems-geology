package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface IGeoBlock extends IForgeBlock {

    // Return ore state of block
    OreType getOreType();

    // Return grade state of block
    OreGrade getGrade();

    // Return the stone type of the block
    GeoType getGeoType();
}
