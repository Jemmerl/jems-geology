package com.jemmerl.jemsgeology.capabilities.chunk.chunkheight;

import com.jemmerl.jemsgeology.JemsGeology;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ChunkHeightCapability implements IChunkHeightCap {

    @CapabilityInject(IChunkHeightCap.class)
    public static final Capability<IChunkHeightCap> CHUNK_HEIGHT_CAPABILITY = null;

    private int chunkHeight;
    //private WTQuality wtQuality; // May be used later, consider.

    public ChunkHeightCapability() {
        this.chunkHeight = Integer.MIN_VALUE;
    }

    public ChunkHeightCapability(int chunkHeight) {
        this.chunkHeight = chunkHeight;
    }

    @Override
    public int getChunkHeight() {
        return chunkHeight;
    }

    @Override
    public void setChunkHeight(int chunkHeight) {
        this.chunkHeight = chunkHeight;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt(JemsGeology.MOD_ID+":chunkHT", chunkHeight);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        chunkHeight = nbt.getInt(JemsGeology.MOD_ID+":chunkHT");
    }


}
