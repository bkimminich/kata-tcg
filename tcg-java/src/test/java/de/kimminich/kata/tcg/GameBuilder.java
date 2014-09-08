package de.kimminich.kata.tcg;

import org.mockito.BDDMockito;
import org.mockito.Mock;

import static de.kimminich.kata.tcg.PlayerBuilder.anyPlayer;
import static org.mockito.BDDMockito.*;

public class GameBuilder {

    private Player activePlayer = anyPlayer();
    private Player opponentPlayer = anyPlayer();

    Game.StartingPlayerChooser startingPlayerChooser = mock(Game.StartingPlayerChooser.class);

    GameBuilder() {
    }

    public static GameBuilder aGame() {
        return new GameBuilder();
    }

    public static Game anyGame() {
        return aGame().build();
    }

    public Game build() {
        given(startingPlayerChooser.chooseBetween(activePlayer, opponentPlayer)).willReturn(activePlayer);
        Game game = new Game(activePlayer, opponentPlayer, startingPlayerChooser);
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
