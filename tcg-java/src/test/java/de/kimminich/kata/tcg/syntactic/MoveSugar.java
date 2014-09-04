package de.kimminich.kata.tcg.syntactic;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.Optional;

public class MoveSugar {
    public static Move move(Optional<Card> card, Action action) {
        return new Move(card, action);
    }

    public static Move noMove() {
        return new Move(Optional.empty(), Action.DAMAGE);
    }

}
