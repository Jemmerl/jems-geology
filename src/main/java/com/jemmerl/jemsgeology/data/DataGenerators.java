package com.jemmerl.jemsgeology.data;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.client.ModBlockStateModelProvider;
import com.jemmerl.jemsgeology.data.client.ModItemModelProvider;
import com.jemmerl.jemsgeology.data.client.ModLangProvider;
import com.jemmerl.jemsgeology.data.server.ModBlockTagsProvider;
import com.jemmerl.jemsgeology.data.server.ModCobblestoneRecipeProvider;
import com.jemmerl.jemsgeology.data.server.ModItemTagsProvider;
import com.jemmerl.jemsgeology.data.server.ModStonecutterRecipeProvider;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Client-side data providers
        gen.addProvider(new ModBlockStateModelProvider(gen, JemsGeology.MOD_ID, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, JemsGeology.MOD_ID, existingFileHelper));
        gen.addProvider(new ModLangProvider(gen, JemsGeology.MOD_ID, "en_us"));

        // Server-side data providers
        gen.addProvider(new ModCobblestoneRecipeProvider(gen));
        gen.addProvider(new ModStonecutterRecipeProvider(gen));
        //gen.addProvider(new ModLootTableProvider(gen));
        BlockTagsProvider blocktagsprovider = new ModBlockTagsProvider(gen, JemsGeology.MOD_ID, existingFileHelper);
        gen.addProvider(blocktagsprovider);
        gen.addProvider(new ModItemTagsProvider(gen, blocktagsprovider, JemsGeology.MOD_ID, existingFileHelper));
    }
}
