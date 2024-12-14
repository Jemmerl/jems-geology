package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geologyinit.OreItemRegistry;
import com.jemmerl.jemsgeology.items.QuarryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JemsGeology.MOD_ID);


    //////////
    // MISC //
    //////////

    public static final RegistryObject<Item> MORTAR = ITEMS.register("lime_mortar", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_MISC_GROUP)));
    public static final RegistryObject<Item> WOODEN_QUARRY_TOOL = ITEMS.register("wooden_quarry_tool", () -> new QuarryItem(ItemTier.WOOD));
    public static final RegistryObject<Item> STONE_QUARRY_TOOL = ITEMS.register("stone_quarry_tool", () -> new QuarryItem(ItemTier.STONE));
    public static final RegistryObject<Item> IRON_QUARRY_TOOL = ITEMS.register("iron_quarry_tool", () -> new QuarryItem(ItemTier.IRON));
    public static final RegistryObject<Item> GOLDEN_QUARRY_TOOL = ITEMS.register("golden_quarry_tool", () -> new QuarryItem(ItemTier.GOLD));
    public static final RegistryObject<Item> DIAMOND_QUARRY_TOOL = ITEMS.register("diamond_quarry_tool", () -> new QuarryItem(ItemTier.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_QUARRY_TOOL = ITEMS.register("netherite_quarry_tool", () -> new QuarryItem(ItemTier.NETHERITE));


    //////////
    // Ores //
    //////////

    public static final RegistryObject<Item> ROCKSALT = ITEMS.register("rocksalt", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    public static final RegistryObject<Item> GYPSUM = ITEMS.register("gypsum", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    public static final RegistryObject<Item> LIGNITE_COAL = ITEMS.register("lignite_coal", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    public static final RegistryObject<Item> SUBBITUMINOUS_COAL = ITEMS.register("sub-bituminous_coal", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    public static final RegistryObject<Item> BITUMINOUS_COAL = ITEMS.register("bituminous_coal", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    public static final RegistryObject<Item> ANTHRACITE_COAL = ITEMS.register("anthracite_coal", () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));

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
    public static RegistryObject<Item> registerRockItem(GeoType geoType) {
        return ITEMS.register((geoType.getName() + "_rock"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_COBBLE_GROUP)));
    }

    public static RegistryObject<Item> registerOreItem(OreType oreType) {
        return ITEMS.register((oreType.getName() + "_ore"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    }

    public static RegistryObject<Item> registerPoorOreItem(OreType oreType) {
        return ITEMS.register(("poor_" + oreType.getName() + "_ore"), () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
    }

    // Item registry method
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}

