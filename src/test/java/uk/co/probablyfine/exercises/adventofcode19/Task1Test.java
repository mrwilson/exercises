package uk.co.probablyfine.exercises.adventofcode19;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.input;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;

import org.junit.jupiter.api.Test;

public class Task1Test {

    @Test
    public void firstExample() {
        assertThat(fuelRequiredForMass(12), is(2));
    }

    @Test
    public void secondExample() {
        assertThat(fuelRequiredForMass(1969), is(654));
    }

    @Test
    public void recurseExample() {
        assertThat(totalFuelRequiredForMass(1969), is(966));
    }

    @Test
    public void recurseExample2() {
        assertThat(totalFuelRequiredForMass(100756), is(50346));
    }

    private static int totalFuelRequiredForMass(int mass) {
        int extraFuel = fuelRequiredForMass(mass);
        int totalFuel = 0;

        while (extraFuel > 1) {
            totalFuel += extraFuel;
            extraFuel = fuelRequiredForMass(extraFuel);
        }

        return totalFuel;
    }

    private static int fuelRequiredForMass(int mass) {

        int[] program = {

            // Store input in [a]
            3,
            24,

            // Subtract 3 from [a]
            101,
            -3,
            24,
            24,

            // Add 1 to [b]
            101,
            1,
            25,
            25,

            // Save to [c] whether [a] > 3
            1007,
            24,
            3,
            26,

            // If [a] > 3, go to loop above
            1006,
            26,
            2,

            // Subtract 2 from [b]
            101,
            -2,
            25,
            25,

            // Return [b]
            4,
            25,

            // Halt
            99,

            // Registers: 24[a], 25[b], 26[c]
            0,
            0,
            0,
        };

        IntCode.Output output = IntCode.output();

        runIntcode(program, input(mass), output::consume);

        return output.retrieve();
    }
}
