package uk.co.probablyfine.exercises.jigsaw;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class JigsawTest {

    @Test
    void shouldReturnEmptyGrid() {
        assertThat(jigsaw(0, 0), is(""));
    }

    private static String jigsaw(int width, int height) {
        return "";
    }
}
