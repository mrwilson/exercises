package uk.co.probablyfine.exercises.bowling;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BowlingTest {

    interface Game {
        void roll(int roll);
        int score();
    }


    @Test
    public void simpleFrameScoreIsTotalOfRolls() {

        Game game = new Game() {
            private int total = 0;

            @Override
            public void roll(int roll) {
                total += roll;
            }

            @Override
            public int score() {
                return total;
            }
        };

        game.roll(6);
        game.roll(3);

        assertThat(game.score(), is(9));

    }


}
