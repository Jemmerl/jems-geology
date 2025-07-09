package com.jemmerl.jemsgeology.init.geology;

import com.google.common.collect.ImmutableSet;
import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.api.GeoOreRegistryAPI;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoGroup;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoPredicate;
import com.jemmerl.jemsgeology.geology.geoblocks.GeoType;
import com.jemmerl.jemsgeology.geology.ores.GeoLoot;
import com.jemmerl.jemsgeology.geology.ores.OreType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.RandomValueRange;

import java.util.*;
import java.util.function.Supplier;

public class ModGeoOres {

    // Used during this specific mod's registration.
    private static final LinkedHashSet<OreType> REGISTERED = new LinkedHashSet<>();
    private static final Set<OreType> MOD_ORE_TYPES = Collections.unmodifiableSet(REGISTERED);


    //////////////////////////
    //      ORE TYPES       //
    //////////////////////////

    // Vanilla
    public static final OreType LAPIS = registerOreType(new OreType("lapis", JemsGeology.MOD_ID, false, buildLapisOreLoot(), GeoLoot.EMPTY, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType REDSTONE = registerOreType(new OreType("redstone", JemsGeology.MOD_ID, false, buildRedstoneOreLoot(), GeoLoot.EMPTY, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType EMERALD = registerOreType(new OreType("emerald", JemsGeology.MOD_ID, false, buildEmeraldOreLoot(), GeoLoot.EMPTY, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType NATIVE_GOLD = registerOreType(OreType.of("native_gold", JemsGeology.MOD_ID, true, GeoLoot.SINGLE_DROP_NO_FORTUNE, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType FLINT = registerOreType(OreType.of("flint_nodules", JemsGeology.MOD_ID, false, buildFlintOreLoot(), buildFlintGeoPredicate()));

    // Vanilla-plus (olivine spawns with diamond; hematite, limonite, magnetite replace iron)
    public static final OreType DIAMOND = registerOreType(new OreType("diamond", JemsGeology.MOD_ID, true, buildDiamondOreLoot(), GeoLoot.SINGLE_DROP_NO_FORTUNE, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType OLIVINE = registerOreType(OreType.of("olivine", JemsGeology.MOD_ID, false, GeoLoot.SINGLE_DROP_NO_FORTUNE, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType HEMATITE = registerOreType(OreType.of("hematite", JemsGeology.MOD_ID, true, GeoLoot.SINGLE_DROP_NO_FORTUNE, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType LIMONITE = registerOreType(OreType.of("limonite", JemsGeology.MOD_ID, true, GeoLoot.SINGLE_DROP_NO_FORTUNE, GeoPredicate.NOT_DESERT_SAND));
    public static final OreType MAGNETITE = registerOreType(OreType.of("magnetite", JemsGeology.MOD_ID, true, GeoLoot.SINGLE_DROP_NO_FORTUNE, GeoPredicate.NOT_DESERT_SAND));

    // Modded-based
//    public static final OreType APATITE = registerOreType(new OreType("apatite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType AZURITE = registerOreType(new OreType("azurite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType BARYTE = registerOreType(new OreType("baryte", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType BERYL = registerOreType(new OreType("beryl", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType BISMUTHINITE = registerOreType(new OreType("bismuthinite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CARNOTITE = registerOreType(new OreType("carnotite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType CASSITERITE = registerOreType(new OreType("cassiterite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CELESTINE = registerOreType(new OreType("celestine", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CHALCOPYRITE = registerOreType(new OreType("chalcopyrite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType CHROMITE = registerOreType(new OreType("chromite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CINNABAR = registerOreType(new OreType("cinnabar", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType COBALTITE = registerOreType(new OreType("cobaltite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType COLTAN = registerOreType(new OreType("coltan", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType CRYOLITE = registerOreType(new OreType("cryolite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType FLUORITE  = registerOreType(new OreType("fluorite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType GALENA = registerOreType(new OreType("galena", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType GRAPHITE = registerOreType(new OreType("graphite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
    //hematite
//    public static final OreType ILMENITE = registerOreType(new OreType("ilmenite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType LEPIDOLITE = registerOreType(new OreType("lepidolite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
    //limonite
//    public static final OreType MAGNESITE = registerOreType(new OreType("magnesite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
    //magnetite
//    public static final OreType MALACHITE = registerOreType(new OreType("malachite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType MOLYBDENITE = registerOreType(new OreType("molybdenite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType MONAZITE = registerOreType(new OreType("monazite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType NATIVE_COPPER = registerOreType(new OreType("native_copper", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType NATIVE_ELECTRUM = registerOreType(new OreType("native_electrum", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType NATIVE_SULFUR = registerOreType(new OreType("native_sulfur", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType PENTLANDITE = registerOreType(new OreType("pentlandite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType POLLUCITE = registerOreType(new OreType("pollucite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType PYRITE = registerOreType(new OreType("pyrite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType PYROCHLORE = registerOreType(new OreType("pyrochlore", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType PYROLUSITE = registerOreType(new OreType("pyrolusite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType RUTILE = registerOreType(new OreType("rutile", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SCHEELITE = registerOreType(new OreType("scheelite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SMITHSONITE = registerOreType(new OreType("smithsonite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));//maybe
//    public static final OreType SPHALERITE = registerOreType(new OreType("sphalerite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SPODUMENE = registerOreType(new OreType("spodumene", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType STIBNITE = registerOreType(new OreType("stibnite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType SYLVITE = registerOreType(new OreType("sylvite", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType TETRAHEDRITE = registerOreType(new OreType("tetrahedrite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType TINCAL = registerOreType(new OreType("tincal", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType TRONA = registerOreType(new OreType("trona", JemsGeology.MOD_ID, false, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType URANINITE = registerOreType(new OreType("uraninite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));
//    public static final OreType WOLFRAMITE = registerOreType(new OreType("wolframite", JemsGeology.MOD_ID, true, SINGLE_DROP_NO_FORTUNE, SINGLE_DROP_NO_FORTUNE))));


    //////////////////////////////////
    //      ORE-LOOT BUILDERS       //
    //////////////////////////////////

    private static GeoLoot buildLapisOreLoot() {
        return new GeoLoot(RandomValueRange.of(4f, 9f), true, GeoLoot.FortuneFormula.ORE_DROPS, true, ()->Items.LAPIS_LAZULI);
    }

    private static GeoLoot buildRedstoneOreLoot() {
        return new GeoLoot(RandomValueRange.of(4f, 5f), true, GeoLoot.FortuneFormula.UNIFORM, true, ()->Items.REDSTONE);
    }

    private static GeoLoot buildEmeraldOreLoot() {
        return new GeoLoot(ConstantRange.of(1), true, GeoLoot.FortuneFormula.ORE_DROPS, true, ()->Items.EMERALD);
    }

    private static GeoLoot buildDiamondOreLoot() {
        return new GeoLoot(ConstantRange.of(1), false, GeoLoot.FortuneFormula.ORE_DROPS, true, ()->Items.DIAMOND);
    }

    private static GeoLoot buildFlintOreLoot() {
        return new GeoLoot(ConstantRange.of(1), true, GeoLoot.FortuneFormula.BINOMIAL, true, ()->Items.FLINT);
    }


    ///////////////////////////////////////
    //      GEO-PREDICATE BUILDERS       //
    ///////////////////////////////////////

    private static GeoPredicate buildFlintGeoPredicate() {
        return new GeoPredicate(EnumSet.of(GeoGroup.REGOLITH, GeoGroup.SEDIMENTARY), EnumSet.noneOf(GeoType.class), false);
    }

    //////////////////////////////////////
    //          REGISTRY METHODS        //
    //////////////////////////////////////

    private static OreType registerOreType(OreType oreType) {
        REGISTERED.add(oreType);
        GeoOreRegistryAPI.registerOreType(oreType);
        return oreType;
    }

    // Needed because SOMEONE *glares at the entire language of Java* doesn't want to class load when I grab this set.
    // How dare it try to be optimized and stuff. Ugh.
    // Oh, also, it needs to be down here for static init order. UUGH.
    public static Set<OreType> getModOreTypes() {
        return MOD_ORE_TYPES;
    }
}
