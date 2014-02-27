package de.kimminich.kata.tcg;

import static de.kimminich.kata.tcg.PlayerBuilder.anyPlayer;

public class GameBuilder {

    private Player activePlayer = anyPlayer();
    private Player opponentPlayer = anyPlayer();

    GameBuilder() {
    }

    public static GameBuilder aGame() {
        return new GameBuilder();
    }

    public static Game anyGame() {
        return aGame().build();
    }

    public Game build() {
        Game game = new Game(activePlayer, opponentPlayer);
        if (game.getActivePlayer() != activePlayer) {
            game.switchPlayer();
        }
        return game;
    }

    public GameBuilder withActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
        return this;
    }

    public GameBuilder withOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
        return this;
    }

}
