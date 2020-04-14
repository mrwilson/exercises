package uk.co.probablyfine.exercises.javaclub;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WordWrapper {

    @Test
    public void zeroWidthColumnIsNoBreaks() {
        assertThat(wrap("a b", 0), is("a b"));

    }

    @Test
    public void replaceOneSpaceWithNewline() {
        assertThat(wrap("a b", 1), is("a\nb"));

    }

    @Test
    public void replaceTwoOccurrencesOfSpacesWithNewline() {
        assertThat(wrap("a b c", 1), is("a\nb\nc"));
    }

    private static String wrap(String input, int columns) {

        if (input.length() <= columns) return input;

        String column = input.substring(0, columns+1);
        String rest = input.substring(columns+1);

        if(column.contains(" ") && column.indexOf(" ") <= columns) {
            return column.replaceFirst(" ","\n") + wrap(rest, columns);
        } else {
            return input;
        }
    }

}
