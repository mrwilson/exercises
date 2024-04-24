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

    @Test
    void shouldReturnTwoPieces() {
        assertThat(jigsaw(2, 1), is("""
           _( )__ _( )__ 
         _|     _|     _|
        (_   _ (_   _ (_
         |__( )_|__( )_|"""));
    }

    @Test
    void shouldReturnFourHorizontalPieces() {
        assertThat(jigsaw(4, 1), is("""
           _( )__ _( )__ _( )__ _( )__ 
         _|     _|     _|     _|     _|
        (_   _ (_   _ (_   _ (_   _ (_ 
         |__( )_|__( )_|__( )_|__( )_|"""));
    }

    @Test
    void shouldReturn1x2Pieces() {
        assertThat(jigsaw(1, 2), is("""
           _( )__ 
         _|     _|
        (_   _ (_ 
         |__( )_|
         |_     |_
          _) _   _)
         |__( )_|"""));
    }

    @Test
    void shouldReturn2x2Pieces() {
        assertThat(jigsaw(2, 2), is("""
          _( )__ _( )__ 
        _|     _|     _|
       (_   _ (_   _ (_
        |__( )_|__( )_|
        |_     |_     |_ 
         _) _   _) _   _)
        |__( )_|__( )_|"""));
    }

    @Test
    void shouldReturn1x3Pieces() {
        assertThat(jigsaw(1, 3), is("""
           _( )__ 
         _|     _|
        (_   _ (_
         |__( )_|
         |_     |_
          _) _   _)
         |__( )_|
         _|     _|
        (_   _ (_
         |__( )_|"""));
    }

    private static String jigsaw(int width, int height) {
        if (width == 0 || height == 0) {
            return "";
        }

        var builder = new StringBuilder();

        // Width
        builder.append("  ");
        builder.append(" _( )__".repeat(Math.max(0, width)));
        builder.append("\n");

        for (int i = 0; i < height; i++) {
            if (i % 2 == 0) {
                builder.append(" _|");
                builder.append("     _|".repeat(Math.max(0, width)));
                builder.append("\n");

                builder.append("(_");
                builder.append("   _ (_".repeat(Math.max(0, width)));
                builder.append("\n");

                builder.append(" |");
                builder.append("__( )_|".repeat(Math.max(0, width)));
            } else {
                builder.append(" |_");
                builder.append("     |_".repeat(Math.max(0, width)));
                builder.append("\n");

                builder.append("  _)");
                builder.append(" _   _)".repeat(Math.max(0, width)));
                builder.append("\n");

                builder.append(" |");
                builder.append("__( )_|".repeat(Math.max(0, width)));
            }

            if (i < height-1) {
                builder.append("\n");
            }

        }

        return builder.toString();
    }
}
