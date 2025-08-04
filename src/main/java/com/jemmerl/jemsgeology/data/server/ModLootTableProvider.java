package com.jemmerl.jemsgeology.data.server;

import com.google.common.collect.ImmutableList;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.data.DataGenerators;
import com.jemmerl.jemsgeology.geology.ores.GeoLoot;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
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
        // Copied from BlockLootTables, as it is private
        private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(
                ItemPredicate.Builder.create()
                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

        @Override
        protected void addTables() {
            for (GeoRegistry geoRegistry : ModBlocks.GEO_BLOCKS.values()) {
                boolean hasCobble = geoRegistry.hasCobble();

                registerStoneLootTables(geoRegistry);

//                if (geoRegistry.getGeoType().getGeoGroup().isDetritus()) {
//                    // Loot Tables for base stone/stone ores
//                    registerDetritusLootTables(geoRegistry);
//                } else {
//                    // Loot Tables for base stone/stone ores
//
//                }

                if (hasCobble) {
                    registerCobblesLootTable(geoRegistry);
                    registerDropSelfLootTable(geoRegistry.getCobblestone());
                    registerLootTable(geoRegistry.getCobbleSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(geoRegistry.getCobbleStairs());
                    registerDropSelfLootTable(geoRegistry.getCobbleWall());

                    registerDropSelfLootTable(geoRegistry.getMossyCobblestone());
                    registerLootTable(geoRegistry.getMossyCobbleSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(geoRegistry.getMossyCobbleStairs());
                    registerDropSelfLootTable(geoRegistry.getMossyCobbleWall());

                    registerLootTable(geoRegistry.getRawSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(geoRegistry.getRawStairs());
                    registerDropSelfLootTable(geoRegistry.getRawWall());

                    registerDropSelfLootTable(geoRegistry.getPolishedStone());
                    registerLootTable(geoRegistry.getPolishedSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(geoRegistry.getPolishedStairs());
                    registerDropSelfLootTable(geoRegistry.getPolishedWall());

                    registerDropSelfLootTable(geoRegistry.getBricks());
                    registerLootTable(geoRegistry.getBrickSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(geoRegistry.getBrickStairs());
                    registerDropSelfLootTable(geoRegistry.getBrickWall());

                    registerDropSelfLootTable(geoRegistry.getMossyBricks());
                    registerLootTable(geoRegistry.getMossyBrickSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(geoRegistry.getMossyBrickStairs());
                    registerDropSelfLootTable(geoRegistry.getMossyBrickWall());

                    registerDropSelfLootTable(geoRegistry.getChiseled());
                    registerDropSelfLootTable(geoRegistry.getCracked());
                    registerDropSelfLootTable(geoRegistry.getPillar());
                    registerDropSelfLootTable(geoRegistry.getButton());
                    registerDropSelfLootTable(geoRegistry.getPressurePlate());
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
        private void registerStoneLootTables(GeoRegistry geoRegistry) {
            registerStoneNoOreLoot(geoRegistry);
            for (OreType oreType : ModGeoOres.getModOreTypes()) {
                if (!(oreType.hasOre() && oreType.getGeoPredicate().generatesIn(geoRegistry.getGeoType())) ) continue;
                registerStoneOreLoot(geoRegistry, oreType, Grade.NORMAL);
                if (oreType.hasPoorOre()) {
                    registerStoneOreLoot(geoRegistry, oreType, Grade.POOR);
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////

        private void registerStoneNoOreLoot(GeoRegistry geoRegistry) {
            LootEntry.Builder<?> rockEntry = buildGeoLootEntry(geoRegistry.getGeoType().getGeoLoot(), geoRegistry.getDropItem());
            Block block = geoRegistry.getBaseGeoBlock().getBlock();

            registerLootTable(block, withExplosionDecay(block, LootTable.builder()
                    .addLootPool(LootPool.builder().addEntry(rockEntry))));
        }

        private void registerStoneOreLoot(GeoRegistry geoRegistry, OreType oreType, Grade grade) {
            LootEntry.Builder<?> rockEntry = buildGeoLootEntry(geoRegistry.getGeoType().getGeoLoot(), geoRegistry.getDropItem());

            Block block = geoRegistry.getOreVariant(oreType, grade);
            LootEntry.Builder<?> oreEntry = buildGeoLootEntry(oreType, grade);

            registerLootTable(block, withExplosionDecay(block, LootTable.builder()
                    .addLootPool(LootPool.builder().addEntry(rockEntry))
                    .addLootPool(LootPool.builder().addEntry(oreEntry))));
        }



//        // Register a block with the ability to be silk-touched. More accepting than existing methods.
//        private void withSilkTouch(Block block, LootEntry.Builder<?> lootEntry) {
//            registerLootTable(block, LootTable.builder()
//                    .addLootPool(LootPool.builder()
//                            .rolls(ConstantRange.of(1))
//                            .acceptCondition(SILK_TOUCH)
//                            .acceptCondition(SurvivesExplosion.builder())
//                            .addEntry(ItemLootEntry.builder(block)
//                                    .alternatively(lootEntry))));
//        }


//        // Register a base stone/detritus ore block with the ability to be silk-touched
//        private LootTable.Builder buildBaseStoneOreLootTable(Block block, LootEntry.Builder<?> oreEntry, LootEntry.Builder<?> rockEntry) {
//            return buildOreLootTable(block, oreEntry)
//                    .addLootPool(LootPool.builder()
//                            .rolls(ConstantRange.of(1))
//                            .acceptCondition(SILK_TOUCH.inverted())
//                            .acceptCondition(SurvivesExplosion.builder())
//                            .addEntry(rockEntry));
//        }



        // Build the loot entry component for ore drops
        private LootEntry.Builder<?> buildGeoLootEntry(OreType oreType, Grade grade) {
            Item oreItem;
            GeoLoot geoLoot;
            switch (grade) {
                //case RICH:
                case POOR:
                    oreItem = ModItems.ORE_ITEMS.get(oreType).getOreItem(true).asItem();
                    geoLoot = oreType.getPoorGeoLoot();
                    break;
                case NORMAL:
                default: // NONE should not happen anyway
                    oreItem = ModItems.ORE_ITEMS.get(oreType).getOreItem(false).asItem();
                    geoLoot = oreType.getGeoLoot();
            }
            return buildGeoLootEntry(geoLoot, oreItem);
        }

        private LootEntry.Builder<?> buildGeoLootEntry(GeoLoot geoLoot, Item oreItem) {
            ItemLootEntry.Builder<?> builder = ItemLootEntry.builder(oreItem)
                    .acceptFunction(ExplosionDecay.builder())
                    .acceptFunction(SetCount.builder(geoLoot.getItemRange()));

            if (geoLoot.isAffectedByFortune()) {
                switch (geoLoot.getFortuneFormula()) {
                    case UNIFORM:
                        builder.acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE));
                        break;
                    case BINOMIAL:
                        builder.acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3));
                        break;
                    case ORE_DROPS:
                    default:
                        builder.acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE));
                };
            }
            return builder;
        }



        // Don't worry about this.
        @Override
        protected Iterable<Block> getKnownBlocks() {
              return ModBlocks.BLOCKS.getEntries().stream()
                    .filter(block -> !DataGenerators.IGNORE.contains(block))
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }
    }




}
