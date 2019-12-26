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

        IntCode.runIntcode(program);

        System.out.println(program[0]);
    }

    @Test
    public void noOps() {
        int[] program = {};

        IntCode.runIntcode(program);

        assertThat(program.length, is(0));
    }

    @Test
    public void addition() {
        int[] program = {1, 0, 0, 0};

        IntCode.runIntcode(program);

        assertThat(program[0], is(2));
    }

    @Test
    public void multiplication() {
        int[] program = {2, 3, 0, 3};

        IntCode.runIntcode(program);

        assertThat(program[3], is(6));
    }

    @Test
    public void multipleOpcodes() {
        int[] program = {1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50};

        IntCode.runIntcode(program);

        assertThat(program[3], is(70));
        assertThat(program[0], is(3500));
    }

    @Test
    public void halt() {
        int[] program = {
            99, 1, 0, 0, 0,
        };

        IntCode.runIntcode(program);

        assertThat(program[0], is(99));
    }

    @Test
    public void exampleFromSpec() {
        int[] program = {1, 1, 1, 4, 99, 5, 6, 0, 99};

        IntCode.runIntcode(program);

        assertThat(program[0], is(30));
        assertThat(program[4], is(2));
    }
}
