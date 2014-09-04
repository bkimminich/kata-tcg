package de.kimminich.groovy.tcg

import javax.swing.*

class Game {
    Player activePlayer = new Player()
    Player opponentPlayer = new Player()

    Player winner = null

    def gameLoop() {
        start()
        while (winner == null) {
            beginTurn()
            activePlayer.playTurn(opponentPlayer)
            endTurn()
        }
        end()
    }

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
        activePlayer.drawCard()
    }

    def endTurn() {
        if (activePlayer.health < 1)
            winner = opponentPlayer
        else if (opponentPlayer.health < 1)
            winner = activePlayer
    }

    def end() {
        if (winner != null)
            JOptionPane.showMessageDialog(null, "Game over! Winner is: " + winner.name)
    }

    static main(args) {
        new Game().gameLoop()
    }
}


