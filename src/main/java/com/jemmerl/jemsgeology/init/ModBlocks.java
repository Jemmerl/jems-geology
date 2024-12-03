package com.jemmerl.jemsgeology.init;

import com.google.common.collect.ImmutableMap;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, JemsGeology.MOD_ID);


    //


//    private static final Block.Properties REGOLITH_PROP = AbstractBlock.Properties.create(Material.EARTH)
//            .sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.7f);
//
//    private static final Block.Properties DIRT_PROP = AbstractBlock.Properties.create(Material.EARTH)
//            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.5f);
//
//    private static final Block.Properties CLAY_PROP = AbstractBlock.Properties.create(Material.CLAY)
//            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.6f);
//
//    private static final Block.Properties SAND_PROP = AbstractBlock.Properties.create(Material.SAND)
//            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND).hardnessAndResistance(0.5f);
//
//    private static final Block.Properties GRAVEL_PROP = AbstractBlock.Properties.create(Material.SAND)
//            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.6f);






    // TODO need to test this happens after other mods put ores in.
    public static final ImmutableMap<String, OreType> REGISTERED_ORES = GeoOreRegistryAPI.getRegisteredOres();

    public static final LinkedHashMap<GeoType, GeoRegistry> GEO_BLOCKS = new LinkedHashMap<>();
    static {
        for (GeoType geoType : GeoType.values()) {
            GEO_BLOCKS.put(geoType, new GeoRegistry(geoType));
        }
    }



    //////////////////////////////////
    //      DECORATIVE BLOCKS       //
    //////////////////////////////////

    public static <T extends Block>RegistryObject<T> registerCobblestoneBlock(GeoType geoType) {
        String name = geoType.getName() + "_cobblestone";
        Supplier<T> blockSupplier = () -> (T) new Block(getCobblestoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMGEO_COBBLE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerPolishedStoneBlock(GeoType geoType) {
        String name = "polished_" + geoType.getName() + "_stone";
        Supplier<T> blockSupplier = () -> (T) new Block(getPolishedStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }


    /////////////////
    //    SLABS    //
    /////////////////

    public static <T extends Block>RegistryObject<T> registerCobbleSlab(GeoType geoType) {
        String name = geoType.getName() + "_cobble_slab";
        Supplier<T> blockSupplier = () -> (T) new SlabBlock(getDecorStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerRawStoneSlab(GeoType geoType) {
        String name = geoType.getName() + "_slab";
        Supplier<T> blockSupplier = () -> (T) new SlabBlock(getDecorStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerPolishedSlab(GeoType geoType) {
        String name = "polished_" + geoType.getName() + "_slab";
        Supplier<T> blockSupplier = () -> (T) new SlabBlock(getPolishedStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }


    //////////////////
    //    STAIRS    //
    //////////////////

    public static <T extends Block>RegistryObject<T> registerCobbleStairs(GeoType geoType, java.util.function.Supplier<BlockState> state) {
        String name = geoType.getName() + "_cobble_stairs";
        Supplier<T> blockSupplier = () -> (T) new StairsBlock(state, getDecorStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerRawStoneStairs(GeoType geoType, java.util.function.Supplier<BlockState> state) {
        String name = geoType.getName() + "_stairs";
        Supplier<T> blockSupplier = () -> (T) new StairsBlock(state, getDecorStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerPolishedStairs(GeoType geoType, java.util.function.Supplier<BlockState> state) {
        String name = "polished_" + geoType.getName() + "_stairs";
        Supplier<T> blockSupplier = () -> (T) new StairsBlock(state, getPolishedStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }


    /////////////////
    //    WALLS    //
    /////////////////

    public static <T extends Block>RegistryObject<T> registerCobbleWall(GeoType geoType) {
        String name = geoType.getName() + "_cobble_wall";
        Supplier<T> blockSupplier = () -> (T) new WallBlock(getDecorStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    public static <T extends Block>RegistryObject<T> registerRawStoneWall(GeoType geoType) {
        String name = geoType.getName() + "_wall";
        Supplier<T> blockSupplier = () -> (T) new WallBlock(getDecorStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }


    /////////////////////////////////
    //      PROPERTY BUILDERS      //
    /////////////////////////////////

    private static AbstractBlock.Properties getGeneralStoneProp(GeoType geoType) {
        return AbstractBlock.Properties.create(Material.ROCK, geoType.getMaterialColor()).sound(SoundType.STONE)
                .setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0);
    }

    private static AbstractBlock.Properties getCobblestoneProp(GeoType geoType) {
        return getGeneralStoneProp(geoType).hardnessAndResistance(2F, 7F);
    }

    private static AbstractBlock.Properties getDecorStoneProp(GeoType geoType) {
        return getGeneralStoneProp(geoType).hardnessAndResistance(2.0F, 6.0F);
    }

    private static AbstractBlock.Properties getPolishedStoneProp(GeoType geoType) {
        return getGeneralStoneProp(geoType).hardnessAndResistance(1.5F, 6.0F);
    }



    ////////////////////////////////////
    //      BASIC BLOCK REGISTRY      //
    ////////////////////////////////////

    public static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup itemGroup) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, registeredBlock, 64, itemGroup);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, int stackSize, ItemGroup itemGroup) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
    }

    public static void register(IEventBus eventBus) {
        System.out.println("num ores: " + REGISTERED_ORES.size());
        BLOCKS.register(eventBus);
    }
}
