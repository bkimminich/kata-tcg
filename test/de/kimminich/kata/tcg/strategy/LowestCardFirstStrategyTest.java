package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.withMana;
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
        assertThat(strategy.nextCard(withMana(10), fromCards(0, 1, 2, 3, 8)), is(card(0)));
        assertThat(strategy.nextCard(withMana(10), fromCards(1, 2, 3, 8)), is(card(1)));
        assertThat(strategy.nextCard(withMana(9), fromCards(2, 3, 8)), is(card(2)));
        assertThat(strategy.nextCard(withMana(7), fromCards(3, 8)), is(card(3)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextCard(withMana(1), fromCards(2, 3, 8)), is(noCard()));
    }

}
