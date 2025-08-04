package com.jemmerl.jemsgeology.data;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.data.client.ModBlockStateModelProvider;
import com.jemmerl.jemsgeology.data.client.ModItemModelProvider;
import com.jemmerl.jemsgeology.data.client.ModLangProvider;
import com.jemmerl.jemsgeology.data.server.*;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.ArrayList;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = JemsGeology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    // Blocks that are manually built, not generated.
    public static final ArrayList<RegistryObject<Block>> IGNORE = new ArrayList<>(Arrays.asList(
            ModBlocks.LICHEN_BLOCK, ModBlocks.DESERT_VARNISH_BLOCK,
            ModBlocks.THIN_SALT_CRUST_BLOCK, ModBlocks.THICK_SALT_CRUST_BLOCK
    ));

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
        gen.addProvider(new ModFurnaceRecipeProvider(gen));
        gen.addProvider(new ModLootTableProvider(gen));
        BlockTagsProvider blocktagsprovider = new ModBlockTagsProvider(gen, JemsGeology.MOD_ID, existingFileHelper);
        gen.addProvider(blocktagsprovider);
        gen.addProvider(new ModItemTagsProvider(gen, blocktagsprovider, JemsGeology.MOD_ID, existingFileHelper));
    }
}
