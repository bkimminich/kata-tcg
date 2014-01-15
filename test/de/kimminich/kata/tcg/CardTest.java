package de.kimminich.kata.tcg;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class CardTest {

    @Test
    public void cardShouldCauseDamageEqualToManaCost() {
        assertThat(new Card(1).getDamage(), is(equalTo(1)));
        assertThat(new Card(2).getDamage(), is(equalTo(2)));
        assertThat(new Card(3).getDamage(), is(equalTo(3)));
        assertThat(new Card(4).getDamage(), is(equalTo(4)));
        assertThat(new Card(5).getDamage(), is(equalTo(5)));
        assertThat(new Card(6).getDamage(), is(equalTo(6)));
        assertThat(new Card(7).getDamage(), is(equalTo(7)));
        assertThat(new Card(8).getDamage(), is(equalTo(8)));
    }

}
