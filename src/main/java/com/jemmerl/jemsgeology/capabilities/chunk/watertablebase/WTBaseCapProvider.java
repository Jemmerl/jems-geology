package com.jemmerl.jemsgeology.capabilities.chunk.watertablebase;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WTBaseCapProvider implements ICapabilityProvider {
    private final IWaterTableBase impl = new WTBaseCapability(-1);
    private final LazyOptional<IWaterTableBase> cap = LazyOptional.of(() -> impl);

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capIn, final @Nullable Direction side) {
        if (capIn == WTBaseCapability.WATER_TABLE_BASE_CAPABILITY) {
            return cap.cast();
        }
        return LazyOptional.empty();
    }
}
