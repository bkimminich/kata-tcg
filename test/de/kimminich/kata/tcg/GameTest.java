package de.kimminich.kata.tcg;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(new Player(null), new Player(null));
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
