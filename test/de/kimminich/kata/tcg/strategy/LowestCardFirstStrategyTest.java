package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LowestCardFirstStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new LowestCardFirstStrategy();
    }

    @Test
    public void shouldPlayCardsInOrderFromLowToHigh() {
        assertThat(strategy.nextCard(10, Card.list(0, 1, 2, 3, 8)), is(card(0)));
        assertThat(strategy.nextCard(10, Card.list(1, 2, 3, 8)), is(card(1)));
        assertThat(strategy.nextCard(9, Card.list(2, 3, 8)), is(card(2)));
        assertThat(strategy.nextCard(7, Card.list(3, 8)), is(card(3)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextCard(1, Card.list(2, 3, 8)), is(noCard()));
    }

}
