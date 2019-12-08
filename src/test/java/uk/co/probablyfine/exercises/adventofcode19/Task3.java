package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task3 {

    @Test
    public void noOps() {
        int[][] wire1Points = {
                {0, 0},
                {1, 0},
                {1, 1}
        };

        int[][] wire2Points = {
                {0, 0},
                {0, 1},
                {1, 1}
        };

        assertThat(wireCross(
            Arrays.stream(wire1Points),
            Arrays.stream(wire2Points)
        ), is(2));

    }

    private int wireCross(Stream<int[]> wire1, Stream<int[]> wire2) {
        return Stream.concat(wire1, wire2)
        .map(this::toPair)
        .distinct()
        .mapToInt(pair -> pair.x + pair.y)
        .max()
        .orElse(0);
    }

    private Pair toPair(int[] ints) {
        return new Pair(
            ints[0],
            ints[1]
        );
    }

    static class Pair {
        final int x;
        final int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
