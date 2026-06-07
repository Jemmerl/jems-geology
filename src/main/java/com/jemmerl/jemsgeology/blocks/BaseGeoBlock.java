package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class BaseGeoBlock extends Block implements IGeoBlock {

    private final GeoType geoType;
    private final OreType oreType;
    private final OreGrade oreGrade;

    public BaseGeoBlock(AbstractBlock.Properties properties, GeoType geoType, OreType oreType, OreGrade oreGrade) {
        super(properties);
        this.geoType = geoType;
        this.oreType = oreType;
        this.oreGrade = oreGrade;
    }

    @Override
    public GeoType getGeoType() {
        return geoType;
    }

    @Override
    public OreType getOreType() {
        return oreType;
    }

    @Override
    public OreGrade getGrade() {
        return oreGrade;
    }

}
