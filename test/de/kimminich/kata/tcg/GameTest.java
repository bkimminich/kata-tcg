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
        Player player1 = aPlayer();
        game = aGameWithPlayers(player1, aPlayer()).withActivePlayer(player1);

        game.endTurn();
        assertThat(activePlayer(), is(player2()));
        game.endTurn();
        assertThat(activePlayer(), is(player1()));
    }

    @Test
    public void activePlayerShouldReceiveOneManaSlotOnBeginningOfTurn() {
        Player player1 = aPlayer().withManaSlots(0);
        game = aGameWithPlayers(player1, aPlayer()).withActivePlayer(player1);

        game.beginTurn();

        assertThat(player1().getManaSlots(), is(equalTo(1)));
    }

    @Test
    public void activePlayerShouldRefillManaOnBeginningOfTurn() {
        Player player1 = aPlayer().withManaSlots(3).withMana(0);
        game = aGameWithPlayers(player1, aPlayer()).withActivePlayer(player1);

        game.beginTurn();

        assertThat(player1().getMana(), is(equalTo(player1().getManaSlots())));
    }

    @Test
    public void activePlayerShouldDrawCardOnBeginningOfTurn() {
        Player player1 = spy(aPlayer());
        game = aGameWithPlayers(player1, aPlayer()).withActivePlayer(player1);

        game.beginTurn();

        verify(player1(), times(3 + 1)).drawCard();
    }

    @Test
    public void playerWithOneHealthAndEmptyDeckShouldDieFromBleedingOutOnBeginningOfTurn() {
        Player player1 = aPlayer().withHealth(1).withNoCardsInDeck();
        game = aGameWithPlayers(player1, aPlayer()).withActivePlayer(player1);

        game.beginTurn();

        assertThat(winner(), is(player2()));
    }

    @Test
    public void opponentLoosesWhenHealthIsZero() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6);
        game = aGameWithPlayers(player1, aPlayer().withHealth(10)).withActivePlayer(player1);

        player1().playCard(6, player2());
        player1().playCard(4, player2());

        assertThat(winner(), is(player1()));
    }

    @Test
    public void ongoingGameHasNoWinner() {
        Player player1 = aPlayer().withMana(10).withCardsInHand(4, 6);
        game = aGameWithPlayers(player1, aPlayer().withHealth(30)).withActivePlayer(player1);

        player1().playCard(4, player2());

        assertThat(winner(), is(nullValue()));
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

    private FakeGame aGameWithPlayers(Player player1, Player player2) {
        return new FakeGame(player1,player2);
    }

    private class FakeGame extends Game {

        public FakeGame(Player player1, Player player2) {
            super(player1,player2);
        }

        public FakeGame withActivePlayer(Player activePlayer) {
            this.activePlayer = activePlayer;
            return this;
        }

    }
}
