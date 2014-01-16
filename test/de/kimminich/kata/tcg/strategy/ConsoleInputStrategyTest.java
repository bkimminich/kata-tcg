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
        consoleInput.provideText(player().enter("2").sequentially());

        assertThat(strategy.nextCard(10, Card.list(0, 2, 3)), is(card(2)));
    }

    @Test
    public void willRejectTooExpensiveCardsUntilAffordableCardIsChosen() {
        strategy = new ConsoleInputStrategy();
        consoleInput.provideText(player().enter("8").enter("7").enter("6").sequentially());

        assertThat(strategy.nextCard(6, Card.list(6, 7, 8)), is(card(6)));
    }

    @Test
    public void willRejectCardsNotPresentInHandUntilHandCardIsChosen() {
        strategy = new ConsoleInputStrategy();
        consoleInput.provideText(player().enter("1").enter("2").enter("3").sequentially());

        assertThat(strategy.nextCard(5, Card.list(3, 4, 5)), is(card(3)));
    }

    @Test
    public void willRejectInvalidInputUntilValidCardIsChosen() {
        strategy = new ConsoleInputStrategy();
        consoleInput.provideText(player().enter("-1").enter("9").enter("666").enter("abc").enter("5").sequentially());

        assertThat(strategy.nextCard(10, Card.list(5)), is(card(5)));
    }

    private ConsoleInputBuilder player() {
        return new ConsoleInputBuilder();
    }

    private final class ConsoleInputBuilder {
        StringBuilder buf = new StringBuilder();

        public ConsoleInputBuilder enter(String in) {
            buf.append(in).append("\n");
            return this;
        }

        public String sequentially() {
            return buf.toString();
        }
    }

    private Optional<Card> card(int card) {
        return Optional.of(new Card(card));
    }

}
