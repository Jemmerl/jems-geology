package com.jemmerl.jemsgeology.world.features.config;

import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.*;
import java.util.stream.Collectors;

public class BoulderFeatureConfig implements IFeatureConfig {
    public static final Codec<BoulderFeatureConfig> CODEC = RecordCodecBuilder.create((configInstance) -> {
        return configInstance.group(
            Codec.intRange(1, 12).fieldOf("min_radius_a").forGetter((config) -> config.min_radius_a),

            Codec.intRange(1, 12).fieldOf("max_radius_a").forGetter((config) -> config.max_radius_a),

            Codec.intRange(1, 12).fieldOf("min_radius_b").forGetter((config) -> config.min_radius_b),

            Codec.intRange(1, 12).fieldOf("max_radius_b").forGetter((config) -> config.max_radius_b),

            Codec.intRange(0, 3).fieldOf("max_side_boulders").orElse(0).forGetter((config) -> config.max_side_boulders),

            Codec.BOOL.fieldOf("place_on_hills").orElse(false).forGetter((config) -> config.place_on_hills),

            Codec.STRING.listOf().fieldOf("stones").xmap(
                    (strings) -> strings.stream().map(GeoType::fromString).distinct().collect(Collectors.toCollection(ArrayList::new)),
                    (geotypes) -> geotypes.stream().map(GeoType::getName).collect(Collectors.toList())
            ).forGetter((config) -> config.stoneStates)

        ).apply(configInstance, BoulderFeatureConfig::new);
    });

    private final ArrayList<GeoType> stoneStates;
    // original 2-12, trying 1-12
    public final int max_radius_a;
    public final int min_radius_a;
    public final int max_radius_b;
    public final int min_radius_b;
    public final boolean place_on_hills;
    public final int max_side_boulders;

    public BoulderFeatureConfig(int max_radius_a, int min_radius_a, int max_radius_b, int min_radius_b, int max_side_boulders, boolean place_on_hills, ArrayList<GeoType> stoneStates) {
        int max_a = max_radius_a;
        int min_a = min_radius_a;
        int max_b = max_radius_b;
        int min_b = min_radius_b;

        // I would rather just handle accidentally swapped values instead of making the user hunt down the error
        // Fast swap values if the a-radius min is bigger than the a-radius max
        if (min_a > max_a) {
            max_a = max_a ^ min_a;
            min_a = max_a ^ min_a;
            max_a = max_a ^ min_a;
        }

        // Fast swap values if the b-radius min is bigger than the b-radius max
        if (min_b > max_b) {
            max_b = max_b ^ min_b;
            min_b = max_b ^ min_b;
            max_b = max_b ^ min_b;
        }

        // Clamp values from 2 to 12
        this.max_radius_a = max_a;
        this.min_radius_a = min_a;
        this.max_radius_b = max_b;
        this.min_radius_b = min_b;
        this.place_on_hills = place_on_hills;
        this.max_side_boulders = max_side_boulders;
        this.stoneStates = stoneStates;
    }

    public GeoType getStoneState(Random rand) {
        return stoneStates.get(rand.nextInt(stoneStates.size()));
    }
}
