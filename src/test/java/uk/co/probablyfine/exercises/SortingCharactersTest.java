package uk.co.probablyfine.exercises;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SortingCharactersTest {

    @Test
    public void one() {
        assertThat(sort("a"), is("a"));

    }

    private String sort(String input) {
        return input;
    }
}
