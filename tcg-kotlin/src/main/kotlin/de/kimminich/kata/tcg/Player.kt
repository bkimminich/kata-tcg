package de.kimminich.kata.tcg

import de.kimminich.kata.tcg.custom.IllegalMoveException
import de.kimminich.kata.tcg.strategy.Strategy
import java.util.*
import kotlin.math.min

class Player {
    private var random = Random()
    var health = MAXIMUM_HEALTH
        private set
    private var manaSlots = 0
    private var mana = 0
    private var deck: MutableList<Card> = createListOfCards(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8)
    private var hand: MutableList<Card> = ArrayList()
    private val strategy: Strategy
    private val name: String

    constructor(name: String, strategy: Strategy) {
        this.name = name
        this.strategy = strategy
    }



    internal constructor(
        name: String,
        strategy: Strategy,
        health: Int,
        manaSlots: Int,
        mana: Int,
        deck: MutableList<Card>,
        hand: MutableList<Card>
    ) {
        this.name = name
        this.strategy = strategy
        this.health = health
        this.manaSlots = manaSlots
        this.mana = mana
        this.deck = deck
        this.hand = hand
    }

    fun getNumberOfDeckCardsWithManaCost(manaCost: Int): Int {
        return deck.stream().filter { card: Card -> card.value == manaCost }.count().toInt()
    }

    val numberOfDeckCards: Int
        get() = deck.size

    fun getNumberOfHandCardsWithManaCost(manaCost: Int): Int {
        return hand.stream().filter { card: Card -> card.value == manaCost }.count().toInt()
    }

    val numberOfHandCards: Int
        get() = hand.size

    fun drawCard() {
        if (numberOfDeckCards == 0) {
            println("$this bleeds out!")
            health--
        } else {
            val card = deck[random.nextInt(deck.size)]
            deck.remove(card)
            println("$this draws card: $card")
            if (numberOfHandCards < MAXIMUM_HAND_SIZE) {
                hand.add(card)
            } else {
                println("$this drops card $card from overload!")
            }
        }
    }

    fun giveManaSlot() {
        if (manaSlots < MAXIMUM_MANA_SLOTS) {
            manaSlots++
        }
    }

    fun refillMana() {
        mana = manaSlots
    }

    fun drawStartingHand() {
        for (i in 0..<STARTING_HAND_SIZE) {
            drawCard()
        }
    }

    private fun heal(amount: Int) {
        health = min((health + amount).toDouble(), MAXIMUM_HEALTH.toDouble()).toInt()
    }

    private fun receiveDamage(damage: Int) {
        health -= damage
    }

    fun canPlayCards(): Boolean {
        return hand.stream().filter { card: Card -> card.value <= mana }.count() > 0
    }

    fun playCard(opponent: Player) {
        val move = strategy.nextMove(mana, health, hand)
        playCard(move.card, opponent, move.action)
    }

    fun playCard(card: Card, opponent: Player, action: Action) {
        if (mana < card.value) {
            throw IllegalMoveException("Insufficient Mana ($mana) to pay for card $card.")
        }
        println("$this plays card $card for $action")
        mana -= card.value
        hand.remove(card)
        when (action) {
            Action.DAMAGE -> opponent.receiveDamage(card.value)
            Action.HEALING -> heal(card.value)
            else -> throw IllegalMoveException("Unrecognized game action: $action")
        }
    }

    override fun toString(): String {
        return "Player:" + name + "{" +
                "health=" + health +
                ", mana=" + mana + "/" + manaSlots +
                ", hand=" + hand +
                //", deck=" + deck +
                '}'
    }

    fun getManaSlots(): Int {
        return manaSlots
    }
    fun getMana(): Int {
        return mana
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


    companion object {
        private const val STARTING_HAND_SIZE = 3
        private const val MAXIMUM_HAND_SIZE = 5
        private const val MAXIMUM_HEALTH = 30
        private const val MAXIMUM_MANA_SLOTS = 10
    }


}
