package de.kimminich.groovy.tcg

class Game {
    Player activePlayer = new Player()
    Player opponentPlayer = new Player()

    def start() {
        3.times {
            activePlayer.drawCard()
            opponentPlayer.drawCard()
        }
    }

    def beginTurn() {
        (activePlayer, opponentPlayer) = [opponentPlayer, activePlayer]
        if (activePlayer.maxMana < 10) {
            activePlayer.maxMana++
        }
        activePlayer.mana = activePlayer.maxMana

    }
}
