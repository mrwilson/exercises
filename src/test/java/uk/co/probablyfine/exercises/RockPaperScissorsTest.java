package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.PAPER;
import static uk.co.probablyfine.exercises.RockPaperScissorsTest.Throw.ROCK;

public class RockPaperScissorsTest {

    enum Throw {
        ROCK,
        PAPER;
    }

    @Test
    public void first() {
        assertThat(play(ROCK, ROCK), is("DRAW"));
    }

    @Test
    public void second() {
        assertThat(play(PAPER, ROCK), is("P1"));
        assertThat(play(ROCK, PAPER), is("P2"));
    }

    private Object play(Throw move1, Throw move2) {
        return play(move1.name(), move2.name());
    }

    private Object play(String move1, String move2) {
        if("PAPER".equals(move1) && "ROCK".equals(move2)) {
            return "P1";
        } else if ("ROCK".equals(move1) && "PAPER".equals(move2)) {
            return "P2";
        } else {
            return "DRAW";
        }
    }
}
