package uk.co.probablyfine.exercises.bowling;

public record BowlingGame(int score, int lookBackOne, int numberOfRolls, boolean lastFrameWasSpare, boolean lastFrameWasStrike, int lookBackTwo) {

    public BowlingGame() {
        this(0, 0, 1, false, false, 0);
    }

    public BowlingGame roll(int roll) {
        if (lookBackOne < 10 && (lookBackTwo + lookBackOne) == 10 && numberOfRolls % 2 == 1) {
            return new BowlingGame(score + roll + roll, roll, numberOfRolls + 1, false, false, lookBackOne);
        }

        if (lookBackTwo == 10 && numberOfRolls % 2 == 0) {
            return new BowlingGame(score + lookBackOne + 2*roll, roll, numberOfRolls + 1, false, true, lookBackOne);
        }

        if (roll == 10 && numberOfRolls % 2 == 1) {
            return new BowlingGame(score + roll, roll, numberOfRolls + 2, false, true, lookBackOne);
        }

        return new BowlingGame(score + roll, roll, numberOfRolls + 1, lastFrameWasSpare, lastFrameWasStrike, lookBackOne);
    }
}
