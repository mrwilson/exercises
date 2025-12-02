package uk.co.probablyfine.exercises.aoc25;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseTest<T> {

    protected final String testData;
    protected final String realData;

    public BaseTest(int day) {
        this.testData = data("/day" + day + "-test.txt");
        this.realData = data("/day" + day + ".txt");
    }

    abstract T part1(String input);

    abstract T part2(String input);

    public void run() {
        System.out.println("Part 1: " + part1(realData));
        System.out.println("Part 2: " + part2(realData));
    }

    public String data(String path) {
        try {
            return Files.readString(Paths.get(Day2Test.class.getResource(path).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
