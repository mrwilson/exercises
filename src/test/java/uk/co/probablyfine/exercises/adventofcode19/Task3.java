package uk.co.probablyfine.exercises.adventofcode19;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.Test;

public class Task3 {

    @Test
    public void example() {
        List<Pair> wire1 = streamify("R8,U5,L5,D3");
        List<Pair> wire2 = streamify("U7,R6,D4,L4");

        assertThat(wireCross(wire1, wire2), is(6));
    }

    @Test
    public void example2() {
        List<Pair> wire1 = streamify("R75,D30,R83,U83,L12,D49,R71,U7,L72");
        List<Pair> wire2 = streamify("U62,R66,U55,R34,D71,R55,D58,R83");

        assertThat(wireCross(wire1, wire2), is(159));
    }

    private static List<Pair> streamify(String s) {
        int x = 0, y = 0;

        Stream.Builder<Pair> builder = Stream.builder();

        for (String action : s.split(",")) {

            int count = Integer.parseInt(action.substring(1));

            for (int i = 0; i < count; i++) {

                switch (action.charAt(0)) {
                    case 'U':
                        y++;
                        break;
                    case 'D':
                        y--;
                        break;
                    case 'L':
                        x--;
                        break;
                    case 'R':
                        x++;
                        break;
                    default:
                        break;
                }

                builder.add(new Pair(x, y));
            }
        }

        return builder.build().collect(toList());
    }

    private static int wireCross(List<Pair> wire1, List<Pair> wire2) {

        return wire1.stream()
                .filter(wire2::contains)
                .mapToInt(pair -> Math.abs(pair.x) + Math.abs(pair.y))
                .min()
                .orElse(0);
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
