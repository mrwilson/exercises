package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DiceRollerTest {

    @Test
    public void oneSidedDiceReturnsSameValue() {

        assertThat(roll("1d1"), is(1));

    }

    private int roll(String dice) {
        return 1;
    }
}
