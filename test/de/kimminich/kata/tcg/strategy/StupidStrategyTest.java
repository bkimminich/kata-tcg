package de.kimminich.kata.tcg.strategy;

import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StupidStrategyTest {

    Strategy strategy;

    @Test
    public void stupidStrategyShouldPlayCardsFromLowToHigh() {
        strategy = new StupidStrategy();

        assertThat(strategy.nextCard(10, new int[]{0, 1, 2, 3, 8}), is(card(0)));
        assertThat(strategy.nextCard(10, new int[]{1, 2, 3, 8}), is(card(1)));
        assertThat(strategy.nextCard(9, new int[]{2, 3, 8}), is(card(2)));
        assertThat(strategy.nextCard(7, new int[]{3, 8}), is(card(3)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new StupidStrategy();

        assertThat(strategy.nextCard(1, new int[]{2, 3, 8}), is(noCard()));
    }

    private OptionalInt card(int card) {
        return OptionalInt.of(card);
    }

    private OptionalInt noCard() {
        return OptionalInt.empty();
    }
}
