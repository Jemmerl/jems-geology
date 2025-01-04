package com.jemmerl.jemsgeology.init.worldgen;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.world.features.GeologyFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, JemsGeology.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> GEO_FEATURE
            = FEATURES.register("geo_feature", () -> new GeologyFeature(NoFeatureConfig.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus); }
}
