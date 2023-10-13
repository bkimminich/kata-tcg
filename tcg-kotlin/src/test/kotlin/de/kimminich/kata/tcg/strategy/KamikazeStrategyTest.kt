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

class KamikazeStrategyTest {
    private var strategy: Strategy? = null
    @BeforeEach
    fun setUp() {
        strategy = KamikazeStrategy()
    }

    @Test
    fun shouldPlayHighCostCardsFirst() {
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(10), andHealth(30), fromCards(0, 1, 2, 3, 8)),
            MoveMatchers.isAttackingWithCard(8)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(2), andHealth(30), fromCards(0, 1, 2, 3)),
            MoveMatchers.isAttackingWithCard(2)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(0), andHealth(30), fromCards(0, 1, 3)),
            MoveMatchers.isAttackingWithCard(0)
        )
    }

    @Test
    fun shouldNeverUseHealing() {
        MatcherAssert.assertThat(

            strategy!!.nextMove(withMana(10), andHealth(20), fromCards(10)),
            MoveMatchers.isAttackingWithCard(10)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(10), andHealth(10), fromCards(10)),
            MoveMatchers.isAttackingWithCard(10)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(10), andHealth(5), fromCards(10)),
            MoveMatchers.isAttackingWithCard(10)
        )
        MatcherAssert.assertThat(
            strategy!!.nextMove(withMana(10), andHealth(1), fromCards(10)),
            MoveMatchers.isAttackingWithCard(10)
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
