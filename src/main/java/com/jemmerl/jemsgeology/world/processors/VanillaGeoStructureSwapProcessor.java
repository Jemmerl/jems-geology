package com.jemmerl.jemsgeology.world.processors;


import com.google.common.collect.Maps;
import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import com.jemmerl.jemsgeology.init.geology.GeoRegistry;
import net.minecraft.block.*;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.*;

import java.util.Map;

// todo mod compat could go here. "if mod loaded, replace..."
public class VanillaGeoStructureSwapProcessor extends StructureProcessor {
//    public static final Codec<VanillaGeoStructureSwapProcessor> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
//            Registry.BLOCK.listOf().fieldOf("ignore_block").orElse(new ArrayList<>()).xmap(HashSet::new, ArrayList::new).forGetter(config -> config.blocksToIgnore)
//    ).apply(instance, instance.stable(VanillaGeoStructureSwapProcessor::new)));

    private static final GeoRegistry grayRhyo = ModBlocks.GEO_BLOCKS.get(GeoType.GRAY_RHYOLITE);
    private static final Map<Block, Block> replacementMap = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, grayRhyo.getCobblestone());
        map.put(Blocks.MOSSY_COBBLESTONE, grayRhyo.getMossyCobblestone());
        map.put(Blocks.STONE, grayRhyo.getBaseGeoBlock());
        map.put(Blocks.SMOOTH_STONE, grayRhyo.getPolishedStone());
        map.put(Blocks.STONE_BRICKS, grayRhyo.getBricks());
        map.put(Blocks.MOSSY_STONE_BRICKS, grayRhyo.getMossyBricks());
        map.put(Blocks.COBBLESTONE_STAIRS, grayRhyo.getCobbleStairs());
        map.put(Blocks.MOSSY_COBBLESTONE_STAIRS, grayRhyo.getMossyCobbleStairs());
        map.put(Blocks.STONE_STAIRS, grayRhyo.getRawStairs());
        map.put(Blocks.STONE_BRICK_STAIRS, grayRhyo.getBrickStairs());
        map.put(Blocks.MOSSY_STONE_BRICK_STAIRS, grayRhyo.getMossyBrickStairs());
        map.put(Blocks.COBBLESTONE_SLAB, grayRhyo.getCobbleSlab());
        map.put(Blocks.MOSSY_COBBLESTONE_SLAB, grayRhyo.getMossyCobbleSlab());
        map.put(Blocks.SMOOTH_STONE_SLAB, grayRhyo.getPolishedSlab());
        map.put(Blocks.STONE_SLAB, grayRhyo.getRawSlab());
        map.put(Blocks.STONE_BRICK_SLAB, grayRhyo.getBrickSlab());
        map.put(Blocks.MOSSY_STONE_BRICK_SLAB, grayRhyo.getMossyBrickSlab());
        map.put(Blocks.STONE_BRICK_WALL, grayRhyo.getBrickWall());
        map.put(Blocks.MOSSY_STONE_BRICK_WALL, grayRhyo.getMossyBrickWall());
        map.put(Blocks.COBBLESTONE_WALL, grayRhyo.getCobbleWall());
        map.put(Blocks.MOSSY_COBBLESTONE_WALL, grayRhyo.getMossyCobbleWall());
        map.put(Blocks.CHISELED_STONE_BRICKS, grayRhyo.getChiseled());
        map.put(Blocks.CRACKED_STONE_BRICKS, grayRhyo.getCracked());
    });

    private VanillaGeoStructureSwapProcessor() {
    }

    public Template.BlockInfo func_230386_a_(IWorldReader world, BlockPos hunh, BlockPos whuh, Template.BlockInfo uhhh, Template.BlockInfo blockInfo, PlacementSettings settings) {
        Block replacement = replacementMap.get(blockInfo.state.getBlock());
        if (replacement == null) {
            return blockInfo;
        } else {
            BlockState originalState = blockInfo.state;
            BlockState replaceState = replacement.getDefaultState();
            if (originalState.hasProperty(StairsBlock.FACING)) {
                replaceState = replaceState.with(StairsBlock.FACING, originalState.get(StairsBlock.FACING));
            }

            if (originalState.hasProperty(StairsBlock.HALF)) {
                replaceState = replaceState.with(StairsBlock.HALF, originalState.get(StairsBlock.HALF));
            }

            if (originalState.hasProperty(SlabBlock.TYPE)) {
                replaceState = replaceState.with(SlabBlock.TYPE, originalState.get(SlabBlock.TYPE));
            }

            if (blockInfo.state.getBlock() instanceof AbstractButtonBlock) {
                replaceState = replaceState
                        .with(AbstractButtonBlock.FACE, originalState.get(AbstractButtonBlock.FACE))
                        .with(AbstractButtonBlock.HORIZONTAL_FACING, originalState.get(AbstractButtonBlock.HORIZONTAL_FACING))
                        .with(AbstractButtonBlock.POWERED, originalState.get(AbstractButtonBlock.POWERED));
            }

            if (blockInfo.state.getBlock() instanceof PressurePlateBlock) {
                replaceState = replaceState.with(PressurePlateBlock.POWERED, originalState.get(PressurePlateBlock.POWERED));
            }

            return new Template.BlockInfo(blockInfo.pos, replaceState, blockInfo.nbt);
        }
    }

    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.BLACKSTONE_REPLACE;
    }

    static {
//        CODEC = Codec.unit(() -> {
//            return field_237058_b_;
//        });
    }
}
