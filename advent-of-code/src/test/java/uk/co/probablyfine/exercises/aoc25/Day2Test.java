package uk.co.probablyfine.exercises.aoc25;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class Day2Test {

    public static final Predicate<String> DOUBLE_DIGITS =
            Pattern.compile("(\\d+)(\\1)").asMatchPredicate();

    @Test
    void part1Example() {
        var input =
                "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";

        var t =
                Arrays.stream(input.split(","))
                        .map(x -> x.split("-"))
                        .flatMapToInt(x -> check(Integer.parseInt(x[0]), Integer.parseInt(x[1])))
                        .sum();

        assertThat(t, is(1227775554));
    }

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
        return IntStream.rangeClosed(start, end)
                .mapToObj(Integer::toString)
                .filter(x -> x.length() % 2 == 0)
                .filter(DOUBLE_DIGITS)
                .mapToInt(Integer::parseInt);
    }
}
