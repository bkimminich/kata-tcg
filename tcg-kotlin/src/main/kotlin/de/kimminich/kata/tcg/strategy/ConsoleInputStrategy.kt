package de.kimminich.kata.tcg.strategy

import de.kimminich.kata.tcg.Action
import de.kimminich.kata.tcg.Action.NO_MOVE
import de.kimminich.kata.tcg.Card
import de.kimminich.kata.tcg.Move
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

class ConsoleInputStrategy : Strategy() {

    override fun nextMove(availableMana: Int, currentHealth: Int, availableCards: List<Card>): Move {
        try {
            val br = BufferedReader(InputStreamReader(System.`in`, Charset.defaultCharset()))
            var card = -1
            var action = Action.DAMAGE
            while (card < 0 || card > 8 || card > availableMana || !availableCards.contains(Card(card))) {
                try {
                    var input = br.readLine()
                    if (input.endsWith("h")) {
                        action = Action.HEALING
                        input = input.replace("h", "")
                    }
                    card = Integer.decode(input)
                } catch (e: NumberFormatException) {
                    println("Invalid input: " + e.message)
                }
            }
            return Move(Card(card), action)
        } catch (e: IOException) {
            println("Could not read console input: " + e.message)
        }
        return Move(Card(-1), NO_MOVE)
    }
}
