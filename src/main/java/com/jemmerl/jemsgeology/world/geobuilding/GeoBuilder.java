package com.jemmerl.jemsgeology.world.geobuilding;

import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.IChunkGennedCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.IWaterTableCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability;
import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.ChunkGennedCapability;
import com.jemmerl.jemsgeology.capabilities.world.deposit.DepositCapability;
import com.jemmerl.jemsgeology.capabilities.world.deposit.IDepositCap;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.world.geobuilding.georegions.BasementGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;

import java.util.Random;

public class GeoBuilder {

    private final int maxHeight;
    private final ISeedReader world;
    private final BlockPos cornerPos; // Starting position of this chunk's generation
    private final Random rand;

//    private final int[][][] deformHeights;
//    private final int[] oldFaultShift = new int[3];
//    private final int[] newFaultShift = new int[3];;

    private final UnbakedGeo[][][] wrapperArray; // X Z Y
    private final short[][][] deformArray; // X Z Y
    private final short[][] wtHeights = new short[16][16];

    // Note: do not make these static, in case this builder is used for multiple dimensions.
    // If in the future, the standard is to use a different geo-builder implementation/obj per dim, then can be static.
    //  -> (maybe a GeoBuilderBuilder gets implemented??)

    private final IDepositCap depCap;
    private final IChunkGennedCap cgCap;
    private final IWaterTableCap wtCap;

    // TODO I think it may be best to inherently process chunks by the column. If everything is designed that way,
    //  then there should never be issues with post-placing ores as the column can just recalculate.

    public GeoBuilder(ISeedReader world, BlockPos pos, Random rand, int maxHeight) {
        wrapperArray = new UnbakedGeo[16][16][maxHeight];
        deformArray = new short[16][16][maxHeight];

        this.world = world;
        this.maxHeight = maxHeight;
        this.cornerPos = pos;
        this.rand = rand;

//        this.deformHeights = new int[16][this.chunkReader.getMaxHeight()][16];

        this.depCap = world.getWorld().getCapability(DepositCapability.DEPOSIT_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null in \"GeoBuilder\" feature..."));
        this.cgCap = world.getWorld().getCapability(ChunkGennedCapability.CHUNK_GENNED_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null in \"GeoBuilder\" feature..."));
        this.wtCap = world.getWorld().getCapability(WaterTableCapability.WATER_TABLE_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo water table capability is null in \"GeoBuilder\" feature..."));
    }

//    private boolean checkchunk(int x, int z) {
//        ServerWorld sWorld = world.getWorld();
//        if (sWorld == null) {
//            return false;
//        }
////        System.out.println("1");
//        IChunk iChunk = sWorld.getChunk(x,z, ChunkStatus.SURFACE);
////        System.out.println("2");
//        if (iChunk instanceof Chunk) System.out.println("A");
//        if (iChunk instanceof ChunkPrimer) System.out.println("B");
//        if (iChunk instanceof ChunkPrimerWrapper) System.out.println("C");
//        if (iChunk instanceof EmptyChunk) System.out.println("D");
//
//        if (iChunk instanceof Chunk) {
//            System.out.println("3");
//            IChunkHeightCap chunkHeightCap = ((Chunk) iChunk).getCapability(ChunkHeightCapability.CHUNK_HEIGHT_CAPABILITY).orElse(null);
//            return  ((chunkHeightCap != null) && (chunkHeightCap.getChunkHeight() != Integer.MIN_VALUE));
//        }
//        return false;
//    }



    public UnbakedGeo[][][] build() {
        // check if can get the heights of ungenned chunks around curr gen. if so, check if fully genned, because
        //  wont want to use their height val (might be a tree!). Can use the caps now tho!!
        //  make a custom WTcap function for world gen that takes in info like heights? or just let it do its thing?

        ChunkPos centerCPos = new ChunkPos(cornerPos);
        //noinspection deprecation
        int chunkHeight = wtCap.WG_cacheChunkHeight(centerCPos, wtCap.WG_chunkWTHeightMap(centerCPos, world, wtHeights));
//        System.out.println(world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, cornerPos.add(7,0,7)).getY());

        /*
        for (GeoWrapper[][] layer : wrapperArray) {
            for (GeoWrapper[] row : layer) {
                for (GeoWrapper wrapper : row) {
                    wrapper =
                }
            }
        }
         */

        // Fill the default array WITHOUT SHALLOW COPYING AN ENTIRE DIMENSION OF IT THIS TIME AAAAAAAAAHHH
        for (int x = 0; x < wrapperArray.length; x++) {
            for (int z = 0; z < wrapperArray[0].length; z++) {
                for (int y = 0; y < wrapperArray[0][0].length; y++) {
                    wrapperArray[x][z][y] = new UnbakedGeo();
                }
            }
        }

        generateStones();
        applyRegolith();

        //processOres();
        return wrapperArray;
    }

    private void generateStones() {
        // basement ign intrusive
        // deform (uplift/warp) basement
        generateBasement();
        // first strata faulting/deformation
        // first strata ign intrusive
        generateUpperStrata();
        // second strata faulting
        // second strata ign intrusive
    }

    // todo separate out bedrock faulting/deform only for ore deform calc
    // todo may need to pass in array value for metamorphism??
    private void generateBasement() {
        // basement faulting/deformation
        // basement
        BasementGenerator.buildBasement(wrapperArray, deformArray, cornerPos);


//        for (int x = 0; x < wrapperArray.length; x++) {
//            for (int z = 0; z < wrapperArray[0][0].length; z++) {
//                for (int y = 0; y < wrapperArray[0].length; y++) {
//                    wrapperArray[x][z][y] = BasementGenerator.getGeoWrapper(cornerPos.add(x, y, z));
//                }
//            }
//        }

    }


    private void generateUpperStrata() {
        for (int x = 0; x < wrapperArray.length; x++) {
            for (int z = 0; z < wrapperArray[0].length; z++) {
                for (int y = 0; y < wrapperArray[0][0].length; y++) {
                    // todo make a method in unbaked to make this cleaner?
                    UnbakedGeo unbakedGeo = wrapperArray[x][z][y];
                    if (unbakedGeo.isEmpty()) unbakedGeo.setGeoType(GeoType.LIMESTONE);
                }
            }
        }
    }



    private void applyRegolith() {
        // generate depth map
        // convert to regolith above depth map
    }








    public short[][][] buildDeformArray() {
        short[][][] totalDeformArray = new short[16][maxHeight][16];

        return totalDeformArray;
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

/*
//        for (int x = 0; x < wrapperArray.length; x++) {
//            for (int y = 0; y < wrapperArray[0].length; y++) {
//                for (int z = 0; z < wrapperArray[0][0].length; z++) {
//                    if ((y & 8)/8 == 0) {
//                        wrapperArray[x][y][z] = new GeoWrapper(GeoType.GRAY_RHYOLITE);
//                    } else {
//                        wrapperArray[x][y][z] = new GeoWrapper(GeoType.PINK_RHYOLITE);
//                    }
//                    if (rand.nextFloat() > 0.7f) {
//                        if (y < 60) {
//                            wrapperArray[x][y][z].setOreType(ModGeoOres.MAGNETITE);
//                        } else {
//                            wrapperArray[x][y][z].setOreType(ModGeoOres.LIMONITE);
//                        }
//                        wrapperArray[x][y][z].setGrade(Grade.NORMAL);
//                    }
//                }
//            }
//        }
 */
