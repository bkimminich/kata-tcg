package de.kimminich.kata.tcg.strategy;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class StupidStrategyTest {

    Strategy strategy;

    @Test
    public void stupidStrategyShouldPlayCardsFromLowToHigh() {
        strategy = new StupidStrategy();

        assertThat(strategy.nextCard(0, 1, 2, 3, 8), is(0));
        assertThat(strategy.nextCard(1, 2, 3, 8), is(1));
        assertThat(strategy.nextCard(2, 3, 8), is(2));
        assertThat(strategy.nextCard(3, 8), is(3));
        assertThat(strategy.nextCard(8), is(8));
    }
}
