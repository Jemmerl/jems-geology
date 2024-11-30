package com.jemmerl.jemsgeology.api;

import com.google.common.collect.ImmutableMap;
import com.jemmerl.jemsgeology.JemsGeology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class GeoOreRegistryAPI {

    private static final LinkedHashMap<String, GeoOre> geoOreRegistry = new LinkedHashMap<>();

    // Todo add override boolean to replace conflict instead of throw warning?
    public static void addGeoOre(GeoOre geoOre) {
        String name = geoOre.getName();
        if (geoOreRegistry.containsKey(name)) {
            GeoOre conflictOre = geoOreRegistry.get(name);
            JemsGeology.LOGGER.warn("Ore add attempt named \"" + name + "\" from source: \"" + geoOre.getSource()
                    + "\" is conflicting with ore \"" + name + "\" from source: \"" + conflictOre.getSource() + "\"");
            return;
        }
        geoOreRegistry.put(name, geoOre);
    }

    public static void addGeoOres(Collection<GeoOre> geoOres) {
        geoOres.forEach(GeoOreRegistryAPI::addGeoOre);
    }

    public static ImmutableMap<String, GeoOre> getRegisteredOres() {
        System.out.println("got registered ores");
        return ImmutableMap.copyOf(geoOreRegistry);
    }

}
