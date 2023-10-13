package de.kimminich.kata.tcg

class Move(val card: Card, val action: Action) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (card != other.card) return false
        if (action != other.action) return false

        return true
    }

    override fun hashCode(): Int {
        var result = card.hashCode()
        result = 31 * result + action.hashCode()
        return result
    }

    override fun toString(): String {
        return "Move(card=$card, action=$action)"
    }


}
