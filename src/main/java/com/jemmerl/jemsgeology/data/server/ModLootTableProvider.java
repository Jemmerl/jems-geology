package com.jemmerl.jemsgeology.data.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModLootTableProvider.ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationTracker) {
//        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
//            LootTableManager.validateLootTable(validationTracker, entry.getKey(), entry.getValue());
    }

    // Generate loot tables for blocks
    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {

            for (GeoRegistry geoRegistry : ModBlocks.GEO_BLOCKS.values()) {
                boolean hasCobble = geoRegistry.hasCobble();

                if (geoRegistry.getGeoType().getGeoGroup().isDetritus()) {
                    // Loot Tables for base stone/stone ores
                    registerDetritusLootTables(geoRegistry);
                } else {
                    // Loot Tables for base stone/stone ores
                    registerStoneLootTables(geoRegistry, hasCobble);
                }

                if (hasCobble) {
                    // Loot Tables for regolith/regolith ores
                    registerRegolithLootTables(geoRegistry);

                    registerCobblesLootTable(geoRegistry);
                    registerDropSelfLootTable(geoRegistry.getCobblestone());

                    registerDropSelfLootTable(geoRegistry.getRawSlab());
                    registerDropSelfLootTable(geoRegistry.getRawStairs());
                    registerDropSelfLootTable(geoRegistry.getRawWall());
                    registerDropSelfLootTable(geoRegistry.getCobbleSlab());
                    registerDropSelfLootTable(geoRegistry.getCobbleStairs());
                    registerDropSelfLootTable(geoRegistry.getCobbleWall());
                    registerDropSelfLootTable(geoRegistry.getPolishedStone());
                    registerDropSelfLootTable(geoRegistry.getPolishedSlab());
                    registerDropSelfLootTable(geoRegistry.getPolishedStairs());
                } else {
                    // TODO TEMP! Handles ore-less base stones with no cobble/regolith (evaporites) as well as detritus
                    registerDropSelfLootTable(geoRegistry.getBaseStone());
                }
            }
        }

        //////////////////////////////////////
        //      Loot Table Registries       //
        //////////////////////////////////////

        // LOOSE COBBLES
        private void registerCobblesLootTable(GeoRegistry geoRegistry) {
            Block cobbles = geoRegistry.getCobbles();
            registerLootTable(cobbles, droppingWithSilkTouch(cobbles,
                    withExplosionDecay(cobbles, ItemLootEntry.builder(geoRegistry.getRockItem())
                            .acceptFunction(SetCount.builder(ConstantRange.of(4))))));
        }

        // BASE STONES
        private void registerStoneLootTables(GeoRegistry geoRegistry, boolean hasCobble) {

        }

        // REGOLITH
        private void registerRegolithLootTables(GeoRegistry geoRegistry) {

        }

        // DETRITUS
        private void registerDetritusLootTables(GeoRegistry geoRegistry) {

        }










        // Don't worry about this.
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }
    }




}
