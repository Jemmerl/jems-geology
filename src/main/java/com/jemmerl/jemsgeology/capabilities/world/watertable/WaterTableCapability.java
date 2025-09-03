package com.jemmerl.jemsgeology.capabilities.world.watertable;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.capabilities.chunk.chunkheight.ChunkHeightCapability;
import com.jemmerl.jemsgeology.capabilities.chunk.chunkheight.IChunkHeightCap;
import com.jemmerl.jemsgeology.util.UtilMethods;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;
import java.util.*;
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

    // where WT flows out of walls, moss and lichen!

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

// TODO SOLUTION
//  When WT increased/decreased, store currently loaded in a radius around it. Store a countdown to purge w/ unused

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


// Keep this for personal documentation:
// Water blocks (charges) per chunk level (min 12, max 16)
// max build in modern version is total 383 blocks (-64 to 320). (4596, 6128)
//  -> needs "1111111111111" 13 bits (8191)
// BUT max possible with modifications is 4094 (-2047 to 2048). (49128, 65504)
//  -> needs "1111111111111111" 16 bits (65535)
// int has "1111_1111_1111_1111___111_1111_1111_1111" (31 bits): 16 to WT, 15 to other data.
// same game of extra data can therefore be played in the chunk-stored value.

// Final decision:
// Need to store current WT, cached height, and timer.
// 16 for WT Height (0-65535), giving 16 water blocks per chunk-level todo should be delta not height??
// 12 for cached chunk height (max possible 4094 (-2047 to 2048)) (0-4095)
// 3 for cache timer (0-7) for chunk clearing

// Todo... If I can remove the chunkgenned cap, it would be saving 2xINT for every genned chunks.
//  That would justify me using longs here, or even just two separate ints for ease of calc!


// TODO Provide a modifier on WT yield. Each chunk y-level holds 16 "charges" or "pumps".
//  By default, each charge is 1 block (1000mb), but this could get modified by a config mulitplier... or just raw set?
//  I like directly set more tbh. Less vague to the user. Will need the pump-block interface to handle...
//  And ofc, any in-world water draws (like river decreases) will treat draws like 1 block (1000mb) unless the draw
//  amount is less than 1000, in which case it will take what is needed (if 500mb, will take 2 charges).

// store map of altered chunks in world cap
// purge when back to base
// if need base, calculate
// *** maybe cache last 20 base chunk wts in this cap?

// Chunk primer generation heavily built/cloned from Lost Cities (McJty)
// My gratitude cannot be expressed enough for McJty's prior work in developing this, full credit to them
// https://github.com/McJtyMods/LostCities

public class WaterTableCapability implements IWaterTableCap {

    // HASHCODE EQUALS????????

    private static final int MAX_FLOW = Integer.MAX_VALUE;

    @CapabilityInject(IWaterTableCap.class)
    public static final Capability<IWaterTableCap> WATER_TABLE_CAPABILITY = null;

    private final ConcurrentHashMap<ChunkPos, WTDataCache> chunkWTData;
    public final ConcurrentHashMap<ChunkPos, Integer> chunkHeightTempCache; // Never serialized, very temporary

    // TODO I believe this means if this cap is attached to another dimension, it should have its own sea level.
    private int SEA_LEVEL = 63; // Default 63. Should be overwritten at world attachment.

    public WaterTableCapability() {
        chunkWTData = new ConcurrentHashMap<>();
        chunkHeightTempCache = new ConcurrentHashMap<>();
    }

    public WaterTableCapability(World world) {
        this();
        SEA_LEVEL = extractSeaWater(world);
    }

    // Tries to get the sea level for a world. If it fails, it will return 63 as a default.
    private static int extractSeaWater(World world) {
        AbstractChunkProvider chunkProvider = world.getChunkProvider();
        if (chunkProvider instanceof ServerChunkProvider) {
            ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();
            // Will either get the settings value, or default to 63.
            return generator.getSeaLevel();
        } else {
            JemsGeology.LOGGER.error("Something went wrong getting the world's Sea Level! Tell the dev! (Defaulting to 63)");
            return 63;
        }
    }


    /////////////////////////////
    //      ACCESSIBLE API     //
    /////////////////////////////

    // TODO next is the delta map and methods to add/remove to it
    // TODO have methods to query injects/pump amounts, based on height (pressure) and all that. Later.
    //  for now, keep basic.

    // I think cacheUnload was about if I should force load a unloaded chunk that got reqested water or something?

    // Adds 1 charge's worth of mB.
    public int reqInjectWater(ChunkPos chunkPos, World world, int amount, boolean cacheUnloaded) {
        cache5x5(chunkPos, world, cacheUnloaded);
        WTDataCache wtData = getChunkWTDataCache(chunkPos, world);
        wtData.setCurrHeight(wtData.getCurrHeight() + amount);
        return 0;
    }

