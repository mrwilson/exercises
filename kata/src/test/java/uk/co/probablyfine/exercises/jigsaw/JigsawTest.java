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
         |__( )_|
         """));
    }

    @Test
    void shouldReturnTwoPieces() {
        assertThat(jigsaw(2, 1), is("""
           _( )__ _( )__ 
         _|     _|     _|
        (_   _ (_   _ (_
         |__( )_|__( )_|
         """));
    }

    @Test
    void shouldReturnFourHorizontalPieces() {
        assertThat(jigsaw(4, 1), is("""
           _( )__ _( )__ _( )__ _( )__ 
         _|     _|     _|     _|     _|
        (_   _ (_   _ (_   _ (_   _ (_ 
         |__( )_|__( )_|__( )_|__( )_|
         """));
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
         |__( )_|
         """));
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
        |__( )_|__( )_|
        """));
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
         |__( )_|
         """));
    }

    @Test
    void shouldReturn4x3Pieces() {
        assertThat(jigsaw(4, 3), is("""
           _( )__ _( )__ _( )__ _( )__ 
         _|     _|     _|     _|     _|
        (_   _ (_   _ (_   _ (_   _ (_ 
         |__( )_|__( )_|__( )_|__( )_|
         |_     |_     |_     |_     |_
          _) _   _) _   _) _   _) _   _)
         |__( )_|__( )_|__( )_|__( )_|
         _|     _|     _|     _|     _|
        (_   _ (_   _ (_   _ (_   _ (_
         |__( )_|__( )_|__( )_|__( )_|
         """));
    }

    private static String jigsaw(int width, int height) {
        if (width == 0 || height == 0) {
            return "";
        }

        var builder = new StringBuilder();

        builder.append(topLine(width));

        for (int i = 0; i < height; i++) {
            builder.append(i % 2 == 0 ? evenRow(width) : oddRow(width));
        }

        return builder.toString();
    }

    private static String oddRow(int width) {
        return " |_" + "     |_".repeat(width) + "\n"
                + "  _)" + " _   _)".repeat(width) + "\n"
                + " |" + "__( )_|".repeat(width) + "\n";
    }

    private static String evenRow(int width) {
        return " _|" + "     _|".repeat(width) + "\n"
                + "(_" + "   _ (_".repeat(width) + "\n"
                + " |" + "__( )_|".repeat(width) + "\n";
    }

    private static String topLine(int width) {
        return "  " + " _( )__".repeat(width) + "\n";
    }
}
