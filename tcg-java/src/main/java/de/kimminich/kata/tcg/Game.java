package de.kimminich.kata.tcg;

import de.kimminich.kata.tcg.strategy.AiStrategy;
import de.kimminich.kata.tcg.strategy.ConsoleInputStrategy;

import java.util.Random;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private Player activePlayer;
    private Player opponentPlayer;

    public Game(Player player1, Player player2) {
        this(player1, player2, new StartingPlayerChooser());
    }

    Game(Player player1, Player player2, StartingPlayerChooser startingPlayerChooser) {
        activePlayer = startingPlayerChooser.chooseBetween(player1, player2);
        if (activePlayer == player1) {
            opponentPlayer = player2;
        } else {
            opponentPlayer = player1;
        }
        activePlayer.drawStartingHand();
        opponentPlayer.drawStartingHand();
        opponentPlayer.drawCard(); // extra card to reduce disadvantage from being second to play
    }

    public void beginTurn() {
        activePlayer.giveManaSlot();
        activePlayer.refillMana();
        activePlayer.drawCard();
        logger.info(activePlayer + " plays turn...");
    }

    void switchPlayer() {
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
        while (true) {
            beginTurn();
            if (getWinner() == null) {
                while (activePlayer.canPlayCards()) {
                    activePlayer.playCard(opponentPlayer);
                }
                endTurn();
            } else {
                logger.info(getWinner() + " wins the game!");
                break;
            }
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public static void main(String... args) {
        new Game(new Player("Human", new ConsoleInputStrategy()), new Player("CPU", new AiStrategy())).run();
    }

     static class StartingPlayerChooser {

         private Random random = new Random();

         public Player chooseBetween(Player player1, Player player2) {
            return random.nextBoolean() ? player1 : player2;
         }

     }

}
