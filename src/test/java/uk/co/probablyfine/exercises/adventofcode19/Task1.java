package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task1 {

    @Test
    public void firstExample() {
        assertThat(fuelRequiredForMass(12), is(2));
    }

    private int fuelRequiredForMass(int mass) {
        return 2;
    }
}
