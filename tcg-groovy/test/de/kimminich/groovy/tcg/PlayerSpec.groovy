package de.kimminich.groovy.tcg

import spock.lang.Specification

class PlayerSpec extends Specification {

    def player

    def "player should be properly initialized"() {
        setup:
        player = new Player()

        expect: "player initial health should be 30"
        player.health == 30
        and: "player should have no mana"
        player.mana == 0
        player.maxMana == 0
        and: "player should have default card deck"
        player.deck == [0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8]
        and: "player should hold no cards in his hand"
        player.hand == []
    }

}