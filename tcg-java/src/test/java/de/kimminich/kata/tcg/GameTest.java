package de.kimminich.kata.tcg;

import org.junit.Test;

import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.GameBuilder.aGame;
import static de.kimminich.kata.tcg.GameBuilder.anyGame;
import static de.kimminich.kata.tcg.PlayerBuilder.aPlayer;
import static de.kimminich.kata.tcg.PlayerBuilder.anyPlayer;
import static de.kimminich.kata.tcg.syntactic.CardSugar.aCardWithManaCost;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameTest {

    private Game game;

    @Test
    public void gameShouldHaveTwoPlayers() {
        game = anyGame();

        assertThat(game.getActivePlayer(), is(notNullValue()));
        assertThat(game.getOpponentPlayer(), is(notNullValue()));
    }

    @Test
    public void startingPlayerShouldHaveStartingHandOfThreeCardsFromHisDeck() {
        game = anyGame();

        assertThat(game.getActivePlayer().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(game.getActivePlayer().getNumberOfDeckCards(), is(equalTo(17)));
    }

    @Test
    public void nonStartingPlayerShouldHaveStartingHandOfFourCardsFromHisDeck() {
        game = anyGame();

        assertThat(game.getOpponentPlayer().getNumberOfHandCards(), is(equalTo(4)));
        assertThat(game.getOpponentPlayer().getNumberOfDeckCards(), is(equalTo(16)));
    }

    @Test
    public void activePlayerShouldSwitchOnEndOfTurn() {
        Player player1 = anyPlayer();
        Player player2 = anyPlayer();
        game = aGame().withActivePlayer(player1).withOpponentPlayer(player2).build();

        game.endTurn();
        assertThat(game.getActivePlayer(), is(player2));
        game.endTurn();
        assertThat(game.getActivePlayer(), is(player1));
    }

    @Test
    public void activePlayerShouldReceiveOneManaSlotOnBeginningOfTurn() {
        Player player1 = aPlayer().withManaSlots(0).build();
        game = aGame().withActivePlayer(player1).build();

        game.beginTurn();

        assertThat(player1.getManaSlots(), is(equalTo(1)));
    }

    @Test
    public void activePlayerShouldRefillManaOnBeginningOfTurn() {
        Player player1 = aPlayer().withManaSlots(3).withMana(0).build();
        game = aGame().withActivePlayer(player1).build();

        game.beginTurn();

        assertThat(player1.getMana(), is(equalTo(player1.getManaSlots())));
    }

    @Test
    public void activePlayerShouldDrawCardOnBeginningOfTurn() {
        Player player1 = anyPlayer();
        game = aGame().withActivePlayer(player1).build();
        int numberOfInitialHandCards = player1.getNumberOfHandCards();

        game.beginTurn();

        assertThat(player1.getNumberOfHandCards(), is(equalTo(numberOfInitialHandCards + 1)));
    }

    @Test
    public void playerWithOneHealthAndEmptyDeckShouldDieFromBleedingOutOnBeginningOfTurn() {
        Player player1 = aPlayer().withHealth(1).withNoCardsInDeck().build();
        Player player2 = anyPlayer();
        game = aGame().withActivePlayer(player1).withOpponentPlayer(player2).build();

        game.beginTurn();

        assertThat(game.getWinner(), is(player2));
    }

    @Test
    public void opponentLoosesWhenHealthIsZero() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6).build();
        Player player2 = aPlayer().withHealth(10).build();
        game = aGame().withActivePlayer(player1).withOpponentPlayer(player2).build();

        player1.playCard(aCardWithManaCost(6), player2, DAMAGE);
        player1.playCard(aCardWithManaCost(4), player2, DAMAGE);

        assertThat(game.getWinner(), is(player1));
    }

    @Test
    public void ongoingGameHasNoWinner() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6).build();
        Player player2 = aPlayer().withHealth(30).build();
        game = aGame().withActivePlayer(player1).withOpponentPlayer(player2).build();

        player1.playCard(aCardWithManaCost(4), player2, DAMAGE);

        assertThat(game.getWinner(), is(nullValue()));
    }

}
