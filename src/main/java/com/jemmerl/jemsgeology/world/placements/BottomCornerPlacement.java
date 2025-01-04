package com.jemmerl.jemsgeology.world.placements;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BottomCornerPlacement extends SimplePlacement<NoPlacementConfig> {
    public BottomCornerPlacement(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    // Gets a single spot for placement, the bottom, north-west chunk corner.
    // X and Z increases into the chunk. Starts at Y = 0
    @Override
    protected Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
        return IntStream.range(0, 1).mapToObj((val) -> pos);
    }
}
