package de.kimminich.kata.tcg.strategy;

import java.util.OptionalInt;

import static java.util.Arrays.stream;

public class SmartStrategy implements Strategy {

    public OptionalInt nextCard(int mana, int[] availableCards) {
        int[] affordableCards = stream(availableCards).filter(cost -> cost <= mana).toArray();
        if (affordableCards.length > 0) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.empty();
        }
    }
}
