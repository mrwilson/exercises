package uk.co.probablyfine.exercises.adventofcode19;

import java.util.Arrays;

import static java.util.Comparator.comparingLong;

public class Task8 {

    public static void main(String... args) {

        // Input
        String[] split = "".split("(?<=\\G.{150})");

        Arrays.stream(split)
            .min(comparingLong(layer -> countCharacters(layer, '0')))
            .map(s -> countCharacters(s, '1') * countCharacters(s, '2'))
            .ifPresent(System.out::println);

    }

    private static long countCharacters(String s, char c) {
        return s.chars().filter(x -> x == c).count();
    }
}
