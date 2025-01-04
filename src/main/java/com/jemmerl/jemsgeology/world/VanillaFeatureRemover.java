package com.jemmerl.jemsgeology.world;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.ServerConfig;
import com.jemmerl.jemsgeology.init.ServerConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

public class VanillaFeatureRemover {

    // Looks for water and lava lake features
    public static void processLakes(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.LAKES);
        List<Supplier<ConfiguredFeature<?, ?>>> disable = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());
            if (resolved.feature instanceof LakesFeature) {
                BlockState fillState = ((BlockStateFeatureConfig)(resolved.getConfig())).state;

                if (ServerConfig.DISABLE_WATER_LAKES.get() && fillState.equals(Blocks.WATER.getDefaultState())) {
                    disable.add(featureSupplier);
                    if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(resolved.feature.getRegistryName() + "/water", event.getName());
                    continue;
                }

                if (ServerConfig.DISABLE_LAVA_LAKES.get() && fillState.equals(Blocks.LAVA.getDefaultState())) {
                    disable.add(featureSupplier);
                    if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(resolved.feature.getRegistryName() + "/lava", event.getName());
                }
            }
        }
        features.removeAll(disable);
    }


    // Looks for forest_rock feature
    public static void processLocalModifications(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS);
        List<Supplier<ConfiguredFeature<?, ?>>> disable = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            // todo mossy stone variants
//            if (ServerConfig.SERVER.disable_forest_rocks.get() && (Objects.requireNonNull(resolved.feature.getRegistryName()).toString().equals("minecraft:forest_rock"))) {
//                disable.add(featureSupplier);
//                JemsGeology.LOGGER.debug("Removed minecraft:forest_rock from biome: {}", event.getName());
//            }
        }
        features.removeAll(disable);
    }


    // Looks for monster room, fossils, and mineshaft features
    public static void processUndergroundStructures(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
        List<Supplier<ConfiguredFeature<?, ?>>> disable = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());
            String registryName = Objects.requireNonNull(resolved.feature.getRegistryName()).toString();

            if (ServerConfig.DISABLE_FOSSILS.get() && (registryName.equals("minecraft:fossil"))) {
                disable.add(featureSupplier);
                if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(registryName, event.getName());
            }
        }
        features.removeAll(disable);
    }


    // Look for ore features
    public static void processUndergroundOres(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
        List<Supplier<ConfiguredFeature<?, ?>>> disable = new ArrayList<>();

        List<BlockState> disableStates = new ArrayList<>();
        disableStates.add(Blocks.GRAVEL.getDefaultState());
        disableStates.add(Blocks.DIRT.getDefaultState());

        disableStates.add(Blocks.GRANITE.getDefaultState());
        disableStates.add(Blocks.ANDESITE.getDefaultState());
        disableStates.add(Blocks.DIORITE.getDefaultState());

        disableStates.add(Blocks.COAL_ORE.getDefaultState());
        disableStates.add(Blocks.IRON_ORE.getDefaultState());
        disableStates.add(Blocks.GOLD_ORE.getDefaultState());
        disableStates.add(Blocks.REDSTONE_ORE.getDefaultState());
        disableStates.add(Blocks.LAPIS_ORE.getDefaultState());
        disableStates.add(Blocks.DIAMOND_ORE.getDefaultState());
        disableStates.add(Blocks.EMERALD_ORE.getDefaultState());

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            Block block;
            if (resolved.feature instanceof OreFeature) {
                block = ((OreFeatureConfig) resolved.config).state.getBlock();

//                // Debug
//                if (ServerConfig.SERVER.debug_vanilla_features.get()) {
//                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: ORE_{} from Biome: {}",
//                            state.getBlock().toString().toUpperCase(Locale.ROOT), event.getName());
//                }
            } else if (resolved.feature instanceof ReplaceBlockFeature) {
                block = ((ReplaceBlockConfig) resolved.config).state.getBlock();

//                // Debug
//                if (ServerConfig.SERVER.debug_vanilla_features.get()) {
//                    JemsGeology.getInstance().LOGGER.info("Removed vanilla feature: ORE_EMERALD from Biome: {}", event.getName());
//                }
            } else {
                continue;
            }

            if (disableStates.contains(block.getDefaultState())) {
                disable.add(featureSupplier);
                if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(resolved.feature.getRegistryName() + "/" + block.getRegistryName().getPath(), event.getName());
            }
        }
        features.removeAll(disable);
    }


    // Looks for silverfish ore feature
    public static void processUndergroundDecorations(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> disable = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            if ((resolved.feature instanceof OreFeature) && (((OreFeatureConfig) resolved.config).state == Blocks.INFESTED_STONE.getDefaultState())) {
                disable.add(featureSupplier);
                if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(resolved.feature.getRegistryName(), event.getName());
            }
        }
        features.removeAll(disable);
    }


    // Looks for water and lava spring features
    public static void processVegetalDecorations(final BiomeLoadingEvent event) {
        List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> disable = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
            ConfiguredFeature<?, ?> resolved = resolve(featureSupplier.get());

            if (resolved.feature instanceof SpringFeature && (resolved.config instanceof LiquidsConfig)) {
                LiquidsConfig liquidsConfig = (LiquidsConfig) resolved.config;
                BlockState fluidState = liquidsConfig.state.getBlockState();

                if (ServerConfig.DISABLE_WATER_SPRINGS.get() && (fluidState.equals(Blocks.WATER.getDefaultState()))) {
                    disable.add(featureSupplier);
                    if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(resolved.feature.getRegistryName() + "/water", event.getName());
                    continue;
                }

                if (ServerConfig.DISABLE_LAVA_SPRINGS.get() && (fluidState.equals(Blocks.LAVA.getDefaultState()))) {
                    disable.add(featureSupplier);
                    if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(resolved.feature.getRegistryName() + "/lava", event.getName());
                }
            }
        }
        features.removeAll(disable);
    }

