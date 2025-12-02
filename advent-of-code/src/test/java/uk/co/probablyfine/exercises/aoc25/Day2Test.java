package uk.co.probablyfine.exercises.aoc25;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;

public class Day2Test extends BaseTest<List<Day2Test.Range>, Long> {

    public static final Predicate<String> DOUBLE_DIGITS =
            Pattern.compile("(\\d+)(\\1)").asMatchPredicate();

    public static final Predicate<String> AT_LEAST_DOUBLE_DIGITS =
            Pattern.compile("(\\d+)(\\1)+").asMatchPredicate();

    public Day2Test() {
        super(2);
    }

    @Override
    List<Range> parse(String input) {
        return Arrays.stream(input.split(","))
                .map(x -> x.split("-"))
                .map(x -> new Range(Long.parseLong(x[0]), Long.parseLong(x[1])))
                .toList();
    }

    public record Range(long start, long end) {}

    @Test
    void rangeWithNoDuplicatesReturnsNothing() {
        var invalidIds = check(1, 9, DOUBLE_DIGITS);

        assertThat(invalidIds.count(), is(0L));
    }

    @Test
    void rangeWithDuplicatesReturnsDuplicates() {
        var invalidIds = check(10, 20, DOUBLE_DIGITS);

        assertThat(invalidIds.boxed().toList(), hasItem(11L));
    }

    @Test
    void rangeWithDuplicatesReturnsMultipleDuplicates() {
        var invalidIds = check(10, 30, DOUBLE_DIGITS);

        assertThat(invalidIds.boxed().toList(), hasItems(11L, 22L));
    }

    @Test
    void part1Example() {
        assertThat(part1(testData), is(1227775554L));
    }

    @Test
    void part2Example() {
        assertThat(part2(testData), is(4174379265L));
    }

    private static LongStream check(long start, long end, Predicate<String> numberMatcher) {
        return LongStream.rangeClosed(start, end)
                .parallel()
                .filter(x -> numberMatcher.test(Long.toString(x)));
    }

    @Override
    public Long part1(List<Range> input) {
        return input.parallelStream()
                .flatMapToLong(x -> check(x.start, x.end, DOUBLE_DIGITS))
                .sum();
    }

    @Override
    public Long part2(List<Range> input) {
        return input.parallelStream()
                .flatMapToLong(x -> check(x.start, x.end, AT_LEAST_DOUBLE_DIGITS))
                .sum();
    }

    public static void main(String... args) {
        new Day2Test().run();
    }
}
