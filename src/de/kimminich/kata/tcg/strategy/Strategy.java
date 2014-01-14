package de.kimminich.kata.tcg.strategy;

import java.util.OptionalInt;

public interface Strategy {

    public abstract OptionalInt nextCard(int mana, int[] availableCards);

}
