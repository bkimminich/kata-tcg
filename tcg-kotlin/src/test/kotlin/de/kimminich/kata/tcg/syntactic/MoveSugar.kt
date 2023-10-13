package de.kimminich.kata.tcg.syntactic

import de.kimminich.kata.tcg.Action
import de.kimminich.kata.tcg.Card
import de.kimminich.kata.tcg.Move

object MoveSugar {
    fun move(card: Card, action: Action): Move {
        return Move(card, action)
    }

    fun noMove(): Move {
        return Move(Card(-1), Action.DAMAGE)
    }
}
