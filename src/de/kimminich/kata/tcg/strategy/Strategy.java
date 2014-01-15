package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface Strategy {

    public abstract Optional<Card> nextCard(int mana, List<Card> availableCards);

}

