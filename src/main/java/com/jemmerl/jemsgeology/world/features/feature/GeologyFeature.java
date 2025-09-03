package com.jemmerl.jemsgeology.world.features.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.GeoBuilder;
import com.jemmerl.jemsgeology.geology.GeoWrapper;
import com.jemmerl.jemsgeology.util.ReplaceStatus;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GeologyFeature extends Feature<NoFeatureConfig> {

    // TODO looky at this cool array flattener! McJty
    //     public int getHeight(int x, int z) {
    //        return heightmap[z*16+x] & 0xff;
    //    }

    // TODO McJty's fast rand. To find later. I know I will use it, as I have been using my own implement.
//    private static int fastrand() {
//        gSeed = (214013 * gSeed + 2531011);
//        return (gSeed >> 16) & 0x7FFF;
//    }

    public GeologyFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {



//        if (!NoiseInit.configured) {
//            NoiseInit.init(seedReader.getSeed());
//        }

        GeoBuilder geoBuilder = new GeoBuilder(seedReader, pos, rand, getMaxTerrainHeight(seedReader, pos));
        GeoWrapper[][][] wrapperArray = geoBuilder.build();

        IChunk chunk = seedReader.getChunk(pos);
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                mutablePos.setPos(posX, 0, posZ);
                int topY = seedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, posX, posZ);

                for (int y = 0; y < topY; y++) {
                    mutablePos.setY(y);

                    BlockState originalState = chunk.getBlockState(mutablePos);
                    GeoWrapper geoWrapper = wrapperArray[x][y][z];

                    switch (ReplaceStatus.checkStatus(originalState)) {
                        case FAILED:
                        case GEOBLOCK_STONE:
                        case GEOBLOCK_REGOLITH:
                        case GEOBLOCK_DETRITUS:
                            break;
                        case VANILLA_STONE:
                            chunk.getSections()[y >> 4].setBlockState(x, y & 15, z,
                                    geoWrapper.toGeoBlock(false).getDefaultState(), false);
                            break;
                        case VANILLA_DETRITUS:
                            //if(gravel)
                            //ifelse{

                            //todo regolith blending?? select from random nearby for on borders

                            if (y <= (topY - getDepth(mutablePos.toImmutable()))) {
                                chunk.getSections()[y >> 4].setBlockState(x, y & 15, z,
                                        geoWrapper.toGeoBlock(true).getDefaultState(), false);
                            }
                            break;
                        default:
                            JemsGeology.LOGGER.error("Unexpected block replacement condition in stone replacement feature!");
                    }
                }
            }
        }

        return true;
    }

    //todo temp, controls depth of regolith
    private int getDepth(BlockPos pos) {
        return 2;
    }

    private int getMaxTerrainHeight(ISeedReader world, BlockPos pos) {
        int maxHeight = 0;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int height = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, (pos.getX()+x), (pos.getZ()+z));
                if (height > maxHeight) maxHeight = height;
            }
        }
        return maxHeight;
    }
}
