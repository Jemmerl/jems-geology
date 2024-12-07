package com.jemmerl.jemsgeology.geology.ores;

public enum Grade {

    NONE("none"),
    POOR("lowgrade"),
    NORMAL("midgrade");
    //HIGH("highgrade");

    private final String name;

    Grade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasGrade() {
        return this != NONE;
    }
}
