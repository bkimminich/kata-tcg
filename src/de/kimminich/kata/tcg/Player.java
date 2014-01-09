package de.kimminich.kata.tcg;

public class Player {

    private static final int STARTING_HAND_SIZE = 3;

    private int health = 30;
    private int mana = 0;

    /*
     * The array indexes 0-8 represent the mana cost while the value at that
     * index represents the number of available cards with that cost.
     */
    protected int[] deck = new int[]{2, 2, 3, 4, 3, 2, 2, 1, 1};
    protected int[] hand = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

    protected CardPicker cardPicker;

    public Player(CardPicker cardPicker) {
        this.cardPicker = cardPicker;
        for (int i=0; i<STARTING_HAND_SIZE; i++) {
            drawCard();
        }
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
            deck[card] = deck[card] - 1;
            if (getNumberOfHandCards() < 5) {
                hand[card] = hand[card] + 1;
            }
        }
    }
}