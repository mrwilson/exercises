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

    @Test
    public void thirdExample() {

        int[] program = {
                3,31,
                3,32,
                1002,32,10,32,
                1001,31,-2,31,
                1007,31,0,33,
                1002,33,7,33,
                1,33,31,31,
                1,32,31,31,
                4,31,
                99,
                0,0,0
        };


        assertThat(runThrusters(program,1,0,4,3,2), is(65210));
    }

    private static int runThrusters(int[] program, int... phaseSettings) {
        AtomicInteger returnValue = new AtomicInteger(0);

        runIntcode(program.clone(), input(phaseSettings[0], 0), returnValue::set);
        runIntcode(program.clone(), input(phaseSettings[1], returnValue.get()), returnValue::set);
        runIntcode(program.clone(), input(phaseSettings[2], returnValue.get()), returnValue::set);
        runIntcode(program.clone(), input(phaseSettings[3], returnValue.get()), returnValue::set);
        runIntcode(program.clone(), input(phaseSettings[4], returnValue.get()), returnValue::set);

        return returnValue.get();
    }

}
