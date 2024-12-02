package com.jemmerl.jemsgeology.geology.ores;

public enum Grade {

    NONE("none"),
    POOR("poor"),
    NORMAL("normal");
    //HIGH("high");

    private final String name;

    Grade(String name) {
        this.name = name;
    }

}
