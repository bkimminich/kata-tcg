package de.kimminich.groovy.tcg

class Player {
    int health = 30
    int mana = 0
    int maxMana = 0
    ArrayList<Integer> deck = [0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8]
    ArrayList<Integer> hand = []

    def drawCard() {
        Collections.shuffle(deck)
        hand.add(deck.take(1))
        deck = deck.drop(1)
    }
}
