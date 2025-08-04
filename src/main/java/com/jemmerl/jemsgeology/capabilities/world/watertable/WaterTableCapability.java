package com.jemmerl.jemsgeology.capabilities.world.watertable;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.util.UtilMethods;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerWrapper;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;
import java.util.concurrent.ConcurrentHashMap;

// Reference material:
/*
https://www.ngwa.org/what-is-groundwater/About-groundwater/unconfined-or-water-table-aquifers
https://rainfor.org/en/research-reveals-the-importance-of-water-table-depth-in-forest-growth-and-carbon-modelling-in-the-amazon-region/
https://www.mdpi.com/2073-4441/15/21/3865

    // -- "In speleology, cave passages formed in the vadose zone tend to be canyon-like in shape, as the water dissolves bedrock on the floor of the passage.[3]
    // Passages created in completely water-filled conditions are called phreatic passages and tend to be circular in cross-section"
    // -- "In a cave system, the epiphreatic zone or floodwater zone is the zone between the vadose (unsaturated) zone above
    // and phreatic (saturated) zone below. It is regularly flooded and has a significant porosity. It has a great potential for cave formation"
    // -- vadose zone
    // can have phreatic caves above water table due to strata uplift

    // wts have 10-30% of stored water (based on porosity) recoverable via pumping.

    // could store pollution/saltiness in int bits??

    // worth it to have a custom "ground water" fluid? can make it drain away when aquifer lowers...

    // todo worth it to calculate highest block? that way a 60 block aquifer in a mined out chunk isnt a thing?
    //  maybe not. since can toggle if water pops out during block breaking. otherwise just let ppl cheese. no harm.



 */


/* Jem's Notes:
     Water table volume (and recharge rate) depends immensely on the properties of the rock/regolith it is in.
     Calculating that all would be an efficiency nightmare and 99.9% of players would not care...
     (Will use permeability to determine how fast a pump can work instead... with some way to prevent cheese hopefully)
     -> So, values will be generously approximated.

     Assume (generously) avg. porosity ~25%, and avg. recoverable (due to grain/fracture adhesion) ~20%
     16 * 16 blocks = 256 m^3 layers.
     Recoverable water volume = 256 * .25 * .20 = 12.8 m^3 per 16x16 chunk layer.
     TODO Now, do I round up to 16 nicely, or do I go down to 12 realistically? :)

     TODO River handling?
     * Aquifer levels beneath a stream can never decrease (assuming the stream isn't formed from the aquifer via
     me doing a crazy implementation down the line...), so pumping the aquifer below a stream is essentially infinite.
     That is fine when "fresh water" is infinite, as it is equivalent to pumping from the infinite river itself,
     but how to handle finite freshwater?
        -> Maybe have a toggle to make aquifers not infinite under river? Not as realistic if the river remains when
        the aquifer level goes down, but workable and acceptable for game balance.
        -> Again, this could be solved by an extreme rework of how rivers work in Minecraft, but that is a
        project for another day... year... decade?
 */

public class WaterTableCapability implements IWaterTable {

    @CapabilityInject(IWaterTable.class)
    public static final Capability<IWaterTable> WATER_TABLE_CAPABILITY = null;

    // TODO I believe this means if this cap is attached to another dimension, it could have its own sea level.
    public final int SEA_LEVEL = getSeaLevel();


    // TODO SOLUTION
    // Use chunk primer dummies to get height first time / when unloaded.
    // When WT increased/decreased, load and store in a radius around it. Store a countdown to purge w/ the hieght int
    // cache chunk height in chunk cap. if loaded chunk, use that. else, grab from dummy




    // TODO what to store?
    // if only store height, will have to calculate wt base every time. but height may be useful for other things?
    // if globally only store deltas, wont know which way water needs to "flow" when updating
    //  maybe solution: only update WT deltas when loaded? but then boundaries get weird...

    // need to store delta (plus/minus), as well as..
    // Can use excel style heat transfer stuff, but thats best over a small area. Running over whole map would be a pain.
    // Could only apply such in areas of interest, but need to avoid evaluating chunks multiple times.
    // Maybe make a temp map that references the original while computing, then overwrites when done??


    // Take original map. For each mod chunk, process what it takes in / what it puts out (ONLY DIRECT. ADJ CHUNKS)
    // In a new temp map, store those changed values summed for each chunk. Do not remove deltas of 0 in temp map.
    //  -> (prevents repeated add/remove if fluctuating around 0)
    // When done, sum those changes into the original. If a chunk wasnt modified before, then add to map.
    // If any chunk in the modified original = 0 delta, then remove from map.
    // SOUND GOOD!

