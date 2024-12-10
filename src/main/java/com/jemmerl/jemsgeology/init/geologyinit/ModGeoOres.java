package com.jemmerl.jemsgeology.init.geologyinit;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.geology.ores.OreLoot;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.RandomValueRange;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static com.jemmerl.jemsgeology.api.GeoOreRegistryAPI.registerOreType;

public class ModGeoOres {

    public static final OreLoot EMPTY = new OreLoot(ConstantRange.of(0), false);
    public static final OreLoot SINGLE_DROP_NO_FORTUNE = new OreLoot();


    //////////////////////////
    //      ORE TYPES       //
    //////////////////////////

    // None. No Ore. Does not get registered as an ore.
    public static final OreType NONE = new OreType("none", JemsGeology.MOD_ID);

    // Vanilla
    public static final OreType LAPIS = registerOreType(new OreType("lapis", JemsGeology.MOD_ID, false, buildLapisOreLoot(), EMPTY));
    public static final OreType REDSTONE = registerOreType(new OreType("redstone", JemsGeology.MOD_ID, false, buildRedstoneOreLoot(), EMPTY));
    public static final OreType EMERALD = registerOreType(new OreType("emerald", JemsGeology.MOD_ID, false, buildEmeraldOreLoot(), EMPTY));
    public static final OreType NATIVE_GOLD = registerOreType(OreType.of("native_gold", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE));

    // Vanilla-plus (olivine spawns with diamond; hematite, limonite, magnetite replace iron)
    public static final OreType DIAMOND = registerOreType(new OreType("diamond", JemsGeology.MOD_ID, true, buildDiamondOreLoot(), SINGLE_DROP_NO_FORTUNE));
    public static final OreType OLIVINE = registerOreType(OreType.of("olivine", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE));
    public static final OreType HEMATITE = registerOreType(OreType.of("hematite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE));
    public static final OreType LIMONITE = registerOreType(OreType.of("limonite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE));
    public static final OreType MAGNETITE = registerOreType(OreType.of("magnetite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE));

    // Modded-based
//    public static final OreType APATITE = GeoOreRegistryAPI.registerOreType(new OreType("apatite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType AZURITE = GeoOreRegistryAPI.registerOreType(new OreType("azurite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType BARYTE = GeoOreRegistryAPI.registerOreType(new OreType("baryte", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType BERYL = GeoOreRegistryAPI.registerOreType(new OreType("beryl", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType BISMUTHINITE = GeoOreRegistryAPI.registerOreType(new OreType("bismuthinite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CARNOTITE = GeoOreRegistryAPI.registerOreType(new OreType("carnotite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType CASSITERITE = GeoOreRegistryAPI.registerOreType(new OreType("cassiterite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CELESTINE = GeoOreRegistryAPI.registerOreType(new OreType("celestine", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CHALCOPYRITE = GeoOreRegistryAPI.registerOreType(new OreType("chalcopyrite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType CHROMITE = GeoOreRegistryAPI.registerOreType(new OreType("chromite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CINNABAR = GeoOreRegistryAPI.registerOreType(new OreType("cinnabar", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType COBALTITE = GeoOreRegistryAPI.registerOreType(new OreType("cobaltite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType COLTAN = GeoOreRegistryAPI.registerOreType(new OreType("coltan", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CRYOLITE = GeoOreRegistryAPI.registerOreType(new OreType("cryolite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType FLUORITE  = GeoOreRegistryAPI.registerOreType(new OreType("fluorite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType GALENA = GeoOreRegistryAPI.registerOreType(new OreType("galena", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType GRAPHITE = GeoOreRegistryAPI.registerOreType(new OreType("graphite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
    //hematite
//    public static final OreType ILMENITE = GeoOreRegistryAPI.registerOreType(new OreType("ilmenite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType LEPIDOLITE = GeoOreRegistryAPI.registerOreType(new OreType("lepidolite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
    //limonite
//    public static final OreType MAGNESITE = GeoOreRegistryAPI.registerOreType(new OreType("magnesite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
    //magnetite
//    public static final OreType MALACHITE = GeoOreRegistryAPI.registerOreType(new OreType("malachite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType MOLYBDENITE = GeoOreRegistryAPI.registerOreType(new OreType("molybdenite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType MONAZITE = GeoOreRegistryAPI.registerOreType(new OreType("monazite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType NATIVE_COPPER = GeoOreRegistryAPI.registerOreType(new OreType("native_copper", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType NATIVE_ELECTRUM = GeoOreRegistryAPI.registerOreType(new OreType("native_electrum", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType NATIVE_SULFUR = GeoOreRegistryAPI.registerOreType(new OreType("native_sulfur", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType PENTLANDITE = GeoOreRegistryAPI.registerOreType(new OreType("pentlandite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType POLLUCITE = GeoOreRegistryAPI.registerOreType(new OreType("pollucite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType PYRITE = GeoOreRegistryAPI.registerOreType(new OreType("pyrite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType PYROCHLORE = GeoOreRegistryAPI.registerOreType(new OreType("pyrochlore", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType PYROLUSITE = GeoOreRegistryAPI.registerOreType(new OreType("pyrolusite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType RUTILE = GeoOreRegistryAPI.registerOreType(new OreType("rutile", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SCHEELITE = GeoOreRegistryAPI.registerOreType(new OreType("scheelite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SMITHSONITE = GeoOreRegistryAPI.registerOreType(new OreType("smithsonite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType SPHALERITE = GeoOreRegistryAPI.registerOreType(new OreType("sphalerite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SPODUMENE = GeoOreRegistryAPI.registerOreType(new OreType("spodumene", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType STIBNITE = GeoOreRegistryAPI.registerOreType(new OreType("stibnite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SYLVITE = GeoOreRegistryAPI.registerOreType(new OreType("sylvite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType TETRAHEDRITE = GeoOreRegistryAPI.registerOreType(new OreType("tetrahedrite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType TINCAL = GeoOreRegistryAPI.registerOreType(new OreType("tincal", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType TRONA = GeoOreRegistryAPI.registerOreType(new OreType("trona", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType URANINITE = GeoOreRegistryAPI.registerOreType(new OreType("uraninite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType WOLFRAMITE = GeoOreRegistryAPI.registerOreType(new OreType("wolframite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));


    //////////////////////////////////
    //      ORE-LOOT BUILDERS       //
    //////////////////////////////////

    private static OreLoot buildLapisOreLoot() {
        return new OreLoot(RandomValueRange.of(4f, 9f), true, OreLoot.FortuneFormula.ORE_DROPS, true, Items.LAPIS_LAZULI);
    }

    private static OreLoot buildRedstoneOreLoot() {
        return new OreLoot(RandomValueRange.of(4f, 5f), true, OreLoot.FortuneFormula.UNIFORM, true, Items.REDSTONE);
    }

    private static OreLoot buildEmeraldOreLoot() {
        return new OreLoot(ConstantRange.of(1), true, OreLoot.FortuneFormula.ORE_DROPS, true, Items.EMERALD);
    }

    private static OreLoot buildDiamondOreLoot() {
        return new OreLoot(ConstantRange.of(1), false, OreLoot.FortuneFormula.ORE_DROPS, true, Items.DIAMOND);
    }


    //////////////////////////////////////
    //          REGISTRY METHODS        //
    //////////////////////////////////////

    public static final LinkedHashSet<String> PROTECTED_ORES = new LinkedHashSet<>(Arrays.asList("none", "olivine"));

    public static void init() {
        JemsGeology.LOGGER.info("Registering GeoOres from source: " + JemsGeology.MOD_ID);
    }


}
