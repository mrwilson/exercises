package uk.co.probablyfine.exercises.bowling;

public record BowlingGame(int score, int frameScore, int numberOfRolls) {

    public BowlingGame roll(int roll) {
        if (frameScore == 10 && numberOfRolls % 2 == 0) {
            return new BowlingGame(score + roll + roll, roll, numberOfRolls + 1);
        }

        return new BowlingGame(score + roll, frameScore + roll, numberOfRolls + 1);
    }
}
