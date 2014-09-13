package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * This strategy tries to find optimal card combinations in any given turn. Examples:
 * <ul>
 *     <li>From a hand of 2,3,4 given 5 mana it would play 2+3 (or 3+2) to maximize damage output</li>
 *     <li>From a hand of 1,1,1,3 given 3 mana it would play 1+1+1 to minimize the hand size and avoid card loss from the "Overload" rule</li>
 * </ul>
 *
 * This strategy switches into healing with the highest possible cards when the players health falls below 10.
 */
public class AiStrategy extends Strategy {

    @Override
    public Move nextMove(int availableMana, int currentHealth, List<Card> availableCards) {
        if (currentHealth < 10) {
            return new Move(highestCard(availableMana, availableCards), Action.HEALING);
        } else {
            return new Move(bestCard(availableMana, availableCards), Action.DAMAGE);
        }
    }

    private Optional<Card> bestCard(int availableMana, List<Card> availableCards) {
        List<List<Card>> cardCombos = new ArrayList<>();
        List<Card> remainingCards = new ArrayList<>(availableCards);
        remainingCards.sort(Comparator.<Card>reverseOrder()); // highest mana costs first
        while (!remainingCards.isEmpty()) {
            List<Card> selectedCards = new ArrayList<>();
            collectMaxDamageCardCombo(selectedCards, availableMana, remainingCards);
            cardCombos.add(selectedCards);
            remainingCards.remove(0);
        }

        List<Card> bestCombo = new ArrayList<>();
        int maxDamage = 0;
        for (List<Card> combo : cardCombos) {
            int comboDamage = combo.stream().mapToInt(Card::getValue).sum();
            if (comboDamage > maxDamage || (comboDamage == maxDamage && combo.size() > bestCombo.size())) {
                maxDamage = comboDamage;
                bestCombo = combo;
            }
        }

        return bestCombo.stream().max(Comparator.<Card>naturalOrder());
    }

    private void collectMaxDamageCardCombo(List<Card> selectedCards, int availableMana, List<Card> availableCards) {
        for (Card card : availableCards) {
            List<Card> remainingCards = new ArrayList<>(availableCards);
            if (selectedCards.stream().mapToInt(Card::getValue).sum() + card.getValue() <= availableMana) {
                selectedCards.add(card);
                remainingCards.remove(card);
                collectMaxDamageCardCombo(selectedCards, availableMana - card.getValue(), remainingCards);
            }
        }
    }

}
