package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.events.loot.GeoStoneCondition;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModLootConditionTypes {

    public static LootConditionType IS_GEOSTONE;

    public static void registerLootConditions() {
        IS_GEOSTONE = add("is_geostone", new GeoStoneCondition.Serializer());
    }

    public static LootConditionType add(String name, ILootSerializer<? extends ILootCondition> lootSerializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(JemsGeology.MOD_ID, name), new LootConditionType(lootSerializer));
    }
}
