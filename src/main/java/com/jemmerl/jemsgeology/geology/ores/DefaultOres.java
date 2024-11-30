package com.jemmerl.jemsgeology.geology.ores;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOre;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultOres {

    // These are guaranteed to always be present! Unless they are overridden ofc, in which case stuff may break
    // I should fix that in the future, I suppose.
    private static final GeoOre LAPIS = new GeoOre("lapis", JemsGeology.MOD_ID, false);
    private static final GeoOre REDSTONE = new GeoOre("redstone", JemsGeology.MOD_ID, false);
    private static final GeoOre DIAMOND = new GeoOre("diamond", JemsGeology.MOD_ID, true);
    private static final GeoOre EMERALD = new GeoOre("emerald", JemsGeology.MOD_ID, false);
    private static final GeoOre NATIVE_GOLD = new GeoOre("native_gold", JemsGeology.MOD_ID, true);
    private static final GeoOre OLIVINE = new GeoOre("olivine", JemsGeology.MOD_ID, false);

    public static void registerOres() {
        GeoOreRegistryAPI.addGeoOres(Arrays.asList(LAPIS, REDSTONE, DIAMOND, EMERALD, NATIVE_GOLD, OLIVINE));
    }
}
