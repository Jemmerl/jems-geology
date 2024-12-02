package com.jemmerl.jemsgeology.init.geologyinit;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.ores.OreType;

public class ModGeoOres {

    // None. No Ore.
    private static final OreType NONE = GeoOreRegistryAPI.registerOreType(new OreType("none", JemsGeology.MOD_ID, false));

    // Vanilla / Vanilla-adjacent (e.g. olivine spawns with diamonds)
    private static final OreType LAPIS = GeoOreRegistryAPI.registerOreType(new OreType("lapis", JemsGeology.MOD_ID, false));
    private static final OreType REDSTONE = GeoOreRegistryAPI.registerOreType(new OreType("redstone", JemsGeology.MOD_ID, false));
    private static final OreType DIAMOND = GeoOreRegistryAPI.registerOreType(new OreType("diamond", JemsGeology.MOD_ID, true));
    private static final OreType EMERALD = GeoOreRegistryAPI.registerOreType(new OreType("emerald", JemsGeology.MOD_ID, false));
    private static final OreType NATIVE_GOLD = GeoOreRegistryAPI.registerOreType(new OreType("native_gold", JemsGeology.MOD_ID, true));
    private static final OreType OLIVINE = GeoOreRegistryAPI.registerOreType(new OreType("olivine", JemsGeology.MOD_ID, false));
    private static final OreType HEMATITE = GeoOreRegistryAPI.registerOreType(new OreType("hematite", JemsGeology.MOD_ID, true));
    private static final OreType LIMONITE = GeoOreRegistryAPI.registerOreType(new OreType("limonite", JemsGeology.MOD_ID, true));
    private static final OreType MAGNETITE = GeoOreRegistryAPI.registerOreType(new OreType("magnetite", JemsGeology.MOD_ID, true));

