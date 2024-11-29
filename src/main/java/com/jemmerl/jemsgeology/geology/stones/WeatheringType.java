package com.jemmerl.jemsgeology.geology.stones;

public enum WeatheringType {
    REGOLITH("regolith"),
    SANDY("sandy"),
    SOIL("soil"),
    CLAY("clay"),
    NONE("none");

    private String name;

    WeatheringType(String name) {
        this.name = name;
    }
}
