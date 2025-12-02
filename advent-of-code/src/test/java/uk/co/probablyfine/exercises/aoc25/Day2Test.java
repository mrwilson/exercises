package uk.co.probablyfine.exercises.aoc25;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class Day2Test {

    @Test
    void rangeWithNoDuplicatesReturnsNothing() {
        var invalidIds = check(1, 9);

        assertThat(invalidIds.count(), is(0L));
    }

    @Test
    void rangeWithDuplicatesReturnsDuplicates() {
        var invalidIds = check(10, 20);

        assertThat(invalidIds.boxed().toList(), hasItem(11));
    }

    @Test
    void rangeWithDuplicatesReturnsMultipleDuplicates() {
        var invalidIds = check(10, 30);

        assertThat(invalidIds.boxed().toList(), hasItems(11, 22));
    }

    private IntStream check(int start, int end) {
        return IntStream.range(start, end)
                .filter(x -> ((int) Math.log10(x)) % 2 == 1)
                .filter(
                        x -> {
                            var digits = Math.pow(10, (int) Math.log10(x));
                            var upper = Math.floor(x / digits);
                            var lower = x % digits;
                            return upper == lower;
                        });
    }
}
