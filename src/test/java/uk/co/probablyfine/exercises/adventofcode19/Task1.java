package uk.co.probablyfine.exercises.adventofcode19;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Task1 {

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

    private int totalFuelRequiredForMass(int mass) {
        int extraFuel = fuelRequiredForMass(mass);
        int totalFuel = 0;

        while (extraFuel > 1) {
            totalFuel += extraFuel;
            extraFuel = fuelRequiredForMass(extraFuel);
        }

        return totalFuel;
    }

    private int fuelRequiredForMass(int mass) {
        return (mass / 3) - 2;
    }
}
