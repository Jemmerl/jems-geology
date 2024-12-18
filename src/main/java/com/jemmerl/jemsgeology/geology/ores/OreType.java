package com.jemmerl.jemsgeology.geology.ores;

import com.jemmerl.jemsgeology.JemsGeology;
import net.minecraft.item.Item;

import java.util.Locale;
import java.util.Objects;

public class OreType /*implements IStringSerializable*/ {

    private final String name;
    private final String source;

    private final boolean hasPoorOre;
    private final GeoLoot geoLoot;
    private final GeoLoot poorGeoLoot;

    public OreType(String name, String source) {
        this(name, source, false, GeoLoot.EMPTY, GeoLoot.EMPTY);
    }

    // Simplify commonly used (by me ofc) code, but also easy to see on the registration page if it has poor ore or not
    public static OreType of(String name, String source, boolean hasPoorOre, GeoLoot geoLootBoth) {
        if (hasPoorOre) {
            return new OreType(name, source, true, geoLootBoth, geoLootBoth);
        } else {
            return new OreType(name, source, false, geoLootBoth, GeoLoot.EMPTY);
        }
    }

    public OreType(String name, String source, boolean hasPoorOre, GeoLoot geoLoot, GeoLoot poorGeoLoot) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.source = source;

        this.hasPoorOre = hasPoorOre;
        this.geoLoot = geoLoot;
        this.poorGeoLoot = poorGeoLoot;

        if (!hasPoorOre && poorGeoLoot.hasPresetDrop()) {
            JemsGeology.LOGGER.warn("OreType \"" + name + "\" from source \"" + source + "\" has a pre-set poor ore item without enabling poor ore.");
        }
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public boolean hasPoorOre() {
        return hasPoorOre;
    }

    public GeoLoot getGeoLoot() {
        return geoLoot;
    }

    public GeoLoot getPoorGeoLoot() {
        return poorGeoLoot;
    }

    public boolean hasOre() {
        return !Objects.equals(name, "none");
    }
}
