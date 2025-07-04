package com.jemmerl.jemsgeology.capabilities.chunk.watertablebase;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class WTBaseCapStorage implements Capability.IStorage<IWaterTableBase> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IWaterTableBase> capability, IWaterTableBase instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IWaterTableBase> capability, IWaterTableBase instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT(((CompoundNBT) nbt));
        }
    }

    public class Factory implements Callable<IWaterTableBase> {
        @Override
        public IWaterTableBase call() throws Exception {
            return new WTBaseCapability(-1);
        }
    }

}
