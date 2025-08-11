package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface IWaterTableCap extends INBTSerializable<CompoundNBT> {
        // getBlockWTLevel

    // getChunkWTLevel

    float getWTHeight(int x, int z, ISeedReader world);

    @Deprecated // Do not use this unless you are Jem. (Me, the one writing this comment)
    void cacheChunkHeight(ChunkPos cPos, int chunkHeight);
    @Deprecated // Do not use this either unless you are Jem. (Me, the one also writing this comment)
    int getCachedChunkHeight(ChunkPos cPos);


//    int getWaterTableHeight(BlockPos pos);
//
//    int getWaterTableHeight(int x, int z);
//
//    int getBaseWTLevel(int x, int z);
//    int getBaseWTLevel(BlockPos pos);
//
//
//    boolean isInWaterTable(BlockPos pos);
//
//    boolean isInWaterTable(int x, int y, int z);
}
