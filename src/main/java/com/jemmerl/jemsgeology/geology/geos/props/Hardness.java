package com.jemmerl.jemsgeology.geology.geos.props;

public enum Hardness {
    REGOLITH, // 0 - Its stone, hard stone.
    SOFT, // 1 - Would not survive interaction (or in the case of coal, I don't wanna deal with it)
    HARD; // 2 - Its basically dirt or sand, will just crumble
}
