package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.ConsoleInputStrategy;
import de.kimminich.kata.tcg.strategy.HighestCardFirstStrategy;

import java.util.Random;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());

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
        logger.info(activePlayer + " plays turn...");
    }

    private void switchPlayer() {
        Player previouslyActivePlayer = activePlayer;
        activePlayer = opponentPlayer;
        opponentPlayer = previouslyActivePlayer;
    }

    public void endTurn() {
        logger.info(activePlayer + " ends turn.");
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

    public void run() {
        while (getWinner() == null) {
            beginTurn();
            while (activePlayer.canPlayCards()) {
                activePlayer.playCard(opponentPlayer);
            }
            endTurn();
        }
        logger.info(getWinner() + " wins the game!");
    }

    public static void main(String... args) {
        new Game(new Player("Human", new ConsoleInputStrategy()), new Player("CPU", new HighestCardFirstStrategy())).run();
    }
}
