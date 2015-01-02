package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Test;

import static de.kimminich.kata.tcg.matchers.MoveMatchers.isAttackingWithCard;
import static de.kimminich.kata.tcg.matchers.MoveMatchers.isHealingWithCard;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.noMove;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.*;
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
        assertThat(strategy.nextMove(withMana(8), andHealth(30), fromCards(7, 6, 4, 3, 2)), either(is(isAttackingWithCard(2))).or(is(isAttackingWithCard(6))));
    }

/*    @Test
    public void shouldPlayAsManyCardsAsPossibleForMaximumDamage() {
        assertThat(strategy.nextMove(withMana(3), andHealth(30), fromCards(1, 2, 3)), either(is(isAttackingWithCard(1))).or(is(isAttackingWithCard(2))));
    }

    @Test
    public void shouldPickHighestAffordableCardWhenNoComboIsPossible() {
        assertThat(strategy.nextMove(withMana(2), andHealth(30), fromCards(1, 2, 3)), isAttackingWithCard(2));
    }

    @Test
    public void shouldUseHealingUntilHealthIsAtLeast10() {
        assertThat(strategy.nextMove(withMana(3), andHealth(8), fromCards(1, 1, 1)), isHealingWithCard(1));
        assertThat(strategy.nextMove(withMana(2), andHealth(9), fromCards(1, 1)), isHealingWithCard(1));
        assertThat(strategy.nextMove(withMana(1), andHealth(10), fromCards(1)), isAttackingWithCard(1));
    }*/

    @Test
    public void shouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        assertThat(strategy.nextMove(withMana(1), andHealth(30), fromCards(2, 3, 8)), is(noMove()));
    }

}