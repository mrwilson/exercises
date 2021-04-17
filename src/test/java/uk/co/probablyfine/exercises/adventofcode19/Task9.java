package uk.co.probablyfine.exercises.adventofcode19;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.input;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;

import org.junit.jupiter.api.Test;
import uk.co.probablyfine.exercises.adventofcode19.IntCode.Output;

public class Task9 {

    @Test
    public void testRelativeBase() {
        int[] program = {109, -1, 204, 1, 99};

        Output output = IntCode.output();

        runIntcode(program, input(), output::consume);

        assertThat(output.retrieve(), is(109));
    }
}
