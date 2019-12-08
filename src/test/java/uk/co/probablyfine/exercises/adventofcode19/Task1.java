package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task1 {

    @Test
    public void firstExample() {
        assertThat(fuelRequiredForMass(12), is(2));
    }

    @Test
    public void secondExample() {
        assertThat(fuelRequiredForMass(1969), is(654));
    }

    private int fuelRequiredForMass(int mass) {
        return (mass / 3) - 2;
    }
}
