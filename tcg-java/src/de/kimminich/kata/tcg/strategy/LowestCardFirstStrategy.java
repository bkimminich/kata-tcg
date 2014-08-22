package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LowestCardFirstStrategy implements Strategy {

    @Override
    public Optional<Card> nextCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getManaCost() <= availableMana).min(Comparator.<Card>naturalOrder());
    }

    @Override
    public Move nextMove(int i, List<Card> cards) {
        throw new UnsupportedOperationException();
    }

}
