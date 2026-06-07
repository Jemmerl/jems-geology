package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import net.minecraft.block.Block;

public class FracturedGeoBlock extends Block implements IGeoBlock {

    private final GeoType geoType;

    public FracturedGeoBlock(Properties properties, GeoType geoType) {
        super(properties);
        this.geoType = geoType;
    }

    @Override
    public OreType getOreType() {
        return OreType.NONE;
    }

    @Override
    public OreGrade getGrade() {
        return OreGrade.NONE;
    }

    @Override
    public GeoType getGeoType() {
        return geoType;
    }
}
