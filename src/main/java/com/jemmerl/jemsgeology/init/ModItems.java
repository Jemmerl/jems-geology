package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.geologyinit.OreItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JemsGeology.MOD_ID);


    //////////
    // MISC //
    //////////

    public static final RegistryObject<Item> MORTAR = ITEMS.register("lime_mortar", () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_MISC_GROUP)));
//    public static final RegistryObject<Item> WOODEN_QUARRY_TOOL = ITEMS.register("wooden_quarry_tool", () -> new QuarryItem(ItemTier.WOOD));
//    public static final RegistryObject<Item> STONE_QUARRY_TOOL = ITEMS.register("stone_quarry_tool", () -> new QuarryItem(ItemTier.STONE));
//    public static final RegistryObject<Item> IRON_QUARRY_TOOL = ITEMS.register("iron_quarry_tool", () -> new QuarryItem(ItemTier.IRON));
//    public static final RegistryObject<Item> GOLDEN_QUARRY_TOOL = ITEMS.register("golden_quarry_tool", () -> new QuarryItem(ItemTier.GOLD));
//    public static final RegistryObject<Item> DIAMOND_QUARRY_TOOL = ITEMS.register("diamond_quarry_tool", () -> new QuarryItem(ItemTier.DIAMOND));
//    public static final RegistryObject<Item> NETHERITE_QUARRY_TOOL = ITEMS.register("netherite_quarry_tool", () -> new QuarryItem(ItemTier.NETHERITE));


    //////////
    // Ores //
    //////////

    public static final LinkedHashMap<OreType, OreItemRegistry> ORE_ITEMS = new LinkedHashMap<>();
    static {
        for (OreType oreType: GeoOreRegistryAPI.getRegisteredOres().values()) {
            ORE_ITEMS.put(oreType, new OreItemRegistry(oreType));
        }
    }


    //////////////////////////
    // Registration Methods //
    //////////////////////////

    // Rock Item Creation and Registration
    public static RegistryObject<Item> registerRockItem(GeologyType geologyType) {
        return ITEMS.register((geologyType.getName() + "_rock"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_COBBLE_GROUP)));
    }

    public static RegistryObject<Item> registerOreItem(OreType oreType) {
        return ITEMS.register((oreType.getString() + "_ore"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_ORES_GROUP)));
    }

    public static RegistryObject<Item> registerPoorOreItem(OreType oreType) {
        return ITEMS.register(("poor_" + oreType.getString() + "_ore"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMGEO_ORES_GROUP)));
    }

    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

