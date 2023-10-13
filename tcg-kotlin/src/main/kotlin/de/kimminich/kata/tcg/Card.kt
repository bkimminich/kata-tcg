package de.kimminich.kata.tcg

fun createListOfCards(vararg values: Int): MutableList<Card> {
    return MutableList(values.size) { Card(values[it]) }
}

class Card(val value: Int) : Comparable<Card> {


    override fun compareTo(other: Card): Int {
        return value - other.value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return "Card($value)"
    }


}
