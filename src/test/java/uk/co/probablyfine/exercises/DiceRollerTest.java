package uk.co.probablyfine.exercises;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

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
        String[] arguments = dice.split("");


        int numberOfRolls = Integer.parseInt(arguments[0]);
        int numberOfSides = Integer.parseInt(arguments[2]);

        return randomness
                .limit(numberOfRolls)
                .mapToInt(i -> (int) Math.round(Math.floor(i * numberOfSides)) + 1)
                .sum();
    }
}
