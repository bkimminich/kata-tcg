package de.kimminich.kata.tcg.matchers;

import de.kimminich.kata.tcg.Action;
import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.Move;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;

import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.Action.HEALING;

public class MoveMatchers {

    public static Matcher<Move> isAttackingWithCard(int card) {
        return isPerformingActionWithCard(card, DAMAGE);
    }

    public static Matcher<Move> isHealingWithCard(int card) {
        return isPerformingActionWithCard(card, HEALING);
    }

    public static Matcher<Move> isPerformingActionWithCard(int card, Action action) {
        return new TypeSafeMatcher<Move>() {
            @Override
            public boolean matchesSafely(Move move) {
                Optional<Card> moveCard = move.getCard();
                return moveCard.isPresent() && moveCard.get().getManaCost() == card && move.getAction().equals(action);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(action).appendText(" with card ").appendValue(card);
            }

            @Override
            public void describeMismatchSafely(Move move, Description description) {
                description.appendText("was ").appendValue(move.getAction())
                        .appendText(" with card ").appendValue(move.getCard().get());
            }
        };
    }

}
