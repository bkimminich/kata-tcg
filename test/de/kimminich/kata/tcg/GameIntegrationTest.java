package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.AiStrategy;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GameIntegrationTest {

    private Game game;

    @Test
    public void gameWillHaveWinnerWhenOver() {
        Player player1 = new Player("1", new AiStrategy());
        Player player2 = new Player("2", new AiStrategy());
        game = new Game(player1, player2);

        game.run();

        assertThat(game.getWinner(), is(notNullValue()));
    }

}
