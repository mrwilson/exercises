package uk.co.probablyfine.exercises.adventofcode19;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class Task6 {

    @Test
    public void testSingleCase() {
        String[] input = {"COM)A", "A)B"};

        assertThat(numberOfOrbits(input), is(3));
    }

    @Test
    public void testSpec() {
        String[] input = {
            "COM)B", "B)C", "D)E", "E)F", "C)D", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L"
        };

        assertThat(numberOfOrbits(input), is(42));
    }

    private static int numberOfOrbits(String[] input) {
        // Absolute garbage but done using maps lol

        Map<String, List<String>> counts = new HashMap<>();

        counts.put("COM", new ArrayList<>());

        for (String orbit : input) {
            String[] objects = orbit.split("\\)");

            counts.computeIfAbsent(objects[0], x -> new ArrayList<>()).add(objects[1]);
        }

        int depth = 0;

        Map<Integer, List<String>> nums = new HashMap<>();

        nums.put(0, List.of("COM"));

        while (!counts.isEmpty()) {
            List<String> collect =
                    nums.get(depth).stream()
                            .map(key -> counts.getOrDefault(key, Collections.emptyList()))
                            .flatMap(List::stream)
                            .collect(Collectors.toList());

            nums.get(depth).forEach(counts::remove);

            nums.put(depth + 1, collect);

            depth++;
        }

        return nums.entrySet().stream().mapToInt(x -> x.getKey() * x.getValue().size()).sum();
    }
}
