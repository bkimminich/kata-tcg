package de.kimminich.groovy.tcg

import spock.lang.Specification

class PlayerSpec extends Specification {

    def player

    def "player should have 30 life initially"() {
        when:
        player = new Player()

        then:
        player.health == 30
    }

}