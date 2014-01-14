package de.kimminich.kata.tcg.strategy;

import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MediumStrategyTest {

    Strategy strategy;

    @Test
    public void mediumStrategyShouldPlayCardsFromHighToLow() {
        strategy = new MediumStrategy();

        assertThat(strategy.nextCard(10, new int[]{0, 2, 3, 8, 9}), is(OptionalInt.of(9)));
        assertThat(strategy.nextCard(1, new int[]{0, 2, 3, 8}), is(OptionalInt.of(0)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new MediumStrategy();

        assertThat(strategy.nextCard(1, new int[]{2, 3, 8}), is(OptionalInt.empty()));
    }
}
