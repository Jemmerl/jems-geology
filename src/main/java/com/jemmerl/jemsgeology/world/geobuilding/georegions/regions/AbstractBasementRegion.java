package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public abstract class AbstractBasementRegion {

    // todo ask about sandstone being in basement rocks with very lowgrade metamorphism?
    // todo strata warping should be done before stone gen, as a separate (more distorting) uplift phase

    public final int seed;

    public AbstractBasementRegion(int seed) {
        this.seed = seed;
    }

    // list of valid geoWRAPPERS or types/weights
        // do geotypes first, with support for geowrappers later

    //range of distortion properties

    // metamorphism grade enum? could be used to select faecies, unless region instances are just made for each

    public abstract int buildDeformColumn(int x, int z, short[] deformArrayColumn);

    public abstract UnbakedGeo getUnbakedGeo(int x, int y, int z);

    public final UnbakedGeo getUnbakedGeo(@Nonnull BlockPos blockPos) {
        return getUnbakedGeo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
