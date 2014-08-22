package de.kimminich.kata.tcg.syntactic;

import de.kimminich.kata.tcg.Card;

import java.util.List;

public class StrategySugar {

    public static int withMana(int mana) {
        return mana;
    }

    public static int andHealth(int health) {
        return health;
    }

    public static List<Card> fromCards(Integer... cards) {
        return Card.list(cards);
    }

}
