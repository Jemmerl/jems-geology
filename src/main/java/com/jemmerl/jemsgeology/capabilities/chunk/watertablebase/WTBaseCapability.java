package com.jemmerl.jemsgeology.capabilities.chunk.watertablebase;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.capabilities.world.watertable.WTQuality;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class WTBaseCapability implements IWaterTableBase {

    @CapabilityInject(IWaterTableBase.class)
    public static final Capability<IWaterTableBase> WATER_TABLE_BASE_CAPABILITY = null;

    private int baseWTLevel;
    private WTQuality wtQuality;

    public WTBaseCapability(int baseWTLevel) {
        this.baseWTLevel = baseWTLevel;
    }

    public WTBaseCapability() {
        this.baseWTLevel = -1;
    }

    @Override
    public int getBaseWaterTable() {
        return baseWTLevel;
    }

    @Override
    public void setBaseWaterTable(int tableLevel) {
        baseWTLevel = tableLevel;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt(JemsGeology.MOD_ID+":basewatertable", baseWTLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        baseWTLevel = nbt.getInt(JemsGeology.MOD_ID+":basewatertable");
    }
}
