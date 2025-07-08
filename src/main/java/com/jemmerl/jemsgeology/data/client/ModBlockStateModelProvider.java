package com.jemmerl.jemsgeology.data.client;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
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
            for (Block block: geoRegistry.getAllGeoBlocks()) {
                if (block instanceof IGeoBlock) {
                    buildSimpleOreBlock(block,
                            Objects.requireNonNull(geoRegistry.getBaseGeoBlock().getRegistryName()).getPath());
                }
            }

            // Decorative stone variants / cobbles
            if (geoRegistry.hasCobble()) {
                simpleBlock(geoRegistry.getCobbles());
                simpleBlock(geoRegistry.getCobblestone());

                ResourceLocation baseStoneRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getBaseGeoBlock().getRegistryName()).getPath());
                ResourceLocation cobbleRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getCobblestone().getRegistryName()).getPath());
                ResourceLocation mossyCobbleRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getMossyCobblestone().getRegistryName()).getPath());
                ResourceLocation polishedRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getPolishedStone().getRegistryName()).getPath());
                ResourceLocation brickRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getBricks().getRegistryName()).getPath());
                ResourceLocation mossyBrickRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getMossyBricks().getRegistryName()).getPath());

                slabBlock((SlabBlock) geoRegistry.getRawSlab(), baseStoneRL, baseStoneRL);
                stairsBlock((StairsBlock) geoRegistry.getRawStairs(), baseStoneRL);
                wallBlock((WallBlock) geoRegistry.getRawWall(), baseStoneRL);

                slabBlock((SlabBlock) geoRegistry.getCobbleSlab(), cobbleRL, cobbleRL);
                stairsBlock((StairsBlock) geoRegistry.getCobbleStairs(), cobbleRL);
                wallBlock((WallBlock) geoRegistry.getCobbleWall(), cobbleRL);

                simpleBlock(geoRegistry.getMossyCobblestone());
                slabBlock((SlabBlock) geoRegistry.getMossyCobbleSlab(), mossyCobbleRL, mossyCobbleRL);
                stairsBlock((StairsBlock) geoRegistry.getMossyCobbleStairs(), mossyCobbleRL);
                wallBlock((WallBlock) geoRegistry.getMossyCobbleWall(), mossyCobbleRL);

                simpleBlock(geoRegistry.getPolishedStone());
                slabBlock((SlabBlock) geoRegistry.getPolishedSlab(), polishedRL, polishedRL);
                stairsBlock((StairsBlock) geoRegistry.getPolishedStairs(), polishedRL);
                wallBlock((WallBlock) geoRegistry.getPolishedWall(), polishedRL);

                simpleBlock(geoRegistry.getBricks());
                slabBlock((SlabBlock) geoRegistry.getBrickSlab(), brickRL, brickRL);
                stairsBlock((StairsBlock) geoRegistry.getBrickStairs(), brickRL);
                wallBlock((WallBlock) geoRegistry.getBrickWall(), brickRL);

                simpleBlock(geoRegistry.getMossyBricks());
                slabBlock((SlabBlock) geoRegistry.getMossyBrickSlab(), mossyBrickRL, mossyBrickRL);
                stairsBlock((StairsBlock) geoRegistry.getMossyBrickStairs(), mossyBrickRL);
                wallBlock((WallBlock) geoRegistry.getMossyBrickWall(), mossyBrickRL);

                simpleBlock(geoRegistry.getChiseled());
                simpleBlock(geoRegistry.getCracked());
                logBlock((RotatedPillarBlock) geoRegistry.getPillar());

                buttonBlock((AbstractButtonBlock) geoRegistry.getButton(), polishedRL);
                pressurePlateBlock((PressurePlateBlock) geoRegistry.getPressurePlate(), polishedRL);


                // Inventory models
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getRawWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", baseStoneRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getCobbleWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", cobbleRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getMossyCobbleWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", mossyCobbleRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getPolishedWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", polishedRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getBrickWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", brickRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(geoRegistry.getMossyBrickWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", mossyBrickRL);

                buttonInventoryModel(geoRegistry.getButton(), polishedRL);
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
            String oreName = oreType.getName().toLowerCase(Locale.ENGLISH);
            String gradeName = grade.getName().toLowerCase(Locale.ENGLISH);

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
            String oreName = oreType.getName().toLowerCase(Locale.ENGLISH);
            String gradeName = grade.getName().toLowerCase(Locale.ENGLISH);

            modelFile = models().withExistingParent("block/blockore/" + basePath + "/" + oreName + "/" + gradeName,
                            modLoc("block/stone_ore_parent_sides"))
                    .texture("particle", modLoc("block/" + basePath + "1"))
                    .texture("ends", modLoc("block/" + basePath + "1"))
                    .texture("sides", modLoc("block/" + basePath + "2"))
                    .texture("overlay", modLoc("block/ore/" + grade.getAssetName() + oreName));
        }

        return modelFile;
    }

    // Build button blockstate and model files
    private void buttonBlock(AbstractButtonBlock block, ResourceLocation texture) {
        getVariantBuilder(block).forAllStates(state -> {
            ModelFile buttonModel = buildButtonModel(block, state.get(AbstractButtonBlock.POWERED), texture);
            AttachFace attachFace = state.get(AbstractButtonBlock.FACE);
            Direction direction = state.get(AbstractButtonBlock.HORIZONTAL_FACING);
            boolean uvLock = false;
            int rotX = 0;
            if (attachFace == AttachFace.CEILING) {
                direction = direction.getOpposite();
                rotX = 180;
            } else if (attachFace == AttachFace.WALL) {
                rotX = 270;
                uvLock = true;
            }
            return ConfiguredModel.builder().modelFile(buttonModel).rotationX(rotX)
                    .rotationY((int) direction.getHorizontalAngle()).uvLock(uvLock).build();
        });
    }

    private ModelFile buildButtonModel(Block block, boolean activated, ResourceLocation texture) {
        String name = block.getRegistryName().getPath() + (activated ? "_pressed" : "");
        String modelLoc = "block/button" + (activated ? "_pressed" : "");
        return models().singleTexture(name, mcLoc(modelLoc), "texture", texture);
    }

    private void buttonInventoryModel(Block block, ResourceLocation texture) {
        models().singleTexture("block/" + block.getRegistryName().getPath() + "_inventory",
                mcLoc("block/button_inventory"), "texture", texture);
    }

    // Build pressure plate blockstate and model files
    private void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(buildPressurePlateModel(block, state.get(PressurePlateBlock.POWERED), texture)).build());
    }

    private ModelFile buildPressurePlateModel(Block block, boolean activated, ResourceLocation texture) {
        String name = block.getRegistryName().getPath() + (activated ? "_down" : "");
        String modelLoc = "block/pressure_plate" + (activated ? "_down" : "_up");
        return models().withExistingParent(name, mcLoc(modelLoc)).texture("texture", texture);
    }
}
