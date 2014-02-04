package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Game;
import de.kimminich.kata.tcg.Player;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class StrategyIntegrationTest {

    private Game game;

    @Test
    public void HighestCardFirstStrategyShouldWinMoreOftenThanLowestCardFirstStrategy() {
        int highWins = 0;

        for (int i=0; i<100; i++) {
            Player highestFirstPlayer = new Player("High", new HighestCardFirstStrategy());
            Player lowestFirstPlayer = new Player("Low", new LowestCardFirstStrategy());
            game = new Game(highestFirstPlayer, lowestFirstPlayer);
            game.run();
            if (game.getWinner() == highestFirstPlayer) {
                highWins++;
            }
        }

        assertThat(highWins, is(greaterThan(60)));
    }

    @Test
    public void AiStrategyShouldWinMoreOftenThanLowestCardFirstStrategy() {
        int aiWins = 0;

        for (int i=0; i<100; i++) {
            Player aiPlayer = new Player("AI", new AiStrategy());
            Player lowestFirstPlayer = new Player("Low", new LowestCardFirstStrategy());
            game = new Game(aiPlayer, lowestFirstPlayer);
            game.run();
            if (game.getWinner() == aiPlayer) {
                aiWins++;
            }
        }

        assertThat(aiWins, is(greaterThan(60)));
    }

    @Test
    @Ignore // TODO Improve AI! Always-Highest-First play seems to be smarter than hand-reducing/max. damage AI play
    public void AiStrategyShouldWinMoreOftenThanHighestCardFirstStrategy() {
        int aiWins = 0;

        for (int i=0; i<100; i++) {
            Player aiPlayer = new Player("AI", new AiStrategy());
            Player highestFirstPlayer = new Player("High", new HighestCardFirstStrategy());
            game = new Game(aiPlayer, highestFirstPlayer);
            game.run();
            if (game.getWinner() == aiPlayer) {
                aiWins++;
            }
        }

        assertThat(aiWins, is(greaterThan(60)));
    }

}
