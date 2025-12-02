package uk.co.probablyfine.exercises.aoc25;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Day2Test {

    @Test
    void rangeWithNoDuplicatesReturnsNothing() {
        var invalidIds = check(1, 9);

        assertThat(invalidIds.size(), is(0));
    }

    private List<Integer> check(int start, int end) {
        return Collections.emptyList();
    }

}
