package uk.co.probablyfine.exercises.adventofcode19;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;
import static uk.co.probablyfine.exercises.adventofcode19.Task5.input;

public class Task7 {

    @Test
    public void firstExample() {

        int[] program = {
                3,23,3,24,1002,24,10,24,1002,23,-1,23,
                101,5,23,23,1,24,23,23,4,23,99,0,0
        };


        assertThat(runThrusters(program,0,1,2,3,4), is(54321));
    }

    @Test
    public void secondExample() {

        int[] program = {
                3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0
        };


        assertThat(runThrusters(program,4,3,2,1,0), is(43210));
    }


    private static int runThrusters(int[] program, int... phaseSettings) {
        AtomicInteger returnValue = new AtomicInteger(0);

        runIntcode(program.clone(), input(phaseSettings[0], 0), outA ->
            runIntcode(program.clone(), input(phaseSettings[1], outA), outB ->
                runIntcode(program.clone(), input(phaseSettings[2], outB), outC ->
                    runIntcode(program.clone(), input(phaseSettings[3], outC), outD ->
                        runIntcode(program.clone(), input(phaseSettings[4], outD), returnValue::set)
                    )
                )
            )
        );

        return returnValue.get();
    }

}
