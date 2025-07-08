package com.jemmerl.jemsgeology.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ServerConfig;
import com.jemmerl.jemsgeology.init.geology.ModGeoOres;

import java.util.*;

public class GeoOreRegistryAPI {

    // I, Jem, use these as hard-coded thingies elsewhere. I won't let you mess with them, sorry :)
    public static final ImmutableSet<String> PROTECTED_ORES = ImmutableSet.of("none", "diamond", "olivine");

    // Used during runtime.
    private static final LinkedHashMap<String, OreType> REGISTERED = new LinkedHashMap<>();
    public static final Map<String, OreType> ORE_TYPES = Collections.unmodifiableMap(REGISTERED);

    //TODO itll be easy to remove this and rework ores to not use an API if I decide people dont get opinions.

    // Todo add override boolean to replace conflict instead of throw warning?
    public static boolean registerOreType(OreType oreType) {
        String name = oreType.getName().toLowerCase(Locale.ENGLISH);
        if ((!Objects.equals(oreType.getSource(), JemsGeology.MOD_ID))  && PROTECTED_ORES.contains(name)) {
            JemsGeology.LOGGER.error("Ore add attempt named \"" + name + "\" from source: \"" + oreType.getSource()
                    + "\" attempted to override a protected default ore. Naughty naughty!");
            return false;
        }
        if (REGISTERED.containsKey(name)) {
            OreType conflictOre = REGISTERED.get(name);
            JemsGeology.LOGGER.error("Ore add attempt named \"" + name + "\" from source: \"" + oreType.getSource()
                    + "\" is conflicting with ore \"" + name + "\" from source: \"" + conflictOre.getSource() + "\"");
            return false;
        }
        REGISTERED.put(name, oreType);

        // DEBUG
        if (ServerConfig.DEBUG_ORE_REGISTRY.get()) {
            JemsGeology.LOGGER.info("Registered ore-type named \"" + name + "\" from source: \"" + oreType.getSource() + "\"");
        }

        return true;
    }

    // Used during runtime.
    public static Map<String, OreType> getRegisteredOres() {
        // DEBUG
        if (ServerConfig.DEBUG_ORE_REGISTRY.get()) {
            JemsGeology.LOGGER.info("Retrieved " + REGISTERED.size() + " registered ores");
        }
        return ORE_TYPES;
    }

    public static OreType fromString(String oreName) {
        return ORE_TYPES.getOrDefault(oreName.toLowerCase(Locale.ENGLISH), OreType.NONE);
    }

}
