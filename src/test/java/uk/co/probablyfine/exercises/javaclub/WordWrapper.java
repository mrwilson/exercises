package uk.co.probablyfine.exercises.javaclub;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WordWrapper {

    @Test
    public void zeroWidthColumnIsNoBreaks() {
        assertThat(wrap("a b", 0), is("a b"));

    }

    private static String wrap(String input, int columns) {
        return input;
    }

}
