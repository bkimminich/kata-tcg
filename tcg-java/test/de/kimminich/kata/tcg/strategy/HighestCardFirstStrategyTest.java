package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Action;
import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.move;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.noMove;
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
        assertThat(strategy.nextMove(withMana(10), fromCards(0, 2, 3, 8, 9)), is(move(card(9), DAMAGE)));
        assertThat(strategy.nextMove(withMana(1), fromCards(0, 2, 3, 8)), is(move(card(0), DAMAGE)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextMove(withMana(1), fromCards(2, 3, 8)), is(noMove()));
    }

}
