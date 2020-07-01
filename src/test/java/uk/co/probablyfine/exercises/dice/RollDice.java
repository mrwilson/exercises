package uk.co.probablyfine.exercises.dice;

import static java.util.stream.Collectors.toList;

import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;

public class RollDice {

    private final Supplier<DoubleStream> randomness;

    RollDice(Supplier<DoubleStream> randomness) {
        this.randomness = randomness;
    }

    public int roll(String dice) {
        var diceRolls =
                Pattern.compile(
                                "(\\d+)d(\\d+)(([+-\\\\*])(\\d+))?(\\w((\\d+)d(\\d+)(([+-\\\\*])(\\d+))?))*")
                        .matcher(dice)
                        .results()
                        .collect(toList());

        if (diceRolls.isEmpty()) {
            throw new InvalidDiceRollException("[" + dice + "] is not a valid dice-roll pattern");
        }

        return diceRolls.stream().mapToInt(this::rollSingleDiceExpression).sum();
    }

    private int rollSingleDiceExpression(MatchResult match) {
        int numberOfRolls = Integer.parseInt(match.group(1));
        int numberOfSides = Integer.parseInt(match.group(2));

        int sum =
                randomness
                        .get()
                        .limit(numberOfRolls)
                        .mapToInt(i -> (int) Math.round(Math.floor(i * numberOfSides)) + 1)
                        .sum();

        if (match.group(3) != null) {
            switch (match.group(4)) {
                case "+":
                    return sum + Integer.parseInt(match.group(5));
                case "-":
                    return sum - Integer.parseInt(match.group(5));
                case "*":
                    return sum * Integer.parseInt(match.group(5));
                default:
                    throw new InvalidDiceRollException(
                            "[" + match.group(0) + "] is not a valid dice-roll operation");
            }
        } else {
            return sum;
        }
    }
}
