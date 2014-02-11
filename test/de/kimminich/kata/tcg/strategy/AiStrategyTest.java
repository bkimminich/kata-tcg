package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.fromCards;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.withMana;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AiStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new AiStrategy();
    }

    @Test
    public void shouldMaximizeDamageOutputInCurrentTurn() {
        assertThat(strategy.nextCard(withMana(8), fromCards(7, 6, 4, 3, 2)), either(is(card(2))).or(is(card(6))));
    }

    @Test
    public void shouldPlayAsManyCardsAsPossibleForMaximumDamage() {
        assertThat(strategy.nextCard(withMana(3), fromCards(1, 2, 3)), either(is(card(1))).or(is(card(2))));
    }

    @Test
    public void shouldPickHighestAffordableCardWhenNoComboIsPossible() {
        assertThat(strategy.nextCard(withMana(2), fromCards(1, 2, 3)), is(card(2)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextCard(withMana(1), fromCards(2, 3, 8)), is(noCard()));
    }

}