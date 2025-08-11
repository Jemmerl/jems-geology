package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WaterTableCapStorage implements Capability.IStorage<IWaterTableCap> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IWaterTableCap> capability, IWaterTableCap instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IWaterTableCap> capability, IWaterTableCap instance, Direction side, INBT nbt) {
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
