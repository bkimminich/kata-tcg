package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.List;
import java.util.Optional;

public interface Strategy {

    Optional<Card> nextCard(int availableMana, List<Card> availableCards);

}

