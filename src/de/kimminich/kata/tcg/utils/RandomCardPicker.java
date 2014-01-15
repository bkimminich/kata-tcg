package de.kimminich.kata.tcg.utils;

import de.kimminich.kata.tcg.Card;

import java.util.List;
import java.util.Random;

public class RandomCardPicker {

    Random random = new Random();

    public Card pick(List<Card> cards) {
        return cards.get(random.nextInt(cards.size()));
    }
}
