package uk.co.probablyfine.exercises.adventofcode19;

import java.util.Objects;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4 {

    public static void main(String... args) {

        int start = 235741;
        int finish = 706948;

        long numberOfValidPasswords =
                IntStream.range(start, finish)
                        .mapToObj(Objects::toString)
                        .filter(Task4::validPassword)
                        .count();

        System.out.println(numberOfValidPasswords);
    }

    @Test
    public void atLeastOnePairOfMatchingDigits() {
        assertTrue(validPassword("111111"));
        assertFalse(validPassword("121212"));
    }

    @Test
    public void notDescendingOrder() {
        assertTrue(validPassword("123466"));
        assertFalse(validPassword("664321"));
    }

    private static boolean validPassword(String password) {
        boolean hasADouble = false;
        boolean descending = false;

        for (int i = 0; i < password.length() - 1; i++) {
            hasADouble = hasADouble || (password.charAt(i) == password.charAt(i + 1));
            descending = descending || (password.charAt(i) > password.charAt(i + 1));
        }

        return hasADouble && !descending;
    }
}
