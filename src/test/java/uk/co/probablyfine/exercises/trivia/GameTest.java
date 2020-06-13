package uk.co.probablyfine.exercises.trivia;

import uk.co.probablyfine.exercises.trivia.GameRunner.BoundedRandomness;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameTest {

    public static void main(String... args) throws FileNotFoundException {
        File fixedOutput = new File("fixed-output.txt");

        if (fixedOutput.exists()) return;

        System.setOut(
            new PrintStream(new FileOutputStream(fixedOutput))
        );

        List<Integer> rolls = new ArrayList<>();

        BoundedRandomness recorder = maximum -> {
            int value = new Random().nextInt(maximum);
            rolls.add(value);
            return value;
        };

        GameRunner.runGame(recorder);

        String collect = rolls.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        System.out.println(collect);
    }
}
