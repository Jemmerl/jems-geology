package com.jemmerl.jemsgeology.geology.ores;

import java.util.Locale;
import java.util.Objects;

public class OreType /*implements IStringSerializable*/ {

    private final String name;
    private final String source;
    private final boolean hasPoorOre;

    public OreType(String name, String source, boolean hasPoorOre) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.source = source;
        this.hasPoorOre = hasPoorOre;
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
}
