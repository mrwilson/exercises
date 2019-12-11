package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
        int[] input = {3, 0, 99};

        int[] output = runIntcode(input, input(10), i -> {});

        assertThat(output[0], is(10));
    }

    @Test
    public void testOutput() {
        int[] input = {4, 2, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(99));
    }

    @Test
    public void testLoadAndStore() {
        int[] program = {3, 0, 4, 0, 99};
        int randomInt = new Random().nextInt();

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(program, input(randomInt), output::set);

        assertThat(output.get(), is(randomInt));
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

        runIntcode(program, input(0), output::set);

        assertThat(output.get(), is(10));
    }

    @Test
    public void testJumpIfTrue() {
        int[] input = {105, 0, 10, 0, 0, 0, 0, 0, 0, 0, 104, 33, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(33));
    }

    @Test
    public void testJumpIfFalse() {
        int[] input = {106, 1, 10, 0, 0, 0, 0, 0, 0, 0, 104, 33, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(33));
    }

    @Test
    public void testLessThan() {
        int[] input = {1107, 1, 2, 5, 104, 33, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(1));
    }

    @Test
    public void testLessThan_false() {
        int[] input = {1107, 2, 1, 5, 104, 33, 99};

        AtomicInteger output = new AtomicInteger(1);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(0));
    }

    @Test
    public void testEq() {
        int[] input = {1108, 1, 1, 5, 104, 33, 99};

        AtomicInteger output = new AtomicInteger(0);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(1));
    }

    @Test
    public void testNE() {
        int[] input = {1108, 2, 1, 5, 104, 33, 99};

        AtomicInteger output = new AtomicInteger(1);

        runIntcode(input, input(0), output::set);

        assertThat(output.get(), is(0));
    }


    static Supplier<Integer> input(int... inputs) {

        return new Supplier<>() {

            int index = 0;

            @Override
            public Integer get() {
                return inputs[index++];
            }
        };

    }
}
