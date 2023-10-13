package de.kimminich.kata.tcg

import de.kimminich.kata.tcg.strategy.AiStrategy
import de.kimminich.kata.tcg.strategy.ConsoleInputStrategy
import java.util.*

class Game internal constructor(player1: Player, player2: Player, startingPlayerChooser: StartingPlayerChooser) {
    var activePlayer: Player

    var opponentPlayer: Player


    constructor(player1: Player, player2: Player) : this(player1, player2, StartingPlayerChooser())

    init {
        activePlayer = startingPlayerChooser.chooseBetween(player1, player2)
        opponentPlayer = if (activePlayer === player1) {
            player2
        } else {
            player1
        }
        println("Players : $activePlayer - $opponentPlayer")
        activePlayer.drawStartingHand()
        opponentPlayer.drawStartingHand()
        opponentPlayer.drawCard() // extra card to reduce disadvantage from being second to play
        println("active : $activePlayer")
        println("opponent : $opponentPlayer")
    }

    fun beginTurn() {
        activePlayer.giveManaSlot()
        activePlayer.refillMana()
        activePlayer.drawCard()
        println("$activePlayer plays turn...")
    }

    fun switchPlayer() {
        println("switching players : $activePlayer - $opponentPlayer")
        val previouslyActivePlayer = activePlayer
        activePlayer = opponentPlayer
        opponentPlayer = previouslyActivePlayer
        println("switching players: : $activePlayer - $opponentPlayer")
    }

    fun endTurn() {
        println("$activePlayer ends turn.")
        switchPlayer()
    }

    val winner: Player?
        get() = if (activePlayer.health < 1) {
            opponentPlayer
        } else if (opponentPlayer.health < 1) {
            activePlayer
        } else {
            null
        }

    fun run() {
        while (true) {
            if (winner == null) {
                beginTurn()
                while (activePlayer.canPlayCards()) {
                    activePlayer.playCard(opponentPlayer)
                }
                endTurn()
            } else {
                println(winner.toString() + " wins the game!")
                break
            }
        }
    }

    class StartingPlayerChooser {
        private val random = Random()
        fun chooseBetween(player1: Player, player2: Player): Player {
            return if (random.nextBoolean()) player1 else player2
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Game(Player("Human", ConsoleInputStrategy()), Player("CPU", AiStrategy())).run()
        }
    }
}
