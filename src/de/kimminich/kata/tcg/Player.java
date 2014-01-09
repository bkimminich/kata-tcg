package de.kimminich.kata.tcg;

public class Player {

    private int health = 30;
    private int mana = 0;

    private int[] deck = new int[] {2};

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