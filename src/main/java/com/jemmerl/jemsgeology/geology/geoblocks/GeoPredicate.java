package com.jemmerl.jemsgeology.geology.geoblocks;

import java.util.EnumSet;

public class GeoPredicate {

    public static final GeoPredicate NONE = new GeoPredicate(false);
    public static final GeoPredicate ALL = new GeoPredicate(true);
    public static final GeoPredicate NOT_DESERT_SAND = new GeoPredicate(EnumSet.of(GeoGroup.DESERT_SAND), EnumSet.noneOf(GeoType.class), true);

    private final boolean inverted;
    private final EnumSet<GeoGroup> validGeoGroups;
    private final EnumSet<GeoType> validGeoTypes;

    private GeoPredicate(boolean inverted) {
        this.inverted = inverted;
        this.validGeoGroups = EnumSet.noneOf(GeoGroup.class);
        this.validGeoTypes = EnumSet.noneOf(GeoType.class);
    }

    public GeoPredicate(EnumSet<GeoGroup> validGeoGroups, EnumSet<GeoType> validGeoTypes, boolean inverted) {
        this.inverted = inverted;
        this.validGeoGroups = GeoGroup.simplifyGeoGroups(validGeoGroups);
        this.validGeoTypes = GeoGroup.simplifyGeoTypes(validGeoTypes, this.validGeoGroups);
    }

    public boolean generatesIn(GeoType geoType) {
        return ((geoType.getGeoGroup().isAny(validGeoGroups)) || validGeoTypes.contains(geoType)) != inverted;
    }
}
