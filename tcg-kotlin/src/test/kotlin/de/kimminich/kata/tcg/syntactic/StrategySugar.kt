package de.kimminich.kata.tcg.syntactic

import de.kimminich.kata.tcg.Card

object StrategySugar {
    fun withMana(mana: Int): Int {
        return mana
    }

    fun andHealth(health: Int): Int {
        return health
    }

    fun fromCards(vararg values: Int): MutableList<Card> {
        return MutableList(values.size) { Card(values[it]) }
    }
}
