package de.kimminich.kata.tcg

import de.kimminich.kata.tcg.strategy.KamikazeStrategy
import de.kimminich.kata.tcg.strategy.Strategy

class PlayerBuilder {
    private var health = 30
    private var manaSlots = 0
    private var mana = 0
    private var deck: MutableList<Card> = createListOfCards(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8)
    private var hand: MutableList<Card> = java.util.ArrayList<Card>()
    private val strategy: Strategy = KamikazeStrategy()
    private val name = "Player" + playerNo++
    fun build(): Player {
        return Player(name, strategy, health, manaSlots, mana, deck, hand)
    }

    fun withCardsInDeck(vararg cards: Int): PlayerBuilder {
        deck = createListOfCards(*cards)
        return this
    }

    fun withNoCardsInDeck(): PlayerBuilder {
        deck = java.util.ArrayList<Card>()
        return this
    }

    fun withCardsInHand(vararg cards: Int): PlayerBuilder {
        hand = createListOfCards(*cards)
        return this
    }

    fun withNoCardsInHand(): PlayerBuilder {
        hand = java.util.ArrayList<Card>()
        return this
    }

    fun withManaSlots(manaSlots: Int): PlayerBuilder {
        this.manaSlots = manaSlots
        return this
    }

    fun withMana(mana: Int): PlayerBuilder {
        this.mana = mana
        return this
    }

    fun withHealth(health: Int): PlayerBuilder {
        this.health = health
        return this
    }

    companion object {
        private var playerNo = 0
        @JvmStatic
        fun aPlayer(): PlayerBuilder {
            return PlayerBuilder()
        }

        @JvmStatic
        fun anyPlayer(): Player {
            return PlayerBuilder().build()
        }
    }
}
