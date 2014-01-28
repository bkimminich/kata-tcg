package de.kimminich.kata.tcg.syntactic;

import de.kimminich.kata.tcg.Player;
import de.kimminich.kata.tcg.PlayerBuilder;

public class PlayerSugar {

    public static PlayerBuilder aPlayer() {
        return new PlayerBuilder();
    }

    public static Player anyPlayer() {
        return aPlayer().build();
    }

}
