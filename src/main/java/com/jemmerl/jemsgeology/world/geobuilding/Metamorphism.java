package com.jemmerl.jemsgeology.world.geobuilding;

import com.google.common.collect.Sets;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.util.Pair;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

public class Metamorphism {

    public static Pair<Temp, Pressure> getRegionalMetamorphism(int seed) {
        return new Pair<>(Temp.T000C, Pressure.P000MPA);
    }

    public static final Set<Temp> REGIONAL_META_TEMPS = Sets.immutableEnumSet(EnumSet.complementOf(EnumSet.of(Temp.T000C, Temp.T100C)));
    public static final Set<Pressure> REGIONAL_META_PRESSURE = Sets.immutableEnumSet(EnumSet.complementOf(EnumSet.of(Pressure.P000MPA)));

    public final Function<Temp, Pressure> barrovianPvT = t -> Pressure.valueOf(((1000*Math.round(t.getTemp()) - 1000) / 7 ) + 200);

    // todo note lower pressures/temps needed to merge regional and contact meta
    public enum Temp {
        T000C(0),
        T100C(100),
        T200C(200),
        T300C(300),
        T400C(400),
        T500C(500),
        T600C(600),
        T700C(700),
        T800C(800);

        private final int temp;
        Temp(int temp) {
            this.temp = temp;
        }

        public int getTemp() { return temp; }
    }

    public enum Pressure {
        P000MPA(0),
        P200MPA(200),
        P400MPA(400),
        P600MPA(600),
        P800MPA(800),
        P1000MPA(1000),
        P1200MPA(1200);

        private final int press;
        Pressure(int press) {
            this.press = press;
        }

        public int getPress() { return press; }
        public static Pressure valueOf(int value) {
            int v = clean(value);
            return Arrays.stream(values())
                    .filter(pressure -> pressure.press == v)
                    .findFirst().orElse(P000MPA);
        }

        public static int clean(int press) {
            return Math.round(Math.max(0, Math.min(1200, press))/200) * 200;
        }
    }

    // todo is this useful?
    public enum MetaGrade {
        VERYLOW,
        LOW,
        INTERMEDIATE,
        HIGH
    }

    // TODO set this aside for now, major rabbit hole/incompelte
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
        ADV_ARGILLIC,
        SERICITIC, // Low-Sulfidation --> How to indicate sericite???
        GREISEN
        // alkalai metasomatisn
    }
}



