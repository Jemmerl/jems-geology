package com.jemmerl.jemsgeology.capabilities.chunk.chunkheight;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class ChunkHeightCapStorage implements Capability.IStorage<IChunkHeightCap> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IChunkHeightCap> capability, IChunkHeightCap instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IChunkHeightCap> capability, IChunkHeightCap instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT(((CompoundNBT) nbt));
        }
    }

    public class Factory implements Callable<IChunkHeightCap> {
        @Override
        public IChunkHeightCap call() throws Exception {
            return new ChunkHeightCapability();
        }
    }

}
