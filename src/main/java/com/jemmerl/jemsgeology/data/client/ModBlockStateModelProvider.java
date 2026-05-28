package com.jemmerl.jemsgeology.data.client;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.geology.geos.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.georegistries.BaseGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.HardStoneGeoRegistry;
import com.jemmerl.jemsgeology.init.geology.georegistries.SoftStoneGeoRegistry;
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
        for (BaseGeoRegistry geoRegistry: ModBlocks.GEO_BLOCKS.values()) {
            // Base geoblock/ores
            for (Block block: geoRegistry.getAllGeoBlocks()) {
                if (block instanceof IGeoBlock) {
                    buildSimpleOreBlock(block,
                            Objects.requireNonNull(geoRegistry.getBaseGeoBlock().getRegistryName()).getPath());
                }
            }

            ResourceLocation baseStoneRL;
            ResourceLocation cobblestoneRL;
            if (geoRegistry instanceof SoftStoneGeoRegistry) {
                SoftStoneGeoRegistry softStoneGeoRegistry = (SoftStoneGeoRegistry) geoRegistry;
                baseStoneRL = modLoc("block/" +
                        Objects.requireNonNull(geoRegistry.getBaseGeoBlock().getRegistryName()).getPath());

                simpleBlock(softStoneGeoRegistry.getCobbles());
                slabBlock((SlabBlock) softStoneGeoRegistry.getRawSlab(), baseStoneRL, baseStoneRL);
                stairsBlock((StairsBlock) softStoneGeoRegistry.getRawStairs(), baseStoneRL);
            } else {
                continue;
            }

            if (geoRegistry instanceof HardStoneGeoRegistry) {
                HardStoneGeoRegistry hardStoneGeoRegistry = (HardStoneGeoRegistry) geoRegistry;

                cobblestoneRL = modLoc("block/" +
                        Objects.requireNonNull(hardStoneGeoRegistry.getCobblestone().getRegistryName()).getPath());
                ResourceLocation mossyCobbleRL = modLoc("block/" +
                        Objects.requireNonNull(hardStoneGeoRegistry.getMossyCobblestone().getRegistryName()).getPath());
                ResourceLocation polishedRL = modLoc("block/" +
                        Objects.requireNonNull(hardStoneGeoRegistry.getPolishedStone().getRegistryName()).getPath());
                ResourceLocation brickRL = modLoc("block/" +
                        Objects.requireNonNull(hardStoneGeoRegistry.getBricks().getRegistryName()).getPath());
                ResourceLocation mossyBrickRL = modLoc("block/" +
                        Objects.requireNonNull(hardStoneGeoRegistry.getMossyBricks().getRegistryName()).getPath());

                simpleBlock(hardStoneGeoRegistry.getCobblestone());
                wallBlock((WallBlock) hardStoneGeoRegistry.getRawWall(), baseStoneRL);

                slabBlock((SlabBlock) hardStoneGeoRegistry.getCobbleSlab(), cobblestoneRL, cobblestoneRL);
                stairsBlock((StairsBlock) hardStoneGeoRegistry.getCobbleStairs(), cobblestoneRL);
                wallBlock((WallBlock) hardStoneGeoRegistry.getCobbleWall(), cobblestoneRL);

                simpleBlock(hardStoneGeoRegistry.getMossyCobblestone());
                slabBlock((SlabBlock) hardStoneGeoRegistry.getMossyCobbleSlab(), mossyCobbleRL, mossyCobbleRL);
                stairsBlock((StairsBlock) hardStoneGeoRegistry.getMossyCobbleStairs(), mossyCobbleRL);
                wallBlock((WallBlock) hardStoneGeoRegistry.getMossyCobbleWall(), mossyCobbleRL);

                simpleBlock(hardStoneGeoRegistry.getPolishedStone());
                slabBlock((SlabBlock) hardStoneGeoRegistry.getPolishedSlab(), polishedRL, polishedRL);
                stairsBlock((StairsBlock) hardStoneGeoRegistry.getPolishedStairs(), polishedRL);
                wallBlock((WallBlock) hardStoneGeoRegistry.getPolishedWall(), polishedRL);

                simpleBlock(hardStoneGeoRegistry.getBricks());
                slabBlock((SlabBlock) hardStoneGeoRegistry.getBrickSlab(), brickRL, brickRL);
                stairsBlock((StairsBlock) hardStoneGeoRegistry.getBrickStairs(), brickRL);
                wallBlock((WallBlock) hardStoneGeoRegistry.getBrickWall(), brickRL);

                simpleBlock(hardStoneGeoRegistry.getMossyBricks());
                slabBlock((SlabBlock) hardStoneGeoRegistry.getMossyBrickSlab(), mossyBrickRL, mossyBrickRL);
                stairsBlock((StairsBlock) hardStoneGeoRegistry.getMossyBrickStairs(), mossyBrickRL);
                wallBlock((WallBlock) hardStoneGeoRegistry.getMossyBrickWall(), mossyBrickRL);

                simpleBlock(hardStoneGeoRegistry.getChiseled());
                simpleBlock(hardStoneGeoRegistry.getCracked());
                logBlock((RotatedPillarBlock) hardStoneGeoRegistry.getPillar());

                buttonBlock((AbstractButtonBlock) hardStoneGeoRegistry.getButton(), polishedRL);
                pressurePlateBlock((PressurePlateBlock) hardStoneGeoRegistry.getPressurePlate(), polishedRL);

                // Inventory models
                models().withExistingParent("block/" +
                                Objects.requireNonNull(hardStoneGeoRegistry.getRawWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", baseStoneRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(hardStoneGeoRegistry.getCobbleWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", cobblestoneRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(hardStoneGeoRegistry.getMossyCobbleWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", mossyCobbleRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(hardStoneGeoRegistry.getPolishedWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", polishedRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(hardStoneGeoRegistry.getBrickWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", brickRL);
                models().withExistingParent("block/" +
                                Objects.requireNonNull(hardStoneGeoRegistry.getMossyBrickWall().getRegistryName()).getPath() +
                                "_inventory", mcLoc("block/wall_inventory"))
                        .texture("wall", mossyBrickRL);

                buttonInventoryModel(hardStoneGeoRegistry.getButton(), polishedRL);
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
