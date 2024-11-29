package com.jemmerl.jemsgeology.util;

// Credit to phooji who gave this solution in the StackOverflow forum
// https://stackoverflow.com/questions/5212089/how-to-randomly-select-a-key-based-on-its-integer-value-in-a-map-with-respect-to

public class Pair<X, Y> {
    public Pair(X x, Y y) {
        first = x;
        second = y;
    }

    X first;
    Y second;
}
