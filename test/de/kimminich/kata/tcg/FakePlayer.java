package de.kimminich.kata.tcg;

class FakePlayer extends Player {

    public FakePlayer(CardPicker cardPicker) {
        super(cardPicker);
        this.deck = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.hand = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public FakePlayer withCardsInDeck(int... manaCost) {
        for (int cost : manaCost) {
            deck[cost] = deck[cost] + 1;
        }
        return this;
    }

    public FakePlayer withNoCardsInDeck() {
        this.deck = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        return this;
    }

    public FakePlayer withCardsInHand(int... manaCost) {
        for (int cost : manaCost) {
            hand[cost] = hand[cost] + 1;
        }
        return this;
    }

    public FakePlayer withNoCardsInHand() {
        this.hand = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
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
}
