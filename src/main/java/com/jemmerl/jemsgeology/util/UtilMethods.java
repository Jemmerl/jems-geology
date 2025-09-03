package com.jemmerl.jemsgeology.util;

import com.jemmerl.jemsgeology.blocks.IGeoBlock;
import com.jemmerl.jemsgeology.blocks.RegolithGeoBlock;
import com.jemmerl.jemsgeology.blocks.StoneGeoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class UtilMethods {

    public static final float[][] gaussKernel3 = new float[][]
            {   {0.0625f, 0.125f, 0.0625f}  ,
                {0.125f, 0.25f, 0.125f}     ,
                {0.0625f, 0.125f, 0.0625f}  };

    // x1 ≤ x ≤ x2 and y1 ≤ y ≤ y2;
    public static float bilinearLerp(int x, int y, float x1, float x2, float y1, float y2, int Q11, int Q21, int Q12, int Q22) {
        float x2x = x2-x;
        float y2y = y2-y;
        float xx1 = x-x1;
        float yy1 = y-y1;

        return (y2y*(x2x*Q11 + xx1*Q21) + yy1*(x2x*Q12 + xx1*Q22)) / ((x2-x1)*(y2-y1));
    }

    // Returns a float over a given range mapped to a new range
    public static float remap(float oldVal, float[] oldDomain, float[] newDomain) {
        float oldRange = oldDomain[1] - oldDomain[0];
        float oldRangeValue = oldVal - oldDomain[0];
        float percentile = oldRangeValue / oldRange;

        float newRange = newDomain[1] - newDomain[0];
        float newRangeValue = percentile * newRange;
        float newVal = newRangeValue + newDomain[0];

        return newVal;
    }


    // Check if a block is a regolith geo block
    @Deprecated
    public static boolean isRegolith(Block block) {
        return ((block instanceof IGeoBlock) && isRegolith((IGeoBlock) block));
    }

    // Check if a geo block is a regolith block
    @Deprecated
    public static boolean isRegolith(IGeoBlock block) {
        return block.getRegistryName().toString().contains("regolith");
    }

//    // Attempt to convert a geoblock to a regolith form, preserving properties (else return original)
//    public static BlockState convertRegolith(BlockState blockState, boolean keepOre) {
//        // Catch errors if the passed block state does not belong to an IGeoBlock
//        if (!(blockState.getBlock() instanceof IGeoBlock)) {
//            return blockState;
//        }
//        IGeoBlock geoBlock = (IGeoBlock) (blockState.getBlock());
//        GeologyType geologyType = geoBlock.getGeologyType();
//
//        if (keepOre) {
//            // Detritus is its own regolith ...regolith is its own regolith too
//            if ((geoBlock.getStoneGroupType() == StoneGroupType.DETRITUS) || (isGeoBlockRegolith(blockState.getBlock()))) {
//                return blockState;
//            }
//            return ModBlocks.GEOBLOCKS.get(geologyType)
//                    .getRegolithOre(geoBlock.getOreType(), geoBlock.getGradeType()).getDefaultState();
//        }
//        return ModBlocks.GEOBLOCKS.get(geologyType).getRegolith().getDefaultState();
//    }

    // Returns an item given a string representation of its resource location
    // Reliance on this should be minimized
    @Deprecated
    public static Item stringToItem(String itemName) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
    }


    ////////////////////////
    // ARITHMETIC METHODS //
    ////////////////////////

    // Returns the 2D unit vector using 2D coordinates
    public static float[] points2DUnitVec(float x, float y) {
        float magnitude = (float)Math.sqrt(x*x + y*y);
        return new float[]{(x / magnitude), (y / magnitude)};
    }

    // Returns the 3D unit vector using 3D coordinates
    public static float[] points3DUnitVec(float x, float y, float z) {
        float magnitude = (float)Math.sqrt(x*x + y*y + z*z);
        return new float[]{(x / magnitude), (y / magnitude), (z / magnitude)};
    }

    // Returns the 2D unit vector using an angle in radians
    public static float[] angles2DUnitVec(float alpha) {
        return new float[]{((float)Math.cos(alpha)), ((float)Math.sin(alpha))};
    }

    // Returns the 3D unit vector using two angles in radians
    public static float[] angles3DUnitVec(float alpha, float beta) {
        float cosB = (float)Math.cos(beta);
        return new float[]{(cosB * (float)Math.sin(alpha)), ((float)Math.sin(beta)), (cosB * (float)Math.cos(alpha))};
    }

    // Return the 2D distance between two variables
    public static double getDistance2D(int x1, int z1, int x2, int z2) {
        return Math.hypot((x1 - x2), (z1 - z2));
    }

    // Return the 3D distance squared between two integer-based variables
    public static float getSquareDistance3D(int x1, int y1, int z1, int x2, int y2, int z2) {
        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
    }

    // Return the 3D distance squared between two float-based variables
    public static float getSquareDistance3D(float x1, float y1, float z1, float x2, float y2, float z2) {
        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
    }

    //    int mod = mod(x, 2) + ((mod(z, 2) + 1) * 2) - 2;
    //    public static int mod(int a, int b) {
    //        return (a % b + b) % b;
    //    }

    /////////////////////////////
    // DIRECTION-BASED METHODS //
    /////////////////////////////

