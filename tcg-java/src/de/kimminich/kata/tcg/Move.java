package de.kimminich.kata.tcg;

import java.util.Optional;

public class Move {

    private final Optional<Card> card;
    private final Action action;

    public Move(Optional<Card> card, Action action) {
        this.card = card;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (action != move.action) return false;
        if (card != null ? !card.equals(move.card) : move.card != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = card != null ? card.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    public Optional<Card> getCard() {
        return card;
    }
}
