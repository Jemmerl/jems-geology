package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.world.geobuilding.Metamorphism;
import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.BasementRegionNoise;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.noise.InstStrataNoise;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.function.Function;

// Rocks subjected to orogenic style metamorphism. Basically a modified AgedStrataRegion
public class OrogenicBasementRegion extends AbstractBasementRegion {

    private final Random random;
    private final InstStrataNoise strataInstNoise;

    private final double aAngle;
    private final double  bAngle;

    private static final int WIDTH = 200;
    //T 100-800+ (100 or 200)
    //P 200-1200+
    // find a function that will map the above T range to the P range, both linearly start at 0
    // then, make a function of T based on Y using the width as reference
    // then get a starting point for the region based on arbitrary Y. this will be a general erosional/uplift offset
    // to the region! this will be ranged based on the width and expected uplift amount
    // overall, settings should be done so the later stages are rarely exposed on the surface.
    // note 30-35 c/km avg, but may be as low as 10 (alpine?) read into, maybe set gradient randomly

    public OrogenicBasementRegion(int seed) {
        super(seed);
        random = new Random(seed + 2);
        strataInstNoise = new InstStrataNoise(seed - 1138746360, 3000, 5000);

        aAngle = Math.toRadians(random.nextInt(360)); // ROTATION ANGLE
        bAngle = Math.toRadians(random.nextInt(90)+90); // ANGLE "UP" OFF-PLANE
    }

    @Override
    public int buildDeformColumn(int x, int z, short[] deformArrayColumn) {
        float sumAvg = 0f;
        for (int ix = -10; ix <= 10; ix+=10) {
            for (int iz = -10; iz <= 10; iz+=10) {
                float gaussKernel = UtilMethods.gaussKernel3[(ix+10)/10][(iz+10)/10];
                sumAvg += (BasementRegionNoise.getBasementRegionDist(x+ix, z+iz) * gaussKernel);
            }
        }
        int reboundHeight = Math.round(sumAvg * sumAvg * 100);

        for (int y = deformArrayColumn.length - 1; y >= 0; y--) {
            if (y < reboundHeight) {
                deformArrayColumn[y] = (short)reboundHeight;
            } else {
                break;
            };
        }

        return reboundHeight;
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        double ux = x*Math.cos(aAngle)*Math.cos(bAngle);
        double uz = z*Math.sin(aAngle)*Math.cos(bAngle);
        double uy = y*Math.sin(bAngle);

        int W = (int)Math.round(ux+uz+uy);


        if (((W / 10)) % 2 == 0) {
            return new UnbakedGeo(GeoType.BASALT);
        } else {
            return new UnbakedGeo(GeoType.ANDESITE);
        }

//        int age = strataInstNoise.getStrataIdentifier(x, y, z).second;
//
//        float perc = (5000-age) / 2500f;
//
//        if (random.nextFloat() > perc) {
//            return new UnbakedGeo(GeoType.BASALT);
//        } else {
//            return new UnbakedGeo(GeoType.ANDESITE);
//        }

//        int TEMP = ((int)Math.ceil(((5000-age)/2000f) * GeoType.ALL_STONE_GEOTYPES.size()));
//        int TEMP = (age/140f) * GeoType.ALL_STONE_GEOTYPES.size();

//        if (TEMP <= 0) {
//            System.out.println(seed + ", " + y + ", " + age + ", " + TEMP);
//            TEMP = 0;
//        }
//
//        if (((x % 16) == 0) && ((z % 16) == 0) ) {
//            System.out.println(seed + ", " + y + ", " + age + ", " + TEMP);
//        }

//        return new UnbakedGeo(GeoType.values()[TEMP]);
    }
}



