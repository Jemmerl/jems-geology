package com.jemmerl.jemsgeology.capabilities.chunk.chunkheight;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IChunkHeightCap extends INBTSerializable<CompoundNBT> {
    int getChunkHeight();
    void setChunkHeight(int chunkHeight);
}
