package de.kimminich.kata.tcg.strategy;

import org.junit.Ignore;
import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SmartStrategyTest {

    Strategy strategy;

    @Test
    public void smartStrategyShouldMaximizeCombinedDamageOutput() {
        strategy = new SmartStrategy();

        assertThat(strategy.nextCard(10, new int[]{2, 3, 4, 5}), is(card(5)));
        assertThat(strategy.nextCard(5, new int[]{2, 3, 4}), is(card(3)));
        assertThat(strategy.nextCard(2, new int[]{2, 4}), is(card(2)));
    }

    @Test
    public void smartStrategyShouldFavorCombinedDamageOutputOverHighestCard() {
        strategy = new SmartStrategy();

        assertThat(strategy.nextCard(10, new int[]{2, 2, 3, 8, 9}), is(card(8)));
        assertThat(strategy.nextCard(2, new int[]{2, 2, 3, 9}), is(card(2)));

    }

    @Test
    @Ignore
    public void smartStrategyShouldPlayFavorLowerCardsToAvoidOverload() {
        strategy = new SmartStrategy();

        assertThat(strategy.nextCard(4, new int[]{1,1,1,1,4}), is(card(1)));
        assertThat(strategy.nextCard(3, new int[]{1,1,1,4}), is(card(1)));
        assertThat(strategy.nextCard(2, new int[]{1,1,4}), is(card(1)));
        assertThat(strategy.nextCard(1, new int[]{1,4}), is(card(1)));

    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new SmartStrategy();

        assertThat(strategy.nextCard(1, new int[]{2, 3, 8}), is(noCard()));
    }

    private OptionalInt card(int card) {
        return OptionalInt.of(card);
    }

    private OptionalInt noCard() {
        return OptionalInt.empty();
    }
}
