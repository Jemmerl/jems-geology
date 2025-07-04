package com.jemmerl.jemsgeology.capabilities.world.watertable;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.capabilities.chunk.watertablebase.IWaterTableBase;
import com.jemmerl.jemsgeology.capabilities.chunk.watertablebase.WTBaseCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WaterTableCapability implements IWaterTable {

    @CapabilityInject(IWaterTable.class)
    public static final Capability<IWaterTable> WATER_TABLE_CAPABILITY = null;

    // NEEDS:
    //  - Biomes of current and unloaded chunks -> EZ, done.
    //  - Heightmaps of current (EZ) and unloaded (uhoh) chunks


    // note: cache somehow??

    // stored as int, but can be parts of a block. like, every 32 integers is 1 block (divide  by 32)


    // store map of altered chunks in world cap
    // purge when back to base
    // if need base, calculate
    // *** maybe cache last 20 base chunk wts in this cap?

//    private final ConcurrentLinkedQueue<ChunkPos> generatedChunks;
    private final ConcurrentHashMap<ChunkPos, Integer> changedWTchunks;

    public WaterTableCapability() {
        this.changedWTchunks = new ConcurrentHashMap<>();
    }

    //////////////////////////////
    //      GETTERS/SETTERS     //
    //////////////////////////////

    // TODO incase i decide i want one or the other. remove unused ones later
    public int getChunkBaseWTLevel(int x, int z, World world) {
        return getChunkBaseWTLevel(new ChunkPos(x, z), world);
    }

    public int getChunkBaseWTLevel(BlockPos blockPos, World world) {
        return getChunkBaseWTLevel(new ChunkPos(blockPos), world);
    }

//    @Override
//    public boolean hasChunkGenerated(ChunkPos pos) {
//        return this.generatedChunks.contains(pos);
//    }
//
//    @Override
//    public void setChunkGenerated(ChunkPos pos) {
//        this.generatedChunks.add(pos);
//    }

    //////////////////////////////
    //      IMPLEMENTATION      //
    //////////////////////////////

    public int getChunkBaseWTLevel(ChunkPos chunkPos, World world) {
        if (world.getDimensionKey().getLocation().toString().equals("minecraft:overworld")) {

            Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
            IWaterTableBase chunkWTBaseCap = chunk.getCapability(WTBaseCapability.WATER_TABLE_BASE_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo base water table capability is null..."));
            int baseWTHeight = chunkWTBaseCap.getBaseWaterTable();

            BlockPos centerPos = new BlockPos(chunkPos.getXStart()+7, 0, chunkPos.getZStart()+7);

            int seaLevel = -1;
            DimensionSettings dimSettings = WorldGenRegistries.NOISE_SETTINGS.getValueForKey(DimensionSettings.OVERWORLD);
            if (dimSettings != null) {
                seaLevel = dimSettings.getSeaLevel();

            } else {
                System.out.println("aaaaaaaaahhhh!!!!");
                seaLevel = 63;
                //world.getSeaLevel();
            }
            System.out.println("sealevel: " + seaLevel);

            // todo maybe a check if in worldgen or not, thn pick the right surface? ugh but will vary on ppl building?
            int height = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, centerPos).getY();
            float precipt = world.getBiome(centerPos).getDownfall(); // todo average between adj chunks?
            //funkySecretSauce (random noise?)
            return height - 10 - Math.round(10 * (1 - precipt));
        }

        return 0;
    }




    //////////////////////////////
    //      SERIALIZATION       //
    //////////////////////////////

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        ListNBT wtChunks = new ListNBT();
        this.changedWTchunks.forEach((chunkPos, level) -> {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("x", chunkPos.x);
            tag.putInt("z", chunkPos.z);
            tag.putInt("wtlev", level);
            wtChunks.add(tag);
        });
        compound.put(JemsGeology.MOD_ID+":wt", wtChunks);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        ListNBT wtChunks = compound.getList(JemsGeology.MOD_ID+":wt", 10);
        wtChunks.forEach(entry -> {
            CompoundNBT nbt = (CompoundNBT) entry;
            ChunkPos chunkPos = new ChunkPos(nbt.getInt("x"), nbt.getInt("z"));
            this.changedWTchunks.put(chunkPos, nbt.getInt("wtlev"));
        });
    }


    // Implementation
    /*
    Chunk value based on
    - Precipitation (how determine? take avg over chunk? just center point??)
    - Elevation
    - "Ocean level" which is also river level, aka minimum possible value.
        -> If chunk contains(?) mainly contains(?) river/lake, then set chunk value to ocean level
    - Temperature(?)
    - Slope(?) (may factor in more w/ corners)
    - Soil (coarse wont matter much, but fine soil wicks upwards) Maybe not
    - Rock below? Maybe not

    Corner values
    - 4 Corner values calculated based on avg. of 3 adjacent chunks and the chunk center
    - Ensures chunks that share a corner all have the same value

     */
}
