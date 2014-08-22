package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;

import java.util.List;

public interface Strategy {

    Move nextMove(int availableMana, int currentHealth, List<Card> availableCards);
}

