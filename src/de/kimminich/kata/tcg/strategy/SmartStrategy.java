package de.kimminich.kata.tcg.strategy;

import java.util.OptionalInt;

import static java.util.Arrays.stream;

public class SmartStrategy implements Strategy {

    public OptionalInt nextCard(int mana, int[] availableCards) {
        int[] affordableCards = stream(availableCards).filter(cost -> cost <= mana).sorted().toArray();
        int maxDamage = 0;
        for (int i = affordableCards.length - 1; i >= 0; i--) {
            int card = affordableCards[i];
            for (int comboCard : affordableCards) {
                if (card + comboCard > maxDamage && card + comboCard <= mana) {
                    maxDamage = card + comboCard;
                    if (maxDamage == mana) {
                        return OptionalInt.of(card);
                    }
                }
            }
        }
        return stream(affordableCards).max();
    }
}
