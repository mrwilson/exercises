package uk.co.probablyfine.exercises.dice;

import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;
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

        assertThat(roll("1d1*3 1d1+1"), is(5));
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
        return new RollDice(() -> new Random().doubles(0.0d, 1.0d)).roll(dice);
    }

    private int roll(String dice, Supplier<DoubleStream> randomness) {
       return new RollDice(randomness).roll(dice);
    }

    private Supplier<DoubleStream> repeatDoubles(double value) {
        return () -> DoubleStream.iterate(value, i -> i);
    }

    private Supplier<DoubleStream> supplyDoubles(double... values) {
        return () -> DoubleStream.of(values);
    }

}
