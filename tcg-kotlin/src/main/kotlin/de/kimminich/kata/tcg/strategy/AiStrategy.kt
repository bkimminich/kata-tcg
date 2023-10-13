package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Action
import de.kimminich.kata.tcg.Card
import de.kimminich.kata.tcg.Move
import java.util.*

/**
 * This strategy tries to find optimal card combinations in any given turn. Examples:
 *
 *  * From a hand of 2,3,4 given 5 mana it would play 2+3 (or 3+2) to maximize damage output
 *  * From a hand of 1,1,1,3 given 3 mana it would play 1+1+1 to minimize the hand size and avoid card loss from the "Overload" rule
 *
 *
 * This strategy switches into healing with the highest possible cards when the players health falls below 10.
 */
class AiStrategy : Strategy() {
    override fun nextMove(availableMana: Int, currentHealth: Int, availableCards: List<Card>): Move {
        return if (currentHealth < 10) {
            Move(highestCard(availableMana, availableCards), Action.HEALING)
        } else {
            Move(bestCard(availableMana, availableCards), Action.DAMAGE)
        }
    }

    private fun bestCard(availableMana: Int, availableCards: List<Card>): Card {
        val cardCombos: MutableList<List<Card>> = ArrayList()
        val remainingCards: MutableList<Card> = ArrayList(availableCards)
        remainingCards.sortedWith(Comparator.reverseOrder<Card>()) // highest mana costs first
        while (remainingCards.isNotEmpty()) {
            val selectedCards: MutableList<Card> = ArrayList()
            collectMaxDamageCardCombo(selectedCards, availableMana, remainingCards)
            cardCombos.add(selectedCards)
            remainingCards.removeAt(0)
        }
        var bestCombo: List<Card> = ArrayList()
        var maxDamage = 0
        for (combo in cardCombos) {
            val comboDamage = combo.stream().mapToInt(Card::value).sum()
            if (comboDamage > maxDamage || comboDamage == maxDamage && combo.size > bestCombo.size) {
                maxDamage = comboDamage
                bestCombo = combo
            }
        }
        return bestCombo.stream().max(Comparator.naturalOrder()).orElse(Card(-1))
    }

    private fun collectMaxDamageCardCombo(
        selectedCards: MutableList<Card>,
        availableMana: Int,
        availableCards: List<Card>
    ) {
        for (card in availableCards) {
            val remainingCards: MutableList<Card> = ArrayList(availableCards)
            if (selectedCards.stream().mapToInt(Card::value).sum() + card.value <= availableMana) {
                selectedCards.add(card)
                remainingCards.remove(card)
                collectMaxDamageCardCombo(selectedCards, availableMana - card.value, remainingCards)
            }
        }
    }
}
