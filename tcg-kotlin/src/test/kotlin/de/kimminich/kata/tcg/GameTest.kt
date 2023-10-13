package de.kimminich.kata.tcg

import de.kimminich.kata.tcg.Action.DAMAGE
import de.kimminich.kata.tcg.syntactic.CardSugar
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import kotlin.test.Test

class GameTest {

    @Test
    fun gameShouldHaveTwoPlayers() {
        val game: Game = GameBuilder.anyGame()
        assertThat(game.activePlayer, `is`(notNullValue()))
        assertThat(game.opponentPlayer, `is`(notNullValue()))
    }

    @Test
    fun startingPlayerShouldHaveStartingHandOfThreeCardsFromHisDeck() {
        val game: Game = GameBuilder.anyGame()
        assertThat(game.activePlayer.numberOfHandCards, `is`(equalTo(3)))
        assertThat(game.activePlayer.numberOfDeckCards, `is`(equalTo(17)))
    }

    @Test
    fun nonStartingPlayerShouldHaveStartingHandOfFourCardsFromHisDeck() {
        val game: Game = GameBuilder.anyGame()
        assertThat(game.opponentPlayer.numberOfHandCards, `is`(equalTo(4)))
        assertThat(game.opponentPlayer.numberOfDeckCards, `is`(equalTo(16)))
    }

    @Test
    fun activePlayerShouldSwitchOnEndOfTurn() {
        val player1: Player = PlayerBuilder.anyPlayer()
        val player2: Player = PlayerBuilder.anyPlayer()
        val game = GameBuilder.aGame().withActivePlayer(player1).withOpponentPlayer(player2).build()
        assertThat(game.activePlayer, `is`(player1))
        game.endTurn()
        assertThat(game.activePlayer, `is`(player2))
        game.endTurn()
        assertThat(game.activePlayer, `is`(player1))
    }

    @Test
    fun activePlayerShouldReceiveOneManaSlotOnBeginningOfTurn() {
        val player1: Player = PlayerBuilder.aPlayer().withManaSlots(0).build()
        val player2: Player = PlayerBuilder.anyPlayer()
        val game = GameBuilder.aGame().withActivePlayer(player1).withOpponentPlayer(player2).build()
        game.beginTurn()
        assertThat(player1.getManaSlots(), `is`(equalTo(1)))
    }

    @Test
    fun activePlayerShouldRefillManaOnBeginningOfTurn() {
        val player1: Player = PlayerBuilder.aPlayer().withManaSlots(3).withMana(0).build()
        val player2: Player = PlayerBuilder.aPlayer().withManaSlots(3).withMana(0).build()
        val game = GameBuilder.aGame()
            .withActivePlayer(player1)
            .withOpponentPlayer(player2)
            .build()
        game.beginTurn()
        assertThat(player1.getMana(), `is`(equalTo(player1.getManaSlots())))
    }

    @Test
    fun activePlayerShouldDrawCardOnBeginningOfTurn() {
        val player1: Player = PlayerBuilder.aPlayer().withManaSlots(3).withMana(0).build()
        val player2: Player = PlayerBuilder.aPlayer().withManaSlots(3).withMana(0).build()
        val game = GameBuilder.aGame()
            .withActivePlayer(player1)
            .withOpponentPlayer(player2)
            .build()
        val numberOfInitialHandCards: Int = player1.numberOfHandCards
        game.beginTurn()
        assertThat(player1.numberOfHandCards, `is`(equalTo(numberOfInitialHandCards + 1)))
    }

    @Test
    fun playerWithOneHealthAndEmptyDeckShouldDieFromBleedingOutOnBeginningOfTurn() {
        val player1: Player = PlayerBuilder.aPlayer().withHealth(1).withNoCardsInDeck().build()
        val player2: Player = PlayerBuilder.anyPlayer()
        val game = GameBuilder.aGame().withActivePlayer(player1).withOpponentPlayer(player2).build()
        game.beginTurn()
        assertThat(game.winner, `is`(player2))
    }

    @Test
    fun opponentLoosesWhenHealthIsZero() {
        val player1: Player = PlayerBuilder.aPlayer().withMana(10).withCardsInHand(4, 6).build()
        val player2: Player = PlayerBuilder.aPlayer().withHealth(10).build()
        val game = GameBuilder.aGame().withActivePlayer(player1).withOpponentPlayer(player2).build()
        player1.playCard(CardSugar.aCardWithValue(6), player2, DAMAGE)
        player1.playCard(CardSugar.aCardWithValue(4), player2, DAMAGE)
        assertThat(game.winner, `is`(player1))
    }

    @Test
    fun ongoingGameHasNoWinner() {
        val player1: Player = PlayerBuilder.aPlayer().withMana(10).withCardsInHand(4, 6).build()
        val player2: Player = PlayerBuilder.aPlayer().withHealth(30).build()
        val game = GameBuilder.aGame().withActivePlayer(player1).withOpponentPlayer(player2).build()
        player1.playCard(CardSugar.aCardWithValue(4), player2, DAMAGE)
        assertThat(game.winner, `is`(nullValue()))
    }
}
