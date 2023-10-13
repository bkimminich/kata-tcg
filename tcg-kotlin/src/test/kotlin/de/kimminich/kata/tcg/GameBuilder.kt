package de.kimminich.kata.tcg

import org.mockito.kotlin.given
import org.mockito.kotlin.mock

class GameBuilder internal constructor() {
    private var activePlayer: Player? = null
    private var opponentPlayer: Player? = null
    private var startingPlayerChooser: Game.StartingPlayerChooser = mock()
    fun build(): Game {
        if(activePlayer == null) throw ExceptionInInitializerError("activePlayer must not be null")
        if(opponentPlayer == null) throw ExceptionInInitializerError("opponentPlayer must not be null")
        given(startingPlayerChooser.chooseBetween(activePlayer!!, opponentPlayer!!)).willReturn(activePlayer)
        return Game(activePlayer!!, opponentPlayer!!, startingPlayerChooser)
    }

    fun withActivePlayer(activePlayer: Player): GameBuilder {
        this.activePlayer = activePlayer
        return this
    }

    fun withOpponentPlayer(opponentPlayer: Player): GameBuilder {
        this.opponentPlayer = opponentPlayer
        return this
    }

    companion object {

        fun aGame(): GameBuilder {
            return GameBuilder()
        }


        fun anyGame(): Game {
            return  GameBuilder()
                .withActivePlayer(PlayerBuilder.anyPlayer())
                .withOpponentPlayer(PlayerBuilder.anyPlayer())
                .build()
        }
    }
}
