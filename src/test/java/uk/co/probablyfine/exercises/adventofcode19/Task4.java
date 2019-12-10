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

    private boolean validPassword(String password) {
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i+1)) return true;
        }

        return false;
    }

}
