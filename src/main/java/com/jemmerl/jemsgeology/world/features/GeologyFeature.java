package com.jemmerl.jemsgeology.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
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

//        if (!NoiseInit.configured) {
//            NoiseInit.init(seedReader.getSeed());
//        }

//        ChunkReader chunkReader = new ChunkReader(seedReader, pos);
//        GeoMapBuilder geoMapBuilder = new GeoMapBuilder(chunkReader, pos, rand);
//        processChunk(seedReader, chunkReader, geoMapBuilder, pos);

        return true;
    }
}
