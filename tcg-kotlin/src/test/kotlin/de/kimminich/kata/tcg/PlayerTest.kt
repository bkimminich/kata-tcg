package de.kimminich.kata.tcg

import de.kimminich.kata.tcg.Action.DAMAGE
import de.kimminich.kata.tcg.Action.HEALING
import de.kimminich.kata.tcg.custom.IllegalMoveException
import de.kimminich.kata.tcg.strategy.Strategy
import de.kimminich.kata.tcg.syntactic.CardSugar
import de.kimminich.kata.tcg.syntactic.MoveSugar
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertFailsWith

class PlayerTest {
    private val strategy: Strategy = mock()

    private var player: Player = Player("Player", strategy)

     @Test
    fun playerShouldHave30InitialHealth() {
        assertThat(player.health, `is`(equalTo(30)))
    }

    @Test
    fun playerShouldHaveZeroInitialMana() {
        assertThat(player.getMana(), `is`(equalTo(0)))
    }

    @Test
    fun cardDeckShouldContainInitialCards() {
        assertThat(player.getNumberOfDeckCardsWithManaCost(0), `is`(equalTo(2)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(1), `is`(equalTo(2)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(2), `is`(equalTo(3)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(3), `is`(equalTo(4)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(4), `is`(equalTo(3)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(5), `is`(equalTo(2)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(6), `is`(equalTo(2)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(7), `is`(equalTo(1)))
        assertThat(player.getNumberOfDeckCardsWithManaCost(8), `is`(equalTo(1)))
    }

    @Test
    fun playerStartsWithEmptyHand() {
        assertThat(player.numberOfHandCards, `is`(equalTo(0)))
    }

    @Test
    fun drawingACardShouldMoveOneCardFromDeckIntoHand() {
        player = PlayerBuilder.aPlayer().withCardsInDeck(1, 1, 2).withNoCardsInHand().build()
        player.drawCard()
        assertThat(player.numberOfDeckCards, `is`(equalTo(2)))
        assertThat(player.numberOfHandCards, `is`(equalTo(1)))
    }

    @Test
    fun playerShouldTakeOneDamageWhenDrawingFromEmptyDeck() {
        player = PlayerBuilder.aPlayer().withHealth(30).withNoCardsInDeck().build()
        player.drawCard()
        assertThat(player.health, `is`(equalTo(29)))
    }

    @Test
    fun shouldDiscardDrawnCardWhenHandSizeIsFive() {
        player = PlayerBuilder.aPlayer().withCardsInDeck(1).withCardsInHand(1, 2, 3, 4, 5).build()
        player.drawCard()
        assertThat(player.numberOfHandCards, `is`(equalTo(5)))
        assertThat(player.numberOfDeckCards, `is`(equalTo(0)))
    }

    @Test
    fun playingCardsReducesPlayersMana() {
        player = PlayerBuilder.aPlayer().withMana(10).withCardsInHand(8, 1).build()
        player.playCard(CardSugar.aCardWithValue(8), PlayerBuilder.anyPlayer(), DAMAGE)
        player.playCard(CardSugar.aCardWithValue(1), PlayerBuilder.anyPlayer(), DAMAGE)
        assertThat(player.getMana(), `is`(equalTo(1)))
    }

    @Test
    fun playingCardsRemovesThemFromHand() {
        player = PlayerBuilder.aPlayer().withMana(5).withCardsInHand(0, 2, 2, 3).build()
        player.playCard(CardSugar.aCardWithValue(3), PlayerBuilder.anyPlayer(), DAMAGE)
        player.playCard(CardSugar.aCardWithValue(2), PlayerBuilder.anyPlayer(), DAMAGE)
        assertThat(player.getNumberOfHandCardsWithManaCost(3), `is`(equalTo(0)))
        assertThat(player.getNumberOfHandCardsWithManaCost(2), `is`(equalTo(1)))
    }

    @Test
    fun playingCardWithInsufficientManaShouldFail() {
        player = PlayerBuilder.aPlayer().withMana(3).withCardsInHand(4, 4, 4).build()
       assertFailsWith(
            exceptionClass = IllegalMoveException::class,
            block = { player.playCard(CardSugar.aCardWithValue(4), PlayerBuilder.anyPlayer(), DAMAGE) }
        )
    }

    @Test
    fun playingCardCausesDamageToOpponent() {
        player = PlayerBuilder.aPlayer().withMana(10).withCardsInHand(3, 2).build()
        val opponent: Player = PlayerBuilder.aPlayer().withHealth(30).build()
        player.playCard(CardSugar.aCardWithValue(3), opponent, DAMAGE)
        player.playCard(CardSugar.aCardWithValue(2), opponent, DAMAGE)
        assertThat(opponent.health, `is`(equalTo(25)))
    }

    @Test
    fun playerWithSufficientManaCanPlayCards() {
        player = PlayerBuilder.aPlayer().withMana(2).withCardsInHand(3, 2).build()
        assertThat(player.canPlayCards(), `is`(true))
    }

    @Test
    fun playerWithInsufficientManaCannotPlayCards() {
        player = PlayerBuilder.aPlayer().withMana(1).withCardsInHand(3, 2).build()
        assertThat(player.canPlayCards(), `is`(false))
    }

    @Test
    fun playerWithEmptyHandCannotPlayCards() {
        player = PlayerBuilder.aPlayer().withNoCardsInHand().build()
        assertThat(player.canPlayCards(), `is`(false))
    }

    @Test
    fun playingCardShouldFailWhenStrategyCannotChooseCard() {

        given(strategy.nextMove(anyInt(), anyInt(), anyList())).willReturn(MoveSugar.noMove())
        val before = player.numberOfHandCards
        player.playCard(PlayerBuilder.anyPlayer())
        assertThat(player.numberOfHandCards, `is`(equalTo(before)))
    }

    @Test
    fun playingCardAsHealingRestoresHealth() {
        player = PlayerBuilder.aPlayer().withHealth(10).withMana(10).withCardsInHand(3, 4).build()
        player.playCard(CardSugar.aCardWithValue(3), PlayerBuilder.anyPlayer(), HEALING)
        player.playCard(CardSugar.aCardWithValue(4), PlayerBuilder.anyPlayer(), HEALING)
        assertThat(player.health, `is`(17))
    }

    @Test
    fun playerCannotHealAbove30Health() {
        player = PlayerBuilder.aPlayer().withHealth(27).withMana(10).withCardsInHand(4).build()
        player.playCard(CardSugar.aCardWithValue(4), PlayerBuilder.anyPlayer(), HEALING)
        assertThat(player.health, `is`(30))
    }
}
