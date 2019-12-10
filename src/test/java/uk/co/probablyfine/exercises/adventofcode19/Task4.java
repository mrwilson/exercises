package uk.co.probablyfine.exercises.adventofcode19;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Task4 {


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

    private boolean validPassword(String password) {
        boolean hasADouble = false;
        boolean descending = false;

        for (int i = 0; i < password.length() - 1; i++) {
            hasADouble = hasADouble || (password.charAt(i) == password.charAt(i+1));
            descending = descending || (password.charAt(i) > password.charAt(i+1));
        }

        return hasADouble && !descending;
    }

}
