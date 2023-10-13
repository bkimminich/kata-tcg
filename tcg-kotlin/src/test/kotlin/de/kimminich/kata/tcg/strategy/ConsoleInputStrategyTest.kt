package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Move
import de.kimminich.kata.tcg.matchers.MoveMatchers
import de.kimminich.kata.tcg.syntactic.StrategySugar.andHealth
import de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards
import de.kimminich.kata.tcg.syntactic.StrategySugar.withMana
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ConsoleInputStrategyTest {
    var strategy: Strategy? = null

    @BeforeEach
    fun setUp() {
        strategy = ConsoleInputStrategy()
    }


    @Test
    fun shouldPlayCardsSelectedOnSystemConsole() {
        player().enters("2").finished()
        MatcherAssert.assertThat<Move?>(
            strategy!!.nextMove(withMana(10), andHealth(30), fromCards(0, 2, 3)),
            MoveMatchers.isAttackingWithCard(2)
        )
    }

    @Test
    fun willRejectTooExpensiveCardsUntilAffordableCardIsChosen() {
        player().enters("8").enters("7").enters("6").finished()
        MatcherAssert.assertThat<Move?>(
            strategy!!.nextMove(withMana(6), andHealth(30), fromCards(6, 7, 8)),
            MoveMatchers.isAttackingWithCard(6)
        )
    }

    @Test
    fun willRejectCardsNotPresentInHandUntilHandCardIsChosen() {
        player().enters("1").enters("2").enters("3").finished()
        MatcherAssert.assertThat<Move?>(
            strategy!!.nextMove(withMana(5), andHealth(30), fromCards(3, 4, 5)),
            MoveMatchers.isAttackingWithCard(3)
        )
    }

    @Test
    fun willUseChosenCardForHealingWhenInputIsSuffixedWithLetterH() {
        player().enters("5h").finished()
        MatcherAssert.assertThat<Move?>(
            strategy!!.nextMove(withMana(10), andHealth(30), fromCards(5)),
            MoveMatchers.isHealingWithCard(5)
        )
    }

    @Test
    fun invalidInputWillBeRejectedUntilValidChoiceIsMade() {
        player().enters("xxx").enters("§$%").enters("2").finished()
        MatcherAssert.assertThat<Move?>(
            strategy!!.nextMove(withMana(10), andHealth(30), fromCards(2)),
            MoveMatchers.isAttackingWithCard(2)
        )
    }

    private fun player(): ConsoleInputBuilder {
        return ConsoleInputBuilder()
    }

    private inner class ConsoleInputBuilder {
        var buf = StringBuilder()
        fun enters(`in`: String?): ConsoleInputBuilder {
            buf.append(`in`).append("\n")
            return this
        }

        fun finished() {
            System.setIn(buf.toString().byteInputStream())
        }
    }
}
