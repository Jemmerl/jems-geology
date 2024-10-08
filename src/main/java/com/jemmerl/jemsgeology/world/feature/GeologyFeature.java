package com.jemmerl.jemsgeology.world.feature;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.NoiseInit;
import com.jemmerl.jemsgeology.util.UtilMethods;
import com.jemmerl.jemsgeology.util.noise.GenerationNoise.BlobNoise;
import com.jemmerl.jemsgeology.geology.ChunkReader;
import com.jemmerl.jemsgeology.geology.GeoMapBuilder;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GeologyFeature extends Feature<NoFeatureConfig> {

    public GeologyFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (!NoiseInit.configured) {
            NoiseInit.init(seedReader.getSeed());
        }

        ChunkReader chunkReader = new ChunkReader(seedReader, pos);
        GeoMapBuilder geoMapBuilder = new GeoMapBuilder(chunkReader, pos, rand);
        processChunk(seedReader, chunkReader, geoMapBuilder, pos);

        return true;
    }


    private void processChunk(ISeedReader reader, ChunkReader chunkReader, GeoMapBuilder geoMapBuilder, BlockPos pos) {
        IChunk chunk = reader.getChunk(pos);
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                mutablePos.setPos(posX, 0, posZ);

                int topY = chunkReader.getMaxHeightVal(x, z);

                for (int y = 0; y < topY; y++) {
                    mutablePos.setY(y);

                    BlockState originalState = chunk.getBlockState(mutablePos);
                    GeologyType stoneGeoType = geoMapBuilder.getGeoWrapper(x, y, z).getGeologyType();
                    OreType oreType = geoMapBuilder.getGeoWrapper(x, y, z).getOreType();
                    GradeType gradeType = geoMapBuilder.getGeoWrapper(x, y, z).getGradeType();

                    BlockState placeState;
                    switch (UtilMethods.replaceableStatus(originalState)) {
                        case FAILED:
                        case GEOBLOCK_STONE:
                        case GEOBLOCK_REGOLITH:
                        case GEOBLOCK_DETRITUS:
                            break;
                        case VANILLA_STONE:
                            placeState = ModBlocks.GEOBLOCKS.get(stoneGeoType)
                                    .getStoneOre(oreType, gradeType).getDefaultState();

                            chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, placeState, false);
                            break;
                        case VANILLA_DETRITUS:
                            //if(gravel)
                            //ifelse{
                            if (y <= (topY - getDepth(mutablePos.toImmutable()))) {
                                placeState = ModBlocks.GEOBLOCKS.get(stoneGeoType)
                                        .getRegolithOre(oreType, gradeType).getDefaultState();

                                chunk.getSections()[y >> 4].setBlockState(x, y & 15, z, placeState, false);
                            }
                            break;
                        default:
                            JemsGeology.getInstance().LOGGER.warn("Unexpected block condition in Stone Generation replacement; contact mod dev!");
                    }
                }
            }
        }
    }

    private int getDepth(BlockPos pos) {
        return Math.round((Math.abs(BlobNoise.blobWarpRadiusNoise((pos.getX() * 2), 0, (pos.getZ() * 2))) * 3) + 3);
    }

}



/*
Boolean isDet;
                topY = chunkReader.getMaxHeightVal(x, z);
                for (int y = 0; y < topY; y++) {
                    original = chunk.getBlockState(mutable);
                    isDet = original.getBlock().isIn(ModTags.Blocks.DETRITUS); // Check if valid detritus

                    if (!isDet) {
                        replacing = stateMap.getStoneState(x, y, z);
                        // Check if the pulled blockstate is null or if the block being replaced is invalid
                        // If either is true, then skip this position
                        if ((replacing == null) || !replaceStone(original)) {
                            continue;
                        }
                    } else {
                        BlockState stateHolder = stateMap.getDetritusState(x, y, z);
                        // Same as above, except there is no need to check if the block is valid
                        // Since it is already confirmed to be a detritus block, placement is valid
                        if (stateHolder == null) {
                            continue;
                        }
                        // Pull the stored state properties and put them into the appropriate detritus
                        replacing = StoneType.convertToDetritus(original)
                                .with(StoneOreBlock.ORE_TYPE, stateHolder.get(StoneOreBlock.ORE_TYPE))
                                .with(StoneOreBlock.GRADE_TYPE, stateHolder.get(StoneOreBlock.GRADE_TYPE));
                    }
 */



