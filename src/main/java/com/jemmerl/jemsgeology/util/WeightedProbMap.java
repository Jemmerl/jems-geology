package com.jemmerl.jemsgeology.util;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

// Credit to phooji who gave this solution in the StackOverflow forum
// https://stackoverflow.com/questions/5212089/how-to-randomly-select-a-key-based-on-its-integer-value-in-a-map-with-respect-to
// ELTS IS SHORT FOR ELEMENTS!

public class WeightedProbMap<EltType> {
    private SortedMap<Integer, EltType> elts = new TreeMap<Integer, EltType>();
    private Random rand = new Random();
    private int sum = 0;

    // assume: each weight is > 0; there is at least one element;
    //         elements should not be repeated
    // ensure: this.elts maps cumulative weights to elements;
    //         this.sum is the total weight
    public WeightedProbMap(Iterable<Pair<Integer, EltType>> weights) {
        for (Pair<Integer, EltType> e : weights) {
            this.elts.put(this.sum, e.second);
            this.sum += e.first;
        }
    }

    // assume: this was initialized properly (cf. constructor req)
    // ensure: return an EltType with relative probability proportional
    //         to its associated weight
    public EltType nextElt() {
        int index = this.rand.nextInt(this.sum) + 1;
        SortedMap<Integer, EltType> view = this.elts.headMap(index);
        return view.get(view.lastKey());
    }

    public boolean isEmpty() {
        return elts.isEmpty();
    }

}


