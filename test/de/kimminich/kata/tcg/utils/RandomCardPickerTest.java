package de.kimminich.kata.tcg.utils;

import de.kimminich.kata.tcg.Card;
import org.junit.Test;

import java.util.Collections;

public class RandomCardPickerTest {

    private RandomCardPicker cardPicker;

    @Test(expected = IllegalArgumentException.class)
    public void pickingCardFromNoCardsShouldFail() {
        cardPicker = new RandomCardPicker();
        cardPicker.pick(Collections.<Card>emptyList());
    }

}