    // Returns 1 charge's worth of mB if below water level.
    public int reqPumpWater(ChunkPos chunkPos, World world, int amount, boolean cacheUnloaded) {
        cache5x5(chunkPos, world, cacheUnloaded);
        WTDataCache wtData = getChunkWTDataCache(chunkPos, world);
        wtData.setCurrHeight(Math.max((wtData.getCurrHeight() - amount), 0));
        return 0;
    }

    // Used when modifying the water table.
    // TODO this could be a lot of calls with frequent modifications
    //  optimize or find a way to call less often.
    private void cache5x5(ChunkPos centerPos, World world, boolean cacheUnloaded) {
        for (int ix = -2; ix < 3; ix++) {
            for (int iz = -2; iz < 3; iz++) {
                if ((ix == 0) && (iz == 0)) continue;
                ChunkPos checkChunkPos = new ChunkPos(centerPos.x+ix, centerPos.z+iz);
                WTDataCache wtData = chunkWTData.get(checkChunkPos);
                if (wtData != null) {
                    wtData.resetCount();
                    continue;
                }
                System.out.println(checkChunkPos + ", " + world.chunkExists(checkChunkPos.x, checkChunkPos.z));
                if (cacheUnloaded || world.chunkExists(checkChunkPos.x, checkChunkPos.z)) {
                    calcChunkWTHeight(checkChunkPos, world);
                }
            }
        }
        chunkWTData.forEach((trhis, th) -> System.out.println(trhis + ": " + th.getCurrHeight()));
    }



    // TODO FLOAT OR INT?? ROUND BEFORE RETURN??? HOW MANY WATER BLOCKS PER LEVEL ???
    //  -> FLOOR in the direction of sea level?
    @Override
    public float getWTHeight(int x, int z, World world) {
        // TODO change this to work on different dimensions. Maybe hold off until feature requested.
        //  THis isnt attached to any dimensions besides overworld, so this is not needed
//        if (!world.getWorld().getDimensionKey().getLocation().toString().equals("minecraft:overworld")) {
//            return 0f;
//        }

        // TODO in case rivers are non-sea level, have config option?
        // TODO also need a way to handle other dimensions? Maybe hold off until feature requested.
        Biome.Category category = world.getBiome(new BlockPos(x, SEA_LEVEL, z)).getCategory();
        if (category.equals(Biome.Category.OCEAN) || /*(Config.stuff && */category.equals(Biome.Category.RIVER)) {
            return SEA_LEVEL;
        }

        return getBlockWTHeight(x, z, world);
    }


    ////////////////////////////////////
    //      WATER TABLE UPDATING      //
    ////////////////////////////////////

    private static void printMap(AbstractMap<ChunkPos, WTDataCache> map) {
        int[][] temp = new int[11][11];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = Integer.MIN_VALUE;
            }
        }

//        System.out.println("ALL CACHED:");
        map.forEach(((chunkPos, dataCache) -> {
//            System.out.println("Pos: " + chunkPos + ", val: " + dataCache.getCurrHeight());

            if (((chunkPos.x+5) < 0) || ((chunkPos.x+5) >= temp.length)) return;
            if (((chunkPos.z+5) < 0) || ((chunkPos.z+5) >= temp[0].length)) return;

            temp[chunkPos.x+5][chunkPos.z+5] = dataCache.delta();
        }));
