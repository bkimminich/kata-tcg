package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.List;
import java.util.Optional;

public interface Strategy {

    Move nextMove(int availableMana, int currentHealth, List<Card> availableCards);
}

