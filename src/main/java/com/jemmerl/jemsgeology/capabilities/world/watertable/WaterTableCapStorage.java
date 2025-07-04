package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class WaterTableCapStorage implements Capability.IStorage<IWaterTable> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IWaterTable> capability, IWaterTable instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IWaterTable> capability, IWaterTable instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT(((CompoundNBT) nbt));
        }
    }

//    public class Factory implements Callable<IWaterTable> {
//        @Override
//        public IWaterTable call() throws Exception {
//            return new WaterTableCapability();
//        }
//    }

}
