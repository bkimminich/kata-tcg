package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.withMana;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AiStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new AiStrategy();
    }

    @Test
    public void shouldTryToMaximizeDamageOutputInCurrentTurn() {
        assertThat(strategy.nextCard(withMana(8), fromCards(7, 6, 5, 2)), is(card(2)));
        assertThat(strategy.nextCard(withMana(6), fromCards(7, 6, 5)), is(card(6)));
    }

    @Test
    public void shouldTryToReduceHandSizeToTwo() {
        assertThat(strategy.nextCard(withMana(4), fromCards(1, 1, 1, 1, 3)), is(card(1)));
        assertThat(strategy.nextCard(withMana(3), fromCards(1, 1, 1, 3)), is(card(1)));
        assertThat(strategy.nextCard(withMana(2), fromCards(1, 1, 3)), is(card(1)));
    }

    @Test
    public void shouldPickHighestAffordableCardFromHandOfSizeTwo() {
        assertThat(strategy.nextCard(withMana(6), fromCards(4, 5)), is(card(5)));
        assertThat(strategy.nextCard(withMana(1), fromCards(1, 2)), is(card(1)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextCard(withMana(1), fromCards(2, 3, 8)), is(noCard()));
    }

}