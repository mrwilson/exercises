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

    @Test
    public void breakWithoutSpace() {
        assertThat(wrap("abc", 2), is("a-\nbc"));
    }

    @Test
    public void moreBreaksWithoutSpaces() {
        assertThat(wrap("abcd", 2), is("a-\nb-\ncd"));

        assertThat(wrap("abcdefgh", 3), is("ab-\ncd-\nef-\ngh"));
    }

    private static String wrap(String input, int columns) {

        if (input.length() <= columns || columns == 0) return input;

        String line = input.substring(0, columns + 1);
        String rest = input.substring(columns+1);


        if (!line.contains(" ")) {
            return input.substring(0, columns-1) + "-\n" + wrap(input.substring(columns-1), columns);
        }

        if (line.indexOf(" ") <= columns) {
            return line.replaceFirst(" ", "\n") + wrap(rest, columns);
        } else {
            return input;
        }
    }

}
