//package com.jemmerl.jemsgeology.capabilities.world.watertable;
//
//import net.minecraft.block.BlockState;
//
//// Class heavily built/cloned from Lost Cities (McJty)
//// My gratitude cannot be expressed enough for McJty's prior work in developing this, full credit to them
//// https://github.com/McJtyMods/LostCities
//
//// TODO McJty's fast rand. To find later. I know I will use it, as I have been.
////    private static int fastrand() {
////        gSeed = (214013 * gSeed + 2531011);
////        return (gSeed >> 16) & 0x7FFF;
////    }
//
//
//public class ChunkHeightmap {
//    private final byte heightmap[] = new byte[16*16];
//    private Integer maxHeight = null;
//    private Integer minHeight = null;
//    private Integer avgHeight = null;
//
//    public ChunkHeightmap() {
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                heightmap[z * 16 + x] = 0;
//            }
//        }
//    }
//
//    public void update(int x, int y, int z, BlockState state) {
//        BlockState air = LostCityTerrainFeature.air;
//        if (state == air) {
//            return;
//        }
//
//        int current = heightmap[z * 16 + x] & 0xff;
//        if (y <= current) {
//            return;
//        }
//
//        heightmap[z * 16 + x] = (byte) y;
//    }
//
//    public int getHeight(int x, int z) {
//        return heightmap[z*16+x] & 0xff;
//    }
//
//    public void setHeight(int x, int z, int height) {
//        heightmap[z*16+x] = (byte) height;
//    }
//
//    private void calculateHeightInfo() {
//        int max = 0;
//        int min = 256;
//        int avg = 0;
//        for (int x = 0 ; x < 16 ; x++) {
//            for (int z = 0 ; z < 16 ; z++) {
//                int h = getHeight(x, z);
//                if (h > max) {
//                    max = h;
//                }
//                if (h < min) {
//                    min = h;
//                }
//                avg += h;
//            }
//        }
//        avgHeight = avg / 256;
//        minHeight = min;
//        maxHeight = max;
//    }
//
//    public int getAverageHeight() {
//        if (avgHeight == null) {
//            calculateHeightInfo();
//        }
//        return avgHeight;
//
//    }
//
//    public int getMinimumHeight() {
//        if (minHeight == null) {
//            calculateHeightInfo();
//        }
//        return minHeight;
//    }
//
//    public int getMaximumHeight() {
//        if (maxHeight == null) {
//            calculateHeightInfo();
//        }
//        return maxHeight;
//    }
//
//}