//    // Copy blockstate property from one similar block to another
//    public static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
//        return to.with(property, from.get(property));
//    }

//    // Convert vanilla detritus to GeoBlock detritus; If not convertible, return original state
//    public static BlockState convertVanillaToDetritus(BlockState vanillaState) {
//        GeologyType vanillaType = ModBlockLists.VANILLA_DET_LIST.getOrDefault(vanillaState, null);
//        return (vanillaType == null) ? vanillaState : ModBlocks.GEOBLOCKS.get(vanillaType).getBaseState();
//    }

    // Rotate a face in a relative direction :)
    /*
        Assumes "UP" rotations return the input whereas "DOWN" returns its opposite
        Assumes N,S,E,W to be the relative directions of up, down, right, and left respectively
        The result is rotation in any relative direction based off the starting face
        You can confirm this by building a 6x6 logic table of the input face and rotation directions and its outputs!
     */
    public static Direction rotateDirection(Direction face, Direction rotation) {
        // Rotations based on input face (UP and DOWN)
        switch (face) {
            case UP:
                return rotation;
            case DOWN:
                return rotation.getOpposite();
            default:
        }

        // Rotations based on rotation direction (N, S, E, W)
        switch (rotation) {
            case NORTH:
                return Direction.UP;
            case SOUTH:
                return Direction.DOWN;
            case EAST:
                return face.rotateYCCW();
            case WEST:
                return face.rotateY();
            case DOWN:
                return face.getOpposite();
            case UP:
            default:
                return face;
        }
    }

    // Adds a value to specific BlockPos ordinate(s)
    public static BlockPos addToOrdinate(BlockPos pos, float add, int flag) {
        switch (flag) {
            case 0:
                return pos.add(add, 0, 0);
            case 1:
                return pos.add(0, add, 0);
            case 2:
                return pos.add(0, 0, add);
            case 3:
                return pos.add(add, add, 0);
            case 4:
                return pos.add(0, add, add);
            case 5:
                return pos.add(add, 0, add);
//            case 3:
//                return pos.add(add, add, add);
            default:
                return pos;
        }
    }

    public static ChunkPos adjChunkPos(ChunkPos originPos, Direction direction) {
        switch (direction) {
            case NORTH:
                return new ChunkPos(originPos.x, originPos.z-1);
            case SOUTH:
                return new ChunkPos(originPos.x, originPos.z+1);
            case EAST:
                return new ChunkPos(originPos.x+1, originPos.z);
            case WEST:
                return new ChunkPos(originPos.x-1, originPos.z);
            default:
        }
        return originPos;
    }


}
