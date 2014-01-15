package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import org.junit.Test;

import java.util.Optional;
import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MediumStrategyTest {

    Strategy strategy;

    @Test
    public void mediumStrategyShouldPlayCardsFromHighToLow() {
        strategy = new MediumStrategy();

        assertThat(strategy.nextCard(10, Card.list(0, 2, 3, 8, 9)), is(card(9)));
        assertThat(strategy.nextCard(1, Card.list(0, 2, 3, 8)), is(card(0)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new MediumStrategy();

        assertThat(strategy.nextCard(1, Card.list(2, 3, 8)), is(noCard()));
    }

    private Optional<Card> card(int card) {
        return Optional.of(new Card(card));
    }

    private Optional<Card> noCard() {
        return Optional.empty();
    }
}
