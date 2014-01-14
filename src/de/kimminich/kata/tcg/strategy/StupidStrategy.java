package de.kimminich.kata.tcg.strategy;

import static java.util.Arrays.*;

public class StupidStrategy implements Strategy {

    public int nextCard(int... availableCards) {
        return stream(availableCards).min().getAsInt();
    }
}
