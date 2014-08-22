package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.move;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.noMove;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class KamikazeStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new KamikazeStrategy();
    }

    @Test
    public void shouldPlayLowCostCardsFirst() {
        assertThat(strategy.nextMove(withMana(10), andHealth(30), fromCards(0, 1, 2, 3, 8)), is(move(card(0), DAMAGE)));
        assertThat(strategy.nextMove(withMana(10), andHealth(30), fromCards(1, 2, 3, 8)), is(move(card(1), DAMAGE)));
        assertThat(strategy.nextMove(withMana(9), andHealth(30), fromCards(2, 3, 8)), is(move(card(2), DAMAGE)));
        assertThat(strategy.nextMove(withMana(7), andHealth(30), fromCards(3, 8)), is(move(card(3), DAMAGE)));
    }

    @Test
    public void shouldNeverUseHealing() {
        assertThat(strategy.nextMove(withMana(10), andHealth(20), fromCards(10)), is(move(card(10), DAMAGE)));
        assertThat(strategy.nextMove(withMana(10), andHealth(10), fromCards(10)), is(move(card(10), DAMAGE)));
        assertThat(strategy.nextMove(withMana(10), andHealth(5), fromCards(10)), is(move(card(10), DAMAGE)));
        assertThat(strategy.nextMove(withMana(10), andHealth(1), fromCards(10)), is(move(card(10), DAMAGE)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextMove(withMana(1), andHealth(30), fromCards(2, 3, 8)), is(noMove()));
    }

}
