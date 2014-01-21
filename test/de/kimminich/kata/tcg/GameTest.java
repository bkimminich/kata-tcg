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
        game = new Game(anyPlayer(), anyPlayer());

        assertThat(activePlayer(), is(notNullValue()));
        assertThat(opponentPlayer(), is(notNullValue()));
    }

    @Test
    public void eachPlayerShouldHaveStartingHandOfThreeCardsFromHisDeck() {
        game = new Game(anyPlayer(), anyPlayer());

        assertThat(activePlayer().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(activePlayer().getNumberOfDeckCards(), is(equalTo(17)));
        assertThat(opponentPlayer().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(opponentPlayer().getNumberOfDeckCards(), is(equalTo(17)));
    }

    @Test
    public void activePlayerShouldSwitchOnEndOfTurn() {
        Player player1 = anyPlayer();
        Player player2 = anyPlayer();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        game.endTurn();
        assertThat(activePlayer(), is(player2));
        game.endTurn();
        assertThat(activePlayer(), is(player1));
    }

    @Test
    public void activePlayerShouldReceiveOneManaSlotOnBeginningOfTurn() {
        Player player1 = aPlayer().withManaSlots(0).build();
        game = aGameWithPlayers(player1, anyPlayer()).withActivePlayer(player1);

        game.beginTurn();

        assertThat(player1.getManaSlots(), is(equalTo(1)));
    }

    @Test
    public void activePlayerShouldRefillManaOnBeginningOfTurn() {
        Player player1 = aPlayer().withManaSlots(3).withMana(0).build();
        game = aGameWithPlayers(player1, anyPlayer()).withActivePlayer(player1);

        game.beginTurn();

        assertThat(player1.getMana(), is(equalTo(player1.getManaSlots())));
    }

    @Test
    public void activePlayerShouldDrawCardOnBeginningOfTurn() {
        Player player1 = spy(anyPlayer());
        game = aGameWithPlayers(player1, anyPlayer()).withActivePlayer(player1);

        game.beginTurn();

        verify(player1, times(3 + 1)).drawCard();
    }

    @Test
    public void playerWithOneHealthAndEmptyDeckShouldDieFromBleedingOutOnBeginningOfTurn() {
        Player player1 = aPlayer().withHealth(1).withNoCardsInDeck().build();
        Player player2 = anyPlayer();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        game.beginTurn();

        assertThat(winner(), is(player2));
    }

    @Test
    public void opponentLoosesWhenHealthIsZero() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6).build();
        Player player2 = aPlayer().withHealth(10).build();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        player1.playCard(new Card(6), player2);
        player1.playCard(new Card(4), player2);

        assertThat(winner(), is(player1));
    }

    @Test
    public void ongoingGameHasNoWinner() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6).build();
        Player player2 = aPlayer().withHealth(30).build();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        player1.playCard(new Card(4), player2);

        assertThat(winner(), is(nullValue()));
    }

    private PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    private Player anyPlayer() {
        return anyPlayer();
    }

    private Player activePlayer() {
        return game.getActivePlayer();
    }

    private Player opponentPlayer() {
        return game.getOpponentPlayer();
    }

    private Player winner() {
        return game.getWinner();
    }

    private FakeGame aGameWithPlayers(Player player1, Player player2) {
        return new FakeGame(player1, player2);
    }

    private class FakeGame extends Game {

        public FakeGame(Player player1, Player player2) {
            super(player1, player2);
        }

        public FakeGame withActivePlayer(Player activePlayer) {
            if (this.activePlayer != activePlayer) {
                this.opponentPlayer = this.activePlayer;
                this.activePlayer = activePlayer;
            }
            return this;
        }

    }
}
