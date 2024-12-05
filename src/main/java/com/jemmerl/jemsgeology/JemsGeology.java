package com.jemmerl.jemsgeology;

import com.jemmerl.jemsgeology.init.ModEntities;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.geologyinit.ModGeoOres;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JemsGeology.MOD_ID)
public class JemsGeology
{
    private static JemsGeology instance;
    public static final String MOD_ID = "jemsgeo";
    public static final Logger LOGGER = LogManager.getLogger();

    public JemsGeology() {
        instance = this;
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModGeoOres.init();
        System.out.println("done register ores");

        ModEntities.register(eventBus);
        ModBlocks.register(eventBus);
        ModItems.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_SPEC);
//        ServerConfig.loadConfig(ServerConfig.SERVER_SPEC, FMLPaths.GAMEDIR.get()
//                .resolve(FMLConfig.defaultConfigPath()).resolve(MOD_ID + "-server.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        System.out.println("done setup");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    public static JemsGeology getInstance() {
        return instance;
    }
}