    // UUUGGGHHH but need relative change between two to determine flow direction, and how to get that from
    // unmodified and unloaded chunks? If loaded, then ez. But when just processing the delta map in deep unloaded areas?
    // todo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ALSO???? if not using deltas, then how know when back to "0"?
    //todo potenital solutions
    //  1. guesstimate and rectify somehow when loaded. if it resolves itself before ever being loaded, then who cares
    //      if guesstimate is good enough, why should most players notice?
    //  2. load a radius around a change into memory and process as needed. issue, how to prevent immediate unloading?
    //      maybe use one of the bits to force "keep in array" for certain number of "checks"
    //  3. somehow quickly pull unmodified value from unloaded chunks? if there is an ez way to do, then would solve
    //      lots of issues.
    //      ->>> maybe use same technique used to populate the stored world height? half-gen the chunk.
    //      ... if its fast enough, should be okay.

    // Now how to determine the relative change rate if only storing a delta? Hmmmmmm
    // Solution is to store the current changed value. No way around it.
    // But how to get the relative value for adj UNMODIFIED value??

    // Now. How much of an int do we need to store the maximum possible water (for 12 and 16 instances)?
    // Any bits leftover can be used for other things if desired.

    // (min 12, max 16)
    // max build in modern version is total 383 blocks (-64 to 320). (4596, 6128)
    //  -> needs "1111111111111" 13 bits (8191)
    // BUT max possible with modifications is 4094 (-2047 to 2048). (49128, 65504)
    //  -> needs "1111111111111111" 16 bits (65535)
    // int has "1111_1111_1111_1111___111_1111_1111_1111" (31 bits): 16 to WT, 15 to other data.
    // same game of extra data can therefore be played in the chunk-stored value.

    //1111_1111_1111_1111___111_111_111_111_111

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
    private final ConcurrentHashMap<ChunkPos, Integer> chunkWTDelta;

    public WaterTableCapability() {
        this.chunkWTDelta = new ConcurrentHashMap<>();
    }

    /////////////////////////////
    //      ACCESSIBLE API     //
    /////////////////////////////

    // TODO next is the delta map and methods to add/remove to it
    // then how it gets updated





    // Block-wise current water table height
    // todo might sepatating the world into 4x4 center-chunk blobs work well here for efficiency???
    //  calculate the coords first w/ quick math, may allow for simplifications?

    // TODO FLOAT OR INT?? ROUND BEFORE RETURN??? HOW MANY WATER BLOCKS PER LEVEL ???
    @Override
    public float getWTHeight(int x, int z, ISeedReader world) {
        if (!world.getWorld().getDimensionKey().getLocation().toString().equals("minecraft:overworld")) {
            return 0f;
        }

        // TODO in case rivers are non-sea level, have config option?
        Biome.Category category = world.getBiome(new BlockPos(x, SEA_LEVEL, z)).getCategory();
        if (category.equals(Biome.Category.OCEAN) || /*(Config.stuff && */category.equals(Biome.Category.RIVER)) {
            return SEA_LEVEL;
        }


        world.getChunk(0,0);
//        ChunkPrimerWrapper;
//        ChunkPrimer;

        //net.minecraft.world.chunk.ChunkPrimer.func_217303_b(Type)
        /*
        private Heightmap generateHeightMapChunk(IWorld world, int x, int z) {

            ChunkPrimer chunk = new ChunkPrimer(new ChunkPos(x, z), new UpgradeData(new CompoundNBT()));

            generator.generateBiomes(chunk);
            generator.makeBase(world, chunk);

            return chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);

        }
         */



        // todo the separating into chunks would allow for simplifying AND caching!
//        ChunkPos centerPos = new ChunkPos(new BlockPos(x, 0, z));


        //Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
        //            IWaterTableBase chunkWTBaseCap = chunk.getCapability(WTBaseCapability.WATER_TABLE_BASE_CAPABILITY)
        //                    .orElseThrow(() -> new RuntimeException("JemsGeo base water table capability is null..."));
        //            int baseWTHeight = chunkWTBaseCap.getBaseWaterTable();


        return getBlockWTHeight(x, z, world);
    }




    //////////////////////////////
    //      IMPLEMENTATION      //
    //////////////////////////////

