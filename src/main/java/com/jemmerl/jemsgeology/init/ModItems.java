package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;
import com.jemmerl.jemsgeology.init.geology.OreItemRegistry;
import com.jemmerl.jemsgeology.items.QuarryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;

//@ObjectHolder(JemsGeology.MOD_ID)
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


    ////////////////
    // Stone Ores //
    ////////////////

    public static final RegistryObject<Item> ROCKSALT_ORE = registerOreItem("rocksalt_ore");
    public static final RegistryObject<Item> GYPSUM_ORE = registerOreItem("gypsum_ore");
    public static final RegistryObject<Item> ROCKSALT = registerOreItem("rocksalt");
    public static final RegistryObject<Item> GYPSUM = registerOreItem("gypsum");
    public static final RegistryObject<Item> BORAX = registerOreItem("borax");
    public static final RegistryObject<Item> LIGNITE_COAL = registerOreItem("lignite_coal_ore");
    public static final RegistryObject<Item> SUBBITUMINOUS_COAL = registerOreItem("sub-bituminous_coal_ore");
    public static final RegistryObject<Item> BITUMINOUS_COAL = registerOreItem("bituminous_coal_ore");
    public static final RegistryObject<Item> ANTHRACITE_COAL = registerOreItem("anthracite_coal_ore");

    public static final LinkedHashMap<OreType, OreItemRegistry> ORE_ITEMS = buildOreRegistries();
    private static LinkedHashMap<OreType, OreItemRegistry> buildOreRegistries() {
        LinkedHashMap<OreType, OreItemRegistry> oreItems = new LinkedHashMap<>();
        for (OreType oreType: ModGeoOres.getModOreTypes()) {
            oreItems.put(oreType, new OreItemRegistry(oreType));
        }
        return oreItems;
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

