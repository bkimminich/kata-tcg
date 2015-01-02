package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.exception.IllegalMoveException;
import de.kimminich.kata.tcg.strategy.Strategy;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static de.kimminich.kata.tcg.Action.*;
import static de.kimminich.kata.tcg.Action.DAMAGE;
import static de.kimminich.kata.tcg.PlayerBuilder.aPlayer;
import static de.kimminich.kata.tcg.PlayerBuilder.anyPlayer;
import static de.kimminich.kata.tcg.syntactic.CardSugar.aCardWithValue;
import static de.kimminich.kata.tcg.syntactic.MoveSugar.noMove;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    private Player player;

    @Mock
    private Strategy strategy;

    @Before
    public void setUp() {
        player = new Player("Player", strategy);
    }

    @Test
    public void playerShouldHave30InitialHealth() {
        assertThat(player.getHealth(), is(equalTo(30)));
    }

    @Test
    public void playerShouldHaveZeroInitialMana() {
        assertThat(player.getMana(), is(equalTo(0)));
    }

    @Test
    public void cardDeckShouldContainInitialCards() {
        assertThat(player.getNumberOfDeckCardsWithManaCost(0), is(equalTo(2)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(1), is(equalTo(2)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(2), is(equalTo(3)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(3), is(equalTo(4)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(4), is(equalTo(3)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(5), is(equalTo(2)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(6), is(equalTo(2)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(7), is(equalTo(1)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(8), is(equalTo(1)));
    }

    @Test
    public void playerStartsWithEmptyHand() {
        assertThat(player.getNumberOfHandCards(), is(equalTo(0)));
    }

    @Test
    public void drawingACardShouldMoveOneCardFromDeckIntoHand() {
        player = aPlayer().withCardsInDeck(1, 1, 2).withNoCardsInHand().build();

        player.drawCard();

        assertThat(player.getNumberOfDeckCards(), is(equalTo(2)));
        assertThat(player.getNumberOfHandCards(), is(equalTo(1)));
    }

    @Test
    public void playerShouldTakeOneDamageWhenDrawingFromEmptyDeck() {
        player = aPlayer().withHealth(30).withNoCardsInDeck().build();

        player.drawCard();

        assertThat(player.getHealth(), is(equalTo(29)));
    }

    @Test
    public void shouldDiscardDrawnCardWhenHandSizeIsFive() {
        player = aPlayer().withCardsInDeck(1).withCardsInHand(1, 2, 3, 4, 5).build();

        player.drawCard();

        assertThat(player.getNumberOfHandCards(), is(equalTo(5)));
        assertThat(player.getNumberOfDeckCards(), is(equalTo(0)));
    }

    @Test
    public void playingCardsReducesPlayersMana() {
        player = aPlayer().withMana(10).withCardsInHand(8, 1).build();

        player.playCard(aCardWithValue(8), anyPlayer(), DAMAGE);
        player.playCard(aCardWithValue(1), anyPlayer(), DAMAGE);

        assertThat(player.getMana(), Matchers.is(Matchers.equalTo(1)));
    }

    @Test
    public void playingCardsRemovesThemFromHand() {
        player = aPlayer().withMana(5).withCardsInHand(0, 2, 2, 3).build();

        player.playCard(aCardWithValue(3), anyPlayer(), DAMAGE);
        player.playCard(aCardWithValue(2), anyPlayer(), DAMAGE);

        assertThat(player.getNumberOfHandCardsWithManaCost(3), is(equalTo(0)));
        assertThat(player.getNumberOfHandCardsWithManaCost(2), is(equalTo(1)));
    }

    @Test(expected = IllegalMoveException.class)
    public void playingCardWithInsufficientManaShouldFail() {
        player = aPlayer().withMana(3).withCardsInHand(4, 4, 4).build();

        player.playCard(aCardWithValue(4), anyPlayer(), DAMAGE);
    }

    @Test
    public void playingCardCausesDamageToOpponent() {
        player = aPlayer().withMana(10).withCardsInHand(3, 2).build();
        Player opponent = aPlayer().withHealth(30).build();

        player.playCard(aCardWithValue(3), opponent, DAMAGE);
        player.playCard(aCardWithValue(2), opponent, DAMAGE);

        assertThat(opponent.getHealth(), is(equalTo(25)));
    }

    @Test
    public void playerWithSufficientManaCanPlayCards() {
        player = aPlayer().withMana(2).withCardsInHand(3, 2).build();

        assertThat(player.canPlayCards(), is(true));
    }

    @Test
    public void playerWithInsufficientManaCannotPlayCards() {
        player = aPlayer().withMana(1).withCardsInHand(3, 2).build();

        assertThat(player.canPlayCards(), is(false));
    }

    @Test
    public void playerWithEmptyHandCannotPlayCards() {
        player = aPlayer().withNoCardsInHand().build();

        assertThat(player.canPlayCards(), is(false));
    }

    @Test(expected = IllegalMoveException.class)
    public void playingCardShouldFailWhenStrategyCannotChooseCard() {
        given(strategy.nextMove(anyInt(), anyInt(), anyListOf(Card.class))).willReturn(noMove());
        player.playCard(anyPlayer());
    }

    @Test
    public void playingCardAsHealingRestoresHealth() {
        player = aPlayer().withHealth(10).withMana(10).withCardsInHand(3, 4).build();

        player.playCard(aCardWithValue(3), anyPlayer(), HEALING);
        player.playCard(aCardWithValue(4), anyPlayer(), HEALING);

        assertThat(player.getHealth(), is(17));
    }

    @Test
    public void playerCannotHealAbove30Health() {
        player = aPlayer().withHealth(27).withMana(10).withCardsInHand(4).build();

        player.playCard(aCardWithValue(4), anyPlayer(), HEALING);

        assertThat(player.getHealth(), is(30));
    }

}
