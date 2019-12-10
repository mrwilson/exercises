package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task5 {

    @Test
    public void testStore() {
        int[] input = {3, 0, 99};

        int[] output = IntCode.runIntcode(input, 10);

        assertThat(output[0], is(10));
    }

}
