package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.georegistries.BaseGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.HardStoneGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.SoftStoneGeoRegistry;
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
        for (BaseGeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {

            Item rockItem;
            Block rawStone;
            if (geoRegistry instanceof SoftStoneGeoRegistry) {
                SoftStoneGeoRegistry softStoneGeoRegistry = (SoftStoneGeoRegistry) geoRegistry;
                rockItem = softStoneGeoRegistry.getRockItem();
                rawStone = softStoneGeoRegistry.getBaseGeoBlock();

                // Raw decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), softStoneGeoRegistry.getRawSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "raw_" + geoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), softStoneGeoRegistry.getRawStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "raw_" + geoRegistry.getGeoType().getName() + "_stairs_stonecutting");
            } else {
                continue;
            }

            if (geoRegistry instanceof HardStoneGeoRegistry) {
                HardStoneGeoRegistry hardStoneGeoRegistry = (HardStoneGeoRegistry) geoRegistry;
                Block cobblestone = hardStoneGeoRegistry.getCobblestone();
                Block mossyCobblestone = hardStoneGeoRegistry.getMossyCobblestone();
                Block polished = hardStoneGeoRegistry.getPolishedStone();
                Block bricks = hardStoneGeoRegistry.getBricks();
                Block mossyBricks = hardStoneGeoRegistry.getMossyBricks();

                // Raw decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), hardStoneGeoRegistry.getRawWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "raw_" + hardStoneGeoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Cobble decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), hardStoneGeoRegistry.getCobbleSlab(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "cobble_" + hardStoneGeoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), hardStoneGeoRegistry.getCobbleStairs())
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "cobble_" + hardStoneGeoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(cobblestone), hardStoneGeoRegistry.getCobbleWall(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "cobble_" + hardStoneGeoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Mossy cobble decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyCobblestone), hardStoneGeoRegistry.getMossyCobbleSlab(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "mossy_cobble_" + hardStoneGeoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyCobblestone), hardStoneGeoRegistry.getMossyCobbleStairs())
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "mossy_cobble_" + hardStoneGeoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyCobblestone), hardStoneGeoRegistry.getMossyCobbleWall(), 2)
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer, "mossy_cobble_" + hardStoneGeoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Polished decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), polished)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + hardStoneGeoRegistry.getGeoType().getName() + "_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(polished), hardStoneGeoRegistry.getPolishedSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + hardStoneGeoRegistry.getGeoType().getName() + "_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(polished), hardStoneGeoRegistry.getPolishedStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + hardStoneGeoRegistry.getGeoType().getName() + "_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(polished), hardStoneGeoRegistry.getPolishedWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "polished_" + hardStoneGeoRegistry.getGeoType().getName() + "_walls_stonecutting");

                // Brick decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), bricks)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_bricks_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(bricks), hardStoneGeoRegistry.getBrickSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_brick_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(bricks), hardStoneGeoRegistry.getBrickStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_brick_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(bricks), hardStoneGeoRegistry.getBrickWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_brick_walls_stonecutting");

                // Mossy brick decor blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyBricks), hardStoneGeoRegistry.getMossyBrickSlab(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "mossy_" + hardStoneGeoRegistry.getGeoType().getName() + "_brick_slabs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyBricks), hardStoneGeoRegistry.getMossyBrickStairs())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "mossy_" + hardStoneGeoRegistry.getGeoType().getName() + "_brick_stairs_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(mossyBricks), hardStoneGeoRegistry.getMossyBrickWall(), 2)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "mossy_" + hardStoneGeoRegistry.getGeoType().getName() + "_brick_walls_stonecutting");

                // Misc decor and function blocks
                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), hardStoneGeoRegistry.getChiseled())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, "chiseled_" + hardStoneGeoRegistry.getGeoType().getName() + "_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), hardStoneGeoRegistry.getPillar())
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_pillar_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), hardStoneGeoRegistry.getButton(), 4)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_button_stonecutting");

                SingleItemRecipeBuilder.stonecuttingRecipe(
                                Ingredient.fromItems(rawStone), hardStoneGeoRegistry.getPressurePlate(), 3)
                        .addCriterion("has_geostone", hasItem(rawStone.asItem()))
                        .build(consumer, hardStoneGeoRegistry.getGeoType().getName() + "_pressure_plate_stonecutting");
            }

        }
    }
}
