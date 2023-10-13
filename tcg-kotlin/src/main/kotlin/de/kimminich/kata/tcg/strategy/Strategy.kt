package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Card
import de.kimminich.kata.tcg.Move

abstract class Strategy {
    abstract fun nextMove(availableMana: Int, currentHealth: Int, availableCards: List<Card>): Move
    protected fun highestCard(availableMana: Int, availableCards: List<Card>): Card {
        return availableCards.stream()
            .filter { card: Card -> card.value <= availableMana }
            .max(Comparator.naturalOrder())
            .orElse(Card(-1))  // ugly hack to avoid Optional.get() which throws NoSuchElementException

    }

    protected fun lowestCard(availableMana: Int, availableCards: List<Card>): Card {
        val min = availableCards.stream()
            .filter { card: Card -> card.value <= availableMana }
            .min(Comparator.naturalOrder())
            .orElse(Card(-1)) // ugly hack to avoid Optional.get() which throws NoSuchElementException
        return min
    }
}
