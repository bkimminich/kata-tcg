package de.kimminich.kata.tcg.strategy;

import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SmartStrategyTest {

    Strategy strategy;

    @Test
    public void smartStrategyShouldPlayCardsToMaximizeCombinedDamage() {
        strategy = new SmartStrategy();

        assertThat(strategy.nextCard(10, new int[]{2, 2, 3, 8, 9}), is(OptionalInt.of(8)));
        assertThat(strategy.nextCard(2, new int[]{2, 2, 3, 8}), is(OptionalInt.of(2)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new SmartStrategy();

        assertThat(strategy.nextCard(1, new int[]{2, 3, 8}), is(OptionalInt.empty()));
    }
}
