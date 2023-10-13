package de.kimminich.kata.tcg.syntactic

import de.kimminich.kata.tcg.Card

object CardSugar {
    fun card(card: Int): Card {
        return Card(card)
    }

    fun aCardWithValue(value: Int): Card {
        return Card(value)
    }
}
