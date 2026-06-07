package com.jemmerl.jemsgeology.world.geobuilding;

import com.google.common.collect.Sets;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.util.Pair;

import java.util.EnumSet;
import java.util.Set;

public class Metamorphism {

    public static Pair<Temp, Pressure> getRegionalMetamorphism(int seed) {
        return new Pair<>(Temp.T000C, Pressure.P000MPA);
    }

    public static final Set<Temp> REGIONAL_META_TEMPS = Sets.immutableEnumSet(EnumSet.complementOf(EnumSet.of(Temp.T000C)));
    public static final Set<Pressure> REGIONAL_META_PRESSURE = Sets.immutableEnumSet(EnumSet.complementOf(EnumSet.of(Pressure.P000MPA)));

    // todo note lower pressures/temps needed to merge regional and contact meta
    public enum Temp {
        T000C,
        T100C,
        T200C,
        T300C,
        T400C,
        T500C,
        T600C,
        T700C,
        T800C;
    }

    public enum Pressure {
        P000MPA,
        P200MPA,
        P400MPA,
        P600MPA,
        P800MPA,
        P1000MPA,
        P1200MPA;
    }

    // todo is this useful?
    public enum MetaGrade {
        VERYLOW,
        LOW,
        INTERMEDIATE,
        HIGH
    }

    // TODO simulate metasomatism distance using porosity of rocks?
    // Have a noise region around the intrusion, but the metasomic noise envelope is scaled differently depending on porosity
    // IMPLEMENTATION: assign an int "value" to the metasomatism that decreases away from the ign body, but is applied
    // assuming max porosity. If less porous, then subtract by a certain factor and only apply changes if >0.
    // more extreme changes could apply at higher values?
    // ISSUE: how to deal with less porous rock next to more porous rock?
    public enum MetaSomatism {
        NONE,
        DEHYDRATE, //use?
        CARBONIC,
        SILICIC,
        SODIC,
        POTASSIC,
        PROPYLITIC,
        PHYLLIC,
        ARGILLIC, // High-Sulfidation
        SERICITIC, // Low-Sulfidation --> How to indicate sericite???

    }
}



