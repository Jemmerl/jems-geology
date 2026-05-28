package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.geology.geos.GeoType;
import com.jemmerl.jemsgeology.geology.geos.props.Hardness;
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
        for (GeoType geoBlock : GeoType.values()) {

            BaseGeoRegistry geoRegistry = ModBlocks.GEO_BLOCKS.get(geoBlock);
            Item rockItem;
            if (geoBlock.getHardness().ordinal() > 0) {
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

            if (geoBlock.getHardness().ordinal() > 1) {
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
