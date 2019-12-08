package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task2 {

    public static void main(String... args) {

        int[] input = {
            // PASTE INPUT FROM TEST
        };

        input[1] = 12;
        input[2] = 2;

        int[] output = runIntcode(input);

        System.out.println(output[0]);
    }

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

    @Test
    public void multipleOpcodes() {
        int[] input = {
            1, 9, 10, 3,
            2, 3, 11, 0,
            99,
            30, 40, 50

        };

        int[] output = runIntcode(input);

        assertThat(output[3], is(70));
        assertThat(output[0], is(3500));
    }

    @Test
    public void halt() {
        int[] input = {
            99,
            1, 0, 0, 0,
        };

        int[] output = runIntcode(input);

        assertThat(output[0], is(99));
    }

    @Test
    public void exampleFromSpec() {
        int[] input = {
            1,1,1,4,99,5,6,0,99
        };

        int[] output = runIntcode(input);

        assertThat(output[0], is(30));
        assertThat(output[4], is(2));
    }

    interface Operation {
        int ADD = 1;
        int MULTIPLY = 2;
    }

    private static int[] runIntcode(int[] input) {

        for (int i = 0; i < input.length; i += 4) {
            if (input[i] == Operation.ADD) {
                input[input[i+3]] = input[input[i+1]] + input[input[i+2]];
            } else if (input[i] == Operation.MULTIPLY) {
                input[input[i+3]] = input[input[i+1]] * input[input[i+2]];
            } else if (input[i] == 99) {
                break;
            }
        }

        return input;
    }
}
