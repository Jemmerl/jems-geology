package com.jemmerl.jemsgeology.events.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.jemmerl.jemsgeology.blocks.StoneGeoBlock;
import com.jemmerl.jemsgeology.init.ModLootConditionTypes;
import com.jemmerl.jemsgeology.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class GeoStoneCondition implements ILootCondition {

    private static final GeoStoneCondition INSTANCE = new GeoStoneCondition();

    private GeoStoneCondition() {}

    @Override
    public LootConditionType getConditionType() {
        return ModLootConditionTypes.IS_GEOSTONE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockState = lootContext.get(LootParameters.BLOCK_STATE);

        if (blockState != null) System.out.println((blockState.getBlock() instanceof StoneGeoBlock));

        return (blockState != null) && (blockState.getBlock() instanceof StoneGeoBlock);
    }

    public static class Serializer implements ILootSerializer<GeoStoneCondition> {
        @Override
        public void serialize(JsonObject jsonObject, GeoStoneCondition stonesCondition, JsonSerializationContext context) {}

        @Override
        public GeoStoneCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return INSTANCE;
        }
    }
}
