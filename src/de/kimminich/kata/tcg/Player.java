package de.kimminich.kata.tcg;

public class Player {

    private int health = 30;
    private int mana = 0;

    /*
     * The array index 0-8 represents the mana cost while the value at that
     * index represents the number of available cards with that cost.
     */
    private int[] deck = new int[]{2, 2, 3, 4, 3, 2, 2, 1, 1};

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getNumberOfCardsWithManaCost(int manaCost) {
        return deck[manaCost];
    }
}