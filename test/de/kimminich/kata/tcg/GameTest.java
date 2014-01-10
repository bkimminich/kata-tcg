package de.kimminich.kata.tcg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    private Game game;

    @Test
    public void gameShouldHaveTwoPlayers() {
        game = new Game(aPlayer(), aPlayer());

        assertThat(player1(), is(notNullValue()));
        assertThat(player2(), is(notNullValue()));
    }

    @Test
    public void eachPlayerShouldHaveStartingHandOfThreeCardsFromHisDeck() {
        game = new Game(aPlayer(), aPlayer());

        assertThat(player1().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(player1().getNumberOfDeckCards(), is(equalTo(17)));
        assertThat(player2().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(player2().getNumberOfDeckCards(), is(equalTo(17)));
    }

    @Test
    public void activePlayerShouldSwitchOnEndOfTurn() {
        game = new Game(aPlayer(), aPlayer());
        game.setActivePlayer(player1());

        game.endTurn();
        assertThat(activePlayer(), is(player2()));
        game.endTurn();
        assertThat(activePlayer(), is(player1()));
    }

    @Test
    public void activePlayerShouldReceiveOneManaSlotOnBeginningOfTurn() {
        game = new Game(aPlayer().withManaSlots(0), aPlayer());
        game.setActivePlayer(player1());

        game.beginTurn();

        assertThat(player1().getManaSlots(), is(equalTo(1)));
    }

    @Test
    public void activePlayerShouldRefillManaOnBeginningOfTurn() {
        game = new Game(aPlayer().withManaSlots(3).withMana(0), aPlayer());
        game.setActivePlayer(player1());

        game.beginTurn();

        assertThat(player1().getMana(), is(equalTo(player1().getManaSlots())));
    }

    @Test
    public void activePlayerShouldDrawCardOnBeginningOfTurn() {
        game = new Game(spy(aPlayer()), aPlayer());
        game.setActivePlayer(player1());

        game.beginTurn();

        verify(player1(), times(3 + 1)).drawCard();
    }

    @Test
    public void playerWithOneHealthAndEmptyDeckShouldDieFromBleedingOutOnBeginningOfTurn() {
        game = new Game(aPlayer().withHealth(1).withNoCardsInDeck(), aPlayer());
        game.setActivePlayer(player1());

        game.beginTurn();

        assertThat(winner(), is(player2()));
    }

    private FakePlayer aPlayer() {
        return new FakePlayer(mock(CardPicker.class));
    }

    private Player player1() {
        return game.getPlayer1();
    }

    private Player player2() {
        return game.getPlayer2();
    }

    private Player activePlayer() {
        return game.getActivePlayer();
    }

    private Player winner() {
        return game.getWinner();
    }
}
