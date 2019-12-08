package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task2 {

    @Test
    public void noOps() {
        int[] input = {};

        assertThat(runIntcode(input), is(input));
    }

    private int[] runIntcode(int[] input) {
        return input;
    }
}
