package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.KamikazeStrategy;
import de.kimminich.kata.tcg.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;

public class PlayerBuilder {

    private int health = 30;
    private int manaSlots = 0;
    private int mana = 0;
    private List<Card> deck = Card.list(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);
    private List<Card> hand = new ArrayList<>();
    private Strategy strategy = new KamikazeStrategy();
    private String name = "Player" + playerNo++;

    private static int playerNo = 0;

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }

    public Player build() {
        return new Player(name, strategy, health, manaSlots, mana, deck, hand);
    }

    public PlayerBuilder withCardsInDeck(Integer... manaCost) {
        this.deck = stream(manaCost).map(Card::new).collect(toCollection(ArrayList::new));
        return this;
    }

    public PlayerBuilder withNoCardsInDeck() {
        this.deck = new ArrayList<>();
        return this;
    }

    public PlayerBuilder withCardsInHand(Integer... manaCost) {
        this.hand = stream(manaCost).map(Card::new).collect(toCollection(ArrayList::new));
        return this;
    }

    public PlayerBuilder withNoCardsInHand() {
        this.hand = new ArrayList<>();
        return this;
    }

    public PlayerBuilder withManaSlots(int manaSlots) {
        this.manaSlots = manaSlots;
        return this;
    }

    public PlayerBuilder withMana(int mana) {
        this.mana = mana;
        return this;
    }

    public PlayerBuilder withHealth(int health) {
        this.health = health;
        return this;
    }

}
