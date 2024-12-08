package com.jemmerl.jemsgeology.api;

import com.google.common.collect.ImmutableMap;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import com.jemmerl.jemsgeology.init.ServerConfig;
import com.jemmerl.jemsgeology.init.geologyinit.ModGeoOres;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;

public class GeoOreRegistryAPI {

    private static final LinkedHashMap<String, OreType> ORE_TYPES = new LinkedHashMap<>();

    //TODO itll be easy to remove this and rework ores to not use an API if I decide people dont get opinions.

    // Todo add override boolean to replace conflict instead of throw warning?
    public static OreType registerOreType(OreType oreType) {
        String name = oreType.getName().toLowerCase(Locale.ROOT);
        if ((!Objects.equals(oreType.getSource(), JemsGeology.MOD_ID))  && ModGeoOres.PROTECTED_ORES.contains(name)) {
            JemsGeology.LOGGER.error("Ore add attempt named \"" + name + "\" from source: \"" + oreType.getSource()
                    + "\" attempted to override a protected default ore. Naughty naughty!");
            return null;
        }
        if (ORE_TYPES.containsKey(name)) {
            OreType conflictOre = ORE_TYPES.get(name);
            JemsGeology.LOGGER.error("Ore add attempt named \"" + name + "\" from source: \"" + oreType.getSource()
                    + "\" is conflicting with ore \"" + name + "\" from source: \"" + conflictOre.getSource() + "\"");
            return null;
        }
        ORE_TYPES.put(name, oreType);

        // DEBUG
        if (ServerConfig.DEBUG_ORE_REG.get()) {
            JemsGeology.LOGGER.info("Registered ore-type named \"" + name + "\" from source: \"" + oreType.getSource() + "\"");
        }

        return oreType;
    }

    public static ImmutableMap<String, OreType> getRegisteredOres() {
        // DEBUG
        if (ServerConfig.DEBUG_ORE_REG.get()) {
            JemsGeology.LOGGER.info("Retrieved " + ORE_TYPES.size() + " registered ores");
        }
        return ImmutableMap.copyOf(ORE_TYPES);
    }

}
