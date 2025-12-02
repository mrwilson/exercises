package uk.co.probablyfine.exercises.aoc25;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseTest<T> {

    abstract T part1(String input);

    abstract T part2(String input);

    public void run(String path) {
        System.out.println("Part 1: " + part1(data(path)));
        System.out.println("Part 2: " + part2(data(path)));
    }

    public String data(String path) {
        try {
            return Files.readString(Paths.get(Day2Test.class.getResource(path).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
