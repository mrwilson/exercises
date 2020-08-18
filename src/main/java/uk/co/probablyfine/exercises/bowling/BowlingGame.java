package uk.co.probablyfine.exercises.bowling;

public record BowlingGame(int score, int frameScore, int numberOfRolls, boolean lastFrameWasSpare, boolean lastFrameWasStrike) {

    public BowlingGame() {
        this(0, 0, 1, false, false);
    }

    public BowlingGame roll(int roll) {
        if (lastFrameWasSpare && numberOfRolls % 2 == 1) {
            return new BowlingGame(score + roll + roll, 0, numberOfRolls + 1, false, false);
        }

        if ((frameScore + roll) == 10 && numberOfRolls % 2 == 0) {
            return new BowlingGame(score + roll, 0, numberOfRolls + 1, true, false);
        }

        if (roll == 10 && numberOfRolls % 2 == 1) {
            return new BowlingGame(score + roll, 0, numberOfRolls + 2, false, true);
        }

        if (lastFrameWasStrike && numberOfRolls % 2 == 0) {
            return new BowlingGame(score + frameScore + 2*roll, 0, numberOfRolls + 1, false, false);
        }

        return new BowlingGame(score + roll, numberOfRolls % 2 == 0 ? 0 : frameScore + roll, numberOfRolls + 1, lastFrameWasSpare, lastFrameWasStrike);
    }
}
