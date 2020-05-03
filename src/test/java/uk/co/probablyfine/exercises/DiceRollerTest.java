package uk.co.probablyfine.exercises;

import org.junit.Test;

import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        assertThat(roll("1d2", DoubleStream.of(0.0d)), is(1));
        assertThat(roll("2d2", DoubleStream.of(0.0d, 0.9d)), is(3));
        assertThat(roll("3d2", DoubleStream.of(0.0d, 0.9d, 0.1d)), is(4));
    }

    private int roll(String dice) {
        return roll(dice, new Random().doubles(0.0d, 1.0d));
    }

    private int roll(String dice, DoubleStream randomness) {
        Matcher matcher = Pattern.compile("(\\d+)d(\\d+)").matcher(dice);

        matcher.find();

        int numberOfRolls = Integer.parseInt(matcher.group(1));
        int numberOfSides = Integer.parseInt(matcher.group(2));

        return randomness
                .limit(numberOfRolls)
                .mapToInt(i -> (int) Math.round(Math.floor(i * numberOfSides)) + 1)
                .sum();
    }
}