    private float getBlockWTHeight(int x, int z, ISeedReader world) {
        int center_cX = x >> 4;
        int center_cZ = z >> 4;
        float center_X = (center_cX << 4) + 7.5f;
        float center_Z = (center_cZ << 4) + 7.5f;
        int xFlag = (x >= center_X) ? 1 : 0;
        int zFlag = (z >= center_Z) ? 1 : 0;

        float x1 = center_X - ((1 - xFlag) * 16); // min x
        float x2 = center_X + (xFlag * 16); // max x
        float z1 = center_Z - ((1 - zFlag) * 16); // min z
        float z2 = center_Z + (zFlag * 16); // max z
        int Q11 = getChunkWTHeight(center_cX - (1 - xFlag), center_cZ - (1 - zFlag), world); // min x, min z
        int Q21 = getChunkWTHeight(center_cX + xFlag, center_cZ - (1 - zFlag), world); // max x, min z?
        int Q12 = getChunkWTHeight(center_cX- (1 - xFlag), center_cZ+ + zFlag, world); // min x, max z?
        int Q22 = getChunkWTHeight(center_cX + xFlag, center_cZ + zFlag, world); // max x, max z

        return UtilMethods.bilinearLerp(x, z, x1, x2, z1, z2, Q11, Q21, Q12, Q22);
    }

    // Combine raw chunk WT with world-stored delta
    private int getChunkWTHeight(int cX, int cZ, ISeedReader world) {
        return getRawChunkWTHeight(cX, cZ, world) - chunkWTDelta.getOrDefault(new ChunkPos(cX,cZ),0);
    }


    // could replace water lakes entirely with localized lakes from water table...........
    // BUT that messes with so much spawning stuff maybe? see how vanilla handles lakes and mob spawns and sugarcane and stuff.
    // Rivers are fine. theyll stay as is, bc always at sea level

    // Returns the center WT height raw value
    private int getRawChunkWTHeight(int cX, int cZ, ISeedReader world) {
        int x = (cX << 4) + 7;
        int z = (cZ << 4) + 7;
        Biome.Category category = world.getBiome(new BlockPos(x, SEA_LEVEL, z)).getCategory();
        if (category.equals(Biome.Category.OCEAN) || /*(Config.stuff && */category.equals(Biome.Category.RIVER)) {
            return SEA_LEVEL;
        }

        int chunkHeight = getChunkHeight(cX, cZ, world);
        int abv = chunkHeight - SEA_LEVEL;
        if (abv <= 0) return SEA_LEVEL;

        // TODO balance constant C and the downfall/temp etc.
        // Lower C is closer WT tracking
        final float C = 7.5f - 2f * averagedDownfall(x, z, world) - 2f * averagedTemp(x, z, world);
        final float W = (4*C*C*C)/(27f*SEA_LEVEL);
        float WT = (abv+3*W) - C*(float)Math.cbrt((abv+2*W)*(abv+2*W) / (float)SEA_LEVEL);

        return Math.max((int)Math.floor(WT)+SEA_LEVEL, SEA_LEVEL);
    }

    // TODO works fine during WG, but not normally.
    // Return height in center of chunk
    private int getChunkHeight(int cX, int cZ, ISeedReader world) {
        return world.getHeight(Heightmap.Type.OCEAN_FLOOR, (cX<<4)+7, (cZ<<4)+7);
    }

    private float averagedDownfall(int x, int z, ISeedReader world) {
        float sum = 0f;
        for (int ix = -2; ix <= 2; ix+=2) {
            for (int iz = -2; iz <= 2; iz+=2) {
                sum += getChunkDownfall(x+(ix*16), z+(iz*16), world) * UtilMethods.gaussKernel3[(ix+2)/2][(iz+2)/2];
            }
        }
        return sum;
    }

    private float averagedTemp(int x, int z, ISeedReader world) {
        float sum = 0f;
        for (int ix = -2; ix <= 2; ix+=2) {
            for (int iz = -2; iz <= 2; iz+=2) {
                sum += getChunkTemp(x+(ix*16), z+(iz*16), world) * UtilMethods.gaussKernel3[(ix+2)/2][(iz+2)/2];
            }
        }
        return sum;
    }


    // Return precipt in center of chunk
    // 0 for least rainfall, 1 for most
    private float getChunkDownfall(int x, int z, ISeedReader world) {
        return world.getBiome(new BlockPos(x, SEA_LEVEL, z)).getDownfall();
    }

