package com.jemmerl.jemsgeology.geology;

import com.jemmerl.jemsgeology.geology.stones.GeoType;
import com.jemmerl.jemsgeology.world.capability.chunk.ChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.chunk.IChunkGennedCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.DepositCapability;
import com.jemmerl.jemsgeology.world.capability.deposit.IDepositCapability;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;

import java.util.Random;

public class GeoBuilder {

    private final ISeedReader world;
    private final BlockPos cornerPos; // Starting position of this chunk's generation
    private final Random rand;

//    private final int[][][] deformHeights;
//    private final int[] oldFaultShift = new int[3];
//    private final int[] newFaultShift = new int[3];;

    private final GeoWrapper[][][] wrapperArray;

    private final IDepositCapability depCap;
    private final IChunkGennedCapability cpCap;

    public GeoBuilder(ISeedReader world, BlockPos pos, Random rand, int maxHeight) {
        this.world = world;
        this.cornerPos = pos;
        this.rand = rand;
        wrapperArray = new GeoWrapper[16][maxHeight][16];

//        this.deformHeights = new int[16][this.chunkReader.getMaxHeight()][16];

        this.depCap = world.getWorld().getCapability(DepositCapability.DEPOSIT_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo deposit capability is null..."));
        this.cpCap = world.getWorld().getCapability(ChunkGennedCapability.CHUNK_GENNED_CAPABILITY)
                .orElseThrow(() -> new RuntimeException("JemsGeo chunk gen capability is null..."));
    }

    public GeoWrapper[][][] build() {
        // Fill the default array WITHOUT SHALLOW COPYING AN ENTIRE DIMENSION OF IT THIS TIME AAAAAAAAAHHH
        for (int i = 0; i < wrapperArray.length; i++) {
            for (int j = 0; j < wrapperArray[0].length; j++) {
                for (int k = 0; k < wrapperArray[0][0].length; k++) {
                    wrapperArray[i][j][k] = new GeoWrapper(GeoType.GRAY_RHYOLITE);
                }
            }
        }




        //processOres();
        return wrapperArray;
    }

    public int[] getHorizontalDeformOffset(int x, int y, int z) {
        // things that cause horizontal deformation
        // x/z component of faults

        int age = 0;

        switch (age) {
            case 1:
            case 2:
            default:
        }
    }

    public int getYDeformOffset(int x, int y, int z) {
        // things that cause VERTICAL deformation
        // y-component of faults
        // intrusive ign features
            // sills, batholiths
        // tilt
        // buckling



        int age = 0;

        switch (age) {
            case 1:
            case 2:
            default:
        }
    }


}
