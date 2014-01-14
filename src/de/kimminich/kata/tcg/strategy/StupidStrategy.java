package de.kimminich.kata.tcg.strategy;

import java.util.OptionalInt;

import static java.util.Arrays.*;

public class StupidStrategy implements Strategy {

    public OptionalInt nextCard(int mana, int[] availableCards) {
        return stream(availableCards).filter(cost -> cost <= mana).min();
    }
}
