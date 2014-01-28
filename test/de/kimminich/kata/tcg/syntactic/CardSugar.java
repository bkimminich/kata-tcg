package de.kimminich.kata.tcg.syntactic;

import de.kimminich.kata.tcg.Card;

import java.util.Optional;

public class CardSugar {

    public static Optional<Card> card(int card) {
        return Optional.of(new Card(card));
    }

    public static Optional<Card> noCard() {
        return Optional.empty();
    }

}
