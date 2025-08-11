package com.jemmerl.jemsgeology.capabilities.world.watertable;

public class WTDataCache {
    private static final int INIT_COUNT = 5;

    // maybe private so can force min/max caps?
    private final int baseHeight;
    private int currHeight;
    public byte count;

    // TODO should height be based on diff between sea level? Bc I think that is how I did it in test.
    //  Also, terrain can be below sea level IRL (see death valley, badwater basin). Need to accommodate eventually.

    WTDataCache(int baseHeight) {
        this.baseHeight = baseHeight;
        this.currHeight = baseHeight;
        this.count = INIT_COUNT;
    }

    WTDataCache(int baseHeight, int currHeight, byte count) {
        this.baseHeight = baseHeight;
        this.currHeight = currHeight;
        this.count = count;
    }

    public void setCurrHeight(int currHeight) {
        this.currHeight = currHeight;
    }

    public int getBaseHeight() {
        resetCount();
        return baseHeight;
    }

    public int getCurrHeight() {
        resetCount();
        return currHeight;
    }

    public int delta() {
        return currHeight - baseHeight;
    }

    public void resetCount() {
        count = INIT_COUNT;
    }





//    //////////////////////////////
//    //      SERIALIZATION       //
//    //////////////////////////////
//
//    public int combineHeightCount() {
//        // Clamp values for storage
//        height = Math.min(Math.max(Short.MIN_VALUE, height), Short.MAX_VALUE);
////        count = (byte) Math.min(Math.max(Byte.MIN_VALUE, count), Byte.MAX_VALUE);
//        count = (byte) Math.max(count, 0);
//        return (height << 16) | (count & 0xFF);
//    }
//
//    public static byte extractCount(int combined) {
//        return (byte)combined;
//    }
//
//    public static int extractHeight(int combined) {
//        return (short)(combined >> 16);
//    }
}
