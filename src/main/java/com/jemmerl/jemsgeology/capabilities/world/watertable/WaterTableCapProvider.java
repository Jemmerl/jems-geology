package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterTableCapProvider implements ICapabilitySerializable<CompoundNBT> {
    private final IWaterTable impl = new WaterTableCapability();
    private final LazyOptional<IWaterTable> cap = LazyOptional.of(() -> impl);

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capIn, final @Nullable Direction side) {
        if (capIn == WaterTableCapability.WATER_TABLE_CAPABILITY) {
            return cap.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        impl.deserializeNBT(nbt);
    }
}
