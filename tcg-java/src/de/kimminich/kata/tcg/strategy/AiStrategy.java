package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AiStrategy implements Strategy {

    private Optional<Card> nextCard(int availableMana, List<Card> availableCards) {
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
            int comboDamage = combo.stream().mapToInt(Card::getManaCost).sum();
            if (comboDamage > maxDamage || (comboDamage == maxDamage && combo.size() > bestCombo.size())) {
                maxDamage = comboDamage;
                bestCombo = combo;
            }
        }

        return bestCombo.stream().max(Comparator.<Card>naturalOrder());
    }

    @Override
    public Move nextMove(int availableMana, List<Card> availableCards) {
        return new Move(nextCard(availableMana, availableCards), Action.DAMAGE);
    }

    private void collectMaxDamageCardCombo(List<Card> selectedCards, int availableMana, List<Card> availableCards) {
        for (Card card : availableCards) {
            List<Card> remainingCards = new ArrayList<>(availableCards);
            if (selectedCards.stream().mapToInt(Card::getManaCost).sum() + card.getManaCost() <= availableMana) {
                selectedCards.add(card);
                remainingCards.remove(card);
                collectMaxDamageCardCombo(selectedCards, availableMana - card.getManaCost(), remainingCards);
            }
        }
    }

}
