package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Action.DAMAGE
import de.kimminich.kata.tcg.Action.HEALING
import de.kimminich.kata.tcg.Move
import de.kimminich.kata.tcg.matchers.MoveMatchers
import de.kimminich.kata.tcg.syntactic.CardSugar.card
import de.kimminich.kata.tcg.syntactic.MoveSugar.move
import de.kimminich.kata.tcg.syntactic.MoveSugar.noMove
import de.kimminich.kata.tcg.syntactic.StrategySugar.andHealth
import de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards
import de.kimminich.kata.tcg.syntactic.StrategySugar.withMana
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class CautiousStrategyTest {
    private lateinit var strategy: Strategy

    @BeforeEach
    fun setUp() {
        strategy = CautiousStrategy()
    }

    @Test
    fun shouldUseHealingUntilHealthIsAbove20() {
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(
                withMana(10),
                andHealth(17),
                fromCards(2, 3, 4)
            ),
            MoveMatchers.isHealingWithCard(2)
        )
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(7), andHealth(19), fromCards(3, 4)),
            MoveMatchers.isHealingWithCard(3)
        )
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(4), andHealth(22), fromCards(4)),
            MoveMatchers.isAttackingWithCard(4)
        )
    }

    @Test
    fun shouldPlayHighCostCardsFirstWhenAttacking() {
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(10), andHealth(30), fromCards(0, 2, 3, 8, 9)),
            CoreMatchers.`is`<Move>(move(card(9), DAMAGE))
        )
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(1), andHealth(30), fromCards(0, 2, 3, 8)),
            CoreMatchers.`is`<Move>(move(card(0), DAMAGE))
        )
    }

    @Test
    fun shouldPlayHighCostCardsFirstWhenHealing() {
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(10), andHealth(1), fromCards(1, 2, 3, 8, 9)),
            CoreMatchers.`is`<Move>(move(card(1), HEALING))
        )
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(9), andHealth(2), fromCards(2, 3, 8, 9)),
            CoreMatchers.`is`<Move>(move(card(2), HEALING))
        )
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(7), andHealth(4), fromCards(3, 8, 9)),
            CoreMatchers.`is`<Move>(move(card(3), HEALING))
        )
    }

    @Test
    fun shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        MatcherAssert.assertThat<Move?>(
            strategy.nextMove(withMana(1), andHealth(30), fromCards(2, 3, 8)),
            CoreMatchers.`is`<Move>(noMove())
        )
    }
}
