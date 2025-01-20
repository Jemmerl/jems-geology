package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
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
        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            if (geoRegistry.hasCobble()) {
                Block rawStone = geoRegistry.getBaseStone();
                Block cobblestone = geoRegistry.getCobblestone();
                Block mossyCobblestone = geoRegistry.getMossyCobblestone();
                Block polished = geoRegistry.getPolishedStone();
                Block bricks = geoRegistry.getBricks();
                Block mossyBricks = geoRegistry.getMossyBricks();
                Item rockItem = geoRegistry.getRockItem();

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
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "cobble_" + geoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), geoRegistry.getCobbleStairs())
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "cobble_" + geoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), geoRegistry.getCobbleWall(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "cobble_" + geoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Mossy cobble decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyCobblestone), geoRegistry.getMossyCobbleSlab(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "mossy_cobble_" + geoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyCobblestone), geoRegistry.getMossyCobbleStairs())
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "mossy_cobble_" + geoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyCobblestone), geoRegistry.getMossyCobbleWall(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "mossy_cobble_" + geoRegistry.getGeoType().getName() + "_walls_stonecutting");

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

                // Brick decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), bricks)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_bricks_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(bricks), geoRegistry.getBrickSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_brick_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(bricks), geoRegistry.getBrickStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_brick_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(bricks), geoRegistry.getBrickWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_brick_walls_stonecutting");

                // Mossy brick decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyBricks), geoRegistry.getMossyBrickSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "mossy_" + geoRegistry.getGeoType().getName() + "_brick_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyBricks), geoRegistry.getMossyBrickStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "mossy_" + geoRegistry.getGeoType().getName() + "_brick_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyBricks), geoRegistry.getMossyBrickWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "mossy_" + geoRegistry.getGeoType().getName() + "_brick_walls_stonecutting");

                // Misc decor and function blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), geoRegistry.getChiseled())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "chiseled_" + geoRegistry.getGeoType().getName() + "_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), geoRegistry.getPillar())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_pillar_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), geoRegistry.getButton(), 4)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_button_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), geoRegistry.getPressurePlate(), 3)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, geoRegistry.getGeoType().getName() + "_pressure_plate_stonecutting");
            }
        }
    }
}
