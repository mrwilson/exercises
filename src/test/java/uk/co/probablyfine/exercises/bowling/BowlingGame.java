package uk.co.probablyfine.exercises.bowling;

public record BowlingGame(int score, int numberOfRolls, int lookBackOne, int lookBackTwo) {

    public BowlingGame() {
        this(0, 1, 0, 0);
    }

    public BowlingGame roll(int roll) {
        if (previousFrameWasASpare()) {
            return new BowlingGame(score + 2 * roll, numberOfRolls + 1, roll, lookBackOne);
        }

        if (previousFrameWasAStrike()) {
            return new BowlingGame(
                    score + lookBackOne + 2 * roll, numberOfRolls + 1, roll, lookBackOne);
        }

        if (isAStrike(roll)) {
            return new BowlingGame(score + roll, numberOfRolls + 2, roll, lookBackOne);
        }

        return new BowlingGame(score + roll, numberOfRolls + 1, roll, lookBackOne);
    }

    private boolean previousFrameWasASpare() {
        return lookBackOne < 10 && (lookBackTwo + lookBackOne) == 10 && numberOfRolls % 2 == 1;
    }

    private boolean isAStrike(int roll) {
        return roll == 10 && numberOfRolls % 2 == 1;
    }

    private boolean previousFrameWasAStrike() {
        return lookBackTwo == 10 && numberOfRolls % 2 == 0;
    }
}
