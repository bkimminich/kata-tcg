package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static java.util.Arrays.stream;

public class StupidStrategy implements Strategy {

    @Override
    public Optional<Card> nextCard(int mana, List<Card> availableCards) {
        return availableCards.stream().filter(card -> card.getManaCost() <= mana).min(Comparator.<Card>naturalOrder());
    }
}
