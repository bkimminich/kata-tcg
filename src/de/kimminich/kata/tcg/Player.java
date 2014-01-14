package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.Strategy;

import java.util.Arrays;
import java.util.OptionalInt;

import static java.util.Arrays.stream;

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

    protected final CardPicker cardPicker;
    private final Strategy strategy;
    private final String name;

    public Player(String name, CardPicker cardPicker, Strategy strategy) {
        this.name = name;
        this.cardPicker = cardPicker;
        this.strategy = strategy;
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
        return stream(deck).sum();
    }

    public Integer getNumberOfHandCardsWithManaCost(int manaCost) {
        return hand[manaCost];
    }

    public int getNumberOfHandCards() {
        return stream(hand).sum();
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
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            drawCard();
        }
    }

    public void playCard(int card, Player opponent) {
        if (mana < card) {
            throw new IllegalMoveException("Insufficient Mana (" + mana + ") to pay for card (" + card + ").");
        }
        System.out.println(this + " plays card: " + card);
        mana -= card;
        hand[card]--;
        opponent.receiveDamage(card);
    }

    private void receiveDamage(int damage) {
        health -= damage;
    }

    public boolean canPlayCards() {
        if (getNumberOfHandCards() > 0) {
            for (int i = 1; i < hand.length; i++) {
                if (hand[i] > 0 && mana >= i) return true;
            }
        }
        return false;
    }

    public void playCard(Player opponent) {
        OptionalInt card = strategy.nextCard(mana, denormalizeArray(hand));
        if (card.isPresent()) {
            playCard(card.getAsInt(), opponent);
        } else {
            throw new IllegalMoveException("No card can be played from hand " + Arrays.toString(hand) + " with (" + mana + ") mana.");
        }
    }

    @Override
    public String toString() {
        return "Player:" + name + "{" +
                "health=" + health +
                ", mana=" + mana + "/" + manaSlots +
                ", hand=" + Arrays.toString(denormalizeArray(hand)) +
                ", deck=" + Arrays.toString(denormalizeArray(deck)) +
                '}';
    }

    private int[] denormalizeArray(int[] cards) {
        int[] result = new int[stream(cards).sum()];
        int pos = 0;
        for (int manaCost = 0; manaCost < cards.length; manaCost++) {
            for (int count = 0; count < cards[manaCost]; count++) {
                result[pos] = manaCost;
                pos++;
            }
        }
        return result;
    }

}
