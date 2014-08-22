package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.Action.*;
import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.move;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.noMove;
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
        assertThat(strategy.nextMove(withMana(8), fromCards(7, 6, 4, 3, 2)), either(is(move(card(2), DAMAGE))).or(is(move(card(6), DAMAGE))));
    }

    @Test
    public void shouldPlayAsManyCardsAsPossibleForMaximumDamage() {
        assertThat(strategy.nextMove(withMana(3), fromCards(1, 2, 3)), either(is(move(card(1), DAMAGE))).or(is(move(card(2), DAMAGE))));
    }

    @Test
    public void shouldPickHighestAffordableCardWhenNoComboIsPossible() {
        assertThat(strategy.nextMove(withMana(2), fromCards(1, 2, 3)), is(move(card(2), DAMAGE)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextMove(withMana(1), fromCards(2, 3, 8)), is(noMove()));
    }

}