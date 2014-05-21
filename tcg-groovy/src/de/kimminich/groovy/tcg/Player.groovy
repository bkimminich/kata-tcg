package de.kimminich.groovy.tcg

import javax.swing.*

class Player {
    static int id = 0

    String name = "Player " + id++
    int health = 30
    int mana = 0
    int maxMana = 0
    ArrayList<Integer> deck = [0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8]
    ArrayList<Integer> hand = []

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

    def playCard(int card, Player opponent) {
        opponent.health -= card
        hand.remove(card as Object)
    }

    def playTurn(Player opponent) {
        String input;
        while ((input = JOptionPane.showInputDialog(playerInfo() + " - Choose card to play: " + hand)) != null) {
            if (input.isInteger()) {
                Integer cardToPlay = input.toInteger()
                if (hand.contains(cardToPlay)) {
                    playCard(cardToPlay, opponent)
                } else {
                    JOptionPane.showMessageDialog(null, "You do not have this card!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input!");
            }
        }
    }

    private String playerInfo() {
        name + " | Health: " + health +  " | Mana: " + mana + "/" + maxMana
    }
}
