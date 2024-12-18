package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geologyinit.OreItemRegistry;
import com.jemmerl.jemsgeology.items.QuarryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.LinkedHashMap;

//@ObjectHolder(JemsGeology.MOD_ID)
public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JemsGeology.MOD_ID);

    private static Item[] ITEMS1 = {};

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


    ////////////////
    // Stone Ores //
    ////////////////

//    @ObjectHolder("rocksalt_ore")
//    public static final Item ROCKSALT_ORE = null;
//    @ObjectHolder("gypsum_ore")
//    public static final Item GYPSUM_ORE = null;
//    @ObjectHolder("lignite_coal_ore")
//    public static final Item LIGNITE_COAL_ORE = null;
//    @ObjectHolder("bituminous_coal_ore")
//    public static final Item SUBBITUMINOUS_COAL_ORE = null;
//    @ObjectHolder("bituminous_coal_ore")
//    public static final Item BITUMINOUS_COAL_ORE = null;
//    @ObjectHolder("anthracite_coal_ore")
//    public static final Item ANTHRACITE_COAL_ORE = null;

    public static final RegistryObject<Item> ROCKSALT = registerOreItem("rocksalt_ore");
    public static final RegistryObject<Item> GYPSUM = registerOreItem("gypsum_ore");
    public static final RegistryObject<Item> LIGNITE_COAL = registerOreItem("lignite_coal_ore");
    public static final RegistryObject<Item> SUBBITUMINOUS_COAL = registerOreItem("sub-bituminous_coal_ore");
    public static final RegistryObject<Item> BITUMINOUS_COAL = registerOreItem("bituminous_coal_ore");
    public static final RegistryObject<Item> ANTHRACITE_COAL = registerOreItem("anthracite_coal_ore");

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

    public static RegistryObject<Item> registerOreItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().group(ModItemGroups.JEMSGEO_ORES_GROUP)));
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

//    @SubscribeEvent
//    public static void registerItems(final RegistryEvent.Register<Item> event) {
//        //LOGGER.debug("Registering Items...");
//        event.getRegistry().registerAll(
//                new Item().setRegistryName(JemsGeology.MOD_ID, )
//
//        );
//    }

}

