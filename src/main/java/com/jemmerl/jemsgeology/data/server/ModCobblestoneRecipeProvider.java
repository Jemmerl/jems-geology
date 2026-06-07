package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.geology.georegistries.BaseGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.HardStoneGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.SoftStoneGeoRegistry;
import net.minecraft.data.*;
import net.minecraft.item.Item;

import java.util.function.Consumer;

public class ModCobblestoneRecipeProvider extends RecipeProvider {

    public ModCobblestoneRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for (GeoType geoType : GeoType.ALL_GEOTYPES) {
            BaseGeoRegistry geoRegistry = ModBlocks.GEO_BLOCKS.get(geoType);
            Item rockItem;
            if (geoType.getHardness().ordinal() > 0) {
                SoftStoneGeoRegistry softStoneGeoRegistry = (SoftStoneGeoRegistry) geoRegistry;
                rockItem = softStoneGeoRegistry.getRockItem();

                // Recipe to craft cobbles
                ShapedRecipeBuilder.shapedRecipe(softStoneGeoRegistry.getCobbles())
                        .key('x', rockItem)
                        .patternLine("xx")
                        .patternLine("xx")
                        .setGroup("cobbles")
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer);

                // Recipe to break down cobbles
                ShapelessRecipeBuilder.shapelessRecipe(rockItem, 4)
                        .addIngredient(softStoneGeoRegistry.getCobbles())
                        .setGroup("rocks")
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer);
            } else {
                continue;
            }

            if (geoType.getHardness().ordinal() > 1) {
                HardStoneGeoRegistry hardStoneGeoRegistry = (HardStoneGeoRegistry) geoRegistry;

                // Recipe to craft cobblestone
                ShapedRecipeBuilder.shapedRecipe(hardStoneGeoRegistry.getCobblestone(), 3)
                        .key('x', rockItem)
                        .key('m', ModItems.MORTAR.get())
                        .patternLine("xxx")
                        .patternLine("mxm")
                        .patternLine("xxx")
                        .setGroup("cobblestones")
                        .addCriterion("has_rock", hasItem(rockItem))
                        .build(consumer);
            }

        }
    }

}
