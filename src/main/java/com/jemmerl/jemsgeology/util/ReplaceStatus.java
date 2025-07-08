package com.jemmerl.jemsgeology.util;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.blocks.RegolithGeoBlock;
import com.jemmerl.jemsgeology.blocks.StoneGeoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

public enum ReplaceStatus {
    GEOBLOCK_STONE,
    VANILLA_STONE,
    GEOBLOCK_REGOLITH,
    GEOBLOCK_DETRITUS,
    VANILLA_DETRITUS,
    FAILED;

    // Check if the block is some form of vanilla stone
    public static boolean isVanillaStone (Block block) {
        return (block.isIn(Tags.Blocks.OBSIDIAN) || block.isIn(BlockTags.BASE_STONE_OVERWORLD)
                || block.isIn(Tags.Blocks.ORES) || block.equals(Blocks.SANDSTONE) || block.equals(Blocks.RED_SANDSTONE));
    }

    // Check if the block is some form of GeoBlock stone
    public static boolean isGeoBlockStone(Block block) {
        return (block instanceof StoneGeoBlock);
    }

    // Check if the block is some form of GeoBlock regolith
    public static boolean isGeoBlockRegolith(Block block) {
        return (block instanceof RegolithGeoBlock);
    }

    // Check if the block is some form of GeoBlock detritus
    public static boolean isGeoBlockDetritus(Block block) {
        return ((block instanceof IGeoBlock) && ((IGeoBlock) block).getGeoType().getGeoGroup().isRegolith());
    }

    // Check if the block is some form of stone (vanilla or GeoBlock)
    public static boolean isStoneBlock (Block block) {
        return (isGeoBlockStone(block) || isVanillaStone(block));
    }

    // Check if the block is some form of vanilla detritus
    public static boolean isDetritusBlock(Block block) {
        return (isVanillaDetritus(block) || isGeoBlockDetritus(block));
    }

    // Check if the block is some form of vanilla detritus
    public static boolean isVanillaDetritus(Block block) {
        return (block.isIn(Tags.Blocks.DIRT) || block.isIn(Tags.Blocks.SAND)
                || block.isIn(Tags.Blocks.GRAVEL));
    }

    // Check a block's type for evaluating its replaceability
    public static ReplaceStatus checkStatus(BlockState blockState) {
        Block replaced = blockState.getBlock();
        if (replaced instanceof IGeoBlock) {
            if (isGeoBlockStone(replaced)) { return GEOBLOCK_STONE; }
            if (isGeoBlockRegolith(replaced)) { return GEOBLOCK_REGOLITH; }
            if (isGeoBlockDetritus(replaced)) { return GEOBLOCK_DETRITUS; }
        }
        if (isVanillaStone(replaced)) { return VANILLA_STONE; }
        if (isVanillaDetritus(replaced)) { return VANILLA_DETRITUS; }
        return FAILED; // If not any stone or detritus,
    }

}
