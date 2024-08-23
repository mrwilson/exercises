package uk.co.probablyfine.exercises.doublets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class DoubletsTest {

    @Test
    void theSameWordDoubletReturnsTheOriginalWord() {
        assertThat(doublet("test", "test"), is(List.of("test")));
    }

    private static List<String> doublet(String head, String tail) {
        return List.of(head);
    }
}
