package com.jemmerl.jemsgeology;

import com.jemmerl.jemsgeology.init.*;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import com.jemmerl.jemsgeology.init.geologyinit.ModGeoOres;
import com.jemmerl.jemsgeology.init.worldgen.ModConfiguredFeatures;
import com.jemmerl.jemsgeology.init.worldgen.ModFeaturePlacements;
import com.jemmerl.jemsgeology.init.worldgen.ModFeatures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
TODO board
    - handle silverfish??
    - mossy and cracked variants
 */

@Mod(JemsGeology.MOD_ID)
public class JemsGeology
{
    public static final String MOD_ID = "jemsgeo";
    public static final Logger LOGGER = LogManager.getLogger();

    public JemsGeology() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModGeoOres.init();

        ModEntities.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModFeaturePlacements.register(eventBus);
        ModFeatures.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
        ServerConfig.loadConfig(ServerConfig.SERVER_SPEC, FMLPaths.GAMEDIR.get()
                .resolve(FMLConfig.defaultConfigPath()).resolve(MOD_ID + "-server.toml"));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModLootConditionTypes.registerLootConditions();

        // Don't remove lambda in case needed later
        DeferredWorkQueue.runLater(() -> {
            ModConfiguredFeatures.registerConfiguredFeatures();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Set transparent textures for all ore blocks
        for (GeoRegistry geoRegistry : ModBlocks.GEO_BLOCKS.values()) {
            for(Block block: geoRegistry.getAllOreBlocks())
                RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
        RenderTypeLookup.setRenderLayer(ModBlocks.LICHEN_BLOCK.get(), RenderType.getCutout());
    }
}
