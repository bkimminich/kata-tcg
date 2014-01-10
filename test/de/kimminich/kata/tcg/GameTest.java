package de.kimminich.kata.tcg;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    private Game game;

    private Player player1;

    private Player player2;

    @Mock
    private CardPicker player1CardPicker;

    @Mock
    private CardPicker player2CardPicker;

    @Before
    public void setUp() {
        player1 = new Player(player1CardPicker);
        player2 = new Player(player2CardPicker);
        game = new Game(player1, player2);
    }

    @Test
    public void gameShouldHaveTwoPlayers() {
        assertThat(game.getPlayer1(), is(notNullValue()));
        assertThat(game.getPlayer2(), is(notNullValue()));
    }

    @Test
    public void eachPlayerShouldHaveStartingHandOfThreeCardsFromHisDeck() {
        assertThat(game.getPlayer1().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(game.getPlayer1().getNumberOfDeckCards(), is(equalTo(17)));
        assertThat(game.getPlayer2().getNumberOfHandCards(), is(equalTo(3)));
        assertThat(game.getPlayer2().getNumberOfDeckCards(), is(equalTo(17)));
    }

    @Test
    public void activePlayerShouldSwitchOnEndOfTurn() {
        game.setActivePlayer(player1);
        game.endTurn();
        assertThat(game.getActivePlayer(), is(player2));
        game.endTurn();
        assertThat(game.getActivePlayer(), is(player1));
    }

}
