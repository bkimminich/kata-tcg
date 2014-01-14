package de.kimminich.kata.tcg;

import java.util.Random;

public class Game {

    protected Player activePlayer;
    protected Player opponentPlayer;

    private Random random = new Random();

    public Game(Player player1, Player player2) {
        activePlayer = random.nextBoolean() ? player1 : player2;
        if (activePlayer == player1) {
            opponentPlayer = player2;
        } else {
            opponentPlayer = player1;
        }
        activePlayer.drawStartingHand();
        opponentPlayer.drawStartingHand();

    }

    public Player getActivePlayer() {
        return activePlayer;
    }
    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void beginTurn() {
        if (activePlayer.getManaSlots() < 10) {
            activePlayer.giveManaSlot();
        }
        activePlayer.refillMana();
        activePlayer.drawCard();
    }

    private void switchPlayer() {
        Player previouslyActivePlayer = activePlayer;
        activePlayer = opponentPlayer;
        opponentPlayer = previouslyActivePlayer;
    }

    public void endTurn() {
        switchPlayer();
    }

    public Player getWinner() {
        if (activePlayer.getHealth() < 1) {
            return opponentPlayer;
        } else if (opponentPlayer.getHealth() < 1) {
            return activePlayer;
        } else {
            return null;
        }
    }

}
