package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.Strategy;

import java.util.ArrayList;

class FakePlayer extends Player {

    private static int playerNo = 0;

    public FakePlayer(Strategy strategy) {
        super("" + playerNo++, strategy);
        this.deck = Card.list(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);
        this.hand = new ArrayList<>();
    }

    public FakePlayer withCardsInDeck(int... manaCost) {
        this.deck = new ArrayList<>();
        for (int cost : manaCost) {
            deck.add(new Card(cost));
        }
        return this;
    }

    public FakePlayer withNoCardsInDeck() {
        this.deck = new ArrayList<>();
        return this;
    }

    public FakePlayer withCardsInHand(int... manaCost) {
        for (int cost : manaCost) {
            hand.add(new Card(cost));
        }
        return this;
    }

    public FakePlayer withNoCardsInHand() {
        this.hand = new ArrayList<>();
        return this;
    }

    public FakePlayer withManaSlots(int manaSlots) {
        this.manaSlots = manaSlots;
        return this;
    }

    public FakePlayer withMana(int mana) {
        this.mana = mana;
        return this;
    }

    public FakePlayer withHealth(int health) {
        this.health = health;
        return this;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

}
