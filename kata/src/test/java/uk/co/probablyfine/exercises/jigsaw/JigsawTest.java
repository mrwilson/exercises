package uk.co.probablyfine.exercises.jigsaw;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class JigsawTest {

    @Test
    void shouldReturnEmptyGrid() {
        assertThat(jigsaw(0, 0), is(""));
    }

    @Test
    void shouldReturnSinglePiece() {
        assertThat(jigsaw(1, 1), is("""
           _( )__  
         _|     _|
        (_   _ (_ 
         |__( )_|"""));
    }


    private static String jigsaw(int width, int height) {
        if (width > 0 && height > 0) {
            return "   _( )__\n _|     _|\n(_   _ (_\n |__( )_|";
        }
        return "";
    }
}