    // Modded-based
//    private static final OreType APATITE = GeoOreRegistryAPI.registerOreType(new OreType("apatite", JemsGeology.MOD_ID, false));
//    private static final OreType AZURITE = GeoOreRegistryAPI.registerOreType(new OreType("azurite", JemsGeology.MOD_ID, true));
//    private static final OreType BARYTE = GeoOreRegistryAPI.registerOreType(new OreType("baryte", JemsGeology.MOD_ID, false));
//    private static final OreType BERYL = GeoOreRegistryAPI.registerOreType(new OreType("beryl", JemsGeology.MOD_ID, false));
//    private static final OreType BISMUTHINITE = GeoOreRegistryAPI.registerOreType(new OreType("bismuthinite", JemsGeology.MOD_ID, true));
//    private static final OreType CARNOTITE = GeoOreRegistryAPI.registerOreType(new OreType("carnotite", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType CASSITERITE = GeoOreRegistryAPI.registerOreType(new OreType("cassiterite", JemsGeology.MOD_ID, true));
//    private static final OreType CELESTINE = GeoOreRegistryAPI.registerOreType(new OreType("celestine", JemsGeology.MOD_ID, false));
//    private static final OreType CHALCOPYRITE = GeoOreRegistryAPI.registerOreType(new OreType("chalcopyrite", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType CHROMITE = GeoOreRegistryAPI.registerOreType(new OreType("chromite", JemsGeology.MOD_ID, true));
//    private static final OreType CINNABAR = GeoOreRegistryAPI.registerOreType(new OreType("cinnabar", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType COBALTITE = GeoOreRegistryAPI.registerOreType(new OreType("cobaltite", JemsGeology.MOD_ID, true));
//    private static final OreType COLTAN = GeoOreRegistryAPI.registerOreType(new OreType("coltan", JemsGeology.MOD_ID, true));
//    private static final OreType CRYOLITE = GeoOreRegistryAPI.registerOreType(new OreType("cryolite", JemsGeology.MOD_ID, false));
//    private static final OreType FLUORITE  = GeoOreRegistryAPI.registerOreType(new OreType("fluorite", JemsGeology.MOD_ID, false));
//    private static final OreType GALENA = GeoOreRegistryAPI.registerOreType(new OreType("galena", JemsGeology.MOD_ID, true));
//    private static final OreType GRAPHITE = GeoOreRegistryAPI.registerOreType(new OreType("graphite", JemsGeology.MOD_ID, false));
    //hematite
//    private static final OreType ILMENITE = GeoOreRegistryAPI.registerOreType(new OreType("ilmenite", JemsGeology.MOD_ID, true));
//    private static final OreType LEPIDOLITE = GeoOreRegistryAPI.registerOreType(new OreType("lepidolite", JemsGeology.MOD_ID, false));
    //limonite
//    private static final OreType MAGNESITE = GeoOreRegistryAPI.registerOreType(new OreType("magnesite", JemsGeology.MOD_ID, false));
    //magnetite
//    private static final OreType MALACHITE = GeoOreRegistryAPI.registerOreType(new OreType("malachite", JemsGeology.MOD_ID, true));
//    private static final OreType MOLYBDENITE = GeoOreRegistryAPI.registerOreType(new OreType("molybdenite", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType MONAZITE = GeoOreRegistryAPI.registerOreType(new OreType("monazite", JemsGeology.MOD_ID, true));
//    private static final OreType NATIVE_COPPER = GeoOreRegistryAPI.registerOreType(new OreType("native_copper", JemsGeology.MOD_ID, true));
//    private static final OreType NATIVE_ELECTRUM = GeoOreRegistryAPI.registerOreType(new OreType("native_electrum", JemsGeology.MOD_ID, true));
//    private static final OreType NATIVE_SULFUR = GeoOreRegistryAPI.registerOreType(new OreType("native_sulfur", JemsGeology.MOD_ID, false));
//    private static final OreType PENTLANDITE = GeoOreRegistryAPI.registerOreType(new OreType("pentlandite", JemsGeology.MOD_ID, true));
//    private static final OreType POLLUCITE = GeoOreRegistryAPI.registerOreType(new OreType("pollucite", JemsGeology.MOD_ID, false));
//    private static final OreType PYRITE = GeoOreRegistryAPI.registerOreType(new OreType("pyrite", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType PYROCHLORE = GeoOreRegistryAPI.registerOreType(new OreType("pyrochlore", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType PYROLUSITE = GeoOreRegistryAPI.registerOreType(new OreType("pyrolusite", JemsGeology.MOD_ID, true));
//    private static final OreType RUTILE = GeoOreRegistryAPI.registerOreType(new OreType("rutile", JemsGeology.MOD_ID, true));
//    private static final OreType SCHEELITE = GeoOreRegistryAPI.registerOreType(new OreType("scheelite", JemsGeology.MOD_ID, true));
//    private static final OreType SMITHSONITE = GeoOreRegistryAPI.registerOreType(new OreType("smithsonite", JemsGeology.MOD_ID, false));//maybe
//    private static final OreType SPHALERITE = GeoOreRegistryAPI.registerOreType(new OreType("sphalerite", JemsGeology.MOD_ID, true));
//    private static final OreType SPODUMENE = GeoOreRegistryAPI.registerOreType(new OreType("spodumene", JemsGeology.MOD_ID, false));
//    private static final OreType STIBNITE = GeoOreRegistryAPI.registerOreType(new OreType("stibnite", JemsGeology.MOD_ID, true));
//    private static final OreType SYLVITE = GeoOreRegistryAPI.registerOreType(new OreType("sylvite", JemsGeology.MOD_ID, false));
//    private static final OreType TETRAHEDRITE = GeoOreRegistryAPI.registerOreType(new OreType("tetrahedrite", JemsGeology.MOD_ID, true));
//    private static final OreType TINCAL = GeoOreRegistryAPI.registerOreType(new OreType("tincal", JemsGeology.MOD_ID, false));
//    private static final OreType TRONA = GeoOreRegistryAPI.registerOreType(new OreType("trona", JemsGeology.MOD_ID, false));
//    private static final OreType URANINITE = GeoOreRegistryAPI.registerOreType(new OreType("uraninite", JemsGeology.MOD_ID, true));
//    private static final OreType WOLFRAMITE = GeoOreRegistryAPI.registerOreType(new OreType("wolframite", JemsGeology.MOD_ID, true));


    //////////////////////////////////////
    //          REGISTRY METHODS        //
    //////////////////////////////////////

    public static void init() {
        JemsGeology.LOGGER.info("Registering GeoOres from source: " + JemsGeology.MOD_ID);
    }


//    public static void registerOres() {
//        GeoOreRegistryAPI.addGeoOres(Arrays.asList(LAPIS, REDSTONE, DIAMOND, EMERALD, NATIVE_GOLD, OLIVINE));
//    }
}
