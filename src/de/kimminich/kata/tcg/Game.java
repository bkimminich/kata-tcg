package de.kimminich.kata.tcg;

import java.util.Random;

public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;

    private Random random = new Random();

    public Game(Player player1, Player player2) {
       this.player1 = player1;
       player1.drawStartingHand();

       this.player2 = player2;
       player2.drawStartingHand();

       activePlayer = random.nextBoolean() ? player1 : player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void beginTurn() {
        if (activePlayer.getManaSlots() < 10) {
            activePlayer.giveManaSlot();
        }
        activePlayer.refillMana();
    }

    private void switchPlayer() {
        if (activePlayer == player1) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
    }

    public void endTurn() {
        switchPlayer();
    }
}
