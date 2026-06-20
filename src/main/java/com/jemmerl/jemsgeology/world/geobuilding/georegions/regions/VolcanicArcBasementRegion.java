package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import net.minecraft.util.math.BlockPos;

// Rocks subjected to volcanic-arc style metamorphism
public class VolcanicArcBasementRegion extends AbstractBasementRegion {
    public VolcanicArcBasementRegion(int seed) {
        super(seed);
    }

    @Override
    public int buildDeformColumn(int x, int z, short[] deformArrayColumn) {
        return 0;
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        return null;
    }
}
