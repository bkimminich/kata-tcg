package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Action
import de.kimminich.kata.tcg.Card
import de.kimminich.kata.tcg.Move

/**
 * This strategy plays the highest affordable cards for attacking. It switches into healing with the lowest possible cards when the players health falls below 20.
 */
class CautiousStrategy : Strategy() {
    override fun nextMove(availableMana: Int, currentHealth: Int, availableCards: List<Card>): Move {
        return if (currentHealth < 20) {
            Move(lowestCard(availableMana, availableCards), Action.HEALING)
        } else {
            Move(highestCard(availableMana, availableCards), Action.DAMAGE)
        }
    }
}
