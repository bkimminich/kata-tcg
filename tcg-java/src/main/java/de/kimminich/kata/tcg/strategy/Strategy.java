package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class Strategy {

    public abstract Move nextMove(int availableMana, int currentHealth, List<Card> availableCards);

    protected Optional<Card> highestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getValue() <= availableMana).max(Comparator.<Card>naturalOrder());
    }

    protected Optional<Card> lowestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getValue() <= availableMana).min(Comparator.<Card>naturalOrder());
    }
}

