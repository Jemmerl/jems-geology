package com.jemmerl.jemsgeology.capabilities.world.watertable;

// TODO low priority.
public enum WTQuality {
    FRESH(0),
    BRACKISH(1),
    SALTY(2),
    POLLUTED_FRESH(3),
    POLLUTED_BRACKISH(4),
    POLLUTED_SALTY(5);

    private final int id;

    WTQuality(int id) {
        this.id = id;
    }

    public int toID() {
        return id;
    }

    public WTQuality toQuality(int id) {
        switch(id) {
            case 5:
                return POLLUTED_SALTY;
            case 4:
                return POLLUTED_BRACKISH;
            case 3:
                return POLLUTED_FRESH;
            case 2:
                return SALTY;
            case 1:
                return BRACKISH;
            case 0:
            default:
                return FRESH;
        }
    }
}








// First implement fresh/salty, then work on pollution stuff later
// Water quality depends on depth when near the ocean. Dig too deep -> get salt water
// How deep depends on the Ghyben–Herzberg ratio, as well as how much is being pumped out of the water table


/*

"Ghyben–Herzberg ratio"
h = height of fresh water table above sea level
z = height of fresh water table below sea level
z ~= 40h
*** Obv need to scale this down for minecraft purposes.

----------\
    h ^     \
F     V       \
R - - - - - - - - - - Sea level - - - - - - - - -
E     ^       |                     |
S     z       / <- "interface"      /
H     |      /                     /
      V    /    BRACKISH WATER   /  SALT WATER
--------/                      /
                            /
______________________________________________

 */
