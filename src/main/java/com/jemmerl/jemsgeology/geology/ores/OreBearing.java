package com.jemmerl.jemsgeology.geology.ores;

public enum OreBearing {

    ANY("any"),
    //KIMBERLITE("kimberlite"),
    NONE("none");

    private String name;

    OreBearing(String name) {
        this.name = name;
    }
}
