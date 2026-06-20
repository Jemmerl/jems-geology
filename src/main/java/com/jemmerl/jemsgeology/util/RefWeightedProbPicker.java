package com.jemmerl.jemsgeology.util;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RefWeightedProbPicker<K> {

    private final Reference2IntOpenHashMap<K> WEIGHTED_PROB_MAP;
    private final int weightTot;
    private final K defaultReturn;
    
    public RefWeightedProbPicker(Map<K, Integer> probMap, K defaultReturn) {
        WEIGHTED_PROB_MAP = new Reference2IntOpenHashMap<>();
        WEIGHTED_PROB_MAP.putAll(probMap);
        weightTot = WEIGHTED_PROB_MAP.values().stream().mapToInt(Integer::intValue).sum();
        this.defaultReturn = defaultReturn;
    }

    public K pickRandom(Random random) {
        AtomicInteger pickWeight = new AtomicInteger(random.nextInt(weightTot));
        return WEIGHTED_PROB_MAP.reference2IntEntrySet().stream().filter(entry -> {
            pickWeight.getAndAdd(-entry.getIntValue());
            return (pickWeight.get() < 0);
        }).map(Reference2IntMap.Entry::getKey).findFirst().orElse(defaultReturn);
    }
}
