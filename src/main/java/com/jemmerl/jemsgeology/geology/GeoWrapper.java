package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import net.minecraft.block.Block;

public class GeoWrapper {
    private GeoType geoType;
    private OreType oreType;
    private Grade grade;
    private boolean contactMeta = false;
    //private GeoType protolithGeoType = null;
    //private boolean isRegolith;

    public GeoWrapper(GeoType geoType) {
        this.geoType = geoType;
        this.oreType = ModGeoOres.NONE;
        this.grade = Grade.NONE;
        //this.isRegolith = isRegolith;
    }

    public GeoWrapper(GeoType geoType, OreType oreType, Grade grade) {
        this.geoType = geoType;
        this.oreType = oreType;
        this.grade = grade;
        //this.isRegolith = isRegolith;
    }

    public void setGeoType(GeoType geologyType) { this.geoType = geologyType; }
    public void setOreType(OreType oreType) { this.oreType = oreType; }
    public void setGrade(Grade grade) { this.grade = grade; }
    public void setContactMeta(boolean contactMeta) { this.contactMeta = contactMeta; }
    //public void setProtolithGeoType(GeoType protolithGeoType) { this.protolithGeoType = protolithGeoType; }
    //public void setRegolith(boolean isRegolith) { this.isRegolith = isRegolith; }

    public GeoType getGeoType() { return geoType; }
    public OreType getOreType() { return oreType; }
    public Grade getGrade() { return grade; }
    public boolean getContactMeta() { return contactMeta; }
    //public GeoType getProtolithGeoType() { return protolithGeoType; }
    //public Grade isRegolith() { return isRegolith; }

    public Block toGeoBlock(boolean regolith) {
        return ModBlocks.GEO_BLOCKS.get(geoType).getOreVariant(oreType, grade);
//        if (regolith && geoType.hasRegolith()) {
//            return ModBlocks.GEO_BLOCKS.get(geoType).getRegolithOre(oreType, grade);
//        } else {
//            return ModBlocks.GEO_BLOCKS.get(geoType).getBaseOre(oreType, grade);
//        }
    }
}
