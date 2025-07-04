package com.jemmerl.jemsgeology.capabilities.chunk.watertablebase;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IWaterTableBase extends INBTSerializable<CompoundNBT> {
    int getBaseWaterTable();
    void setBaseWaterTable(int tableLevel);
}
