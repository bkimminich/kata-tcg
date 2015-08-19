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
        and: "player should have no minions on the board"
        player.minions == []
    }

    def "drawing a card should move one card from the deck into the hand"() {
        given:
        player = new Player(deck: [1, 2], hand: [])

        when:
        player.drawCard()

        then:
        (player.deck == [1] && player.hand == [2]) || (player.deck == [2] && player.hand == [1])
    }

    def "drawing a card from a depleted deck should cause one damage to player"() {
        given:
        player = new Player(health: 30, deck: [])

        when:
        player.drawCard()

        then:
        player.health == 29
    }

    def "drawing a card when hand already contains five cards causes taken card to be dropped from overload"() {
        given:
        player = new Player(deck: [8], hand: [1, 2, 3, 4, 5])

        when:
        player.drawCard()

        then:
        player.deck == []
        player.hand == [1, 2, 3, 4, 5]
    }

    @Unroll("attacking with a card of value #card diminishes opponent health from 30 to #expectedHealth")
    def "attacking with a card causes damage equal to mana cost to opponent player"() {
        given:
        player = new Player()
        and:
        Player opponent = new Player(health: 30)

        when:
        player.playCard(card, opponent, "a")

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

    @Unroll("healing with a card of value #card increases own health from 23 to #expectedHealth")
    def "healing with a card increases own health equal to mana cost without healing above 30"() {
        given:
        player = new Player(health: 23)

        when:
        player.playCard(card, new Player(), "h")

        then:
        player.health == expectedHealth

        where:
        card | expectedHealth
        0    | 23
        1    | 24
        2    | 25
        3    | 26
        4    | 27
        5    | 28
        6    | 29
        7    | 30
        8    | 30
    }

    @Unroll("placing a minion with a card of value #card creates a minion with #expectedHealth health and #expectedAttack attack")
    def "placing a minion with a card creates a minion with health equal to mana cost (but at least 1) and attack equal to mana cost"() {
        given:
        player = new Player()

        when:
        player.playCard(card, new Player(), "m")

        then:
        player.minions[0].health == expectedHealth
        player.minions[0].attack == expectedAttack

        where:
        card | expectedHealth | expectedAttack
        0    | 1              | 0
        1    | 1              | 1
        2    | 2              | 2
        3    | 3              | 3
        4    | 4              | 4
        5    | 5              | 5
        6    | 6              | 6
        7    | 7              | 7
        8    | 8              | 8
    }

    def "playing a card removes that card from the hand"() {
        given:
        player = new Player(hand: [1, 2, 3, 4, 5])

        when:
        player.playCard(2, new Player())

        then:
        player.hand == [1, 3, 4, 5]
    }

    def "player should be able to display all his current stats"() {
        setup:
        player = new Player(health: 29, mana: 6, maxMana: 7)

        expect:
        player.playerInfo() ==~ /Player \d* \| Health: 29 \| Mana: 6\/7/

    }

    def "invalid choices for card to play should not be accepted"() {
        given:
        OptionPane optionPane = Mock(OptionPane)
        optionPane.showInputDialog(_ as String) >>> ["aa", "abc", "a4", "a3", "x3", null]
        and:
        player = new Player(hand: [1, 2, 3], mana: 2, optionPane: optionPane)

        when:
        player.playTurn(new Player())

        then:
        player.hand == [1, 2, 3]
    }

    def "player can play multiple cards as long as he has sufficient mana"() {
        given:
        OptionPane optionPane = Mock(OptionPane)
        optionPane.showInputDialog(_ as String) >>> ["a1", "a2", null]
        and:
        player = new Player(hand: [1, 2, 5], mana: 3, optionPane: optionPane)

        when:
        player.playTurn(new Player())

        then:
        player.hand == [5]
    }

    def "player can choose to use a card for attacking his opponent"() {
        given:
        OptionPane optionPane = Mock(OptionPane)
        optionPane.showInputDialog(_ as String) >>> ["a3", null]
        and:
        player = new Player(hand: [3], mana: 3, optionPane: optionPane)
        and:
        Player opponent = new Player(health: 30)

        when:
        player.playTurn(opponent)

        then:
        opponent.health == 27

    }

    def "player can choose to use a card for healing himself"() {
        given:
        OptionPane optionPane = Mock(OptionPane)
        optionPane.showInputDialog(_ as String) >>> ["h3", null]
        and:
        player = new Player(health: 8, hand: [3], mana: 3, optionPane: optionPane)

        when:
        player.playTurn(new Player())

        then:
        player.health == 11

    }

    def "player can choose higher card for healing but will not heal beyond initial health of 30"() {
        given:
        OptionPane optionPane = Mock(OptionPane)
        optionPane.showInputDialog(_ as String) >>> ["h8", null]
        and:
        player = new Player(health: 28, hand: [8], mana: 8, optionPane: optionPane)

        when:
        player.playTurn(new Player())

        then:
        player.health == 30

    }

    def "attacking is the default action for playing cards when no action is specified"() {
        given:
        OptionPane optionPane = Mock(OptionPane)
        optionPane.showInputDialog(_ as String) >>> ["4", null]
        and:
        player = new Player(hand: [4], mana: 4, optionPane: optionPane)
        and:
        Player opponent = new Player(health: 30)

        when:
        player.playTurn(opponent)

        then:
        opponent.health == 26

    }

}