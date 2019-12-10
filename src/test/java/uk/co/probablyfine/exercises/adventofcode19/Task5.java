package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;

public class Task5 {

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


}
