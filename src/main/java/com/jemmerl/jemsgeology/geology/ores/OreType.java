package com.jemmerl.jemsgeology.geology.ores;

import net.minecraft.util.IStringSerializable;

public class OreType /*implements IStringSerializable*/ {

    private String name;
    private String source;
    private boolean hasPoorGrade;

    public OreType(String name, String source, boolean hasPoorGrade) {
        this.name = name;
        this.source = source;
        this.hasPoorGrade = hasPoorGrade;
    }

    public String getName() {
        return this.name;
    }

    public String getSource() {
        return this.source;
    }

    public boolean hasPoorGrade() {
        return this.hasPoorGrade;
    }

}
