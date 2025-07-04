package com.jemmerl.jemsgeology.capabilities;

import net.minecraft.util.math.BlockPos;

// Class and related methods heavily built with permission from Geolosys (oitsjustjose)
// My gratitude cannot be expressed enough for oitsjustjose's prior work in developing this, full credit to them
// https://github.com/oitsjustjose/Geolosys/tree/a8e2ba469a2627bfee862f5d8b99774cc1b5981c

public class CapabilityUtils {
    public static String toString(BlockPos pos) {
        return "[" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "]";
    }

    public static BlockPos fromString(String s) {
        String[] parts = s.replace("[", "").replace("]", "").split(",");
        return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
