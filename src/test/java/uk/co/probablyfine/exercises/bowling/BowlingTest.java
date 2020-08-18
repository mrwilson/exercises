package uk.co.probablyfine.exercises.bowling;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import org.junit.Test;

public class BowlingTest {

    @Test
    public void simpleFrameScoreIsTotalOfRolls() {
        assertThat(roll(6, 3), is(9));
    }

    @Test
    public void sparesAddNextRollToTotal() {
        assertThat(roll(6, 4, 5), is(10 + 5 + 5));
    }

    @Test
    public void strikesAddExtra10ToTotal() {
        assertThat(roll(10, 5, 4), is(10 + 9 + 9));
    }

    public int roll(int... rolls) {
        return Arrays.stream(rolls)
                .boxed()
                .reduce(new BowlingGame(), BowlingGame::roll, (l, r) -> l)
                .score();
    }
}
