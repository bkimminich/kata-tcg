package de.kimminich.kata.tcg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    private Player player;

    @Mock
    private CardPicker cardPicker;

    @Before
    public void setUp() {
        player = new Player(cardPicker);
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
    public void drawingACardShouldPutOneCardFromDeckIntoHand() {
        // given
        player = aPlayer().withCardsInDeck(1).withNoCardsInHand();
        // when
        player.drawCard();
        // then
        assertThat(player.getNumberOfHandCards(), is(equalTo(1)));
        assertThat(player.getNumberOfDeckCards(), is(equalTo(0)));
    }

    @Test
    public void drawingACardShouldPutThatCardFromDeckIntoHand() {
        // given
        player = aPlayer().withCardsInDeck(1,1,2).withNoCardsInHand();
        given(cardPicker.pick(anyDeck())).willReturn(1);
        // when
        player.drawCard();
        // then
        assertThat(player.getNumberOfHandCardsWithManaCost(1), is(equalTo(1)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(1), is(equalTo(1)));
        assertThat(player.getNumberOfDeckCardsWithManaCost(2), is(equalTo(1)));
    }

    @Test
    public void playerShouldTakeOneDamageWhenDrawingFromEmptyDeck() {
        // given
        player = aPlayer().withNoCardsInDeck();
        int preDrawHealth = player.getHealth();
        // when
        player.drawCard();
        // then
        assertThat(player.getHealth(), is(equalTo(preDrawHealth-1)));
    }

    @Test
    public void shouldDiscardDrawnCardWhenHandSizeIsFive() {
        // given
        player = aPlayer().withCardsInDeck(1).withCardsInHand(1, 2, 3, 4, 5);
        given(cardPicker.pick(anyDeck())).willReturn(1);
        // when
        player.drawCard();
        // then
        assertThat(player.getNumberOfHandCards(), is(equalTo(5)));
        assertThat(player.getNumberOfDeckCards(), is(equalTo(0)));
    }

    private int[] anyDeck() {
        return any(int[].class);
    }

    private FakePlayer aPlayer() {
        return new FakePlayer(cardPicker);
    }

}
