package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SmartStrategy implements Strategy {

    public Optional<Card> nextCard(int mana, List<Card> availableCards) {
        int[] affordableCards = availableCards.stream().filter(card -> card.getManaCost() <= mana).mapToInt(Card::getManaCost).toArray();
        int maxDamage = 0;
        for (int i = affordableCards.length - 1; i >= 0; i--) {
            int card = affordableCards[i];
            for (int comboCard : affordableCards) {
                if (card + comboCard > maxDamage && card + comboCard <= mana) {
                    maxDamage = card + comboCard;
                    if (maxDamage == mana) {
                        return Optional.of(new Card(card));
                    }
                }
            }
        }
        return availableCards.stream().filter(card -> card.getManaCost() <= mana).max(Comparator.<Card>naturalOrder());
    }

}