//    // Unused
//    public static void processRawGeneration(final BiomeLoadingEvent event) { }

//    // Unused
//    public static void processTopLayerModifications(final BiomeLoadingEvent event) { }


    // Remove specific structures (mineshafts, strongholds, and ruined portals)
    public static void processStructures(final BiomeLoadingEvent event) {
        List<Supplier<StructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
        List<Supplier<StructureFeature<?, ?>>> disable = new ArrayList<>();

        for (Supplier<StructureFeature<?, ?>> structureSupplier : structures) {
            String structureName = structureSupplier.get().field_236268_b_.getStructureName();

            if (ServerConfig.DISABLE_MINESHAFTS.get() && (structureName.equals("mineshaft"))) {
                disable.add(structureSupplier);
                if (ServerConfig.DEBUG_FEATURE_REMOVER.get()) debugMsg(structureName, event.getName());
            }

//            if (ServerConfig.SERVER.disable_ruined_portals.get() && (structureName.equals("ruined_portal"))) {
//                disable.add(structureSupplier);
//
//                // Debug
//                if (ServerConfig.SERVER.debug_vanilla_features.get()) {
//                    JemsGeology.getInstance().LOGGER.info("Removed a vanilla ruined_portal structure from Biome: {}", event.getName());
//                }
//
//                continue;
//            }

        }
        structures.removeAll(disable);
    }


    // As far as I can tell, this loops to find the actual feature below some unknown levels of abstraction
    private static ConfiguredFeature<?, ?> resolve(ConfiguredFeature<?, ?> f) {
        ConfiguredFeature<?, ?> subFeature = f;
        while (subFeature.getFeature() instanceof DecoratedFeature) {
            subFeature = ((DecoratedFeatureConfig) subFeature.getConfig()).feature.get();
        }
        return subFeature;
    }

    private static void debugMsg(ResourceLocation featureName, ResourceLocation biomeName) {
        JemsGeology.LOGGER.debug("Removed vanilla feature: {} from biome: {}", featureName, biomeName);
    }

    private static void debugMsg(String featureName, ResourceLocation biomeName) {
        JemsGeology.LOGGER.debug("Removed vanilla feature: {} from biome: {}", featureName, biomeName);
    }
}
