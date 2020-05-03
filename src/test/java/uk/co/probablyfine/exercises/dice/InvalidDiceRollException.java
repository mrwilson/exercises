package uk.co.probablyfine.exercises.dice;

public class InvalidDiceRollException extends RuntimeException {
    public InvalidDiceRollException(String message) {
        super(message);
    }
}
