package com.jemmerl.jemsgeology;

import com.jemmerl.jemsgeology.capabilities.chunk.chunkheight.ChunkHeightCapProvider;
import com.jemmerl.jemsgeology.capabilities.chunk.chunkheight.IChunkHeightCap;
import com.jemmerl.jemsgeology.capabilities.chunk.chunkheight.ChunkHeightCapStorage;
import com.jemmerl.jemsgeology.capabilities.chunk.chunkheight.ChunkHeightCapability;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapProvider;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapStorage;
import com.jemmerl.jemsgeology.init.*;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import com.jemmerl.jemsgeology.init.worldgen.ModConfiguredFeatures;
import com.jemmerl.jemsgeology.init.worldgen.ModFeaturePlacements;
import com.jemmerl.jemsgeology.init.worldgen.ModFeatures;
import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.ChunkGennedCapProvider;
import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.ChunkGennedCapStorage;
import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.ChunkGennedCapability;
import com.jemmerl.jemsgeology.capabilities.world.chunkgenned.IChunkGennedCap;
import com.jemmerl.jemsgeology.capabilities.world.deposit.DepositCapProvider;
import com.jemmerl.jemsgeology.capabilities.world.deposit.DepositCapStorage;
import com.jemmerl.jemsgeology.capabilities.world.deposit.DepositCapability;
import com.jemmerl.jemsgeology.capabilities.world.deposit.IDepositCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.IWaterTableCap;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    - If/when implementing cave-ins, make regoliths (soils, saproite) much harder to support to encourage pit mines
    - Add salt water? Would be useful for in salt flats. Config option to disable generating in oceans, if compat needed
        -> Config to not infinite source, or if infinite, only if in ocean biome AND below sea level

    - Yeah, that'd do it. Rule of thumb: nuke all static caches with level context in ServerStoppingEvent or
    ServerStoppedEvent (the latter is probably safer since it happens after saving
 */

@Mod(JemsGeology.MOD_ID)
public class JemsGeology
{
    public static final String MOD_ID = "jemsgeo";
    public static final Logger LOGGER = LogManager.getLogger();

    public JemsGeology() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

//        USED FOR GETTING ALL THE GEO BLOCK NAMES EASILY
//        String str = "";
//        for (GeoType geoType: GeoType.values()) {
//            str = str + "\n " + geoType.getName();
//        }
//        System.out.println(str);

        ModEntities.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModFeaturePlacements.register(eventBus);
        ModFeatures.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
        ServerConfig.loadConfig(ServerConfig.SERVER_SPEC, FMLPaths.GAMEDIR.get()
                .resolve(FMLConfig.defaultConfigPath()).resolve(MOD_ID + "-server.toml"));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IDepositCap.class, new DepositCapStorage(), DepositCapability::new);
        CapabilityManager.INSTANCE.register(IChunkGennedCap.class, new ChunkGennedCapStorage(), ChunkGennedCapability::new);
        CapabilityManager.INSTANCE.register(IWaterTableCap.class, new WaterTableCapStorage(), WaterTableCapability::new);
        CapabilityManager.INSTANCE.register(IChunkHeightCap.class, new ChunkHeightCapStorage(), ChunkHeightCapability::new);

        ModLootConditionTypes.registerLootConditions();

        // Don't remove lambda in case needed later
        DeferredWorkQueue.runLater(() -> {
            ModConfiguredFeatures.registerConfiguredFeatures();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Set transparent textures for all ore blocks
        for (GeoRegistry geoRegistry : ModBlocks.GEO_BLOCKS.values()) {
            for(Block block: geoRegistry.getAllOreGeoBlocks())
                RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
        RenderTypeLookup.setRenderLayer(ModBlocks.LICHEN_BLOCK.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public void attachWorldCap(AttachCapabilitiesEvent<World> event) {
        if (event.getObject().isRemote) return;

        //TODO note, can attach to other dims in the future, just for massive ore deposits
        // for now, kept to the overworld for simplicity
        String dimName = event.getObject().getDimensionKey().getLocation().toString();

        if (dimName.equals("minecraft:overworld")) {
            event.addCapability(new ResourceLocation(JemsGeology.MOD_ID, "deposit"), new DepositCapProvider());
            event.addCapability(new ResourceLocation(JemsGeology.MOD_ID, "generated_chunks"), new ChunkGennedCapProvider());
            event.addCapability(new ResourceLocation(JemsGeology.MOD_ID, "water_table"), new WaterTableCapProvider(event.getObject()));

            JemsGeology.LOGGER.debug("JemsGeology world-capabilities successfully attached for {}", dimName);
        }
    }

    @SubscribeEvent
    public void attachChunkCap(AttachCapabilitiesEvent<Chunk> event) {
        World eventWorld = event.getObject().getWorld();
        if (eventWorld.isRemote) return;

        //TODO note, can attach to other dims in the future, just for massive ore deposits
        // for now, kept to the overworld for simplicity
        String dimName = eventWorld.getDimensionKey().getLocation().toString();

        if (dimName.equals("minecraft:overworld")) {
            IWaterTableCap wtCap = eventWorld.getCapability(WaterTableCapability.WATER_TABLE_CAPABILITY).orElse(null);

            int height = Integer.MIN_VALUE;
            if (wtCap != null) {
                //noinspection deprecation
                height = wtCap.WG_consumeChunkHeight(event.getObject().getPos());
            }
//            System.out.println("Added to: " + event.getObject().getPos());
            event.addCapability(new ResourceLocation(JemsGeology.MOD_ID, "chunk_height"), new ChunkHeightCapProvider(height));
        }
    }
}

/*
TODO notes
    - EVERYTHING MUST TIE INTO THE WORLD SEED
 ==========================================================
    - generate everything flat, initially.
    - place down strata based ores, ones that depend on the layer <- rethink?
    - use global richness values (based on stone region or volcanic region) as well as local richness values
        (based on the individual strata. this can be done using the noise value and the region value (which already
        tie into the world seed) as seeds for individual random values)
        -> multiple richness value types could be made, TBD, such as "silicate content", etc.
    - strata ores can be placed by a noise map (web/vein like), invert noise map (blob-like), or as vanilla blobs
    - THEN deform layers.



    - FAULT NOTE:
        -> chance around edge of fault to spawn rock as regolith. Can do be searching +/- around block and see
           if different fault noise. then % chance to be regolith (or have chance be inherent in #of search tries?)


 */
