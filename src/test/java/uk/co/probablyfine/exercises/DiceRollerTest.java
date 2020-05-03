package uk.co.probablyfine.exercises;

import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DiceRollerTest {

    @Test
    public void oneSidedDiceReturnsSameValue() {
        assertThat(roll("1d1"), is(1));
        assertThat(roll("2d1"), is(2));
        assertThat(roll("3d1"), is(3));
    }

    @Test
    public void twoSidedDiceRequireRandomness() {
        assertThat(roll("1d2", supplyDoubles(0.0d)), is(1));
        assertThat(roll("2d2", supplyDoubles(0.0d, 0.9d)), is(3));
        assertThat(roll("3d2", supplyDoubles(0.0d, 0.9d, 0.1d)), is(4));
    }

    @Test
    public void doubleDigitSidesAndNumberOfRolls() {
        assertThat(roll("10d10", repeatDoubles(0.0d)), is(10));
        assertThat(roll("10d10", repeatDoubles(0.9d)), is(100));
    }

    @Test
    public void abilityToPerformOperationsOnRolls() {
        assertThat(roll("1d1+1"), is(2));
        assertThat(roll("1d1-1"), is(0));
        assertThat(roll("1d1*3"), is(3));
    }

    @Test
    public void addMultipleRollsTogether() {
        assertThat(roll("1d1 1d1"), is(2));
    }

    @Test(expected = InvalidDiceRollException.class)
    public void throwWhenInputIsInvalid() {
        roll("not a dice roll");
    }

    @Test(expected = InvalidDiceRollException.class)
    public void throwWhenOperationSyntaxIsInvalid() {
        roll("1d1/3");
    }

    private int roll(String dice) {
        return roll(dice, () -> new Random().doubles(0.0d, 1.0d));
    }

    private int roll(String dice, Supplier<DoubleStream> randomness) {
        Matcher matcher = Pattern.compile("(\\d+)d(\\d+)(([+-\\\\*])(\\d+))?(\\w((\\d+)d(\\d+)(([+-\\\\*])(\\d+))?))*").matcher(dice);

        List<MatchResult> matchResults = matcher.results().collect(Collectors.toList());

        if (matchResults.isEmpty()) {
            throw new InvalidDiceRollException("["+dice+"] is not a valid dice-roll pattern");
        }

        return matchResults
            .stream()
            .mapToInt(match -> {

                int numberOfRolls = Integer.parseInt(match.group(1));
                int numberOfSides = Integer.parseInt(match.group(2));

                int sum = randomness
                        .get()
                        .limit(numberOfRolls)
                        .mapToInt(i -> (int) Math.round(Math.floor(i * numberOfSides)) + 1)
                        .sum();

                if (match.group(3) != null) {
                    switch (match.group(4)) {
                        case "+": return sum + Integer.parseInt(match.group(5));
                        case "-": return sum - Integer.parseInt(match.group(5));
                        case "*": return sum * Integer.parseInt(match.group(5));
                        default:
                            throw new InvalidDiceRollException("["+match.group(0)+"] is not a valid dice-roll operation");
                    }

                } else {
                    return sum;
                }
            })
                .sum();
    }

    private Supplier<DoubleStream> repeatDoubles(double value) {
        return () -> DoubleStream.iterate(value, i -> i);
    }

    private Supplier<DoubleStream> supplyDoubles(double... values) {
        return () -> DoubleStream.of(values);
    }

    private static class InvalidDiceRollException extends RuntimeException {
        public InvalidDiceRollException(String message) {
            super(message);
        }
    }
}
