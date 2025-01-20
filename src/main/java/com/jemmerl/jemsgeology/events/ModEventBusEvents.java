package com.jemmerl.jemsgeology.events;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.events.loot.StoneQuarryModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.annotation.Nonnull;

//bus = Mod.EventBusSubscriber.Bus.MOD
@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new StoneQuarryModifier.Serializer().setRegistryName(
                new ResourceLocation(JemsGeology.MOD_ID, "quarry_stone_block")
        ));
    }

//    //@OnlyIn(Dist.DEDICATED_SERVER)
//    @SubscribeEvent
//    public static void beforeServerStart(final FMLServerAboutToStartEvent event) {
//        System.out.println("before ================================================================================================================================");
//        try {
//            System.out.println(event.getServer().getWorld(World.OVERWORLD).getSeed());
//        } catch (NullPointerException e) {
//
//        };
//    }
//
//    //@OnlyIn(Dist.DEDICATED_SERVER)
//    @SubscribeEvent
//    public static void serverStarting(final FMLServerStartingEvent event) {
//        System.out.println("during ================================================================================================================================");
//        try {
//            System.out.println(event.getServer().getWorld(World.OVERWORLD).getSeed());
//        } catch (NullPointerException e) {
//
//        };
//    }
//
//    //@OnlyIn(Dist.DEDICATED_SERVER)
//    @SubscribeEvent
//    public static void serverStarted(final FMLServerStartedEvent event) {
//        System.out.println("done ================================================================================================================================");
//        try {
//            System.out.println(event.getServer().getWorld(World.OVERWORLD).getSeed());
//        } catch (NullPointerException e) {
//
//        };
//    }
//
//    @SubscribeEvent
//    public static void stuffLoaded(final FMLLoadCompleteEvent event) {
//        System.out.println("loaded =============================================XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX===================================================");
//    }
}
