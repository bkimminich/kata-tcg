package de.kimminich.kata.tcg;

import org.junit.Test;

import static de.kimminich.kata.tcg.syntactic.CardSugar.aCardWithManaCost;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CardTest {

    @Test
    public void cardsShouldCauseDamageEqualToManaCost() {
        assertThat(aCardWithManaCost(0).getDamage(), is(equalTo(0)));
        assertThat(aCardWithManaCost(1).getDamage(), is(equalTo(1)));
        assertThat(aCardWithManaCost(2).getDamage(), is(equalTo(2)));
        assertThat(aCardWithManaCost(3).getDamage(), is(equalTo(3)));
        assertThat(aCardWithManaCost(4).getDamage(), is(equalTo(4)));
        assertThat(aCardWithManaCost(5).getDamage(), is(equalTo(5)));
        assertThat(aCardWithManaCost(6).getDamage(), is(equalTo(6)));
        assertThat(aCardWithManaCost(7).getDamage(), is(equalTo(7)));
        assertThat(aCardWithManaCost(8).getDamage(), is(equalTo(8)));
    }


}
