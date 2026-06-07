package com.jemmerl.jemsgeology.world.geobuilding.georegions.regions;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoGroup;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoProperties;
import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.world.geobuilding.UnbakedGeo;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// Consists of a single GeoType, preferably intrusive igneous.
// For my purposes, this represents an older, large batholith region
public class SingleBasementRegion extends AbstractBasementRegion {
    // TODO ore placement options
    // TODO this will eventually be data-created, including map / ores

    private static final Reference2IntOpenHashMap<GeoType> SINGLE_BASEMENT_LIST = buildMap();
    private static Reference2IntOpenHashMap<GeoType> buildMap() {
        Reference2IntOpenHashMap<GeoType> map = new Reference2IntOpenHashMap<>();
        map.put(GeoType.DIORITE, 5);
        map.put(GeoType.GRANODIORITE, 8);
        map.put(GeoType.PINK_GRANITE, 23);
        map.put(GeoType.GRAY_GRANITE, 23);
        map.put(GeoType.WHITE_GRANITE, 23);
        map.put(GeoType.SYENITE, 7);
        map.put(GeoType.GABBRO, 1);
        map.put(GeoType.ANORTHOSITE, 10);
        return map;
    }

    private GeoType baseRock;

    public SingleBasementRegion(int seed) {
        this(seed, false);
    }

    public SingleBasementRegion(int seed, boolean useRandomSeed) {
        super(seed, useRandomSeed);
        this.baseRock = pickRandomRock(new Random(seed + 816622703).nextInt(100));
    }

    @Override
    public UnbakedGeo getUnbakedGeo(int x, int y, int z) {
        return new UnbakedGeo(baseRock);
    }

    private static GeoType pickRandomRock(int initPickWeight) {
        AtomicInteger pickWeight = new AtomicInteger(initPickWeight);
        return SINGLE_BASEMENT_LIST.reference2IntEntrySet().stream().filter(geoTypeEntry -> {
            pickWeight.getAndAdd(-geoTypeEntry.getIntValue());
            return (pickWeight.get() < 0);
        }).map(Reference2IntMap.Entry::getKey).findFirst().orElse(GeoType.EMPTY);
    }
}

/*
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyClass {
  public static void main(String args[]) {
    final HashMap<GeoType, Integer> cumulate = new HashMap<>();

    int X = 10000000;
    Random random = new Random(10);
    for (int i=0; i<X; i++) {
        cumulate.merge(pickRandomRock(random.nextInt(100)), 1, Integer::sum);
    }


    cumulate.forEach((key, value) -> System.out.println(key + ": " + (value/(float)X * 100)));
  }

  public enum GeoType {
      EMPTY,
      DIORITE,
      GRANODIORITE,
      PINK_GRANITE,
      GRAY_GRANITE,
      WHITE_GRANITE,
      SYENITE,
      GABBRO,
      ANORTHOSITE,
      DIABASE
  }

  private static final HashMap<GeoType, Integer> SINGLE_BASEMENT_LIST = buildMap();
    private static HashMap<GeoType, Integer> buildMap() {
        HashMap<GeoType, Integer> map = new HashMap<>();
        map.put(GeoType.GRANODIORITE, 8);
        map.put(GeoType.DIORITE, 5);
        map.put(GeoType.PINK_GRANITE, 23);
        map.put(GeoType.GRAY_GRANITE, 23);
        map.put(GeoType.WHITE_GRANITE, 23);
        map.put(GeoType.SYENITE, 7);
        map.put(GeoType.GABBRO, 1);
        map.put(GeoType.ANORTHOSITE, 8);
        map.put(GeoType.DIABASE, 2);
        return map;
    }

    private static GeoType pickRandomRock(int initPickWeight) {
        AtomicInteger pickWeight = new AtomicInteger(initPickWeight);
        return SINGLE_BASEMENT_LIST.entrySet().stream().filter(geoTypeEntry -> {
            pickWeight.getAndAdd(-geoTypeEntry.getValue());
            return (pickWeight.get() < 0);
        }).map(Map.Entry::getKey).findFirst().orElse(GeoType.EMPTY);
    }
}

GRANODIORITE: 899
GRAY_GRANITE: 2284
DIABASE: 203
WHITE_GRANITE: 2303
ANORTHOSITE: 812
PINK_GRANITE: 2325
DIORITE: 462
GABBRO: 111
SYENITE: 601

 */