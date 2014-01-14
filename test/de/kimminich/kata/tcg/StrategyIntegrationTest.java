package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.MediumStrategy;
import de.kimminich.kata.tcg.strategy.SmartStrategy;
import de.kimminich.kata.tcg.strategy.StupidStrategy;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@Ignore
public class StrategyIntegrationTest {

    private Game game;

    @Test
    public void mediumPlayerWinsMoreOftenThanStupidPlayer() {
        int mediumWins = 0;

        for (int i = 0; i < 1000; i++) {
            Player mediumPlayer = new Player("Medium", new RandomCardPicker(), new MediumStrategy());
            Player stupidPlayer = new Player("Stupid", new RandomCardPicker(), new StupidStrategy());
            game = new Game(mediumPlayer, stupidPlayer);
            game.run();
            if (game.getWinner() == mediumPlayer) {
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
            Player smartPlayer = new Player("Smart", new RandomCardPicker(), new SmartStrategy());
            Player stupidPlayer = new Player("Stupid", new RandomCardPicker(), new StupidStrategy());
            game = new Game(smartPlayer, stupidPlayer);
            game.run();
            if (game.getWinner() == smartPlayer) {
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
            Player smartPlayer = new Player("Smart", new RandomCardPicker(), new SmartStrategy());
            Player mediumPlayer = new Player("Medium", new RandomCardPicker(), new MediumStrategy());
            game = new Game(smartPlayer, mediumPlayer);
            game.run();
            if (game.getWinner() == smartPlayer) {
                smartWins++;
            }
        }
        System.out.println("Smart wins: " + smartWins);
        assertThat(smartWins, is(greaterThan(600)));
    }

}
