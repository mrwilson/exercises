package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task2 {

    @Test
    public void noOps() {
        int[] input = {};

        assertThat(runIntcode(input), is(input));
    }

    @Test
    public void addition() {
        int[] input = {1, 0, 0, 0};

        int[] output = runIntcode(input);

        assertThat(output[0], is(2));
    }

    @Test
    public void multiplication() {
        int[] input = {2, 3, 0, 3};

        int[] output = runIntcode(input);

        assertThat(output[3], is(6));
    }

    private int[] runIntcode(int[] input) {

        for (int i = 0; i < input.length; i++) {
            if (input[i] == 1) {
                input[input[i+3]] = input[input[i+1]] + input[input[i+2]];
            } else if (input[i] == 2) {
                input[input[i+3]] = input[input[i+1]] * input[input[i+2]];
            }
        }

        return input;
    }
}
