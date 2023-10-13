package de.kimminich.kata.tcg

import de.kimminich.kata.tcg.strategy.AiStrategy
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import kotlin.test.Test

class GameIntegrationTest {
    val player1 = Player("1", AiStrategy())
    val player2 = Player("2", AiStrategy())
    val game: Game = Game(player1, player2)
    @Test
    fun gameWillHaveWinnerWhenOver() {
        game.run()
        MatcherAssert.assertThat(game.winner, `is`(notNullValue()))
    }
}
