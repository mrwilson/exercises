package uk.co.probablyfine.exercises.doublets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class DoubletsTest {

    record Doublets(List<String> dictionary) {
        public List<String> doublet(String head, String tail) {
            if (head.equals(tail)) {
                return Collections.singletonList(head);
            }

            if (compare(head, tail) == 1) {
                return List.of(head, tail);
            }

            throw new UnsupportedOperationException();
        }
    }

    @Test
    void theSameWordDoubletReturnsTheOriginalWord() {
        assertThat(doublet("test", "test"), is(List.of("test")));
    }

    @Test
    void wordsOneLetterApartReturnsAListOfBothEntries() {
        assertThat(doublet("head", "heal"), is(List.of("head", "heal")));
    }

    private static List<String> doublet(String head, String tail) {
        return new Doublets(List.of("head", "tail", "test")).doublet(head, tail);
    }

    private static long compare(String head, String tail) {
        return IntStream.range(0, head.length())
                .filter(i -> head.charAt(i) != tail.charAt(i))
                .count();
    }
}
