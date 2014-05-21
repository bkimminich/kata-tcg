package de.kimminich.groovy.tcg

import spock.lang.Specification
import spock.lang.Unroll

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
        game.beginTurn()

        then:
        game.activePlayer == previouslyInactivePlayer
    }

    @Unroll("receiving +1 max. mana to #currentMaxMana current max. mana gives the player #expectedMaxMana max. mana and replenishes mana also to #expectedMaxMana")
    def "every turn the active player receives 1 max. mana and his mana is fully replenished"() {
        given:
        game = new Game()
        game.opponentPlayer = new Player(mana: 0, maxMana: currentMaxMana)

        when:
        game.beginTurn()

        then:
        game.activePlayer.maxMana == expectedMaxMana
        game.activePlayer.mana == expectedMaxMana

        where:
        currentMaxMana | expectedMaxMana
        0              | 1
        1              | 2
        2              | 3
        3              | 4
        4              | 5
        5              | 6
        6              | 7
        7              | 8
        8              | 9
        9              | 10
    }

    def "max. mana is capped at a value of 10"() {
        given:
        game = new Game()
        game.opponentPlayer = new Player(maxMana: 10)

        when:
        game.beginTurn()

        then:
        game.activePlayer.maxMana == 10
    }
}
