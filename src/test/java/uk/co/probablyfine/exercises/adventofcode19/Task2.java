package uk.co.probablyfine.exercises.adventofcode19;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.input;

import org.junit.Test;
import uk.co.probablyfine.exercises.adventofcode19.IntCode.Output;

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

    private final Output output = IntCode.output();

    @Test
    public void noOps() {
        int[] program = {};

        IntCode.runIntcode(program);

        assertThat(program.length, is(0));
    }

    @Test
    public void addition() {
        int[] program = {1, 0, 0, 0, 4, 0, 99};

        IntCode.runIntcode(program, input(), output::consume);

        assertThat(output.retrieve(), is(2));
    }

    @Test
    public void multiplication() {
        int[] program = {2, 3, 0, 3, 4, 3, 99};

        IntCode.runIntcode(program, input(), output::consume);

        assertThat(output.retrieve(), is(6));
    }

    @Test
    public void multipleOpcodes() {
        int[] program = {1, 13, 14, 3, 2, 3, 15, 0, 4, 3, 4, 0, 99, 30, 40, 50};

        IntCode.runIntcode(program, input(), output::consume);

        assertThat(output.retrieve(), is(3500));
        assertThat(output.retrieve(), is(70));
    }

    @Test
    public void halt() {
        int[] program = {
            99, 4, 0,
        };

        IntCode.runIntcode(program, input(), output::consume);

        assertThat(output.size(), is(0));
    }

    @Test
    public void exampleFromSpec() {
        int[] program = {1, 1, 1, 4, 99, 5, 6, 0, 4, 0, 4, 4, 99};

        IntCode.runIntcode(program, input(), output::consume);

        assertThat(output.retrieve(), is(2));
        assertThat(output.retrieve(), is(30));
    }
}
