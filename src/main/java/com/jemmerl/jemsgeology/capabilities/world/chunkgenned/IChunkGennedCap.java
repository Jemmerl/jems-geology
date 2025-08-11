package com.jemmerl.jemsgeology.capabilities.world.chunkgenned;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.INBTSerializable;

// Class and related methods heavily built/cloned with permission from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public interface IChunkGennedCap extends INBTSerializable<CompoundNBT> {
    boolean hasChunkGenerated(ChunkPos pos);

    void setChunkGenerated(ChunkPos pos);
}
