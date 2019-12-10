package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode19.IntCode.runIntcode;

public class Task6 {


    @Test
    public void testSingleCase() {
        String[] input = {
            "COM)A",
            "A)B"
        };

        assertThat(numberOfOrbits(input), is(3));
    }

    private int numberOfOrbits(String[] input) {
        Map<String, Integer> counts = new HashMap<>();

        counts.put("COM", 0);

        for(String orbit : input) {
            String[] objects = orbit.split("\\)");

            counts.put(objects[1], counts.get(objects[0]) + 1);
        }

        return counts.values().stream().mapToInt(x -> x).sum();

    }
}
