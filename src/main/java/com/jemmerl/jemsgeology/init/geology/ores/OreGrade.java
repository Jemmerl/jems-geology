package com.jemmerl.jemsgeology.init.geology.ores;

public enum OreGrade {

    NONE("none", ""),
    POOR("lowgrade", "poor_"),
    NORMAL("midgrade", "");
    //HIGH("highgrade", "rich_");

    private final String name;
    private final String assetName;

    OreGrade(String name, String assetName) {
        this.name = name;
        this.assetName = assetName;
    }

    public String getName() {
        return name;
    }

    public String getAssetName() {
        return assetName;
    }

    public boolean hasGrade() {
        return this != NONE;
    }
}
