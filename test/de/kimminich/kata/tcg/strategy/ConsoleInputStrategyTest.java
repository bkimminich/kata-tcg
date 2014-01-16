package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ConsoleInputStrategyTest {

    Strategy strategy;

    @Rule
    public TextFromStandardInputStream consoleInput = emptyStandardInputStream();

    @Test
    public void manualInputStrategyShouldPlayCardsSelectedOnSystemConsole() {
        strategy = new ConsoleInputStrategy();
        consoleInput.provideText("2\n");

        assertThat(strategy.nextCard(10, Card.list(0, 2, 3)), is(card(2)));
    }

    @Test
    public void willRejectTooExpensiveCardsUntilAffordableCardIsChosen() {
        strategy = new ConsoleInputStrategy();
        consoleInput.provideText("8\n7\n6\n");

        assertThat(strategy.nextCard(6, Card.list(6, 7, 8)), is(card(6)));
    }

    @Test
    public void willRejectInvalidInputUntilValidCardIsChosen() {
        strategy = new ConsoleInputStrategy();
        consoleInput.provideText("-1\n9\n666\nabc\n5\n");

        assertThat(strategy.nextCard(10, Card.list(5)), is(card(5)));
    }

    private Optional<Card> card(int card) {
        return Optional.of(new Card(card));
    }

    private Optional<Card> noCard() {
        return Optional.empty();
    }

}
