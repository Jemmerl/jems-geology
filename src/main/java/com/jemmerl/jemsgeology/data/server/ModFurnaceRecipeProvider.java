package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModFurnaceRecipeProvider extends RecipeProvider {
    public ModFurnaceRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Furnace Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            if (geoRegistry.hasCobble()) {
                CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(geoRegistry.getBricks()), geoRegistry.getCracked(),
                        0.1F, 200).addCriterion("stone_bricks_block", hasItem(geoRegistry.getBricks())).build(consumer);
            }
        }
    }
}
