package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CautiousStrategy implements Strategy {

    private Optional<Card> highestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getValue() <= availableMana).max(Comparator.<Card>naturalOrder());
    }

    private Optional<Card> lowestCard(int availableMana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getValue() <= availableMana).min(Comparator.<Card>naturalOrder());
    }

    @Override
    public Move nextMove(int availableMana, int currentHealth, List<Card> availableCards) {
        if (currentHealth > 20) {
            return new Move(highestCard(availableMana, availableCards), Action.DAMAGE);
        } else {
            return new Move(lowestCard(availableMana, availableCards), Action.HEALING);
        }
    }

}
