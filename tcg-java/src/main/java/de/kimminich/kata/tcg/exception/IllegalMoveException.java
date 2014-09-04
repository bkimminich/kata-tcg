package de.kimminich.kata.tcg.exception;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(String message) {
        super(message);
    }

}
