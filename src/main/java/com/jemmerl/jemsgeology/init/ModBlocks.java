package com.jemmerl.jemsgeology.init;

import com.google.common.collect.ImmutableMap;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.blocks.*;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import com.jemmerl.jemsgeology.init.geologyinit.ModGeoOres;
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

@SuppressWarnings("unchecked")
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, JemsGeology.MOD_ID);

    private static final Block.Properties DIRT_PROP = AbstractBlock.Properties.create(Material.EARTH)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.5f);

    private static final Block.Properties CLAY_PROP = AbstractBlock.Properties.create(Material.CLAY)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.6f);

    private static final Block.Properties SAND_PROP = AbstractBlock.Properties.create(Material.SAND)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND).hardnessAndResistance(0.5f);

    private static final Block.Properties GRAVEL_PROP = AbstractBlock.Properties.create(Material.SAND)
            .harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND).hardnessAndResistance(0.6f);

    public static final ImmutableMap<String, OreType> REGISTERED_ORES = GeoOreRegistryAPI.getRegisteredOres();

    public static final LinkedHashMap<GeoType, GeoRegistry> GEO_BLOCKS = new LinkedHashMap<>();
    static {
        for (GeoType geoType : GeoType.values()) {
            GEO_BLOCKS.put(geoType, new GeoRegistry(geoType));
        }
    }

    ///////////////////////////////
    //      NATURAL BLOCKS       //
    ///////////////////////////////

    // For base stone blocks
    public static <T extends Block>RegistryObject<T> registerStoneGeoBlock(GeoType geoType) {
        String blockTypeName = (geoType.getGeoGroup().isDetritus()) ? "_detritus" : "_stone";
        String name = geoType.getName() + blockTypeName;
        Supplier<T> blockSupplier = () -> (T) new StoneGeoBlock(getStoneProp(geoType), geoType, ModGeoOres.NONE, Grade.NONE);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_BASE_STONE_GROUP);
    }

    // For ore-bearing stone blocks
    public static <T extends Block>RegistryObject<T> registerStoneOreBlock(GeoType geoType, OreType oreType, Grade grade) {
        String blockTypeName = (geoType.getGeoGroup().isDetritus()) ? "_detritus" : "_stone";
        String name = geoType.getName() + blockTypeName + "/" + oreType.getName() + "/" + grade.getName();
        Supplier<T> blockSupplier = () -> (T) new StoneGeoBlock(getStoneProp(geoType), geoType, oreType, grade);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_ORE_BLOCK_GROUP);
    }

    // For base regolith blocks
    public static <T extends Block>RegistryObject<T> registerRegolithGeoBlock(GeoType geoType) {
        String name = geoType.getName() + "_regolith";
        Supplier<T> blockSupplier = () -> (T) new RegolithGeoBlock(getRegolithProp(geoType), geoType, ModGeoOres.NONE, Grade.NONE);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_BASE_STONE_GROUP);
    }

    // For ore-bearing regolith blocks
    public static <T extends Block>RegistryObject<T> registerRegolithOreBlock(GeoType geoType, OreType oreType, Grade grade) {
        String name = geoType.getName() + "_regolith/" + oreType.getName() + "/" + grade.getName();
        Supplier<T> blockSupplier = () -> (T) new RegolithGeoBlock(getRegolithProp(geoType), geoType, oreType, grade);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_ORE_BLOCK_GROUP);
    }

    // For base detritus blocks
    public static <T extends Block>RegistryObject<T> registerDetritusGeoBlock(GeoType geoType) {
        String name = geoType.getName() + "_detritus";
        return registerBlock(name, buildDetritusBlock(geoType, ModGeoOres.NONE, Grade.NONE), ModItemGroups.JEMSGEO_BASE_STONE_GROUP);
    }

    // For ore-bearing detritus blocks
    public static <T extends Block>RegistryObject<T> registerDetritusOreBlock(GeoType geoType, OreType oreType, Grade grade) {
        String name = geoType.getName() + "_detritus/" + oreType.getName() + "/" + grade.getName();
        return registerBlock(name, buildDetritusBlock(geoType, oreType, grade), ModItemGroups.JEMSGEO_ORE_BLOCK_GROUP);
    }

    // Build the correct detritus block given its geology type
    private static <T extends Block>Supplier<T> buildDetritusBlock(GeoType geoType, OreType oreType, Grade grade) {
        switch (geoType) {
            case DIRT:
                return () -> (T) new BaseGeoBlock(DIRT_PROP, GeoType.DIRT, oreType, grade);
            case COARSE_DIRT:
                return () -> (T) new BaseGeoBlock(DIRT_PROP, GeoType.COARSE_DIRT, oreType, grade);
            case CLAY:
                return () -> (T) new BaseGeoBlock(CLAY_PROP, GeoType.CLAY, oreType, grade);
            case SAND:
                return () -> (T) new FallingBaseGeoBlock(SAND_PROP, GeoType.SAND, oreType, grade);
            case RED_SAND:
                return () -> (T) new FallingBaseGeoBlock(SAND_PROP, GeoType.RED_SAND, oreType, grade);
            case GRAVEL:
                return () -> (T) new FallingBaseGeoBlock(GRAVEL_PROP, GeoType.GRAVEL, oreType, grade);
            default:
                return null;
        }
    }

    public static <T extends Block>RegistryObject<T> registerCobblesBlock(GeoType geoType) {
        String name = geoType.getName() + "_cobbles";
        Supplier<T> blockSupplier = () -> (T) new FallingCobbleBlock(getCobblesProp(geoType), geoType);
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_COBBLE_GROUP);
    }


    //////////////////////////////////
    //      DECORATIVE BLOCKS       //
    //////////////////////////////////

    public static <T extends Block>RegistryObject<T> registerCobblestoneBlock(GeoType geoType) {
        String name = geoType.getName() + "_cobblestone";
        Supplier<T> blockSupplier = () -> (T) new Block(getCobblestoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_COBBLE_GROUP);
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

    public static <T extends Block>RegistryObject<T> registerPolishedStoneWall(GeoType geoType) {
        String name = "polished_" + geoType.getName() + "_wall";
        Supplier<T> blockSupplier = () -> (T) new WallBlock(getPolishedStoneProp(geoType));
        return registerBlock(name, blockSupplier, ModItemGroups.JEMSGEO_DECOR_STONE_GROUP);
    }

    /////////////////////////////////
    //      PROPERTY BUILDERS      //
    /////////////////////////////////

    private static AbstractBlock.Properties getGeneralStoneProp(GeoType geoType) {
        return AbstractBlock.Properties.create(Material.ROCK, geoType.getMaterialColor()).sound(SoundType.STONE)
                .setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0);
    }

    private static AbstractBlock.Properties getRegolithProp(GeoType geoType) {
        return AbstractBlock.Properties.create(Material.EARTH, geoType.getMaterialColor()).sound(SoundType.GROUND)
                .harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.5f);
    }

    private static AbstractBlock.Properties getStoneProp(GeoType geoType) {
        return getGeneralStoneProp(geoType).hardnessAndResistance((1.5F + geoType.getHardnessAdj()), (6.0F + geoType.getResistAdj()));
    }

    // TODO make cobbles harder to mine than cobblestone, as one is decorative and one is a Natural Problem
    private static AbstractBlock.Properties getCobblesProp(GeoType geoType) {
        return getGeneralStoneProp(geoType).hardnessAndResistance(2F, 7F);
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
