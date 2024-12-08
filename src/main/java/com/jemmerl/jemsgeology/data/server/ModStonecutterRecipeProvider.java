package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModStonecutterRecipeProvider extends RecipeProvider {

    public ModStonecutterRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Stonecutter Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        InventoryChangeTrigger.Instance hasRockTrigger = InventoryChangeTrigger
                .Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.JEMSGEO_ROCKS).build());

        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            if (geoRegistry.hasCobble()) {
                Block rawStone = geoRegistry.getBaseStone();
                Block cobblestone = geoRegistry.getCobblestone();
                Block polished = geoRegistry.getPolishedStone();

                // Raw decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                        Ingredient.fromItems(rawStone), geoRegistry.getRawSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "raw_" + geoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), geoRegistry.getRawStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "raw_" + geoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), geoRegistry.getRawWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "raw_" + geoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Cobble decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), geoRegistry.getCobbleSlab(), 2)
                        .addCriterion("has_rock", hasRockTrigger)
                        .build(consumer, "cobble_" + geoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), geoRegistry.getCobbleStairs())
                        .addCriterion("has_rock", hasRockTrigger)
                        .build(consumer, "cobble_" + geoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), geoRegistry.getCobbleWall(), 2)
                        .addCriterion("has_rock", hasRockTrigger)
                        .build(consumer, "cobble_" + geoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Polished decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), polished)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + geoRegistry.getGeoType().getName() + "_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(polished), geoRegistry.getPolishedSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + geoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(polished), geoRegistry.getPolishedStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + geoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(polished), geoRegistry.getPolishedWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + geoRegistry.getGeoType().getName() + "_walls_stonecutting");
            }
        }
    }
}
