package com.jemmerl.jemsgeology.geology.geoblocks;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

// JEM'S NOTES:
// Many rocks can and do fit into multiple categorizations. It all gets a little messy, so also does this class...
// E.g. calcite can be sedimentary, evaporite, or "hydrothermal",
// and igneous tuff are also well-arguably also described as sedimentary.
//
// DESERT_SAND isn't in the REGOLITH supergroup even though it is one, as basically no ores are ever present in it.
// Whenever it is needed, it can be added manually.

//TODO add carbonate??s

// Adapted from Alexander Radzin's article https://dzone.com/articles/enum-tricks-hierarchical-data
// These are used mainly for strata region building or ore registration.
public enum GeoGroup {
    // Stones
    STONES("stones", GeoGroupType.STONE), // Sub-groups: SEDIMENTARY, HYDROTHERMAL, IGNEOUS, METAMORPHIC
    SEDIMENTARY("sedimentary", GeoGroupType.STONE), // Sub-groups: SED_EVAPORITE, SED_EVAP_HYDRO
    HYDROTHERMAL("hydrothermal", GeoGroupType.STONE), // Sub-groups: SED_EVAP_HYDRO
    SED_EVAPORITE("evaporite", GeoGroupType.STONE),
    SED_EVAP_HYDRO("sed_evap_hydro", GeoGroupType.STONE), // **Both a sedimentary evaporite and a hydrothermal!**
    IGNEOUS("igneous", GeoGroupType.STONE), // Sub-groups: IGN_INTRUSIVE, IGN_EXTRUSIVE, IGN_EJECTA
    IGN_EXTRUSIVE("ign_extrusive", GeoGroupType.STONE),
    IGN_EJECTA("ign_ejecta", GeoGroupType.STONE),
    IGN_INTRUSIVE("ign_intrusive", GeoGroupType.STONE),
    METAMORPHIC("metamorphic", GeoGroupType.STONE),

    // Regolith
    REGOLITH("regolith", GeoGroupType.REGOLITH), // Sub-groups: REGO_SOIL, REGO_GRAVEL, REGO_SANDY, REGO_CLAY
    REGO_SOIL("soil_rego", GeoGroupType.REGOLITH),
    REGO_GRAVEL("gravel_rego", GeoGroupType.REGOLITH),
    REGO_SANDY("sandy_rego", GeoGroupType.REGOLITH), // **Alluvial sand, not desert sand**
    REGO_CLAY("clay_rego", GeoGroupType.REGOLITH),
    DESERT_SAND("desert_sand", GeoGroupType.REGOLITH), // **Desert sand is unique, NOT a sub-group of anything!**
    ;

    private final String name;
    private final GeoGroupType geoGroupType;
    private EnumSet<GeoGroup> parents;

    GeoGroup(String name, GeoGroupType geoGroupType) {
        this.name = name;
        this.geoGroupType = geoGroupType;
    }

    // EnumSet requires the enum to be fully initialized, so they can't be used in the Enum constructor
    static {
        STONES.parents = EnumSet.noneOf(GeoGroup.class);
        SEDIMENTARY.parents = EnumSet.of(STONES);
        HYDROTHERMAL.parents = EnumSet.of(STONES);
        SED_EVAPORITE.parents = EnumSet.of(SEDIMENTARY);
        SED_EVAP_HYDRO.parents = EnumSet.of(SED_EVAPORITE, HYDROTHERMAL); // All this jank is your fault >:(
        IGNEOUS.parents = EnumSet.of(STONES);
        IGN_EXTRUSIVE.parents = EnumSet.of(IGNEOUS);
        IGN_EJECTA.parents = EnumSet.of(IGNEOUS);
        IGN_INTRUSIVE.parents = EnumSet.of(IGNEOUS);
        METAMORPHIC.parents = EnumSet.of(STONES);

        // Regolith
        REGOLITH.parents = EnumSet.noneOf(GeoGroup.class);
        REGO_SOIL.parents = EnumSet.of(REGOLITH);
        REGO_GRAVEL.parents = EnumSet.of(REGOLITH);
        REGO_SANDY.parents = EnumSet.of(REGOLITH);
        REGO_CLAY.parents = EnumSet.of(REGOLITH);
        DESERT_SAND.parents = EnumSet.noneOf(GeoGroup.class);
    }

    public String getName() { return name; }
    public GeoGroupType getGeoGroupType() { return geoGroupType; }
    public ImmutableSet<GeoGroup> getParents() { return Sets.immutableEnumSet(parents); }

    public boolean isRegolith() {
        return this.geoGroupType.equals(GeoGroupType.REGOLITH);
    }

    public boolean is(GeoGroup checkGroup) {
        if (checkGroup == this) {
            return true;
        }
        return this.parents.stream().anyMatch(parent -> parent.is(checkGroup));
    }

    public boolean isAny(EnumSet<GeoGroup> geoGroups) {
        return geoGroups.stream().anyMatch(this::is);
    }

