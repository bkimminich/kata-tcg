package de.kimminich.groovy.tcg

class Minion {

    int health
    int maxHealth
    int damage

    String stats() {
        "Minion | Health: " + health + "/" + maxHealth + " | Damage: " + damage
    }


}
