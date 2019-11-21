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

    @Test
    public void capsExample() {
        assertThat(sort("When not studying nuclear physics, Bambi likes to play beach volleyball."), is("aaaaabbbbcccdeeeeeghhhiiiiklllllllmnnnnooopprsssstttuuvwyyyy"));
    }

    private String sort(String input) {
        return input.toLowerCase()
            .chars()
            .filter(character -> 'A' <= character && character <= 'z')
            .sorted()
            .mapToObj(Character::toString)
            .collect(Collectors.joining());
    }
}
