package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class BaseGeoBlock extends Block implements IGeoBlock {

    private final GeoType geoType;
    private final OreType oreType;
    private final Grade grade;

    public BaseGeoBlock(AbstractBlock.Properties properties, GeoType geoType, OreType oreType, Grade grade) {
        super(properties);
        this.geoType = geoType;
        this.oreType = oreType;
        this.grade = grade;

        // Blockstates may be useful for determining natural vs placed stones, if I want to do custom cavein stuff.
        // Keeping the infrastructure
        //this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE).with(GRADE_TYPE, GradeType.LOWGRADE));
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
    public Grade getGrade() {
        return grade;
    }

}
