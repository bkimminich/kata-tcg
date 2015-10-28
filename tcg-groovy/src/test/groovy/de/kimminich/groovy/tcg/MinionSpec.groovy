package de.kimminich.groovy.tcg

import spock.lang.Specification
import spock.lang.Unroll

class MinionSpec extends Specification {

    def minion

    def "minion should be able to display all his current stats"() {
        setup:
        minion = new Minion(health: 3, maxHealth: 8, damage: 8)

        expect:
        minion.stats() ==~ /Minion \| Health: 3\/8 \| Damage: 8/

    }

}