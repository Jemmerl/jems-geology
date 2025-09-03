package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface IWaterTableCap extends INBTSerializable<CompoundNBT> {
    float getWTHeight(int x, int z, World world);

    void updateWTMap(World world);

    int reqInjectWater(ChunkPos centerChunkPos, World world, int amount, boolean cacheUnloaded);

    int reqPumpWater(ChunkPos centerChunkPos, World world, int amount, boolean cacheUnloaded);


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


    // Called during world-gen only.  Do not use this unless you are Jem. (Me, the one writing this comment)
    @Deprecated
    int WG_cacheChunkHeight(ChunkPos cPos, int chunkHeight);

    // Called during world-gen only. Do not use this either unless you are Jem. (Me, the one also writing this comment)
    @Deprecated
    int WG_consumeChunkHeight(ChunkPos cPos);

    // Called during world-gen only.
    // I suppose you (someone who is not me, Jem) can call this one. Go for it, champ. Dunno why you would though.
    // Maybe you just think this method is really cool. In which case, thank you. I appreciate it. I like it too.
    int WG_chunkWTHeightMap(ChunkPos centerCPos, ISeedReader iSeedReader, short[][] chunkWTOutput);
}
