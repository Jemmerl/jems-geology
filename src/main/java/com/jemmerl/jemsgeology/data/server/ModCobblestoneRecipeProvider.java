package com.jemmerl.jemsgeology.data.server;

import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Item;

import java.util.function.Consumer;

public class ModCobblestoneRecipeProvider extends RecipeProvider {

    public ModCobblestoneRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        InventoryChangeTrigger.Instance hasRockTrigger = InventoryChangeTrigger
                .Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.JEMSGEO_ROCKS).build());

        for (GeoType stone : GeoType.values()) {
            if (stone.hasCobble()) {
                GeoRegistry registry = ModBlocks.GEO_BLOCKS.get(stone);
                Item rockItem = registry.getRockItem();

                // Recipe to craft cobbles
                ShapedRecipeBuilder.shapedRecipe(registry.getCobbles())
                        .key('x', rockItem)
                        .patternLine("xx")
                        .patternLine("xx")
                        .setGroup("cobbles")
                        .addCriterion("has_rock", hasRockTrigger)
                        .build(consumer);

                // Recipe to break down cobbles
                ShapelessRecipeBuilder.shapelessRecipe(rockItem, 4)
                        .addIngredient(registry.getCobbles())
                        .setGroup("rocks")
                        .addCriterion("has_rock", hasRockTrigger)
                        .build(consumer);

                // Recipe to craft cobblestone
                ShapedRecipeBuilder.shapedRecipe(registry.getCobblestone(), 3)
                        .key('x', rockItem)
                        .key('m', ModItems.MORTAR.get())
                        .patternLine("xxx")
                        .patternLine("mxm")
                        .patternLine("xxx")
                        .setGroup("cobblestones")
                        .addCriterion("has_rock", hasRockTrigger)
                        .build(consumer);
            }

        }
    }

}
