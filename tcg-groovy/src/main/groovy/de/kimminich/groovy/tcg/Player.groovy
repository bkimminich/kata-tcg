package de.kimminich.groovy.tcg

class Player {
    static int id = 0

    String name = "Player " + id++
    int health = 30
    int mana = 0
    int maxMana = 0
    ArrayList<Integer> deck = [0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8]
    ArrayList<Integer> hand = []
    ArrayList<Minion> minions = []

    OptionPane optionPane = new DefaultOptionPane()

    def drawCard() {
        if (deck.isEmpty()) {
            health--
        } else {
            Collections.shuffle(deck)
            if (hand.size() < 5) {
                hand.addAll(deck.take(1))
            }
            deck = deck.drop(1)
        }
    }

    static attack(int card, Player opponent) {
        opponent.health -= card
    }

    def heal(int card) {
        health = Math.min(health + card, 30)
    }

    def placeMinion(int card) {
        if (minions.size() < 3) {
            int health = Math.max(card,1)
            minions.add(new Minion(health: health, maxHealth: health, damage: card))
        } else {
            mana += card
            hand.add(card)
            optionPane.showMessageDialog("You cannot have more than 3 minions at a time!")
        }
    }

    def playCard(int card, Player opponent) {
        playCard(card, opponent, "a")
    }

    def playCard(int card, Player opponent, String action) {
        mana -= card
        hand.remove(card as Object)
        switch (action) {
            case "a":
                attack(card, opponent)
                break
            case "h":
                heal(card)
                break
            case "m":
                placeMinion(card)
                break
            default:
                attack(card, opponent)
                break
        }
    }

    def playTurn(Player opponent) {
        String input;
        while ((input = optionPane.showInputDialog(stats() + " - Choose action (<a>ttack, <h>eal, <m>inion) and card to play: " + hand)) != null) {
            if (isValid(input)) {
                String action = input[0]
                Integer cardToPlay = input[-1..-1].toInteger()
                if (hand.contains(cardToPlay)) {
                    if (mana >= cardToPlay) {
                        playCard(cardToPlay, opponent, action)
                    } else {
                        optionPane.showMessageDialog("You do not have enough mana to play this card!")
                    }
                } else {
                    optionPane.showMessageDialog("You do not have this card!")
                }
            } else {
                optionPane.showMessageDialog("Invalid input!")
            }
        }
    }

    static boolean isValid(String input) {
        input ==~ /[ahm]?[0-8]/
    }

    String stats() {
        name + " | Health: " + health + " | Mana: " + mana + "/" + maxMana
    }
}
