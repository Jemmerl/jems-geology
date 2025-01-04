package com.jemmerl.jemsgeology.world;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

// Credit goes to Project Rankine (CannoliCatfish and tritespartan17) for the basis of this class; edited to fit needs
// https://github.com/CannoliCatfish/project-rankine/tree/1.16-1.3.X

@Mod.EventBusSubscriber
public class WorldGeneration {

    // Features in UNDERGROUND_ORES stage
    private static List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>, List<ResourceLocation>>> undergroundOresStageFeatures() {
        List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?, ?>, List<ResourceLocation>>> allStoneFeatures = new ArrayList<>();

        //allStoneFeatures.add(new AbstractMap.SimpleEntry<>(JemsGeoFeatures.STONE_GEN_CONFIG, null));

        //allStoneFeatures.add(new AbstractMap.SimpleEntry<>(JemsGeoFeatures.ORE_CONST_SCATTER_CONFIG, null));

        return allStoneFeatures;
    }

//    // Features in UNDERGROUND_DECORATION stage
//    private static List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>, List<ResourceLocation>>> undergroundDecorationStageFeatures() {
//        List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?, ?>, List<ResourceLocation>>> allStoneFeatures = new ArrayList<>();
//
//        allStoneFeatures.add(new AbstractMap.SimpleEntry<>(JemsGeoFeatures.MAAR_DIATREME_GEN_CONFIG, null));
//        allStoneFeatures.add(new AbstractMap.SimpleEntry<>(JemsGeoFeatures.BOULDER_GEN_CONFIG, null));
//
//        return allStoneFeatures;
//    }
//
//    // Features in TOP_LAYER_MODIFICATION stage
//    private static List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>, List<ResourceLocation>>> topLayerModStageFeatures() {
//        List<LinkedHashMap.SimpleEntry<ConfiguredFeature<?, ?>, List<ResourceLocation>>> allStoneFeatures = new ArrayList<>();
//
//        allStoneFeatures.add(new AbstractMap.SimpleEntry<>(JemsGeoFeatures.ORE_PLACER_CONFIG, null));
//        allStoneFeatures.add(new AbstractMap.SimpleEntry<>(JemsGeoFeatures.DELAYED_ORE_GEN_CONFIG, null));
//
//        return allStoneFeatures;
//    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void processWorldGenFeatures(final BiomeLoadingEvent biomeLoadingEvent) {
        if (biomeLoadingEvent.getName() != null) {

            switch (biomeLoadingEvent.getCategory()) {
                case NETHER:
                case THEEND:
                case NONE:
                    return;
                default:
            }

            // VanillaFeatureRemovers.processRawGeneration(event)
            VanillaFeatureRemover.processLakes(biomeLoadingEvent);
            VanillaFeatureRemover.processLocalModifications(biomeLoadingEvent);
            VanillaFeatureRemover.processUndergroundStructures(biomeLoadingEvent);
            VanillaFeatureRemover.processUndergroundOres(biomeLoadingEvent);
            VanillaFeatureRemover.processUndergroundDecorations(biomeLoadingEvent);
            VanillaFeatureRemover.processVegetalDecorations(biomeLoadingEvent);
            VanillaFeatureRemover.processStructures(biomeLoadingEvent);





            // mandatory removals


            // config based removals
            //



            // Featues in UNDERGROUND_ORES stage
            for (LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : undergroundOresStageFeatures()) {
                GenerationStage.Decoration decorationStage = GenerationStage.Decoration.UNDERGROUND_ORES;
                if ((entry.getValue() == null) || (entry.getValue().contains(biomeLoadingEvent.getName()))) {
                    biomeLoadingEvent.getGeneration().withFeature(decorationStage.ordinal(),entry::getKey);
                }
            }

//            // Featues in UNDERGROUND_DECORATION stage
//            for (LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : undergroundDecorationStageFeatures()) {
//                GenerationStage.Decoration decorationStage = GenerationStage.Decoration.UNDERGROUND_DECORATION;
//                if ((entry.getValue() == null) || (entry.getValue().contains(biomeLoadingEvent.getName()))) {
//                    biomeLoadingEvent.getGeneration().withFeature(decorationStage.ordinal(),entry::getKey);
//                }
//            }
//
//            // Features in TOP_LAYER_MODIFICATION stage
//            for (LinkedHashMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : topLayerModStageFeatures()) {
//                GenerationStage.Decoration decorationStage = GenerationStage.Decoration.TOP_LAYER_MODIFICATION;
//                if ((entry.getValue() == null) || (entry.getValue().contains(biomeLoadingEvent.getName()))) {
//                    biomeLoadingEvent.getGeneration().withFeature(decorationStage.ordinal(),entry::getKey);
//                }
//            }
        }
    }

}
