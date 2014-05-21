package de.kimminich.groovy.tcg

import spock.lang.Specification

class GameSpec extends Specification {

    def Game game;

    def "game should have two players"() {
        setup:
        game = new Game()

        expect:
        game.activePlayer != null
        game.opponentPlayer != null
    }

    def "players should both start the game with 3 cards from their deck in their hands"() {
        given:
        game = new Game()

        when:
        game.start()

        then:
        game.activePlayer.hand.size() == 3
        game.activePlayer.deck.size() == 17
        and:
        game.opponentPlayer.hand.size() == 3
        game.opponentPlayer.deck.size() == 17
    }

    def "every turn the active player changes"() {
        given:
        game = new Game()
        Player previouslyInactivePlayer = game.opponentPlayer

        when:
        game.turn()

        then:
        game.activePlayer == previouslyInactivePlayer
    }

}