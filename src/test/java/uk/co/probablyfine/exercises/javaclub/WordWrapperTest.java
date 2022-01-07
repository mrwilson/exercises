package uk.co.probablyfine.exercises.javaclub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class WordWrapperTest {

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

    @Test
    public void breakOnTwoSpaces() {
        assertThat(wrap("a b c d", 3), is("a b\nc d"));
    }

    private static String wrap(String input, int columns) {

        if (input.length() <= columns || columns == 0) return input;

        int lastSpaceBeforeBreak = input.substring(0, columns + 1).lastIndexOf(' ');

        if (lastSpaceBeforeBreak < 0) {
            return input.substring(0, columns - 1)
                    + "-\n"
                    + wrap(input.substring(columns - 1), columns);
        } else {
            return input.substring(0, lastSpaceBeforeBreak)
                    + "\n"
                    + wrap(input.substring(lastSpaceBeforeBreak + 1), columns);
        }
    }
}
