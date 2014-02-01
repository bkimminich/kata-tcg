package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.withMana;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HighestCardFirstStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new HighestCardFirstStrategy();
    }

    @Test
    public void shouldPlayCardsInOrderFromHighToLow() {
        assertThat(strategy.nextCard(withMana(10), fromCards(0, 2, 3, 8, 9)), is(card(9)));
        assertThat(strategy.nextCard(withMana(1), fromCards(0, 2, 3, 8)), is(card(0)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextCard(withMana(1), fromCards(2, 3, 8)), is(noCard()));
    }

}
