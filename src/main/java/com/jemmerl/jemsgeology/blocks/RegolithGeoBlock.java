package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;

public class RegolithGeoBlock extends BaseGeoBlock{
    public RegolithGeoBlock(Properties properties, GeoType geoType, OreType oreType, Grade grade) {
        super(properties, geoType, oreType, grade);
    }
}
