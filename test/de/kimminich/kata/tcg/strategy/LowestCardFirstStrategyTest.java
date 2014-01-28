package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.syntactic.CardSugar;
import org.junit.Test;

import java.util.Optional;

import static de.kimminich.kata.tcg.syntactic.CardSugar.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LowestCardFirstStrategyTest {

    Strategy strategy;

    @Test
    public void shouldPlayCardsInOrderFromLowToHigh() {
        strategy = new LowestCardFirstStrategy();

        assertThat(strategy.nextCard(10, Card.list(0, 1, 2, 3, 8)), is(card(0)));
        assertThat(strategy.nextCard(10, Card.list(1, 2, 3, 8)), is(card(1)));
        assertThat(strategy.nextCard(9, Card.list(2, 3, 8)), is(card(2)));
        assertThat(strategy.nextCard(7, Card.list(3, 8)), is(card(3)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new LowestCardFirstStrategy();

        assertThat(strategy.nextCard(1, Card.list(2, 3, 8)), is(noCard()));
    }

}
