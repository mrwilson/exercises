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

    @Test
    public void exampleWithNoStrikesOrSpares() {
        assertThat(roll(6, 3, 7, 1, 9, 0, 4, 4, 3, 2, 8, 1, 7, 2, 1, 6, 3, 5, 0, 8), is(80));
    }

    @Test
    public void exampleWithStrikesAndSpares() {
        assertThat(roll(7, 2, 7, 2, 3, 1, 6, 4, 7, 1, 4, 2, 10, 5, 3, 2, 7, 8, 1), is(97));
    }

    public int roll(int... rolls) {
        return Arrays.stream(rolls)
                .boxed()
                .reduce(new BowlingGame(), BowlingGame::roll, (l, r) -> l)
                .score();
    }
}
