package de.kimminich.kata.tcg;

import org.junit.Test;

public class RandomCardPickerTest {

    private RandomCardPicker cardPicker;

    @Test(expected = IllegalArgumentException.class)
    public void pickingCardFromNoCardsShouldFail() {
        cardPicker = new RandomCardPicker();
        cardPicker.pick(new int[] {0,0,0,0,0,0,0,0});
    }

}
