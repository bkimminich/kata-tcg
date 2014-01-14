package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.MediumStrategy;
import de.kimminich.kata.tcg.strategy.SmartStrategy;
import de.kimminich.kata.tcg.strategy.StupidStrategy;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GameIntegrationTest {

    private Game game;

    @Test
    public void gameWillHaveWinnerWhenOver() {
        Player player1 = new Player(new RandomCardPicker(), new StupidStrategy());
        Player player2 = new Player(new RandomCardPicker(), new MediumStrategy());
        game = new Game(player1, player2);

        game.run();

        assertThat(game.getWinner(), is(notNullValue()));
    }

}