    public EnumSet<GeoType> getAllGeoTypes() {
        return Arrays.stream(GeoType.values())
                .filter(geoType -> geoType.getGeoGroup().is(this))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(GeoType.class)));
    }

    public static EnumSet<GeoType> getAllGeoTypesIn(EnumSet<GeoGroup> geoGroups) {
        return Arrays.stream(GeoType.values())
                .filter(geoType -> geoType.getGeoGroup().isAny(geoGroups))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(GeoType.class)));
    }

    // "Relatively" slow to run, but should not be called outside of initialization anyway. Its goal is to ensure
    // the simplest (fastest) list is being used later, such as during generation/gameplay.
    public static EnumSet<GeoGroup> simplifyGeoGroups(final EnumSet<GeoGroup> geoGroups) {
        EnumSet<GeoGroup> simplifiedGeoGroups = EnumSet.copyOf(geoGroups);
        simplifiedGeoGroups.removeIf(geoGroup -> geoGroup.parents.stream().anyMatch(parent -> parent.isAny(geoGroups)));
        return simplifiedGeoGroups;
    }

    public static EnumSet<GeoType> simplifyGeoTypes(final EnumSet<GeoType> geoTypes, final EnumSet<GeoGroup> geoGroups) {
        EnumSet<GeoType> simplifiedGeoTypes = EnumSet.copyOf(geoTypes);
        simplifiedGeoTypes.removeIf(geoType -> geoType.getGeoGroup().isAny(geoGroups));
        return simplifiedGeoTypes;
    }


    public enum GeoGroupType {
        STONE("stone"),
        REGOLITH("regolith");

        private final String name;

        GeoGroupType(String name) {
            this.name = name;
        }
    }

}

//        // is ONE instance of TWO
//        TEST(GeoGroupMOD.STONES, GeoGroupMOD.SEDIMENTARY, false);
//        TEST(GeoGroupMOD.SEDIMENTARY, GeoGroupMOD.STONES, true);
//        TEST(GeoGroupMOD.STONES, GeoGroupMOD.STONES, true);
//        TEST(GeoGroupMOD.SED_EVAP_HYDRO, GeoGroupMOD.SEDIMENTARY, true);
//        TEST(GeoGroupMOD.SED_EVAP_HYDRO, GeoGroupMOD.HYDROTHERMAL, true);
//        TEST(GeoGroupMOD.SED_EVAP_HYDRO, GeoGroupMOD.SED_EVAPORITE, true);
//        TEST(GeoGroupMOD.SED_EVAPORITE, GeoGroupMOD.SED_EVAP_HYDRO, false);
//        TEST(GeoGroupMOD.SED_EVAP_HYDRO, GeoGroupMOD.SED_EVAP_HYDRO, true);
//        TEST(GeoGroupMOD.HYDROTHERMAL, GeoGroupMOD.SED_EVAP_HYDRO, false);
//        TEST(GeoGroupMOD.REGOLITH, GeoGroupMOD.STONES, false);
//        TEST(GeoGroupMOD.REGOLITH, GeoGroupMOD.REGOLITH, true);
//        TEST(GeoGroupMOD.IGN_EJECTA, GeoGroupMOD.IGN_EXTRUSIVE, false);
//        TEST(GeoGroupMOD.IGNEOUS, GeoGroupMOD.IGN_EXTRUSIVE, false);
//        TEST(GeoGroupMOD.IGN_EXTRUSIVE, GeoGroupMOD.IGNEOUS, true);
//        TEST(GeoGroupMOD.IGN_EJECTA, GeoGroupMOD.IGNEOUS, true);
//        TEST(GeoGroupMOD.REGO_SOIL, GeoGroupMOD.REGO_SANDY, false);
//        TEST(GeoGroupMOD.REGO_SANDY, GeoGroupMOD.REGOLITH, true);
//        TEST(GeoGroupMOD.REGOLITH, GeoGroupMOD.REGO_SANDY, false);
//        TEST(GeoGroupMOD.DESERT_SAND, GeoGroupMOD.REGOLITH, false);
//        TEST(GeoGroupMOD.REGOLITH, GeoGroupMOD.DESERT_SAND, false);
//        TEST(GeoGroupMOD.IGNEOUS, GeoGroupMOD.METAMORPHIC, false);
//        TEST(GeoGroupMOD.METAMORPHIC, GeoGroupMOD.STONES, true);

//    private void TEST(GeoGroupMOD group, GeoGroupMOD checkIs, boolean expected) {
//        System.out.println("-------------------------");
//        System.out.println("Is " + group.getName() + " instance of " + checkIs.getName() + "?");
//        boolean result = group.is(checkIs);
//        System.out.println(result);
//        if (result == expected) {
//            System.out.println("PASSED!");
//        } else {
//            System.out.println("FAILED!!!!!!!!!!!");
//        }
//    }