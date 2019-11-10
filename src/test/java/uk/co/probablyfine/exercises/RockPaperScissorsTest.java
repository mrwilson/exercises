package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Outcome.DRAW;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Outcome.P1;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Outcome.P2;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.PAPER;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.ROCK;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.SCISSORS;

public class RockPaperScissorsTest {

    enum Throw {
        ROCK,
        PAPER,
        SCISSORS
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
        assertThat(play(PAPER, PAPER), is(DRAW));
    }

    @Test
    public void third() {
        assertThat(play(PAPER, SCISSORS), is(P2));
        assertThat(play(ROCK, SCISSORS), is(P1));

        assertThat(play(SCISSORS, ROCK), is(P2));
        assertThat(play(SCISSORS, PAPER), is(P1));
    }

    private Outcome play(Throw move1, Throw move2) {
        return new Outcome[][] {
            { DRAW, P2, P1 },
            { P1, DRAW, P2 },
            { P2, P1 }
        }[move1.ordinal()][move2.ordinal()];
    }
}
