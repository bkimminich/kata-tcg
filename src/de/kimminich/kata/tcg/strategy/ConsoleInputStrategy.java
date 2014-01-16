package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.List;
import java.util.Optional;

public class ConsoleInputStrategy implements Strategy {
    @Override
    public Optional<Card> nextCard(int mana, List<Card> availableCards) {
        return null;
    }
}
