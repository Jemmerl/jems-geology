package com.jemmerl.jemsgeology.data.client;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geologyinit.GeoRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Locale;
import java.util.Objects;

public class ModBlockStateModelProvider extends BlockStateProvider {

    public ModBlockStateModelProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (GeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            // Base stone/ores
            for (Block block: geoRegistry.getStoneGeoBlocks()) {
                if (block instanceof IGeoBlock) {
                    buildSimpleOreBlock(block,
                            Objects.requireNonNull(geoRegistry.getBaseStone().getRegistryName()).getPath());
                }
            }

            // Cobble based blocks and regolith/ores
            if (geoRegistry.hasCobble()) {

                if (geoRegistry.getGeoType() != GeoType.PAHOEHOE) {
                    simpleBlock(geoRegistry.getCobbles());
                    simpleBlock(geoRegistry.getCobblestone());

                    for (Block block: geoRegistry.getRegolithGeoBlocks()) {
                        if (block instanceof IGeoBlock) {
                            buildSimpleOreBlock(block,
                                    Objects.requireNonNull(geoRegistry.getRegolith().getRegistryName()).getPath());
                        }
                    }
                }
            }

            // Decorative stone variants
            if (geoRegistry.hasCobble()) {
                ResourceLocation baseStoneRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getBaseStone().getRegistryName()).getPath());
                ResourceLocation cobbleRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getCobblestone().getRegistryName()).getPath());
                ResourceLocation polishedRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getPolishedStone().getRegistryName()).getPath());

                slabBlock((SlabBlock) geoRegistry.getRawSlab(), baseStoneRL, baseStoneRL);
                stairsBlock((StairsBlock) geoRegistry.getRawStairs(), baseStoneRL);
                wallBlock((WallBlock) geoRegistry.getRawWall(), baseStoneRL);

                slabBlock((SlabBlock) geoRegistry.getCobbleSlab(), cobbleRL, cobbleRL);
                stairsBlock((StairsBlock) geoRegistry.getCobbleStairs(), cobbleRL);
                wallBlock((WallBlock) geoRegistry.getCobbleWall(), cobbleRL);

                simpleBlock(geoRegistry.getPolishedStone());
                slabBlock((SlabBlock) geoRegistry.getPolishedSlab(), polishedRL, polishedRL);
                stairsBlock((StairsBlock) geoRegistry.getPolishedStairs(), polishedRL);
                wallBlock((WallBlock) geoRegistry.getPolishedWall(), polishedRL);

                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getRawWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", baseStoneRL);

                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getCobbleWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", cobbleRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getPolishedWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", polishedRL);
            }
        }
    }


    // Construct the state and model for a block with ore
    private void buildSimpleOreBlock(Block oreBlock, String basePath) {
        IGeoBlock geoBlock = (IGeoBlock) oreBlock;
        getVariantBuilder(oreBlock).partialState()
                .setModels(new ConfiguredModel(buildModelFile(
                        geoBlock.getGeoType(), geoBlock.getOreType(), geoBlock.getGrade(), basePath)));

    }


    // Return the appropriate model file for a given block based on if it has different side textures
    private ModelFile buildModelFile(GeoType geologyType, OreType oreType, Grade grade, String basePath) {
        if (GeoType.SIDE_TEXTURE_MODELS.contains(geologyType)) {
            return buildModelDiffSides(basePath, oreType, grade);
        } else {
            return buildModelAllSides(basePath, oreType, grade);
        }
    }


    // Build the model file given the block path and property names with the same texture on all faces
    private ModelFile buildModelAllSides(String basePath, OreType oreType, Grade grade) {
        ModelFile modelFile;

        if (!oreType.hasOre()) {
            modelFile = models().withExistingParent("block/" + basePath, mcLoc("block/cube"))
                    .texture("particle", modLoc("block/" + basePath))
                    .texture("up", modLoc("block/" + basePath))
                    .texture("down", modLoc("block/" + basePath))
                    .texture("north", modLoc("block/" + basePath))
                    .texture("south", modLoc("block/" + basePath))
                    .texture("east", modLoc("block/" + basePath))
                    .texture("west", modLoc("block/" + basePath));
        } else {
            String oreName = oreType.getName().toLowerCase(Locale.ROOT);
            String gradeName = grade.getName().toLowerCase(Locale.ROOT);

            modelFile = models().withExistingParent("block/blockore/" + basePath + "/" + oreName + "/" + gradeName,
                            modLoc("block/stone_ore_parent"))
                    .texture("all", modLoc("block/" + basePath))
                    .texture("particle", modLoc("block/" + basePath))
                    .texture("overlay", modLoc("block/ore/" + grade.getAssetName() + oreName));
        }

        return modelFile;
    }


    // Build the model file given the block path and property names with a different texture on the sides
    private ModelFile buildModelDiffSides(String basePath, OreType oreType, Grade grade) {
        ModelFile modelFile;

        if (!oreType.hasOre()) {
            modelFile = models().withExistingParent("block/" + basePath, mcLoc("block/cube"))
                    .texture("particle", modLoc("block/" + basePath + "1"))
                    .texture("up", modLoc("block/" + basePath + "1"))
                    .texture("down", modLoc("block/" + basePath + "1"))
                    .texture("north", modLoc("block/" + basePath + "2"))
                    .texture("south", modLoc("block/" + basePath + "2"))
                    .texture("east", modLoc("block/" + basePath + "2"))
                    .texture("west", modLoc("block/" + basePath + "2"));
        } else {
            String oreName = oreType.getName().toLowerCase(Locale.ROOT);
            String gradeName = grade.getName().toLowerCase(Locale.ROOT);

            modelFile = models().withExistingParent("block/blockore/" + basePath + "/" + oreName + "/" + gradeName,
                            modLoc("block/stone_ore_parent_sides"))
                    .texture("particle", modLoc("block/" + basePath + "1"))
                    .texture("ends", modLoc("block/" + basePath + "1"))
                    .texture("sides", modLoc("block/" + basePath + "2"))
                    .texture("overlay", modLoc("block/ore/" + grade.getAssetName() + oreName));
        }

        return modelFile;
    }

}
