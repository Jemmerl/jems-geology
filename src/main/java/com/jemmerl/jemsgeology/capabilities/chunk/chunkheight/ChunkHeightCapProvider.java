package com.jemmerl.jemsgeology.capabilities.chunk.chunkheight;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChunkHeightCapProvider implements ICapabilityProvider {
    private final IChunkHeightCap impl;
    private final LazyOptional<IChunkHeightCap> cap;

    public ChunkHeightCapProvider(int height) {
        impl = new ChunkHeightCapability(height);
        cap = LazyOptional.of(() -> impl);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capIn, final @Nullable Direction side) {
        if (capIn == ChunkHeightCapability.CHUNK_HEIGHT_CAPABILITY) {
            return cap.cast();
        }
        return LazyOptional.empty();
    }
}