//        System.out.println("END CACHED");

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                int tempint = temp[i][j];
                if (tempint == Integer.MIN_VALUE) {
                    System.out.print(" empty");
                    continue;
                }
                System.out.printf("%3d%3s", temp[i][j], "");
            }
            System.out.println();
        }
    }

    // Cache biome too? Not until bottleneck found.
    @Override
    public void updateWTMap(World world) {
        System.out.println("BEFORE: ");
        printMap(chunkWTData);
        ConcurrentHashMap<ChunkPos, Integer> wtDeltas = new ConcurrentHashMap<>();
        ArrayList<ChunkPos> removeList = new ArrayList<>();

        //todo concurrent modification issue
        // potential sol? deep copy of keyset.

        // Calculate deltas
        for (ChunkPos currPos : chunkWTData.keySet()) {
            WTDataCache currWTData = chunkWTData.get(currPos);

            // UP, RIGHT, DOWN, LEFT
            ChunkPos[] adjPos = new ChunkPos[]{
                    UtilMethods.adjChunkPos(currPos, Direction.NORTH),
                    UtilMethods.adjChunkPos(currPos, Direction.EAST),
                    UtilMethods.adjChunkPos(currPos, Direction.SOUTH),
                    UtilMethods.adjChunkPos(currPos, Direction.WEST)
            };

            // TODO algorithm needs to be reworked to be okay with (curr-base), not curr.

            int currRelVal = currWTData.delta();
            int[] adjHeight = new int[]{
                    getChunkWTDataCache(adjPos[0], world).delta(),
                    getChunkWTDataCache(adjPos[1], world).delta(),
                    getChunkWTDataCache(adjPos[2], world).delta(),
                    getChunkWTDataCache(adjPos[3], world).delta()
            };

            // Check and mark to purge unused cached chunks
            if ((currRelVal == 0) && (adjHeight[0] == 0) && (adjHeight[1] == 0) && (adjHeight[2] == 0) && (adjHeight[3] == 0)) {
                if (currWTData.decCount() < 1) removeList.add(currPos);
                continue;
            }

            int[] adjDiff = new int[]{
                    getAdjRelDiff(adjHeight[0], currRelVal),
                    getAdjRelDiff(adjHeight[1], currRelVal),
                    getAdjRelDiff(adjHeight[2], currRelVal),
                    getAdjRelDiff(adjHeight[3], currRelVal)
            };
            int totalDiff = Arrays.stream(adjDiff).sum();

            int[] contributions = new int[]{
                    getContribution(adjDiff[0], totalDiff),
                    getContribution(adjDiff[1], totalDiff),
                    getContribution(adjDiff[2], totalDiff),
                    getContribution(adjDiff[3], totalDiff)
            };
            int sumLoss = Arrays.stream(contributions).sum();

            wtDeltas.merge(currPos, -sumLoss, Integer::sum);
            wtDeltas.merge(adjPos[0], contributions[0], Integer::sum);
            wtDeltas.merge(adjPos[1], contributions[1], Integer::sum);
            wtDeltas.merge(adjPos[2], contributions[2], Integer::sum);
            wtDeltas.merge(adjPos[3], contributions[3], Integer::sum);
        }

        // Apply deltas to cache
        for (Map.Entry<ChunkPos, Integer> deltaEntry : wtDeltas.entrySet()) {
            // If the chunk was not previously cached, this method will do so. But it should have been already
            // handled during the delta calculation.
            WTDataCache wtData = getChunkWTDataCache(deltaEntry.getKey(), world);
            int updatedWTHeight = wtData.getCurrHeight() + deltaEntry.getValue();

            updatedWTHeight = Math.min((updatedWTHeight+getChunkRecharge(deltaEntry.getKey())), wtData.getBaseHeight());
            wtData.setCurrHeight(updatedWTHeight);
        }

        // Purge unused chunks
        for (ChunkPos removePos: removeList) {
            if (chunkWTData.get(removePos).delta() != 0) {
                JemsGeology.LOGGER.warn("Tried to remove a modified watertable cached chunk from world: {} at chunk: {}", world, removePos);
                continue;
            }
            chunkWTData.remove(removePos);
        }

        System.out.println("AFTER: ");
        printMap(chunkWTData);
    }

    /*
    [11:56:24] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability:updateWTMap:292]: BEFORE:
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
[11:56:24] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability:updateWTMap:371]: AFTER:
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
[11:56:24] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.events.ForgeBusEvents:updateWTDelta:29]: Ran for: 2 ms
[11:56:54] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability:updateWTMap:292]: BEFORE:
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty-40    empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
[11:56:54] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability:updateWTMap:371]: AFTER:
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty-35    empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
[11:56:54] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.events.ForgeBusEvents:updateWTDelta:29]: Ran for: 14 ms
[11:57:24] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability:updateWTMap:292]: BEFORE:
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty-63    empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
[11:57:24] [Server thread/INFO] [STDOUT/]: [com.jemmerl.jemsgeology.capabilities.world.watertable.WaterTableCapability:updateWTMap:371]: AFTER:
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty-11    empty empty empty empty empty empty empty
 empty empty-11    empty-11    empty empty empty empty empty empty
 empty empty empty-11    empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
 empty empty empty empty empty empty empty empty empty empty empty
     */


    // Used for checking amount to flow-out of a chunk, so only one difference direction is counted.
    private static int getAdjRelDiff(int adj, int curr) {
        return Math.max((curr-adj), 0);
    }

    private static int getContribution(int adjDiff, float totalDiff) {
        // (adjDiff / totalDiff) = contributionPercent
        return Math.min(Math.round(adjDiff * (adjDiff / totalDiff) * .25f), MAX_FLOW);
    }

    private static int getChunkRecharge(ChunkPos chunkPos) {
        return 5;
    }




    //////////////////////////////
    //      IMPLEMENTATION      //
    //////////////////////////////

    // Block-wise current water table height
    // todo might sepatating the world into 4x4 center-chunk blobs work well here for efficiency???
    //  calculate the coords first w/ quick math, may allow for simplifications?
    private float getBlockWTHeight(int x, int z, World world) {
        int center_cX = x >> 4;
        int center_cZ = z >> 4;
        float center_X = (center_cX << 4) + 7;
        float center_Z = (center_cZ << 4) + 7;
        int xFlag = (x > center_X) ? 1 : 0;
        int zFlag = (z > center_Z) ? 1 : 0;

        float x1 = center_X - ((1 - xFlag) * 16); // min x
        float x2 = center_X + (xFlag * 16); // max x
        float z1 = center_Z - ((1 - zFlag) * 16); // min z
        float z2 = center_Z + (zFlag * 16); // max z
        int Q11 = getChunkWTHeight(center_cX - (1 - xFlag), center_cZ - (1 - zFlag), world); // min x, min z
        int Q21 = getChunkWTHeight(center_cX + xFlag, center_cZ - (1 - zFlag), world); // max x, min z
        int Q12 = getChunkWTHeight(center_cX - (1 - xFlag), center_cZ+ + zFlag, world); // min x, max z
        int Q22 = getChunkWTHeight(center_cX + xFlag, center_cZ + zFlag, world); // max x, max z

        return UtilMethods.bilinearLerp(x, z, x1, x2, z1, z2, Q11, Q21, Q12, Q22);
    }


    // Get cached WT or calculate the base chunk WT height
    private int getChunkWTHeight(int cX, int cZ, World world) {
        return getChunkWTHeight(new ChunkPos(cX, cZ), world);
    }

    private int getChunkWTHeight(ChunkPos chunkPos, World world) {
        WTDataCache data = chunkWTData.get(chunkPos);
        return (data != null) ? data.getCurrHeight() : calcChunkWTHeight(chunkPos, world);
    }

    private WTDataCache getChunkWTDataCache(ChunkPos chunkPos, World world) {
        WTDataCache data = chunkWTData.get(chunkPos);
        if (data != null) {
            return data;
        }

        // If not present, then calculate and try again. Shouldn't ever return null.
        calcChunkWTHeight(chunkPos, world);
        return chunkWTData.get(chunkPos);
    }


    // could replace water lakes entirely with localized lakes from water table...........
    // BUT that messes with so much spawning stuff maybe? see how vanilla handles lakes and mob spawns and sugarcane and stuff.
    // Rivers are fine. theyll stay as is, bc always at sea level
    // Except maybe do some random tick shenanigans where if ticked block below WT, then remove?
    // Maybe make custom fresh water to do that? Maybe that should be saved for a future addon mod.

    // Returns the center WT height raw value; check if the data is cached before calling.
    // TODO need to enable to handle terrain that is below sea level.
    private int calcChunkWTHeight(ChunkPos chunkPos, World world) {
        int x = (chunkPos.x << 4) + 7;
        int z = (chunkPos.z << 4) + 7;
        Biome.Category category = world.getBiome(new BlockPos(x, SEA_LEVEL, z)).getCategory();
        if (category.equals(Biome.Category.OCEAN) || /*todo (Config.stuff && */category.equals(Biome.Category.RIVER)) {
            chunkWTData.put(chunkPos, new WTDataCache(SEA_LEVEL));
            return SEA_LEVEL;
        }

        int chunkHeight = getChunkHeight(chunkPos.x, chunkPos.z, world);
        int abv = chunkHeight - SEA_LEVEL;
        if (abv <= 0) {
            chunkWTData.put(chunkPos, new WTDataCache(SEA_LEVEL));
            return SEA_LEVEL;
        }

        // TODO balance constant C and the downfall/temp etc.
        // Lower C is closer WT tracking to chunk height
        final float C = 7.5f - biomeFactor(new BlockPos(x, chunkHeight, z), world);
        final float W = (4*C*C*C)/(27f*SEA_LEVEL);
        float WT = (abv+3*W) - C*(float)Math.cbrt((abv+2*W)*(abv+2*W) / (float)SEA_LEVEL);

        int rawWTreturn = Math.max((int)Math.floor(WT)+SEA_LEVEL, SEA_LEVEL);
        chunkWTData.put(chunkPos, new WTDataCache(rawWTreturn));
        return rawWTreturn;
    }

    // Assumes normalized biome properties to [0,1]
    // Effectively returns (2f * averagedDownfall(x, z, world) + 2f * averagedTemp(x, z, world)), see obsolete method.
    // Final biome factor ranges between 0 and 4.
    private float biomeFactor(BlockPos centerPos, IWorld world) {
        float sumAvg = 0f;
        for (int ix = -2; ix <= 2; ix+=2) {
            for (int iz = -2; iz <= 2; iz+=2) {
                Biome biome = world.getBiome(centerPos.add((ix*16), 0, (iz*16)));
                float gaussKernel = UtilMethods.gaussKernel3[(ix+2)/2][(iz+2)/2];
                sumAvg += ((getBiomeDownfall(biome) + getBiomeTemp(biome)) * gaussKernel);
            }
        }
        return sumAvg + sumAvg;
    }

    // 0 for least rainfall, 1 for most --> does not need remapping, vanilla returns 0 to 1
    // If other mods add biomes that break this pattern, we will have an ISSUE.
    private float getBiomeDownfall(Biome biome) {
        return biome.getDownfall();
    }

    // 0 for lowest temp (-1), 1 for highest (2) --> needs remapping, vanilla returns -1 to 2
    // If other mods add biomes that break this pattern, we will have an ISSUE.
    private float getBiomeTemp(Biome biome) {
        return (biome.getTemperature() + 1) / 3f;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////        MY IMPLEMENTATION        ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // TODO unknown if synchronized is needed. Keep for now, remove if not.
    //  Original method uses a seed reader. Can a world or IWorld work fine too? Yet to be seen.
    private synchronized int getChunkHeight(int cX, int cZ, @Nonnull World world) {
        int chunkHeight = SEA_LEVEL;
        boolean storeFlag = false;
        boolean genFlag = false;

        IChunkHeightCap chunkHeightCap = null;

        if (world.chunkExists(cX, cZ)) {
            chunkHeightCap = world.getChunk(cX, cZ).getCapability(ChunkHeightCapability.CHUNK_HEIGHT_CAPABILITY)
                    .orElseThrow(() -> new RuntimeException("JemsGeo chunk height capability is null in \"Water Table\" capability..."));

            int capHeight = chunkHeightCap.getChunkHeight();
            if (capHeight != Integer.MIN_VALUE) {
                chunkHeight = capHeight;
            } else {
                genFlag = true;
                storeFlag = true;
            }
        } else {
            genFlag = true;
        }

        // If chunk is not generated + loaded, or it is but the height was never stored in its capability:
        if (genFlag) {
//            ChunkHeightmap heightmap = new ChunkHeightmap();

            boolean doNoiseVariant = false;
            AbstractChunkProvider chunkProvider = world.getChunkProvider();
            if (chunkProvider instanceof ServerChunkProvider) {
                ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();
                if (generator instanceof NoiseChunkGenerator) {
                    doNoiseVariant = true;
                }
            }

            if (doNoiseVariant) {
                chunkHeight = makeDummyChunkNoise(cX, cZ, world/*, heightmap*/);
            } else {
//                chunkHeight = makeDummyChunk(/*heightmap*/);
                chunkHeight = SEA_LEVEL;
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! This should not have happened... UH-OH"); //todo
            }
//            chunkHeight = heightmap.getMaximumHeight();
        }

        //noinspection ConstantConditions
        if (storeFlag && (chunkHeightCap != null)) {
            chunkHeightCap.setChunkHeight(chunkHeight);
        }
        return chunkHeight;
    }

    // As far as I know this should never be called, but exists just in case.
    // TODO If I decide I do not need "heightmap" stuff and only want the center chunk value, this can be removed.
//    private void makeDummyChunk(/*ChunkHeightmap heightmap*/) {
//        // This is for now the best we can do given that we don't know the type of terrain generator
//        for (int x = 0 ; x < 16 ; x++) {
//            for (int z = 0 ; z < 16 ; z++) {
//                heightmap.update(x, SEA_LEVEL, z, Blocks.STONE.getDefaultState());
//            }
//        }
//    }

    private int makeDummyChunkNoise(int chunkX, int chunkZ, World world/*, ChunkHeightmap heightmap*/) {
        DummyChunk primer = new DummyChunk(new ChunkPos(chunkX, chunkZ)/*, heightmap*/);
        AbstractChunkProvider chunkProvider = world.getChunkProvider();
        if (chunkProvider instanceof ServerChunkProvider) {
            ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();

            // Sets the biomes in the chunk?
            generator.func_242706_a(world.func_241828_r().getRegistry(Registry.BIOME_KEY), primer);
            return updateHeightmap((NoiseChunkGenerator) generator, primer/*, heightmap*/);
//            generator.generateSurface((WorldGenRegion) region, primer);
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! This should not have happened (2)... UH-OH"); //todo
        return SEA_LEVEL;
    }


    // Seems to be using part of NoiseChunkGenerator#func_230352_b_, also known as fillFromNoise.
    public static int updateHeightmap(NoiseChunkGenerator chunkGenerator, IChunk chunk/*, ChunkHeightmap heightmap*/) {
        int centerMaxHeight = Integer.MIN_VALUE;
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
//                        int chunkIndex = yy >> 4;

                        double d8 = (double)l1 / (double)chunkGenerator.verticalNoiseGranularity;
                        double d9 = MathHelper.lerp(d8, d0, d4);
                        double d10 = MathHelper.lerp(d8, d2, d6);
                        double d11 = MathHelper.lerp(d8, d1, d5);
                        double d12 = MathHelper.lerp(d8, d3, d7);

                        for(int l2 = 0; l2 < chunkGenerator.horizontalNoiseGranularity; ++l2) {
                            int xx = cx + nx * chunkGenerator.horizontalNoiseGranularity + l2;
                            int xxx = xx & 15;
                            if (xxx != 7) continue; // CUSTOM STUFF RIGHT HERE
                            double d13 = (double)l2 / (double)chunkGenerator.horizontalNoiseGranularity;
                            double d14 = MathHelper.lerp(d13, d9, d10);
                            double d15 = MathHelper.lerp(d13, d11, d12);

                            for(int k3 = 0; k3 < chunkGenerator.horizontalNoiseGranularity; ++k3) {
                                int zz = cz + nz * chunkGenerator.horizontalNoiseGranularity + k3;
                                int zzz = zz & 15;
                                if (zzz != 7) continue; // CUSTOM STUFF RIGHT HERE

                                double d16 = (double)k3 / (double)chunkGenerator.horizontalNoiseGranularity;
                                double d17 = MathHelper.lerp(d16, d14, d15);
                                double d18 = MathHelper.clamp(d17 / 200.0D, -1.0D, 1.0D);

                                BlockState blockstate = chunkGenerator.func_236086_a_(d18, yy);
                                if (blockstate == Blocks.AIR.getDefaultState()) continue;
                                if (yyy > centerMaxHeight) centerMaxHeight = yyy; // CUSTOM STUFF RIGHT HERE
//                                    heightmap.update(xxx, (chunkIndex << 4) + yyy, zzz, blockstate);
                            }
                        }
                    }
                }
            }
            double[][] adouble1 = noise[0];
            noise[0] = noise[1];
            noise[1] = adouble1;
        }

        return centerMaxHeight;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////        LOST CITY IMPLEMENTATION        /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    public synchronized ChunkHeightmap getHeightmap(int chunkX, int chunkZ, @Nonnull World world) {
