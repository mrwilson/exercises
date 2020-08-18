package uk.co.probablyfine.exercises.bowling;

public record BowlingGame(int score) {

    public BowlingGame roll(int roll) {
        return new BowlingGame(score + roll);
    }
}
