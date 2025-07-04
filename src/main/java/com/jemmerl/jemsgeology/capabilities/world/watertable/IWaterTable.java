package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface IWaterTable extends INBTSerializable<CompoundNBT> {

    // step 1: base chunk height
    // step 2: render in world
    // step 3: linerp base chunk



    // getBlockWTLevel

    // getChunkWTLevel






//    int getWaterTableHeight(BlockPos pos);
//
//    int getWaterTableHeight(int x, int z);
//
//    int getBaseWTLevel(int x, int z);
//    int getBaseWTLevel(BlockPos pos);
//
    int getChunkBaseWTLevel(int x, int z, World world);
    int getChunkBaseWTLevel(ChunkPos pos, World world);
//
//    boolean isInWaterTable(BlockPos pos);
//
//    boolean isInWaterTable(int x, int y, int z);
}
