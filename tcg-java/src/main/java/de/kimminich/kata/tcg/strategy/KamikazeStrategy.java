package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * This strategy plays the highest affordable cards first and only for attacking. No healing is used regardless of the players health.
 */
public class KamikazeStrategy extends Strategy {

    @Override
    public Move nextMove(int availableMana, int currentHealth, List<Card> availableCards) {
        return new Move(highestCard(availableMana, availableCards), Action.DAMAGE);
    }

}
