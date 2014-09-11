package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.Action.HEALING;
import static de.kimminich.kata.tcg.matchers.MoveMatchers.isAttackingWithCard;
import static de.kimminich.kata.tcg.matchers.MoveMatchers.isHealingWithCard;
import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.move;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.noMove;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CautiousStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new CautiousStrategy();
    }

    @Test
    public void shouldUseHealingUntilHealthIsAbove20() {
        assertThat(strategy.nextMove(withMana(10), andHealth(17), fromCards(2, 3, 4)), isHealingWithCard(2));
        assertThat(strategy.nextMove(withMana(7), andHealth(19), fromCards(3, 4)), isHealingWithCard(3));
        assertThat(strategy.nextMove(withMana(4), andHealth(22), fromCards(4)), isAttackingWithCard(4));
    }

    @Test
    public void shouldPlayHighCostCardsFirstWhenAttacking() {
        assertThat(strategy.nextMove(withMana(10), andHealth(30), fromCards(0, 2, 3, 8, 9)), is(move(card(9), DAMAGE)));
        assertThat(strategy.nextMove(withMana(1), andHealth(30), fromCards(0, 2, 3, 8)), is(move(card(0), DAMAGE)));
    }

    @Test
    public void shouldPlayHighCostCardsFirstWhenHealing() {
        assertThat(strategy.nextMove(withMana(10), andHealth(1), fromCards(1, 2, 3, 8, 9)), is(move(card(1), HEALING)));
        assertThat(strategy.nextMove(withMana(9), andHealth(2), fromCards(2, 3, 8, 9)), is(move(card(2), HEALING)));
        assertThat(strategy.nextMove(withMana(7), andHealth(4), fromCards(3, 8, 9)), is(move(card(3), HEALING)));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextMove(withMana(1), andHealth(30), fromCards(2, 3, 8)), is(noMove()));
    }

}
