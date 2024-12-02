package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.geology.stones.GeoType;
import net.minecraft.block.Block;

public class BaseGeoBlock extends Block implements IGeoBlock {

    private GeoType geoType;

    public BaseGeoBlock(Properties properties) {
        super(properties);
    }

}
