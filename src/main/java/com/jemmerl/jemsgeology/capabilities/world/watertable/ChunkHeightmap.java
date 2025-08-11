package com.jemmerl.jemsgeology.capabilities.world.watertable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

// Class heavily built/cloned from Lost Cities (McJty)
// My gratitude cannot be expressed enough for McJty's prior work in developing this, full credit to them
// https://github.com/McJtyMods/LostCities

// TODO McJty's fast rand. To find later. I know I will use it, as I have been using my own implement.
//    private static int fastrand() {
//        gSeed = (214013 * gSeed + 2531011);
//        return (gSeed >> 16) & 0x7FFF;
//    }


public class ChunkHeightmap {
    private final byte heightmap[] = new byte[16*16];
    private int maxHeight = Integer.MIN_VALUE;
    private int minHeight = Integer.MIN_VALUE;
    private int avgHeight = Integer.MIN_VALUE;

    public ChunkHeightmap() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                heightmap[z * 16 + x] = 0;
            }
        }
    }

    public void update(int x, int y, int z, BlockState state) {
        if (state == Blocks.AIR.getDefaultState()) {
            return;
        }

        int current = heightmap[z * 16 + x] & 0xff;
        if (y <= current) {
            return;
        }

        heightmap[z * 16 + x] = (byte) y;
    }

    public int getHeight(int x, int z) {
        return heightmap[z*16+x] & 0xff;
    }

    public void setHeight(int x, int z, int height) {
        heightmap[z*16+x] = (byte) height;
    }

    private void calculateHeightInfo() {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int avg = 0;
        for (int x = 0 ; x < 16 ; x++) {
            for (int z = 0 ; z < 16 ; z++) {
                int h = getHeight(x, z);
                if (h > max) {
                    max = h;
                }
                if (h < min) {
                    min = h;
                }
                avg += h;
            }
        }
        avgHeight = avg / 256;
        minHeight = min;
        maxHeight = max;
    }

    public int getAverageHeight() {
        if (avgHeight == Integer.MIN_VALUE) {
            calculateHeightInfo();
        }
        return avgHeight;
    }

    public int getMinimumHeight() {
        if (minHeight == Integer.MIN_VALUE) {
            calculateHeightInfo();
        }
        return minHeight;
    }

    public int getMaximumHeight() {
        if (maxHeight == Integer.MIN_VALUE) {
            calculateHeightInfo();
        }
        return maxHeight;
    }

}