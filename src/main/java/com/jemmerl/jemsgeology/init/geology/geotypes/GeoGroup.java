package com.jemmerl.jemsgeology.init.geology.geotypes;

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

    // Empty
    EMPTY("empty", GeoGroupType.EMPTY),

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
    IGN_BOTH("ign_both", GeoGroupType.STONE),
    METAMORPHIC("metamorphic", GeoGroupType.STONE),

    // Regolith
    REGOLITH("regolith", GeoGroupType.REGOLITH), // Sub-groups: REGO_SOIL, REGO_GRAVEL, REGO_SANDY, REGO_CLAY
    REGO_SOIL("soil_rego", GeoGroupType.REGOLITH),
    REGO_GRAVEL("gravel_rego", GeoGroupType.REGOLITH),
    REGO_SANDY("sandy_rego", GeoGroupType.REGOLITH), // **Alluvial sand, not desert sand**
    REGO_CLAY("clay_rego", GeoGroupType.REGOLITH),
    DESERT_SAND("desert_sand", GeoGroupType.REGOLITH); // **Desert sand is unique, NOT a sub-group of anything!**

    private final String name;
    private final GeoGroupType geoGroupType;
    private EnumSet<GeoGroup> parents;

    GeoGroup(String name, GeoGroupType geoGroupType) {
        this.name = name;
        this.geoGroupType = geoGroupType;
    }

    // EnumSet requires the enum to be fully initialized, so they can't be used in the Enum constructor
    static {
        EMPTY.parents = EnumSet.noneOf(GeoGroup.class);

        // Stones
        STONES.parents = EnumSet.noneOf(GeoGroup.class);
        SEDIMENTARY.parents = EnumSet.of(STONES);
        HYDROTHERMAL.parents = EnumSet.of(STONES);
        SED_EVAPORITE.parents = EnumSet.of(SEDIMENTARY);
        SED_EVAP_HYDRO.parents = EnumSet.of(SED_EVAPORITE, HYDROTHERMAL); // All this jank is your fault >:(
        IGNEOUS.parents = EnumSet.of(STONES);
        IGN_EXTRUSIVE.parents = EnumSet.of(IGNEOUS);
        IGN_EJECTA.parents = EnumSet.of(IGNEOUS);
        IGN_INTRUSIVE.parents = EnumSet.of(IGNEOUS);
        IGN_BOTH.parents = EnumSet.of(IGN_EXTRUSIVE, IGN_INTRUSIVE); // You be thankful evap-hydro took the blame
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
        return (checkGroup == this) || this.parents.stream().anyMatch(parent -> parent.is(checkGroup));
    }

    public boolean isAny(EnumSet<GeoGroup> geoGroups) {
        return geoGroups.stream().anyMatch(this::is);
    }

    public EnumSet<GeoType> getAllGeoTypes() {
        return Arrays.stream(GeoType.values())
                .filter(geoType -> geoType.getGeoGroup().is(this))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(GeoType.class)));
    }

    public static EnumSet<GeoType> getAllGeoTypesIn(GeoGroup geoGroup) {
        return getAllGeoTypesIn(EnumSet.of(geoGroup));
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
        EMPTY("empty"),
        STONE("stone"),
        REGOLITH("regolith");

        private final String name;

        GeoGroupType(String name) {
            this.name = name;
        }
    }

}