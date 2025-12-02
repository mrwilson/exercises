package uk.co.probablyfine.exercises.aoc25;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

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

    @Test
    void rangeWithDuplicatesReturnsMultipleDuplicates() {
        var invalidIds = check(10, 30);

        assertThat(invalidIds, hasItems(11, 22));
    }

    private List<Integer> check(int start, int end) {

        var startDigits = (int) Math.log10(start) + 1;
        var endDigits = (int) Math.log10(end) + 1;

        if (startDigits == 1 && endDigits == 1) {
            return Collections.emptyList();
        }

        var startHalf = (int) (start / Math.pow(10, startDigits / 2));
        var endHalf = (int) (end / Math.pow(10, endDigits / 2));

        return IntStream.range(startHalf, endHalf)
                .map(x -> x + (int) Math.round(x * Math.pow(10, endDigits / 2.0)))
                .filter(x -> start <= x && x <= end)
                .boxed()
                .toList();
    }
}
