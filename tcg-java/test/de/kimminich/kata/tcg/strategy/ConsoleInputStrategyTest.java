package de.kimminich.kata.tcg.strategy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.Action.HEALING;
import static de.kimminich.kata.tcg.syntactic.CardSugar.card;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.move;
import static de.kimminich.kata.tcg.syntactic.StrategySugar.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ConsoleInputStrategyTest {

    Strategy strategy;

    @Before
    public void setUp() {
        strategy = new ConsoleInputStrategy();
    }

    @Rule
    public TextFromStandardInputStream consoleInput = emptyStandardInputStream();

    @Test
    public void shouldPlayCardsSelectedOnSystemConsole() {
        player().enters("2").finished();

        assertThat(strategy.nextMove(withMana(10), andHealth(30), fromCards(0, 2, 3)), is(move(card(2), DAMAGE)));
    }

    @Test
    public void willRejectTooExpensiveCardsUntilAffordableCardIsChosen() {
        player().enters("8").enters("7").enters("6").finished();

        assertThat(strategy.nextMove(withMana(6), andHealth(30), fromCards(6, 7, 8)), is(move(card(6), DAMAGE)));
    }

    @Test
    public void willRejectCardsNotPresentInHandUntilHandCardIsChosen() {
        player().enters("1").enters("2").enters("3").finished();

        assertThat(strategy.nextMove(withMana(5), andHealth(30), fromCards(3, 4, 5)), is(move(card(3), DAMAGE)));
    }

    @Test
    public void willUseChosenCardForHealingWhenInputIsSuffixedWithLetterH() {
        player().enters("5h").finished();

        assertThat(strategy.nextMove(withMana(10), andHealth(30), fromCards(5)), is(move(card(5), HEALING)));
    }

    private ConsoleInputBuilder player() {
        return new ConsoleInputBuilder();
    }

    private final class ConsoleInputBuilder {
        StringBuilder buf = new StringBuilder();

        public ConsoleInputBuilder enters(String in) {
            buf.append(in).append("\n");
            return this;
        }

        public void finished() {
            consoleInput.provideText(buf.toString());
        }
    }

}
