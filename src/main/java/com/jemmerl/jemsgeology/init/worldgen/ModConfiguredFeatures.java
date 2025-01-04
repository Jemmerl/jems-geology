package com.jemmerl.jemsgeology.init.worldgen;

import com.jemmerl.jemsgeology.JemsGeology;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> GEO_FEATURE_CONFIG = ModFeatures.GEO_FEATURE.get().withConfiguration(new NoFeatureConfig())
            .withPlacement(ModFeaturePlacements.BOTTOM_CORNER_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

//    // TODO make chance config based? registration shouldnt use configs... move chance into generate method -- see same for boulders
//    public static final ConfiguredFeature<?, ?> MAAR_DIATREME_GEN_CONFIG = ModFeatures.MAAR_DIATREME_GEN.get().withConfiguration(new NoFeatureConfig())
//            .withPlacement(ModFeaturePlacements.PIPE_CHANCE_PLACEMENT.get().configure(new ChanceConfig(200))); // Normally 300, larger is less likely (100 for testing)
//
//    public static final ConfiguredFeature<?, ?> BOULDER_GEN_CONFIG = ModFeatures.BOULDER_GEN.get().withConfiguration(new NoFeatureConfig())
//            .withPlacement(ModFeaturePlacements.BOTTOM_CORNER_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG)); // Placement chance is determined via data
//
//    public static final ConfiguredFeature<?, ?> ORE_PLACER_CONFIG = ModFeatures.ORE_PLACER_GEN.get().withConfiguration(new NoFeatureConfig())
//            .withPlacement(ModFeaturePlacements.PLACER_CONSIST_PLACER.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
//
//    public static final ConfiguredFeature<?, ?> ORE_CONST_SCATTER_CONFIG = ModFeatures.ORE_CONST_SCATTER_GEN.get().withConfiguration(new NoFeatureConfig())
//            .withPlacement(ModFeaturePlacements.BOTTOM_CORNER_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
//
//    public static final ConfiguredFeature<?, ?> DELAYED_ORE_GEN_CONFIG = ModFeatures.DELAYED_ORE_GEN.get().withConfiguration(new NoFeatureConfig())
//            .withPlacement(ModFeaturePlacements.BOTTOM_CORNER_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

    /////////////////////////////////////////////////////////////////////////////////////////

    private static <FC extends IFeatureConfig> void CFRegister(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(JemsGeology.MOD_ID, name), configuredFeature);
    }

    public static void registerConfiguredFeatures() {
        CFRegister("geology_feature", GEO_FEATURE_CONFIG);
    }

}
