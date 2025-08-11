package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterTableCapProvider implements ICapabilitySerializable<CompoundNBT> {
    private final IWaterTableCap impl;
    private final LazyOptional<IWaterTableCap> cap;

    public WaterTableCapProvider(World world) {
        impl = new WaterTableCapability(world);
        cap = LazyOptional.of(() -> impl);
    }

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
