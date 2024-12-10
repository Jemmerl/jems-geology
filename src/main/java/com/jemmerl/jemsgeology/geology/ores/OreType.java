package com.jemmerl.jemsgeology.geology.ores;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.init.geologyinit.ModGeoOres;
import net.minecraft.item.Item;

import java.util.Locale;
import java.util.Objects;

public class OreType /*implements IStringSerializable*/ {

    private final String name;
    private final String source;

    private final boolean hasPoorOre;
    private final OreLoot oreLoot;
    private final OreLoot poorOreLoot;

    public OreType(String name, String source) {
        this(name, source, false, ModGeoOres.EMPTY, ModGeoOres.EMPTY);
    }

    // Simplify commonly used (by me ofc) code, but also easy to see on the registration page if it has poor ore or not
    public static OreType of(String name, String source, boolean hasPoorOre, OreLoot oreLootBoth) {
        if (hasPoorOre) {
            return new OreType(name, source, true, oreLootBoth, oreLootBoth);
        } else {
            return new OreType(name, source, false, oreLootBoth, ModGeoOres.EMPTY);
        }
    }

    // crimmas gift ideas
    // t goggles 100, ski pants 32
    // b first aid kit. good size but compact- one i use for rockhounding?
    // g thermonuclear warheads (too much?)

    public OreType(String name, String source, boolean hasPoorOre, OreLoot oreLoot, OreLoot poorOreLoot) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.source = source;

        this.hasPoorOre = hasPoorOre;
        this.oreLoot = oreLoot;
        this.poorOreLoot = poorOreLoot;

        if (!hasPoorOre && poorOreLoot.hasPresetOre()) {
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

    public boolean hasOre() {
        return !Objects.equals(name, "none");
    }

    public boolean hasPresetOre() {
        return oreLoot.hasPresetOre();
    }

    public boolean hasPresetPoorOre() {
        return poorOreLoot.hasPresetOre();
    }

    public Item getPresetOreItem() {
        return oreLoot.getPresetOreItem();
    }

    public Item getPresetPoorOreItem() {
        return poorOreLoot.getPresetOreItem();
    }
}
