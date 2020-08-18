package uk.co.probablyfine.exercises.bowling;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BowlingTest {

    private final BowlingGame game = new BowlingGame(0);

    @Test
    public void simpleFrameScoreIsTotalOfRolls() {
        assertThat(game.roll(6).roll(3).score(), is(9));
    }

}
