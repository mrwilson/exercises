package uk.co.probablyfine.exercises.trivia;

import java.io.*;
import java.util.Random;

public class GameTest {

    public static void main(String... args) throws FileNotFoundException {
        File fixedOutput = new File("fixed-output.txt");

        if (fixedOutput.exists()) return;

        System.setOut(
            new PrintStream(new FileOutputStream(fixedOutput))
        );

        GameRunner.runGame(new Random()::nextInt);
    }
}
