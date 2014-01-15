package de.kimminich.kata.tcg;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomCardPicker {

    Random random = new Random();

    public Card pick(List<Card> cards) {
        return cards.get(random.nextInt(cards.size()));
    }
}
