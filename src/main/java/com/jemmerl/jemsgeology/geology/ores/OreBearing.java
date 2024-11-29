package com.jemmerl.jemsgeology.geology.ores;

public enum OreBearing {

    ANY("any"),
    DIAMONDIFEROUS("diamondiferous"),
    NONE("none");

    private String name;

    OreBearing(String name) {
        this.name = name;
    }
}
