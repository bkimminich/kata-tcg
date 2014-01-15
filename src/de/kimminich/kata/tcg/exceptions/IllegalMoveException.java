package de.kimminich.kata.tcg.exceptions;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(String message) {
        super(message);
    }

}