    private float getChunkTemp(int x, int z, ISeedReader world) {
        return world.getBiome(new BlockPos(x, SEA_LEVEL, z)).getTemperature();
    }



    private int getSeaLevel() {
        int seaLevel = -1;
        DimensionSettings dimSettings = WorldGenRegistries.NOISE_SETTINGS.getValueForKey(DimensionSettings.OVERWORLD);
        if (dimSettings != null) {
            seaLevel = dimSettings.getSeaLevel();
        } else {
            seaLevel = 63;
            JemsGeology.LOGGER.error("Failed to get dimension settings for Sea Level. That's bad. Tell the dev! (Defaulting to 63)");
        }
        return seaLevel;
    }

    /*

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////        MY IMPLEMENTATION        ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // TODO unknown in synchronized is needed. Keep for now, remove if not.
    // Original method uses a seed reader. Can a world or IWorld work fine too? TO be Seen.
    private synchronized int getChunkHeightTRUE(int cX, int cZ, @Nonnull ISeedReader world) {


        if (false) {
            // if world.getchunk is loaded && cap isnt null, get value
            // else, generate
        } else {
            ChunkHeightmap heightmap = new ChunkHeightmap();

            boolean doNoiseVariant = false;
            AbstractChunkProvider chunkProvider = world.getWorld().getChunkProvider();
            if (chunkProvider instanceof ServerChunkProvider) {
                ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();
                if (generator instanceof NoiseChunkGenerator) {
                    doNoiseVariant = true;
                }
            }

            if (doNoiseVariant) {
                makeDummyChunkNoise(cX, cZ, world, heightmap);
            } else {
                makeDummyChunk(heightmap);
            }

            return heightmap.getMaximumHeight();
        }
    }

    private void makeDummyChunk(ChunkHeightmap heightmap) {
        // This is for now the best we can do given that we don't know the type of terrain generator
        for (int x = 0 ; x < 16 ; x++) {
            for (int z = 0 ; z < 16 ; z++) {
                heightmap.update(x, 70, z, Blocks.STONE.getDefaultState());
            }
        }
    }

    private void makeDummyChunkNoise(int chunkX, int chunkZ, IWorld world, ChunkHeightmap heightmap) {
        DummyChunk primer = new DummyChunk(new ChunkPos(chunkX, chunkZ), heightmap);
        AbstractChunkProvider chunkProvider = world.getChunkProvider();
        if (chunkProvider instanceof ServerChunkProvider) {
            ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();

            // Sets the biomes in the chunk?
            generator.func_242706_a(world.func_241828_r().getRegistry(Registry.BIOME_KEY), primer);
            updateHeightmap((NoiseChunkGenerator) generator, primer, heightmap);
//            generator.generateSurface((WorldGenRegion) region, primer);
        }
    }


    // Seems to be using part of NoiseChunkGenerator#func_230352_b_, also known as fillFromNoise.
    public static void updateHeightmap(NoiseChunkGenerator chunkGenerator, IChunk chunk, ChunkHeightmap heightmap) {
        ChunkPos chunkpos = chunk.getPos();
        int chunkX = chunkpos.x;
        int chunkZ = chunkpos.z;
        int cx = chunkX << 4;
        int cz = chunkZ << 4;

        double[][][] noise = new double[2][chunkGenerator.noiseSizeZ + 1][chunkGenerator.noiseSizeY + 1];

        for(int nz = 0; nz < chunkGenerator.noiseSizeZ + 1; ++nz) {
            noise[0][nz] = new double[chunkGenerator.noiseSizeY + 1];
            chunkGenerator.fillNoiseColumn(noise[0][nz], chunkX * chunkGenerator.noiseSizeX, chunkZ * chunkGenerator.noiseSizeZ + nz);
            noise[1][nz] = new double[chunkGenerator.noiseSizeY + 1];
        }

        for(int nx = 0; nx < chunkGenerator.noiseSizeX; ++nx) {
            int nz;
            for(nz = 0; nz < chunkGenerator.noiseSizeZ + 1; ++nz) {
                chunkGenerator.fillNoiseColumn(noise[1][nz], chunkX * chunkGenerator.noiseSizeX + nx + 1, chunkZ * chunkGenerator.noiseSizeZ + nz);
            }

            for(nz = 0; nz < chunkGenerator.noiseSizeZ; ++nz) {
                for(int ny = chunkGenerator.noiseSizeY - 1; ny >= 0; --ny) {
                    double d0 = noise[0][nz][ny];
                    double d1 = noise[0][nz + 1][ny];
                    double d2 = noise[1][nz][ny];
                    double d3 = noise[1][nz + 1][ny];
                    double d4 = noise[0][nz][ny + 1];
                    double d5 = noise[0][nz + 1][ny + 1];
                    double d6 = noise[1][nz][ny + 1];
                    double d7 = noise[1][nz + 1][ny + 1];

                    for(int l1 = chunkGenerator.verticalNoiseGranularity - 1; l1 >= 0; --l1) {
                        int yy = ny * chunkGenerator.verticalNoiseGranularity + l1;
                        int yyy = yy & 15;
                        int chunkIndex = yy >> 4;

                        double d8 = (double)l1 / (double)chunkGenerator.verticalNoiseGranularity;
                        double d9 = MathHelper.lerp(d8, d0, d4);
                        double d10 = MathHelper.lerp(d8, d2, d6);
                        double d11 = MathHelper.lerp(d8, d1, d5);
                        double d12 = MathHelper.lerp(d8, d3, d7);

                        for(int l2 = 0; l2 < chunkGenerator.horizontalNoiseGranularity; ++l2) {
                            int xx = cx + nx * chunkGenerator.horizontalNoiseGranularity + l2;
                            int xxx = xx & 15;
                            double d13 = (double)l2 / (double)chunkGenerator.horizontalNoiseGranularity;
                            double d14 = MathHelper.lerp(d13, d9, d10);
                            double d15 = MathHelper.lerp(d13, d11, d12);

                            for(int k3 = 0; k3 < chunkGenerator.horizontalNoiseGranularity; ++k3) {
                                int zz = cz + nz * chunkGenerator.horizontalNoiseGranularity + k3;
                                int zzz = zz & 15;
                                double d16 = (double)k3 / (double)chunkGenerator.horizontalNoiseGranularity;
                                double d17 = MathHelper.lerp(d16, d14, d15);
                                double d18 = MathHelper.clamp(d17 / 200.0D, -1.0D, 1.0D);

                                BlockState blockstate = chunkGenerator.func_236086_a_(d18, yy);
                                if (blockstate != air) {
                                    heightmap.update(xxx, (chunkIndex << 4) + yyy, zzz, blockstate);
                                }
                            }
                        }
                    }
                }
            }

            double[][] adouble1 = noise[0];
            noise[0] = noise[1];
            noise[1] = adouble1;
        }

    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////        LOST CITY IMPLEMENTATION        /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public synchronized ChunkHeightmap getHeightmap(int chunkX, int chunkZ, @Nonnull ISeedReader world) {
        ChunkCoord key = new ChunkCoord(world.getLevel().dimension(), chunkX, chunkZ);
        if (cachedHeightmaps.containsKey(key)) {
            return cachedHeightmaps.get(key);
        } else {
            ChunkHeightmap heightmap = new ChunkHeightmap(profile.LANDSCAPE_TYPE, profile.GROUNDLEVEL, base);

            boolean doNoiseVariant = false;
            AbstractChunkProvider chunkProvider = world.getLevel().getChunkSource();
            if (chunkProvider instanceof ServerChunkProvider) {
                ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getGenerator();
                if (generator instanceof NoiseChunkGenerator) {
                    doNoiseVariant = true;
                }
            }

            if (doNoiseVariant) {
                makeDummyChunkNoise(chunkX, chunkZ, world, heightmap);
            } else {
                makeDummyChunk(heightmap);
            }


            cachedHeightmaps.put(key, heightmap);
            return heightmap;
        }
    }

    private void makeDummyChunk2(ChunkHeightmap heightmap) {
        // This is for now the best we can do given that we don't know the type of terrain generator
        for (int x = 0 ; x < 16 ; x++) {
            for (int z = 0 ; z < 16 ; z++) {
                heightmap.update(x, 70, z, Blocks.STONE.defaultBlockState());
            }
        }
    }



    private void makeDummyChunkNoise(int chunkX, int chunkZ, ISeedReader region, ChunkHeightmap heightmap) {
        DummyChunk primer = new DummyChunk(new ChunkPos(chunkX, chunkZ), heightmap);
        AbstractChunkProvider chunkProvider = region.getWorld().getChunkProvider();
        if (chunkProvider instanceof ServerChunkProvider) {
            ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();

            generator.func_242706_a(region.func_241828_r().getRegistry(Registry.BIOME_KEY), primer);
            updateHeightmap((NoiseChunkGenerator) generator, primer, heightmap);
//            generator.generateSurface((WorldGenRegion) region, primer);
        }
    }

    public static void updateHeightmap(NoiseChunkGenerator chunkGenerator, IChunk chunk, ChunkHeightmap heightmap) {
        ChunkPos chunkpos = chunk.getPos();
        int chunkX = chunkpos.x;
        int chunkZ = chunkpos.z;
        int cx = chunkX << 4;
        int cz = chunkZ << 4;

        double[][][] noise = new double[2][chunkGenerator.noiseSizeZ + 1][chunkGenerator.noiseSizeY + 1];

        for(int nz = 0; nz < chunkGenerator.noiseSizeZ + 1; ++nz) {
            noise[0][nz] = new double[chunkGenerator.noiseSizeY + 1];
            chunkGenerator.fillNoiseColumn(noise[0][nz], chunkX * chunkGenerator.noiseSizeX, chunkZ * chunkGenerator.noiseSizeZ + nz);
            noise[1][nz] = new double[chunkGenerator.noiseSizeY + 1];
        }

        for(int nx = 0; nx < chunkGenerator.noiseSizeX; ++nx) {
            int nz;
            for(nz = 0; nz < chunkGenerator.noiseSizeZ + 1; ++nz) {
                chunkGenerator.fillNoiseColumn(noise[1][nz], chunkX * chunkGenerator.noiseSizeX + nx + 1, chunkZ * chunkGenerator.noiseSizeZ + nz);
            }

            for(nz = 0; nz < chunkGenerator.noiseSizeZ; ++nz) {
                for(int ny = chunkGenerator.noiseSizeY - 1; ny >= 0; --ny) {
                    double d0 = noise[0][nz][ny];
                    double d1 = noise[0][nz + 1][ny];
                    double d2 = noise[1][nz][ny];
                    double d3 = noise[1][nz + 1][ny];
                    double d4 = noise[0][nz][ny + 1];
                    double d5 = noise[0][nz + 1][ny + 1];
                    double d6 = noise[1][nz][ny + 1];
                    double d7 = noise[1][nz + 1][ny + 1];

                    for(int l1 = chunkGenerator.verticalNoiseGranularity - 1; l1 >= 0; --l1) {
                        int yy = ny * chunkGenerator.verticalNoiseGranularity + l1;
                        int yyy = yy & 15;
                        int chunkIndex = yy >> 4;

                        double d8 = (double)l1 / (double)chunkGenerator.verticalNoiseGranularity;
                        double d9 = MathHelper.lerp(d8, d0, d4);
                        double d10 = MathHelper.lerp(d8, d2, d6);
                        double d11 = MathHelper.lerp(d8, d1, d5);
                        double d12 = MathHelper.lerp(d8, d3, d7);

                        for(int l2 = 0; l2 < chunkGenerator.horizontalNoiseGranularity; ++l2) {
                            int xx = cx + nx * chunkGenerator.horizontalNoiseGranularity + l2;
                            int xxx = xx & 15;
                            double d13 = (double)l2 / (double)chunkGenerator.horizontalNoiseGranularity;
                            double d14 = MathHelper.lerp(d13, d9, d10);
                            double d15 = MathHelper.lerp(d13, d11, d12);

                            for(int k3 = 0; k3 < chunkGenerator.horizontalNoiseGranularity; ++k3) {
                                int zz = cz + nz * chunkGenerator.horizontalNoiseGranularity + k3;
                                int zzz = zz & 15;
                                double d16 = (double)k3 / (double)chunkGenerator.horizontalNoiseGranularity;
                                double d17 = MathHelper.lerp(d16, d14, d15);
                                double d18 = MathHelper.clamp(d17 / 200.0D, -1.0D, 1.0D);

                                BlockState blockstate = chunkGenerator.func_236086_a_(d18, yy);
                                if (blockstate != air) {
                                    heightmap.update(xxx, (chunkIndex << 4) + yyy, zzz, blockstate);
                                }
                            }
                        }
                    }
                }
            }

            double[][] adouble1 = noise[0];
            noise[0] = noise[1];
            noise[1] = adouble1;
        }

    }

     */


    //////////////////////////////
    //      SERIALIZATION       //
    //////////////////////////////

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        ListNBT wtChunks = new ListNBT();
        this.chunkWTDelta.forEach((chunkPos, level) -> {
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
            this.chunkWTDelta.put(chunkPos, nbt.getInt("wtlev"));
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
