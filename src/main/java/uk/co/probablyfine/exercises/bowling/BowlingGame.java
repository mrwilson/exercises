package uk.co.probablyfine.exercises.bowling;

public record BowlingGame(int score, int numberOfRolls, int lookBackOne, int lookBackTwo) {

    public BowlingGame() {
        this(0, 1, 0, 0);
    }

    public BowlingGame roll(int roll) {
        if (lookBackOne < 10 && (lookBackTwo + lookBackOne) == 10 && numberOfRolls % 2 == 1) {
            return new BowlingGame(score + roll + roll, numberOfRolls + 1, roll, lookBackOne);
        }

        if (lookBackTwo == 10 && numberOfRolls % 2 == 0) {
            return new BowlingGame(score + lookBackOne + 2*roll, numberOfRolls + 1, roll, lookBackOne);
        }

        if (roll == 10 && numberOfRolls % 2 == 1) {
            return new BowlingGame(score + roll, numberOfRolls + 2, roll, lookBackOne);
        }

        return new BowlingGame(score + roll, numberOfRolls + 1, roll, lookBackOne);
    }
}
