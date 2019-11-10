package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Outcome.DRAW;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Outcome.P1;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Outcome.P2;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.PAPER;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.ROCK;

public class RockPaperScissorsTest {

    enum Throw {
        ROCK,
        PAPER
    }

    enum Outcome {
        DRAW,
        P1,
        P2
    }

    @Test
    public void first() {
        assertThat(play(ROCK, ROCK), is(DRAW));
    }

    @Test
    public void second() {
        assertThat(play(PAPER, ROCK), is(P1));
        assertThat(play(ROCK, PAPER), is(P2));
    }

    private Outcome play(Throw move1, Throw move2) {
        return new Outcome[][] {
            { DRAW, P2 },
            { P1, DRAW }
        }[move1.ordinal()][move2.ordinal()];
    }
}
