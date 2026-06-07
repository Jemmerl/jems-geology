package com.jemmerl.jemsgeology.world.geobuilding;

import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;

public class UnbakedGeo {
    private GeoType geoType;
    private OreType oreType;
    private OreGrade oreGrade;
    private Metamorphism.Temp metaTemp;
    private Metamorphism.Pressure metaPressure;
    private Metamorphism.MetaSomatism metaSomatism;

    //private GeoType protolithGeoType = null;
    //private boolean isRegolith;

    // Used for default init
    public UnbakedGeo() {
        this.geoType = GeoType.EMPTY;
        this.oreType = OreType.NONE;
        this.oreGrade = OreGrade.NONE;
        this.metaTemp = Metamorphism.Temp.T000C;
        this.metaPressure = Metamorphism.Pressure.P000MPA;
        this.metaSomatism = Metamorphism.MetaSomatism.NONE;
    }

    public UnbakedGeo(GeoType geoType) {
        this.geoType = geoType;
        this.oreType = OreType.NONE;
        this.oreGrade = OreGrade.NONE;
        //this.isRegolith = isRegolith;
    }

    public UnbakedGeo(GeoType geoType, OreType oreType, OreGrade oreGrade) {
        this.geoType = geoType;
        this.oreType = oreType;
        this.oreGrade = oreGrade;
        //this.isRegolith = isRegolith;
    }

    public void setGeoType(GeoType geologyType) { this.geoType = geologyType; }
    public void setOreType(OreType oreType) { this.oreType = oreType; }
    public void setGrade(OreGrade oreGrade) { this.oreGrade = oreGrade; }
    public void setMetaTemp(Metamorphism.Temp metaTemp) { this.metaTemp = metaTemp; }
    public void setMetaPressure(Metamorphism.Pressure metaPressure) { this.metaPressure = metaPressure; }
    public void setMetaSomatism(Metamorphism.MetaSomatism metaSomatism) { this.metaSomatism = metaSomatism; }
    //public void setProtolithGeoType(GeoType protolithGeoType) { this.protolithGeoType = protolithGeoType; }
    //public void setRegolith(boolean isRegolith) { this.isRegolith = isRegolith; }

    public GeoType getGeoType() { return geoType; }
    public OreType getOreType() { return oreType; }
    public OreGrade getGrade() { return oreGrade; }
    public Metamorphism.Temp getMetaTemp() { return metaTemp; }
    public Metamorphism.Pressure getMetaPressure() { return metaPressure; }
    public Metamorphism.MetaSomatism getMetaSomatism() { return metaSomatism; }
    //public GeoType getProtolithGeoType() { return protolithGeoType; }
    //public Grade isRegolith() { return isRegolith; }

    // Applies metamorphism, but remains a GeoWrapper. Returns "false" if failed to process
    // TODO how to handle ores? ez for pre-placement ores, but not post. may have to recalc just like deformation
    public boolean halfBake() {
        if (geoType == GeoType.EMPTY) return false; // TODO also do this for regolith??

        // Process here

        metaTemp = Metamorphism.Temp.T000C;
        metaPressure = Metamorphism.Pressure.P000MPA;
        metaSomatism = Metamorphism.MetaSomatism.NONE;
        return true;
    }

    public Block bake(boolean regolith) {
        return ModBlocks.GEO_BLOCKS.get((geoType == GeoType.EMPTY) ? GeoType.GRAY_RHYOLITE : geoType).getOreVariant(oreType, oreGrade);
//        if (regolith && geoType.hasRegolith()) {
//            return ModBlocks.GEO_BLOCKS.get(geoType).getRegolithOre(oreType, grade);
//        } else {
//            return ModBlocks.GEO_BLOCKS.get(geoType).getBaseOre(oreType, grade);
//        }
    }
}
