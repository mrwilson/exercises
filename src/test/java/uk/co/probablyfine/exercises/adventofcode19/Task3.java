package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Task3 {

    @Test
    public void noOps() {
        int[][] wire1Points = {
                {1, 0},
                {1, 1}
        };

        int[][] wire2Points = {
                {0, 1},
                {1, 1}
        };

        assertThat(wireCross(
            Arrays.stream(wire1Points),
            Arrays.stream(wire2Points)
        ), is(2));

    }

    @Test
    public void example() {
        Stream<int[]> wire1 = streamify("R8,U5,L5,D3");
        Stream<int[]> wire2 = streamify("U7,R6,D4,L4");

        assertThat(wireCross(wire1, wire2), is(6));
    }

    private Stream<int[]> streamify(String s) {
        int x = 0, y = 0;

        Stream.Builder<int[]> builder = Stream.builder();

        for (String action : s.split(",")) {

            int count = Integer.parseInt(action.substring(1));

            for (int i = 0; i < count; i++) {

                switch (action.charAt(0)) {
                    case 'U':
                        y++; break;
                    case 'D':
                        y--; break;
                    case 'L':
                        x--; break;
                    case 'R':
                        x++; break;
                    default:
                        break;
                }

                builder.add(new int[] {x, y});
            }
        }

        return builder.build();
    }

    private int wireCross(Stream<int[]> wire1, Stream<int[]> wire2) {
        return Stream.concat(wire1, wire2)
            .map(this::toPair)
            .collect(groupingBy(x -> x, counting()))
            .entrySet()
            .stream()
            .filter(x -> x.getValue() > 1)
            .map(Map.Entry::getKey)
            .mapToInt(pair -> pair.x + pair.y)
            .min()
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (x != pair.x) return false;
            return y == pair.y;
        }
    }

}
