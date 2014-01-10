package de.kimminich.kata.tcg;

public class Player {

    private static final int STARTING_HAND_SIZE = 3;

    protected int health = 30;

    protected int manaSlots = 0;
    protected int mana = 0;
    /*
     * The array indexes 0-8 represent the mana cost while the value at that
     * index represents the number of available cards with that cost.
     */
    protected int[] deck = new int[]{2, 2, 3, 4, 3, 2, 2, 1, 1};

    protected int[] hand = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    protected CardPicker cardPicker;

    public Player(CardPicker cardPicker) {
        this.cardPicker = cardPicker;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getNumberOfDeckCardsWithManaCost(int manaCost) {
        return deck[manaCost];
    }

    public int getNumberOfDeckCards() {
        int count = 0;
        for (int numberOfCards : deck) {
            count += numberOfCards;
        }
        return count;
    }

    public Integer getNumberOfHandCardsWithManaCost(int manaCost) {
        return hand[manaCost];
    }

    public int getNumberOfHandCards() {
        int count = 0;
        for (int numberOfCards : hand) {
            count += numberOfCards;
        }
        return count;
    }

    public void drawCard() {
        if (getNumberOfDeckCards() == 0) {
            health--;
        } else {
            int card = cardPicker.pick(deck);
            deck[card]--;
            if (getNumberOfHandCards() < 5) {
                hand[card]++;
            }
        }
    }

    public int getManaSlots() {
        return manaSlots;
    }

    public void giveManaSlot() {
        manaSlots++;
    }

    public void refillMana() {
        mana = manaSlots;
    }

    public void drawStartingHand() {
        for (int i=0; i<STARTING_HAND_SIZE; i++) {
            drawCard();
        }
    }

    public void playCard(int card) {
        if (mana<card) {
            throw new IllegalMoveException("Insufficient Mana (" + mana + ") to pay for card (" + card + ").");
        }
        mana-=card;
        hand[card]--;
    }
}