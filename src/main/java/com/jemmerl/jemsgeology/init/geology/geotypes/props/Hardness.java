package com.jemmerl.jemsgeology.init.geology.geotypes.props;

public enum Hardness {
    FRAGILE, // 0 - Its basically dirt or sand, will just crumble
    SOFT, // 1 - Would not survive interaction (or in the case of coal, I don't wanna deal with it)
    HARD; // 2 - Its stone, hard stone.
}
