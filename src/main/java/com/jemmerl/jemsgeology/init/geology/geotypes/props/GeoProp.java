package com.jemmerl.jemsgeology.init.geology.geotypes.props;

import com.jemmerl.jemsgeology.init.geology.ores.GeoLoot;
import net.minecraft.block.material.MaterialColor;

public class GeoProp {
    // all geotypes should have a "cobble" block.

    private final Hardness hardness;
    private final MaterialColor color;
    private final GeoLoot geoLoot; // TODO rework this eventually. ugh

    public GeoProp(Hardness hardness, MaterialColor color, GeoLoot geoLoot) {
        this.hardness = hardness;
        this.color = color;
        this.geoLoot = geoLoot;
    }

    public GeoProp(Hardness hardness, MaterialColor color) {
        this.hardness = hardness;
        this.color = color;
        this.geoLoot = GeoLoot.BASIC_ROCKS;
    }

    public Hardness getHardness() { return hardness; }
    public MaterialColor getMaterialColor() { return color; }
    public GeoLoot getGeoLoot() { return geoLoot; }

    public float getHardnessAdj() {
        return 0F;
    }
    public float getResistAdj() {
        return 0F;
    }

}
