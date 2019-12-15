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

public class Task9 {

    @Test
    public void testRelativeBase() {
        int[] program = {
            109, -1, 204, 1, 99
        };

        Output output = IntCode.output();

        runIntcode(program, input(), output::consume);

        assertThat(output.retrieve(), is(109));
    }



}
