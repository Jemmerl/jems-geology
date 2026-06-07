package com.jemmerl.jemsgeology.data.server;

import com.google.common.collect.ImmutableList;
import com.jemmerl.jemsgeology.data.DataGenerators;
import com.jemmerl.jemsgeology.init.geology.ores.GeoLoot;
import com.jemmerl.jemsgeology.init.geology.ores.OreGrade;
import com.jemmerl.jemsgeology.init.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.geology.georegistries.BaseGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import com.jemmerl.jemsgeology.init.geology.georegistries.HardStoneGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.SoftStoneGeoRegistry;
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
            for (BaseGeoRegistry geoRegistry : ModBlocks.GEO_BLOCKS.values()) {

                registerStoneLootTables(geoRegistry);

//                if (geoRegistry.getGeoType().getGeoGroup().isDetritus()) {
//                    // Loot Tables for base stone/stone ores
//                    registerDetritusLootTables(geoRegistry);
//                } else {
//                    // Loot Tables for base stone/stone ores
//
//                }
//

                if (geoRegistry instanceof SoftStoneGeoRegistry) {
                    SoftStoneGeoRegistry softStoneGeoRegistry = (SoftStoneGeoRegistry) geoRegistry;

                    registerCobblesLootTable(softStoneGeoRegistry);
                    registerLootTable(softStoneGeoRegistry.getRawSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(softStoneGeoRegistry.getRawStairs());
                } else {
                    continue;
                }

                if (geoRegistry instanceof HardStoneGeoRegistry) {
                    HardStoneGeoRegistry hardStoneGeoRegistry = (HardStoneGeoRegistry) geoRegistry;

                    registerDropSelfLootTable(hardStoneGeoRegistry.getCobblestone());
                    registerLootTable(hardStoneGeoRegistry.getCobbleSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(hardStoneGeoRegistry.getCobbleStairs());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getCobbleWall());

                    registerDropSelfLootTable(hardStoneGeoRegistry.getMossyCobblestone());
                    registerLootTable(hardStoneGeoRegistry.getMossyCobbleSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(hardStoneGeoRegistry.getMossyCobbleStairs());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getMossyCobbleWall());

                    registerDropSelfLootTable(hardStoneGeoRegistry.getRawWall());

                    registerDropSelfLootTable(hardStoneGeoRegistry.getPolishedStone());
                    registerLootTable(hardStoneGeoRegistry.getPolishedSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(hardStoneGeoRegistry.getPolishedStairs());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getPolishedWall());

                    registerDropSelfLootTable(hardStoneGeoRegistry.getBricks());
                    registerLootTable(hardStoneGeoRegistry.getBrickSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(hardStoneGeoRegistry.getBrickStairs());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getBrickWall());

                    registerDropSelfLootTable(hardStoneGeoRegistry.getMossyBricks());
                    registerLootTable(hardStoneGeoRegistry.getMossyBrickSlab(), BlockLootTables::droppingSlab);
                    registerDropSelfLootTable(hardStoneGeoRegistry.getMossyBrickStairs());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getMossyBrickWall());

                    registerDropSelfLootTable(hardStoneGeoRegistry.getChiseled());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getCracked());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getPillar());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getButton());
                    registerDropSelfLootTable(hardStoneGeoRegistry.getPressurePlate());

                }
            }
        }

        //////////////////////////////////////
        //      Loot Table Registries       //
        //////////////////////////////////////

        // LOOSE COBBLES
        private void registerCobblesLootTable(SoftStoneGeoRegistry geoRegistry) {
            Block cobbles = geoRegistry.getCobbles();
            registerLootTable(cobbles, droppingWithSilkTouch(cobbles,
                    withExplosionDecay(cobbles, ItemLootEntry.builder(geoRegistry.getRockItem())
                            .acceptFunction(SetCount.builder(ConstantRange.of(4))))));
        }

        // BASE STONES
        private void registerStoneLootTables(BaseGeoRegistry geoRegistry) {
            registerStoneNoOreLoot(geoRegistry);
            for (OreType oreType : ModGeoOres.getModOreTypes()) {
                if (!(oreType.hasOre() && oreType.getGeoPredicate().generatesIn(geoRegistry.getGeoType())) ) continue;
                registerStoneOreLoot(geoRegistry, oreType, OreGrade.NORMAL);
                if (oreType.hasPoorOre()) {
                    registerStoneOreLoot(geoRegistry, oreType, OreGrade.POOR);
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////

        private void registerStoneNoOreLoot(BaseGeoRegistry geoRegistry) {
            LootEntry.Builder<?> rockEntry = buildGeoLootEntry(geoRegistry.getGeoType().getGeoLoot(), geoRegistry.getMainDropItem());
            Block block = geoRegistry.getBaseGeoBlock().getBlock();

            registerLootTable(block, withExplosionDecay(block, LootTable.builder()
                    .addLootPool(LootPool.builder().addEntry(rockEntry))));
        }

        private void registerStoneOreLoot(BaseGeoRegistry geoRegistry, OreType oreType, OreGrade oreGrade) {
            LootEntry.Builder<?> rockEntry = buildGeoLootEntry(geoRegistry.getGeoType().getGeoLoot(), geoRegistry.getMainDropItem());

            Block block = geoRegistry.getOreVariant(oreType, oreGrade);
            LootEntry.Builder<?> oreEntry = buildGeoLootEntry(oreType, oreGrade);

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
        private LootEntry.Builder<?> buildGeoLootEntry(OreType oreType, OreGrade oreGrade) {
            Item oreItem;
            GeoLoot geoLoot;
            switch (oreGrade) {
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
