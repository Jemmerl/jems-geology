package com.jemmerl.jemsgeology.capabilities.world.deposit;

import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.ores.Grade;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

// Class and related methods heavily built/cloned with permission from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public class DepositCapability implements IDepositCapability {

    @CapabilityInject(IDepositCapability.class)
    public static final Capability<IDepositCapability> DEPOSIT_CAPABILITY = null;

    private final ConcurrentLinkedQueue<ChunkPos> oreGenMap;
    private final ConcurrentHashMap<ChunkPos, ConcurrentLinkedQueue<PendingBlock>> immediatePendingBlocks;
    private final ConcurrentHashMap<ChunkPos, ConcurrentLinkedQueue<PendingBlock>> delayedPendingBlocks;

    public DepositCapability() {
        this.oreGenMap = new ConcurrentLinkedQueue<>();
        this.immediatePendingBlocks = new ConcurrentHashMap<>();
        this.delayedPendingBlocks = new ConcurrentHashMap<>();
    }

    @Override
    public ConcurrentLinkedQueue<ChunkPos> getGenMap() {
        return this.oreGenMap;
    }

    @Override
    public void putImmediatePendingOre(BlockPos pos, OreType oreType, Grade grade, String name) {
        PendingBlock p = new PendingBlock(pos, oreType, grade, false, name);
        ChunkPos cp = new ChunkPos(pos);
        this.immediatePendingBlocks.putIfAbsent(cp, new ConcurrentLinkedQueue<>());
        this.immediatePendingBlocks.get(cp).add(p);
    }

    @Override
    public void putDelayedPendingOre(BlockPos pos, OreType oreType, Grade grade, String name) {
        PendingBlock p = new PendingBlock(pos, oreType, grade, true, name);
        ChunkPos cp = new ChunkPos(pos);
        this.delayedPendingBlocks.putIfAbsent(cp, new ConcurrentLinkedQueue<>());
        this.delayedPendingBlocks.get(cp).add(p);
    }

    @Override
    public void removeImmediatePendingBlocksForChunk(ChunkPos cp) {
        this.immediatePendingBlocks.remove(cp);
    }

    @Override
    public void removeDelayedPendingBlocksForChunk(ChunkPos cp) {
        this.delayedPendingBlocks.remove(cp);
    }

    @Override
    public ConcurrentLinkedQueue<PendingBlock> getImmediatePendingBlocks(ChunkPos chunkPos) {
        return this.immediatePendingBlocks.getOrDefault(chunkPos, new ConcurrentLinkedQueue<>());
    }

    @Override
    public ConcurrentLinkedQueue<PendingBlock> getDelayedPendingBlocks(ChunkPos chunkPos) {
        return this.delayedPendingBlocks.getOrDefault(chunkPos, new ConcurrentLinkedQueue<>());
    }

    @Override
    public int getImmediatePendingBlockCount() {
        return (int) this.immediatePendingBlocks.values().stream().collect(Collectors.summarizingInt(x -> x.size())).getSum();
    }

    @Override
    public int getDelayedPendingBlockCount() {
        return (int) this.delayedPendingBlocks.values().stream().collect(Collectors.summarizingInt(x -> x.size())).getSum();
    }

    @Override
    public void setDepositGenerated(ChunkPos pos) {
        this.oreGenMap.add(pos);
    }

    @Override
    public boolean hasDepositGenerated(ChunkPos pos) {
        return this.oreGenMap.contains(pos);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.put("WorldOreDeposits", new CompoundNBT());
        compound.put("PendingBlocks", new CompoundNBT());

        CompoundNBT oreDeposits = compound.getCompound("WorldOreDeposits");
        CompoundNBT pendingBlocks = compound.getCompound("PendingBlocks");

        this.getGenMap().forEach(cp -> oreDeposits.putBoolean(serializeChunkPos(cp), true));
        this.immediatePendingBlocks.entrySet().forEach(e -> {
            ListNBT p = new ListNBT();
            String key = e.getKey().x + "_" + e.getKey().z;
            e.getValue().forEach(pb -> p.add(pb.serialize()));
            pendingBlocks.put(key, p);
        });
        this.delayedPendingBlocks.entrySet().forEach(e -> {
            ListNBT p = new ListNBT();
            String key = e.getKey().x + "_" + e.getKey().z;
            e.getValue().forEach(pb -> p.add(pb.serialize()));
            pendingBlocks.put(key, p);
        });

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        // Check to see if the old storage is still there
        if (compound.contains("PendingBlocks")) {
            CompoundNBT pendingBlocks = compound.getCompound("PendingBlocks");
            pendingBlocks.keySet().forEach(key -> {
                ListNBT pending = compound.getList(key, 10);
                //ListNBT listNBT = ((ListNBT) Objects.requireNonNull(pendingBlocks.get(key)));
                //CompoundNBT compoundNBT = ((CompoundNBT) Objects.requireNonNull(pendingBlocks.get(key)));
                pending.forEach(e -> {
                    CompoundNBT compoundNBT = (CompoundNBT) e;
                    BlockPos pos = deSerializeBlockPos(compoundNBT.getString("pos"));
                    OreType ore = GeoOreRegistryAPI.fromString(compoundNBT.getString("ore"));
                    Grade grade = Grade.valueOf(compoundNBT.getString("grade"));
                    String name = (compoundNBT.getString("name"));
                    if (compoundNBT.getBoolean("delayed")) {
                        this.putDelayedPendingOre(pos, ore, grade, name);
                    } else {
                        this.putImmediatePendingOre(pos, ore, grade, name);
                    }
                });
            });
        }

        if (compound.contains("PendingBlocksNew")) {
            CompoundNBT pendingBlocks = compound.getCompound("PendingBlocksNew");
            compound.keySet().forEach(chunkPosAsString -> {
                String[] parts = chunkPosAsString.split("_");
                ChunkPos cp = new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

                ListNBT pending = compound.getList(chunkPosAsString, 10);
                ConcurrentLinkedQueue<PendingBlock> lqi = new ConcurrentLinkedQueue<>();
                ConcurrentLinkedQueue<PendingBlock> lqd = new ConcurrentLinkedQueue<>();
                pending.forEach(x -> {
                    PendingBlock pb = PendingBlock.deserialize(x);
                    if (pb != null) {
                        if (pb.delayed) {
                            lqd.add(pb);
                        } else {
                            lqi.add(pb);
                        }
                    }
                });
                this.immediatePendingBlocks.put(cp, lqi);
                this.delayedPendingBlocks.put(cp, lqd);
            });
        }

        CompoundNBT oreDeposits = compound.getCompound("WorldOreDeposits");
        oreDeposits.keySet().forEach(key -> this.setDepositGenerated(deSerializeChunkPos(key)));
    }

    private String serializeChunkPos(ChunkPos pos) {
        return pos.x + "_" + pos.z;
    }

    private String serializeBlockPos(BlockPos pos) {
        return pos.getX() + "_" + pos.getY() + "_" + pos.getZ();
    }

    private ChunkPos deSerializeChunkPos(String asStr) {
        String[] parts = asStr.split("_");
        return new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    private BlockPos deSerializeBlockPos(String asStr) {
        System.out.println(asStr);
        String[] parts = asStr.split("_");
        System.out.println(Arrays.toString(parts));
        return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public static class PendingBlock {
        private BlockPos pos;
        private OreType ore;
        private Grade grade;
        private boolean delayed;
        private String name; // Name of the deposit generating the block

        public PendingBlock(BlockPos pos, OreType oreType, Grade grade, boolean delayed, String name) {
            this.pos = pos;
            this.ore = oreType;
            this.grade = grade;
            this.delayed = delayed;
            this.name = name;
        }

        public BlockPos getPos() { return this.pos; }
        public OreType getOre() { return this.ore; }
        public Grade getGrade() { return this.grade; }
        public boolean getDelayed() { return this.delayed; }
        public String getName() { return this.name; }

        public CompoundNBT serialize() {
            CompoundNBT tag = new CompoundNBT();
            CompoundNBT posTag = NBTUtil.writeBlockPos(this.pos);
            tag.put("pos", posTag);
            tag.putString("ore", this.ore.getName());
            tag.putString("grade", this.grade.getName());
            tag.putBoolean("delayed", this.delayed);
            tag.putString("name", this.name);
            return tag;
        }

        @Nullable
        public static PendingBlock deserialize(INBT nbt) {
            if (nbt instanceof CompoundNBT) {
                CompoundNBT tag = (CompoundNBT) nbt;
                BlockPos pos = NBTUtil.readBlockPos(tag.getCompound("pos"));
                String ore = tag.getCompound("ore").toString();
                String grade = tag.getCompound("grade").toString();
                boolean delayed = tag.getBoolean("delayed");
                String name = tag.getCompound("name").toString();
                return new PendingBlock(pos, GeoOreRegistryAPI.fromString(ore), Grade.valueOf(grade), delayed, name);
            }
            return null;
        }
    }
}