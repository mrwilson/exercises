package uk.co.probablyfine.exercises;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SortingCharactersTest {

    @Test
    public void one() {
        assertThat(sort("a"), is("a"));
    }

    @Test
    public void caps() {
        assertThat(sort("aA"), is("aa"));
    }

    @Test
    public void capsDifferentCharacters() {
        assertThat(sort("Ba"), is("ab"));
    }

    private String sort(String input) {
        return input.toLowerCase()
            .chars()
            .sorted()
            .mapToObj(Character::toString)
            .collect(Collectors.joining());
    }
}
