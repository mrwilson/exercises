package uk.co.probablyfine.exercises.aoc25;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Day2Test {

    @Test
    void rangeWithNoDuplicatesReturnsNothing() {
        var invalidIds = check(1, 9);

        assertThat(invalidIds.size(), is(0));
    }

    @Test
    void rangeWithDuplicatesReturnsDuplicates() {
        var invalidIds = check(10, 20);

        assertThat(invalidIds, hasItem(11));
    }

    private List<Integer> check(int start, int end) {
        var upper = 1;
        var upper2 = 2;

        return IntStream.range(upper, upper2)
                .map(x ->  x + x*10)
                .filter(x -> start <= x && x <= end)
                .boxed()
                .toList();
    }

}
