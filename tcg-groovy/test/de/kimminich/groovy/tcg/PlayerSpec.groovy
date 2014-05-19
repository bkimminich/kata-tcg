package de.kimminich.groovy.tcg

import spock.lang.Specification
import spock.lang.Unroll

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
        player.deck == [0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8]
        and: "player should hold no cards in his hand"
        player.hand == []
    }

    def "drawing a card should move one card from the deck into the hand"() {
        given:
        player = new Player()
        player.deck = [1, 2]
        player.hand = []

        when:
        player.drawCard()

        then:
        (player.deck == [1] && player.hand == [2]) || (player.deck == [2] && player.hand == [1])
    }

    def "drawing a card from a depleted deck should cause one damage to player"() {
        given:
        player = new Player()
        player.health = 30
        player.deck = []

        when:
        player.drawCard()

        then:
        player.health == 29
    }

    def "drawing a card when hand already contains five cards causes taken card to be dropped from overload"() {
        given:
        player = new Player()
        player.deck = [8]
        player.hand = [1, 2, 3, 4, 5]

        when:
        player.drawCard()

        then:
        player.deck == []
        player.hand == [1, 2, 3, 4, 5]
    }

    @Unroll("playing a card with value #card diminishes opponent health from 30 to #expectedHealth")
    def "playing a card causes damage equal to mana cost to opponent player"() {
        given:
        player = new Player()
        and:
        Player opponent = new Player()
        opponent.health = 30

        when:
        player.playCard(card, opponent)

        then:
        opponent.health == expectedHealth

        where:
        card | expectedHealth
        0    | 30
        1    | 29
        2    | 28
        3    | 27
        4    | 26
        5    | 25
        6    | 24
        7    | 23
        8    | 22
    }

}