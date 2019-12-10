package uk.co.probablyfine.exercises.adventofcode19;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class Task5 {

    public static void main(String... args) {
        int[] program = {
            // Paste instructions here
        };

        List<Integer> outputs = new ArrayList<>();

        runIntcode(program, 1, outputs::add);

        System.out.println(outputs);
    }

    @Test
    public void testStore() {
        int[] input = {3, 0, 99};

        int[] output = runIntcode(input, 10, i -> {});

        assertThat(output[0], is(10));
    }

    @Test
    public void testOutput() {
        int[] input = {4, 2, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(input, 0, output::set);

        assertThat(output.get(), is(99));
    }

    @Test
    public void testLoadAndStore() {
        int[] program = {3, 0, 4, 0, 99};

        int input = new Random().nextInt();
        AtomicInteger output = new AtomicInteger(0);

        runIntcode(program, input, output::set);

        assertThat(output.get(), is(input));
    }

    @Test
    public void testOpcodeWithPositions() {
        int[] input = {1002, 4, 3, 4, 33};

        int[] output = runIntcode(input);

        assertThat(output[4], is(99));
    }

    @Test
    public void testOpcodeWithPositions_addition() {
        int[] input = {1101, 10, 10, 4, 99};

        int[] output = runIntcode(input);

        assertThat(output[4], is(20));
    }

    @Test
    public void testOpcodeWithPositions_return() {
        int[] program = {104, 10, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(program, 0, output::set);

        assertThat(output.get(), is(10));
    }
}
