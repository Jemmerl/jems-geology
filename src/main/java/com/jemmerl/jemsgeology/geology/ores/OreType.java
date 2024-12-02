package com.jemmerl.jemsgeology.geology.ores;

public class OreType /*implements IStringSerializable*/ {

    private String name;
    private String source;
    private boolean hasPoorOre;

    public OreType(String name, String source, boolean hasPoorOre) {
        this.name = name;
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

}
