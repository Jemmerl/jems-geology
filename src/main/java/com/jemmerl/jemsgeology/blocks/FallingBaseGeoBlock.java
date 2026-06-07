package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FallingBlock;

public class FallingBaseGeoBlock extends FallingBlock implements IGeoBlock {
    private final GeoType geoType;
    private final OreType oreType;
    private final OreGrade oreGrade;

    public FallingBaseGeoBlock(AbstractBlock.Properties properties, GeoType geoType, OreType oreType, OreGrade oreGrade) {
        super(properties);
        this.geoType = geoType;
        this.oreType = oreType;
        this.oreGrade = oreGrade;

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
    public OreGrade getGrade() {
        return oreGrade;
    }
}