//        ChunkCoord key = new ChunkCoord(world.getLevel().dimension(), chunkX, chunkZ);
//        if (cachedHeightmaps.containsKey(key)) {
//            return cachedHeightmaps.get(key);
//        } else {
//            ChunkHeightmap heightmap = new ChunkHeightmap(profile.LANDSCAPE_TYPE, profile.GROUNDLEVEL, base);
//
//            boolean doNoiseVariant = false;
//            AbstractChunkProvider chunkProvider = world.getLevel().getChunkSource();
//            if (chunkProvider instanceof ServerChunkProvider) {
//                ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getGenerator();
//                if (generator instanceof NoiseChunkGenerator) {
//                    doNoiseVariant = true;
//                }
//            }
//
//            if (doNoiseVariant) {
//                makeDummyChunkNoise(chunkX, chunkZ, world, heightmap);
//            } else {
//                makeDummyChunk(heightmap);
//            }
//
//
//            cachedHeightmaps.put(key, heightmap);
//            return heightmap;
//        }
//    }

//    private void makeDummyChunk2(ChunkHeightmap heightmap) {
//        // This is for now the best we can do given that we don't know the type of terrain generator
//        for (int x = 0 ; x < 16 ; x++) {
//            for (int z = 0 ; z < 16 ; z++) {
//                heightmap.update(x, 70, z, Blocks.STONE.defaultBlockState());
//            }
//        }
//    }



