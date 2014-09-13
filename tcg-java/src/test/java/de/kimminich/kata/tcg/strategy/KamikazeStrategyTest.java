package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.matchers.MoveMatchers.isAttackingWithCard;
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
    public void shouldPlayHighCostCardsFirst() {
        assertThat(strategy.nextMove(withMana(10), andHealth(30), fromCards(0, 1, 2, 3, 8)), isAttackingWithCard(8));
        assertThat(strategy.nextMove(withMana(2), andHealth(30), fromCards(0, 1, 2, 3)), isAttackingWithCard(2));
        assertThat(strategy.nextMove(withMana(0), andHealth(30), fromCards(0, 1, 3)), isAttackingWithCard(0));
    }

    @Test
    public void shouldNeverUseHealing() {
        assertThat(strategy.nextMove(withMana(10), andHealth(20), fromCards(10)), isAttackingWithCard(10));
        assertThat(strategy.nextMove(withMana(10), andHealth(10), fromCards(10)), isAttackingWithCard(10));
        assertThat(strategy.nextMove(withMana(10), andHealth(5), fromCards(10)), isAttackingWithCard(10));
        assertThat(strategy.nextMove(withMana(10), andHealth(1), fromCards(10)), isAttackingWithCard(10));
    }

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextMove(withMana(1), andHealth(30), fromCards(2, 3, 8)), is(noMove()));
    }

}
