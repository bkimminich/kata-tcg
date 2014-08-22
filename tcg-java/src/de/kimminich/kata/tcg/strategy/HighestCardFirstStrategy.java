package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HighestCardFirstStrategy implements Strategy {

    private Optional<Card> nextCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getManaCost() <= availableMana).max(Comparator.<Card>naturalOrder());
    }

    @Override
    public Move nextMove(int availableMana, int currentHealth, List<Card> availableCards) {
        return new Move(nextCard(availableMana, availableCards), Action.DAMAGE);
    }

}
