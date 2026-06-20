package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import net.minecraft.util.math.BlockPos;

// Consists of layers with a strict order, such as Greenstone Belts
public class StrictLayeredBasementRegion extends AbstractBasementRegion {
    public StrictLayeredBasementRegion(int seed) {
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
