package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.List;
import java.util.Optional;

public interface Strategy {

    @Deprecated
    Optional<Card> nextCard(int availableMana, List<Card> availableCards);

    Move nextMove(int availableMana, List<Card> availableCards);
}

