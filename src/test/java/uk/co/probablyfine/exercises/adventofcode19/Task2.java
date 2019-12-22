package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task2 {

    public static void main(String... args) {

        int[] program = {
            // PASTE INPUT FROM TEST
        };

        program[1] = 12;
        program[2] = 2;

        int[] output = IntCode.runIntcode(program);

        System.out.println(output[0]);
    }

    @Test
    public void noOps() {
        int[] program = {};

        assertThat(IntCode.runIntcode(program), is(program));
    }

    @Test
    public void addition() {
        int[] program = {1, 0, 0, 0};

        int[] output = IntCode.runIntcode(program);

        assertThat(output[0], is(2));
    }

    @Test
    public void multiplication() {
        int[] program = {2, 3, 0, 3};

        int[] output = IntCode.runIntcode(program);

        assertThat(output[3], is(6));
    }

    @Test
    public void multipleOpcodes() {
        int[] program = {1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50};

        int[] output = IntCode.runIntcode(program);

        assertThat(output[3], is(70));
        assertThat(output[0], is(3500));
    }

    @Test
    public void halt() {
        int[] program = {
            99, 1, 0, 0, 0,
        };

        int[] output = IntCode.runIntcode(program);

        assertThat(output[0], is(99));
    }

    @Test
    public void exampleFromSpec() {
        int[] program = {1, 1, 1, 4, 99, 5, 6, 0, 99};

        int[] output = IntCode.runIntcode(program);

        assertThat(output[0], is(30));
        assertThat(output[4], is(2));
    }
}
