package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.matchers.MoveMatchers
import de.kimminich.kata.tcg.syntactic.MoveSugar.noMove
import de.kimminich.kata.tcg.syntactic.StrategySugar.andHealth
import de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards
import de.kimminich.kata.tcg.syntactic.StrategySugar.withMana
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class AiStrategyTest {
    var strategy: Strategy? = null
    @BeforeEach
    fun setUp() {
        strategy = AiStrategy()
    }

    @Test
    fun shouldMaximizeDamageOutputInCurrentTurn() {
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(8), andHealth(30), fromCards(7, 6, 4, 3, 2)), CoreMatchers.either(
                CoreMatchers.`is`(MoveMatchers.isAttackingWithCard(2))
            ).or(CoreMatchers.`is`(MoveMatchers.isAttackingWithCard(6)))
        )
    }

    @Test
    fun shouldPlayAsManyCardsAsPossibleForMaximumDamage() {
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(3), andHealth(30), fromCards(1, 2, 3)), CoreMatchers.either(
                CoreMatchers.`is`(MoveMatchers.isAttackingWithCard(1))
            ).or(CoreMatchers.`is`(MoveMatchers.isAttackingWithCard(2)))
        )
    }

    @Test
    fun shouldPickHighestAffordableCardWhenNoComboIsPossible() {
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(2), andHealth(30), fromCards(1, 2, 3)),
            MoveMatchers.isAttackingWithCard(2)
        )
    }

    @Test
    fun shouldUseHealingUntilHealthIsAtLeast10() {
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(3), andHealth(8), fromCards(1, 1, 1)),
            MoveMatchers.isHealingWithCard(1)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(2), andHealth(9), fromCards(1, 1)),
            MoveMatchers.isHealingWithCard(1)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(1), andHealth(10), fromCards(1)),
            MoveMatchers.isAttackingWithCard(1)
        )
    }

    @Test
    fun shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(1), andHealth(30), fromCards(2, 3, 8)),
            CoreMatchers.`is`(noMove())
        )
    }
}