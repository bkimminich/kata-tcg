package de.kimminich.kata.tcg.matchers

import de.kimminich.kata.tcg.Action
import de.kimminich.kata.tcg.Move
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object MoveMatchers {
    fun isAttackingWithCard(card: Int): Matcher<Move> {
        return isPerformingActionWithCard(card, Action.DAMAGE)
    }

    fun isHealingWithCard(card: Int): Matcher<Move> {
        return isPerformingActionWithCard(card, Action.HEALING)
    }

    private fun isPerformingActionWithCard(card: Int, action: Action): Matcher<Move> {
        return object : TypeSafeMatcher<Move>() {
            public override fun matchesSafely(move: Move): Boolean {
                val moveCard = move.card
                return (moveCard.value == card) && (move.action == action)
            }

            override fun describeTo(description: Description) {
                description.appendValue(action).appendText(" with card ").appendValue(card)
            }

            public override fun describeMismatchSafely(move: Move, description: Description) {
                description.appendText("was ").appendValue(move.action)
                    .appendText(" with card ").appendValue(move.card)
            }
        }
    }
}
