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

    @Mock
    private CardPicker player1CardPicker;

    @Mock
    private CardPicker player2CardPicker;

    @Before
    public void setUp() {
        game = new Game(new Player(player1CardPicker), new Player(player2CardPicker));
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

}
