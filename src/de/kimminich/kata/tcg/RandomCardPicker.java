package de.kimminich.kata.tcg;

import java.util.Arrays;
import java.util.Random;

public class RandomCardPicker implements CardPicker {

    Random random = new Random();

    @Override
    public int pick(int[] cards) {
        if (Arrays.stream(cards).sum() > 0) {
            int i = random.nextInt(cards.length);
            return cards[i] > 0 ? i : pick(cards);
        } else {
            throw new IllegalArgumentException("Cannot pick card from empty array of cards!");
        }
    }
}
