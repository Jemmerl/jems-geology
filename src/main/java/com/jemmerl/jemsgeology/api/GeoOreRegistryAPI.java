package com.jemmerl.jemsgeology.api;

import com.google.common.collect.ImmutableMap;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreType;

import java.util.LinkedHashMap;

public class GeoOreRegistryAPI {

    private static final LinkedHashMap<String, OreType> ORE_TYPES = new LinkedHashMap<>();

    //TODO itll be easy to remove this and rework ores to not use an API if I decide people dont get opinions.

    // Todo add override boolean to replace conflict instead of throw warning?
    public static OreType registerOreType(OreType oreType) {
        String name = oreType.getName();
        if (ORE_TYPES.containsKey(name)) {
            OreType conflictOre = ORE_TYPES.get(name);
            JemsGeology.LOGGER.error("Ore add attempt named \"" + name + "\" from source: \"" + oreType.getSource()
                    + "\" is conflicting with ore \"" + name + "\" from source: \"" + conflictOre.getSource() + "\"");
            return null;
        }
        ORE_TYPES.put(name, oreType);
        System.out.println("registered " + name);
        return oreType;
    }

//    public static void addGeoOres(Collection<GeoOre> geoOres) {
//        geoOres.forEach(GeoOreRegistryAPI::addGeoOre);
//    }

    public static ImmutableMap<String, OreType> getRegisteredOres() {
        System.out.println("got registered ores");
        return ImmutableMap.copyOf(ORE_TYPES);
    }

}
