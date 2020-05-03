package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DiceRollerTest {

    @Test
    public void oneSidedDiceReturnsSameValue() {

        assertThat(roll("1d1"), is(1));
        assertThat(roll("2d1"), is(2));
        assertThat(roll("3d1"), is(3));
    }

    private int roll(String dice) {
        String[] arguments = dice.split("");

        return Integer.parseInt(arguments[0]);
    }
}
