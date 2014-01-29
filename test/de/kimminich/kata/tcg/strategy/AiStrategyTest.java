package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.CardSugar.noCard;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AiStrategyTest {

    Strategy strategy;

    @Test
    public void strategyShouldTryToReduceHandSizeToTwo() {
        strategy = new AiStrategy();

        assertThat(strategy.nextCard(4, Card.list(1,1,1,1,3)), is(card(1)));
        assertThat(strategy.nextCard(3, Card.list(1,1,1,3)), is(card(1)));
        assertThat(strategy.nextCard(2, Card.list(1,1,3)), is(card(1)));
    }

    @Test
    public void strategyShouldPickHighestAffordableCardFromHandOfSizeTwo() {
        strategy = new AiStrategy();

        assertThat(strategy.nextCard(6, Card.list(4, 5)), is(card(5)));
        assertThat(strategy.nextCard(1, Card.list(1, 2)), is(card(1)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new AiStrategy();

        assertThat(strategy.nextCard(1, Card.list(2, 3, 8)), is(noCard()));
    }

}