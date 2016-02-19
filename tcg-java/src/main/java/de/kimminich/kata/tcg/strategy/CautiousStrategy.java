package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.List;

/**
 * This strategy plays the highest affordable cards for attacking. It switches into healing with the lowest possible cards when the players health falls below 20.
 */
public class CautiousStrategy extends Strategy {

    @Override
    public Move nextMove(int availableMana, int currentHealth, List<Card> availableCards) {
        if (currentHealth < 20) {
            return new Move(lowestCard(availableMana, availableCards), Action.HEALING);
        } else {
            return new Move(highestCard(availableMana, availableCards), Action.DAMAGE);
        }
    }

}
