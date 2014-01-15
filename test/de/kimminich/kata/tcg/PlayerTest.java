package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.exceptions.IllegalMoveException;
import de.kimminich.kata.tcg.strategy.Strategy;
import de.kimminich.kata.tcg.utils.RandomCardPicker;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

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
    private RandomCardPicker cardPicker;

    @Mock
    private Strategy strategy;

    @Before
    public void setUp() {
        player = new Player("Player", cardPicker, strategy);
        given(cardPicker.pick(anyDeck())).willReturn(new Card(0));
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
    public void drawingACardShouldPutThatCardFromDeckIntoHand() {
        player = aPlayer().withCardsInDeck(1, 1, 2).withNoCardsInHand();
        given(cardPicker.pick(anyDeck())).willReturn(new Card(1));

        player.drawCard();

        assertThat(player.getNumberOfHandCardsWithManaCost(1), is(equalTo(1)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(2), is(equalTo(1)));
    }

    @Test
    public void playerShouldTakeOneDamageWhenDrawingFromEmptyDeck() {
        player = aPlayer().withNoCardsInDeck();
        int preDrawHealth = player.getHealth();

        player.drawCard();

        assertThat(player.getHealth(), is(equalTo(preDrawHealth - 1)));
    }

    @Test
    public void shouldDiscardDrawnCardWhenHandSizeIsFive() {
        player = aPlayer().withCardsInDeck(1).withCardsInHand(1, 2, 3, 4, 5);
        given(cardPicker.pick(anyDeck())).willReturn(new Card(1));

        player.drawCard();

        assertThat(player.getNumberOfHandCards(), is(equalTo(5)));
        assertThat(player.getNumberOfDeckCards(), is(equalTo(0)));
    }

    @Test
    public void playingCardsReducesPlayersMana() {
        player = aPlayer().withMana(10).withCardsInHand(8, 1);

        player.playCard(new Card(8), aPlayer());
        player.playCard(new Card(1), aPlayer());

        assertThat(player.getMana(), Matchers.is(Matchers.equalTo(1)));
    }

    @Test
    public void playingCardsRemovesThemFromHand() {
        player = aPlayer().withMana(5).withCardsInHand(0, 2, 2, 3);

        player.playCard(new Card(3), aPlayer());
        player.playCard(new Card(2), aPlayer());

        assertThat(player.getNumberOfHandCardsWithManaCost(3), is(equalTo(0)));
        assertThat(player.getNumberOfHandCardsWithManaCost(2), is(equalTo(1)));
    }

    @Test(expected = IllegalMoveException.class)
    public void playingCardWithInsufficientManaShouldFail() {
        player = aPlayer().withMana(3).withCardsInHand(4, 4, 4);
        player.playCard(new Card(4), aPlayer());
    }

    @Test
    public void playingCardCausesDamageToOpponent() {
        player = aPlayer().withMana(10).withCardsInHand(3, 2);
        Player opponent = aPlayer().withHealth(30);

        player.playCard(new Card(3), opponent);
        player.playCard(new Card(2), opponent);

        assertThat(opponent.getHealth(), is(equalTo(25)));
    }

    @Test
    public void playerWithSufficientManaCanPlayCards() {
        player = aPlayer().withMana(2).withCardsInHand(3, 2);

        assertThat(player.canPlayCards(), is(true));
    }

    @Test
    public void playerWithInsufficientManaCannotPlayCards() {
        player = aPlayer().withMana(1).withCardsInHand(3, 2);

        assertThat(player.canPlayCards(), is(false));
    }

    @Test
    public void playerWithEmptyHandCannotPlayCards() {
        player = aPlayer().withNoCardsInHand();

        assertThat(player.canPlayCards(), is(false));
    }

    @Test(expected = IllegalMoveException.class)
    public void playingCardShouldFailWhenStrategyCannotChooseCard() {
        given(strategy.nextCard(anyInt(), anyListOf(Card.class))).willReturn(noCard());
        player.playCard(aPlayer());
    }

    private List<Card> anyDeck() {
        return anyListOf(Card.class);
    }

    private FakePlayer aPlayer() {
        return new FakePlayer(cardPicker, strategy);
    }

    private Optional<Card> noCard() {
        return Optional.empty();
    }

}
