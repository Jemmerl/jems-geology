package com.jemmerl.jemsgeology.capabilities.world.deposit;

import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.concurrent.ConcurrentLinkedQueue;

// Class and related methods heavily built/cloned with permission from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public interface IDepositCapability extends INBTSerializable<CompoundNBT> {
    boolean hasDepositGenerated(ChunkPos pos);

    void setDepositGenerated(ChunkPos pos);

    void putImmediatePendingOre(BlockPos pos, OreType oreType, Grade grade, String name);

    void putDelayedPendingOre(BlockPos pos, OreType oreType, Grade grade, String name);

    void removeImmediatePendingBlocksForChunk(ChunkPos cp);

    void removeDelayedPendingBlocksForChunk(ChunkPos cp);

    ConcurrentLinkedQueue<DepositCapability.PendingBlock> getImmediatePendingBlocks(ChunkPos cp);

    ConcurrentLinkedQueue<DepositCapability.PendingBlock> getDelayedPendingBlocks(ChunkPos cp);

    int getImmediatePendingBlockCount();

    int getDelayedPendingBlockCount();

    ConcurrentLinkedQueue<ChunkPos> getGenMap();
}
