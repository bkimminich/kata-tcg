package de.kimminich.kata.tcg.strategy;

public interface Strategy {

    public abstract int nextCard(int... availableCards);

}
