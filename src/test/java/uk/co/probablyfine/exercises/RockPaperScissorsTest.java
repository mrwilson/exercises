package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RockPaperScissorsTest {

    @Test
    public void first() {
        assertThat(play("ROCK", "ROCK"), is("DRAW"));
    }

    private Object play(String rock, String rock1) {
        return "DRAW";
    }
}
