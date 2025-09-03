package com.jemmerl.jemsgeology.capabilities.world.watertable;

public class WTDataCache {
    private static final int INIT_COUNT = 5;
    // TODO this will depend on how frequently the WTs are updated.
    //  Updated every hour? Then a count of 3 is significant. Updated every minute? 10 might not be enough.
    //  Will need play-testing to determine optimal value!

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

    public void setCurrHeight(int newCurrHeight) {
        currHeight = newCurrHeight;
    }

    public void addToCurrHeight(int delta) {
        currHeight += delta;
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public int getCurrHeight() {
        return currHeight;
    }

    public int delta() {
        int delta = currHeight - baseHeight;
        if (delta != 0) resetCount();
        return delta;
    }

    public int decCount() {
        return (count -= 1);
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
