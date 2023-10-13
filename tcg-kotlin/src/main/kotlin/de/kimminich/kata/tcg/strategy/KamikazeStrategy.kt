package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Action
import de.kimminich.kata.tcg.Card
import de.kimminich.kata.tcg.Move

/**
 * This strategy plays the highest affordable cards first and only for attacking. No healing is used regardless of the players health.
 */
class KamikazeStrategy : Strategy() {
    override fun nextMove(availableMana: Int, currentHealth: Int, availableCards: List<Card>): Move {
        return Move(highestCard(availableMana, availableCards), Action.DAMAGE)
    }
}
