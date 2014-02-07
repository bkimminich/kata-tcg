package de.kimminich.kata.tcg;

import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.aCardWithManaCost;
import static de.kimminich.kata.tcg.syntactic.PlayerSugar.aPlayer;
import static de.kimminich.kata.tcg.syntactic.PlayerSugar.anyPlayer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameTest {

    private Game game;

    @Test
    public void gameShouldHaveTwoPlayers() {
        game = new Game(anyPlayer(), anyPlayer());

        assertThat(game.getActivePlayer(), is(notNullValue()));
        assertThat(game.getOpponentPlayer(), is(notNullValue()));
    }

    @Test
    public void eachPlayerShouldHaveStartingHandOfThreeCardsFromHisDeck() {
        game = new Game(anyPlayer(), anyPlayer());

        assertThat(game.getActivePlayer().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(game.getActivePlayer().getNumberOfDeckCards(), is(equalTo(17)));
        assertThat(game.getOpponentPlayer().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(game.getOpponentPlayer().getNumberOfDeckCards(), is(equalTo(17)));
    }

    @Test
    public void activePlayerShouldSwitchOnEndOfTurn() {
        Player player1 = anyPlayer();
        Player player2 = anyPlayer();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        game.endTurn();
        assertThat(game.getActivePlayer(), is(player2));
        game.endTurn();
        assertThat(game.getActivePlayer(), is(player1));
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
        Player player1 = anyPlayer();
        game = aGameWithPlayers(player1, anyPlayer()).withActivePlayer(player1);
        int numberOfInitialHandCards = player1.getNumberOfHandCards();

        game.beginTurn();

        assertThat(player1.getNumberOfHandCards(), is(equalTo(numberOfInitialHandCards + 1)));
    }

    @Test
    public void playerWithOneHealthAndEmptyDeckShouldDieFromBleedingOutOnBeginningOfTurn() {
        Player player1 = aPlayer().withHealth(1).withNoCardsInDeck().build();
        Player player2 = anyPlayer();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        game.beginTurn();

        assertThat(game.getWinner(), is(player2));
    }

    @Test
    public void opponentLoosesWhenHealthIsZero() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6).build();
        Player player2 = aPlayer().withHealth(10).build();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        player1.playCard(aCardWithManaCost(6), player2);
        player1.playCard(aCardWithManaCost(4), player2);

        assertThat(game.getWinner(), is(player1));
    }

    @Test
    public void ongoingGameHasNoWinner() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6).build();
        Player player2 = aPlayer().withHealth(30).build();
        game = aGameWithPlayers(player1, player2).withActivePlayer(player1);

        player1.playCard(aCardWithManaCost(4), player2);

        assertThat(game.getWinner(), is(nullValue()));
    }

    private FakeGame aGameWithPlayers(Player player1, Player player2) {
        return new FakeGame(player1, player2);
    }

    private class FakeGame extends Game {

        public FakeGame(Player player1, Player player2) {
            super(player1, player2);
        }

        public FakeGame withActivePlayer(Player activePlayer) {
            if (getActivePlayer() != activePlayer) {
                switchPlayer();
            }
            return this;
        }

    }
}
