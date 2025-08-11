package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.IChunkGennedCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.IWaterTableCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.ChunkGennedCapability;
import com.jemmerl.jemsgeology.capabilities.world.deposit.DepositCapability;
import com.jemmerl.jemsgeology.capabilities.world.deposit.IDepositCap;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.Heightmap;

import java.util.Random;

public class GeoBuilder {

    private final ISeedReader world;
    private final BlockPos cornerPos; // Starting position of this chunk's generation
    private final Random rand;

//    private final int[][][] deformHeights;
//    private final int[] oldFaultShift = new int[3];
//    private final int[] newFaultShift = new int[3];;

    private final GeoWrapper[][][] wrapperArray;

    // Note: do not make these static, in case this builder is used for multiple dimensions.
    // If in the future, the standard is to use a different geo-builder implementation/obj per dim, then can be static.
    //  -> (maybe a GeoBuilderBuilder gets implemented??)
    private final IDepositCap depCap;
    private final IChunkGennedCap cgCap;
    private final IWaterTableCap wtCap;

    // TODO I think it may be best to inherently process chunks by the column. If everything is designed that way,
    //  then there should never be issues with post-placing ores as the column can just recalculate.

    public GeoBuilder(ISeedReader world, BlockPos pos, Random rand, int maxHeight) {
        this.world = world;
        this.cornerPos = pos;
        this.rand = rand;
        wrapperArray = new GeoWrapper[16][maxHeight][16];

//        this.deformHeights = new int[16][this.chunkReader.getMaxHeight()][16];

        this.depCap = world.getWorld().getCapability(DepositCapability.DEPOSIT_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null in \"GeoBuilder\" feature..."));
        this.cgCap = world.getWorld().getCapability(ChunkGennedCapability.CHUNK_GENNED_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null in \"GeoBuilder\" feature..."));
        this.wtCap = world.getWorld().getCapability(WaterTableCapability.WATER_TABLE_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo water table capability is null in \"GeoBuilder\" feature..."));




//        IChunk ichunk = world.getChunk(pos.getX() >> 2, pos.getZ() >> 2, ChunkStatus.BIOMES, false);
////        return ichunk != null && ichunk.getBiomes() != null ? ichunk.getBiomes().getNoiseBiome(x, y, z) : this.getNoiseBiomeRaw(x, y, z);
//        System.out.println("---------------------------------------");
//        System.out.println("is chunk nonnull: " + (ichunk != null));
//        if ((ichunk != null)) {
//            System.out.println("is biomes nonnull: " + (ichunk.getBiomes() != null));
//        } else {
//            System.out.println("is biomes nonnull: YES IS NULL");
//        }

        //[21:46:23] [Worker-Main-19/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.geology.GeoBuilder:<init>:71]: is chunk nonnull: false
        //[21:46:23] [Worker-Main-19/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.geology.GeoBuilder:<init>:75]: is biomes nonnull: YES IS NULL


//        IChunk chunk = world.getChunk(pos);
//        System.out.println("here");
//        System.out.println(world.getNoiseBiomeRaw(pos.getX(), pos.getY(), pos.getZ()).getCategory());
//        System.out.println("there");
//        System.out.println(world.getNoiseBiomeRaw(4000, 10, 10000).getCategory());
//
//
//        System.out.println("vs here");
//        System.out.println(world.getNoiseBiome(pos.getX(), pos.getY(), pos.getZ()).getCategory());
//        System.out.println("vs there");
//        System.out.println(world.getNoiseBiome(4000, 10, 10000).getCategory());
//        System.out.println("trying more");
//        System.out.println("chunk primer: " + (chunk instanceof ChunkPrimer));

        // Need: height map and biomes. Optimally, from adj chunks as well, but only the single chunk would be workable.
        // can use chunk primer trick to


//        this.wtChunkCap = world.getWorld().getChunk(pos).getCapability(WTBaseCapability.WATER_TABLE_BASE_CAPABILITY)
//                .orElseThrow(() -> new RuntimeException("JemsGeo chunk base water-table capability is null..."));
//
//        world.getWorld().getCapability(WaterTableCapability.WATER_TABLE_CAPABILITY)
//                .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null..."));
    }

    public GeoWrapper[][][] build() {
        // todo water table height calc can be optimized for world gen.
        //  maybe a method that returns a 16x16 array of values?

        // Store the world height to be used later.
        //noinspection deprecation
        wtCap.cacheChunkHeight(new ChunkPos(cornerPos),
                world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, cornerPos.add(7,0,7)).getY());
//        System.out.println(world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, cornerPos.add(7,0,7)).getY());


        // Fill the default array WITHOUT SHALLOW COPYING AN ENTIRE DIMENSION OF IT THIS TIME AAAAAAAAAHHH
        for (int x = 0; x < wrapperArray.length; x++) {
            for (int y = 0; y < wrapperArray[0].length; y++) {
                for (int z = 0; z < wrapperArray[0][0].length; z++) {
                    if ((y & 8)/8 == 0) {
                        wrapperArray[x][y][z] = new GeoWrapper(GeoType.GRAY_RHYOLITE);
                    } else {
                        wrapperArray[x][y][z] = new GeoWrapper(GeoType.PINK_RHYOLITE);
                    }
                    if (rand.nextFloat() > 0.7f) {
                        if (y < 60) {
                            wrapperArray[x][y][z].setOreType(ModGeoOres.LIMONITE);
                        } else {
                            wrapperArray[x][y][z].setOreType(ModGeoOres.MAGNETITE);
                        }
                        wrapperArray[x][y][z].setGrade(Grade.NORMAL);
                    }
                }
            }
        }

        surfaceRegolith();

        //processOres();
        return wrapperArray;
    }

    private void surfaceRegolith() {

    }

//    public int[] getHorizontalDeformOffset(int x, int y, int z) {
//        // things that cause horizontal deformation
//        // x/z component of faults
//
//        int age = 0;
//
//        switch (age) {
//            case 1:
//            case 2:
//            default:
//        }
//    }
//
//    public int getYDeformOffset(int x, int y, int z) {
//        // things that cause VERTICAL deformation
//        // y-component of faults
//        // intrusive ign features
//            // sills, batholiths
//        // tilt
//        // buckling
//
//
//
//        int age = 0;
//
//        switch (age) {
//            case 1:
//            case 2:
//            default:
//        }
//    }


}