//    private void makeDummyChunkNoise2(int chunkX, int chunkZ, World region, ChunkHeightmap heightmap) {
//        DummyChunk primer = new DummyChunk(new ChunkPos(chunkX, chunkZ), heightmap);
//        AbstractChunkProvider chunkProvider = region.getWorld().getChunkProvider();
//        if (chunkProvider instanceof ServerChunkProvider) {
//            ChunkGenerator generator = ((ServerChunkProvider) chunkProvider).getChunkGenerator();
//
//            generator.func_242706_a(region.func_241828_r().getRegistry(Registry.BIOME_KEY), primer);
//            updateHeightmap((NoiseChunkGenerator) generator, primer, heightmap);
////            generator.generateSurface((WorldGenRegion) region, primer);
//        }
//    }
//
//    public static void updateHeightmap2(NoiseChunkGenerator chunkGenerator, IChunk chunk, ChunkHeightmap heightmap) {
//        ChunkPos chunkpos = chunk.getPos();
//        int chunkX = chunkpos.x;
//        int chunkZ = chunkpos.z;
//        int cx = chunkX << 4;
//        int cz = chunkZ << 4;
//
//        double[][][] noise = new double[2][chunkGenerator.noiseSizeZ + 1][chunkGenerator.noiseSizeY + 1];
//
//        for(int nz = 0; nz < chunkGenerator.noiseSizeZ + 1; ++nz) {
//            noise[0][nz] = new double[chunkGenerator.noiseSizeY + 1];
//            chunkGenerator.fillNoiseColumn(noise[0][nz], chunkX * chunkGenerator.noiseSizeX, chunkZ * chunkGenerator.noiseSizeZ + nz);
//            noise[1][nz] = new double[chunkGenerator.noiseSizeY + 1];
//        }
//
//        for(int nx = 0; nx < chunkGenerator.noiseSizeX; ++nx) {
//            int nz;
//            for(nz = 0; nz < chunkGenerator.noiseSizeZ + 1; ++nz) {
//                chunkGenerator.fillNoiseColumn(noise[1][nz], chunkX * chunkGenerator.noiseSizeX + nx + 1, chunkZ * chunkGenerator.noiseSizeZ + nz);
//            }
//
//            for(nz = 0; nz < chunkGenerator.noiseSizeZ; ++nz) {
//                for(int ny = chunkGenerator.noiseSizeY - 1; ny >= 0; --ny) {
//                    double d0 = noise[0][nz][ny];
//                    double d1 = noise[0][nz + 1][ny];
//                    double d2 = noise[1][nz][ny];
//                    double d3 = noise[1][nz + 1][ny];
//                    double d4 = noise[0][nz][ny + 1];
//                    double d5 = noise[0][nz + 1][ny + 1];
//                    double d6 = noise[1][nz][ny + 1];
//                    double d7 = noise[1][nz + 1][ny + 1];
//
//                    for(int l1 = chunkGenerator.verticalNoiseGranularity - 1; l1 >= 0; --l1) {
//                        int yy = ny * chunkGenerator.verticalNoiseGranularity + l1;
//                        int yyy = yy & 15;
//                        int chunkIndex = yy >> 4;
//
//                        double d8 = (double)l1 / (double)chunkGenerator.verticalNoiseGranularity;
//                        double d9 = MathHelper.lerp(d8, d0, d4);
//                        double d10 = MathHelper.lerp(d8, d2, d6);
//                        double d11 = MathHelper.lerp(d8, d1, d5);
//                        double d12 = MathHelper.lerp(d8, d3, d7);
//
//                        for(int l2 = 0; l2 < chunkGenerator.horizontalNoiseGranularity; ++l2) {
//                            int xx = cx + nx * chunkGenerator.horizontalNoiseGranularity + l2;
//                            int xxx = xx & 15;
//                            double d13 = (double)l2 / (double)chunkGenerator.horizontalNoiseGranularity;
//                            double d14 = MathHelper.lerp(d13, d9, d10);
//                            double d15 = MathHelper.lerp(d13, d11, d12);
//
//                            for(int k3 = 0; k3 < chunkGenerator.horizontalNoiseGranularity; ++k3) {
//                                int zz = cz + nz * chunkGenerator.horizontalNoiseGranularity + k3;
//                                int zzz = zz & 15;
//                                double d16 = (double)k3 / (double)chunkGenerator.horizontalNoiseGranularity;
//                                double d17 = MathHelper.lerp(d16, d14, d15);
//                                double d18 = MathHelper.clamp(d17 / 200.0D, -1.0D, 1.0D);
//
//                                BlockState blockstate = chunkGenerator.func_236086_a_(d18, yy);
//                                if (blockstate != Blocks.AIR.getDefaultState()) {
//                                    heightmap.update(xxx, (chunkIndex << 4) + yyy, zzz, blockstate);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            double[][] adouble1 = noise[0];
//            noise[0] = noise[1];
//            noise[1] = adouble1;
//        }
//
//    }


    ////////////////////////////////////////
    //      WORLD-GEN ONLY METHODS        //
    ////////////////////////////////////////

    @Override
    // Called during world-gen only.  Do not use this unless you are Jem. (Me, the one writing this comment)
    // Used to temporarily store a chunk height for storage after the chunk is fully generated. Not serialized.
    public int WG_cacheChunkHeight(ChunkPos cPos, int chunkHeight) {
        chunkHeightTempCache.put(cPos, chunkHeight);
        return chunkHeight;
    }

    @Override
    // Called during world-gen only. Do not use this either unless you are Jem. (Me, the one also writing this comment)
    // If a cached height isn't present, then provide Integer.MIN_VALUE. It will be properly set when called later.
    // If present, remove from the cache and return.
    public int WG_consumeChunkHeight(ChunkPos cPos) {
        Integer height = chunkHeightTempCache.remove(cPos);
        return (height != null) ? height : Integer.MIN_VALUE;
    }

    @Override
    // Called during world-gen only.
    // Fills the passed 16x16 output array with block-wise water table heights.
    // Returns the height of the center chunk (the one being generated) for caching.
    public int WG_chunkWTHeightMap(ChunkPos centerCPos, ISeedReader world, short[][] chunkWTOutput) {
        // Get all needed raw water-table levels
        int height = SEA_LEVEL;
        short[][] adjChunkWTHeights = new short[3][3];
        for (int iCx = -1; iCx<2; iCx++) {
            for (int iCz = -1; iCz<2; iCz++) {
                ChunkPos currCPos = new ChunkPos(centerCPos.x+iCx, centerCPos.z+iCz);
                BlockPos currHeightPos = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, currCPos.asBlockPos().add(7,0,7));
                if ((iCx == 0) && (iCz == 0)) height = currHeightPos.getY();
                adjChunkWTHeights[1+iCx][1+iCz] = (short) WG_getRawChunkWTHeight(currHeightPos, world);
            }
        }

        // TODO could unroll these a bit, then wouldnt have to calculate the flags
        // Fill 16x16 chunk water table height map
        for (int ix = 0; ix < 16; ix++) {
            for (int iz = 0; iz < 16; iz++) {
                int xFlag = (ix > 7) ? 1 : 0;
                int zFlag = (iz > 7) ? 1 : 0;

                float x1 = 7 - ((1 - xFlag) * 16); // min x
                float x2 = 7 + (xFlag * 16); // max x
                float z1 = 7 - ((1 - zFlag) * 16); // min z
                float z2 = 7 + (zFlag * 16); // max z

                int Q11 = adjChunkWTHeights[xFlag][zFlag];
                int Q21 = adjChunkWTHeights[xFlag+1][zFlag];
                int Q12 = adjChunkWTHeights[xFlag][zFlag+1];
                int Q22 = adjChunkWTHeights[xFlag+1][zFlag+1];

                chunkWTOutput[ix][iz] = (short) Math.round(UtilMethods.bilinearLerp(ix, iz, x1, x2, z1, z2, Q11, Q21, Q12, Q22));
            }
        }
        return height;
    }

    private int WG_getRawChunkWTHeight(BlockPos currPos, ISeedReader world) {
        Biome.Category category = world.getBiome(currPos).getCategory();
        if (category.equals(Biome.Category.OCEAN) || /*todo (Config.stuff && */category.equals(Biome.Category.RIVER)) {
            return SEA_LEVEL;
        }

        int abv = currPos.getY() - SEA_LEVEL;
        // TODO need new algo for terrain below sea level
        if (abv <= 0) {
            return SEA_LEVEL;
        }

        // TODO balance constant C and the downfall/temp etc.
        // Lower C is closer WT tracking to chunk height
        final float C = 7.5f - biomeFactor(currPos, world);
        final float W = (4*C*C*C)/(27f*SEA_LEVEL);
        float WT = (abv+3*W) - C*(float)Math.cbrt((abv+2*W)*(abv+2*W) / (float)SEA_LEVEL);

        // todo handle below sea level terrain (just noting everywhere it needs done)
        return Math.max((int)Math.floor(WT)+SEA_LEVEL, SEA_LEVEL);
    }


    //////////////////////////////
    //      SERIALIZATION       //
    //////////////////////////////

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        ListNBT wtChunks = new ListNBT();
        this.chunkWTData.forEach((chunkPos, data) -> {
            CompoundNBT tag = new CompoundNBT();

            // Do not store data cache with 0 count. Just purge these to base, as they should have been anyway.
            // Also purges cached heights from when a chunk was first generated. These should be consumed nearly
            // immediately on creation, but just in case any are missed... this will prevent them permanently "leaked"
            // into the water table cache.
            if (data.count >= 0) return;

            tag.putInt("x", chunkPos.x);
            tag.putInt("z", chunkPos.z);
            tag.putInt("baseHT", data.getBaseHeight());
            tag.putInt("currHT", data.getCurrHeight());
            tag.putByte("count", data.count);
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
            this.chunkWTData.put(chunkPos, new WTDataCache(nbt.getInt("baseHT"), nbt.getInt("currHT"), nbt.getByte("count")));
        });
    }
}

// Obsolete for reference

//    private float averagedDownfall(int x, int z, ISeedReader world) {
//        float sum = 0f;
//        for (int ix = -2; ix <= 2; ix+=2) {
//            for (int iz = -2; iz <= 2; iz+=2) {
//                sum += getChunkDownfall(x+(ix*16), z+(iz*16), world) * UtilMethods.gaussKernel3[(ix+2)/2][(iz+2)/2];
//            }
//        }
//        return sum;
//    }
//
//    private float averagedTemp(int x, int z, ISeedReader world) {
//        float sum = 0f;
//        for (int ix = -2; ix <= 2; ix+=2) {
//            for (int iz = -2; iz <= 2; iz+=2) {
//                sum += getChunkTemp(x+(ix*16), z+(iz*16), world) * UtilMethods.gaussKernel3[(ix+2)/2][(iz+2)/2];
//            }
//        }
//        return sum;
//    }
