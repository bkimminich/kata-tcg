package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.MediumStrategy;
import de.kimminich.kata.tcg.strategy.SmartStrategy;
import de.kimminich.kata.tcg.strategy.StupidStrategy;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Ignore
public class StrategyIntegrationTest {

    private Game game;

    @Test
    public void mediumPlayerWinsMoreOftenThanStupidPlayer() {
        int mediumWins = 0;

        for (int i = 0; i < 1000; i++) {
            Player player1 = new Player(new RandomCardPicker(), new MediumStrategy());
            Player player2 = new Player(new RandomCardPicker(), new StupidStrategy());
            game = new Game(player1, player2);
            game.run();
            if (game.getWinner() == player1) {
                mediumWins++;
            }
        }
        System.out.println("Medium wins: " + mediumWins);
        assertThat(mediumWins, is(greaterThan(600)));
    }

    @Test
    public void smartPlayerWinsMoreOftenThanStupidPlayer() {
        int smartWins = 0;

        for (int i = 0; i < 1000; i++) {
            Player player1 = new Player(new RandomCardPicker(), new SmartStrategy());
            Player player2 = new Player(new RandomCardPicker(), new StupidStrategy());
            game = new Game(player1, player2);
            game.run();
            if (game.getWinner() == player1) {
                smartWins++;
            }
        }
        System.out.println("Smart wins: " + smartWins);
        assertThat(smartWins, is(greaterThan(700)));
    }

    @Test
    public void smartPlayerWinsMoreOftenThanMediumPlayer() {
        int smartWins = 0;

        for (int i = 0; i < 1000; i++) {
            Player player1 = new Player(new RandomCardPicker(), new SmartStrategy());
            Player player2 = new Player(new RandomCardPicker(), new MediumStrategy());
            game = new Game(player1, player2);
            game.run();
            if (game.getWinner() == player1) {
                smartWins++;
            }
        }
        System.out.println("Smart wins: " + smartWins);
        assertThat(smartWins, is(greaterThan(600)));
    }

}
