package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;
import uk.co.probablyfine.exercises.adventofcode19.IntCode.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.input;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;

public class Task5 {

    public static void main(String... args) {
        int[] program = {
            // Paste instructions here
        };

        List<Integer> outputs = new ArrayList<>();

        runIntcode(program, input(1), outputs::add);

        System.out.println(outputs);
    }

    @Test
    public void testStore() {
        int[] program = {3, 0, 99};

        int[] output = runIntcode(program, input(10), i -> {});

        assertThat(output[0], is(10));
    }

    @Test
    public void testOutput() {
        int[] program = {4, 2, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(99));
    }

    @Test
    public void testLoadAndStore() {
        int[] program = {3, 0, 4, 0, 99};
        int randomInt = new Random().nextInt();

        Output output = IntCode.output();

        runIntcode(program, input(randomInt), output::consume);

        assertThat(output.retrieve(), is(randomInt));
    }

    @Test
    public void testOpcodeWithPositions() {
        int[] program = {1002, 4, 3, 4, 33};

        int[] output = runIntcode(program);

        assertThat(output[4], is(99));
    }

    @Test
    public void testOpcodeWithPositions_addition() {
        int[] program = {1101, 10, 10, 4, 99};

        int[] output = runIntcode(program);

        assertThat(output[4], is(20));
    }

    @Test
    public void testOpcodeWithPositions_return() {
        int[] program = {104, 10, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(10));
    }

    @Test
    public void testJumpIfTrue() {
        int[] program = {1105, 1, 10, 0, 0, 0, 0, 0, 0, 0, 104, 33, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(33));
    }

    @Test
    public void testJumpIfFalse() {
        int[] program = {1106, 0, 10, 0, 0, 0, 0, 0, 0, 0, 104, 33, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(33));
    }

    @Test
    public void testLessThan() {
        int[] program = {1107, 1, 2, 5, 104, 33, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(1));
    }

    @Test
    public void testLessThan_false() {
        int[] program = {1107, 2, 1, 5, 104, 33, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(0));
    }

    @Test
    public void testEq() {
        int[] program = {1108, 1, 1, 5, 104, 33, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(1));
    }

    @Test
    public void testNE() {
        int[] program = {1108, 2, 1, 5, 104, 33, 99};

        Output output = IntCode.output();

        runIntcode(program, input(0), output::consume);

        assertThat(output.retrieve(), is(0));
    }

    @Test
    public void example() {
        int[] program = {
            3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0,
            0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4,
            20, 1105, 1, 46, 98, 99
        };

        Output output = IntCode.output();

        runIntcode(program, input(7), output::consume);
        assertThat(output.retrieve(), is(999));

        runIntcode(program, input(8), output::consume);
        assertThat(output.retrieve(), is(1000));

        runIntcode(program, input(9), output::consume);
        assertThat(output.retrieve(), is(1001));
    }

}
