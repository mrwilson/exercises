package uk.co.probablyfine.exercises.trivia;

import org.junit.Test;
import uk.co.probablyfine.exercises.trivia.GameRunner.BoundedRandomness;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GameTest {

    private static final File fixedOutput = new File("fixed-output.txt");

    public static void main(String... args) throws FileNotFoundException {
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

    @Test
    public void runFixedOutput() throws IOException {
        List<String> fixedRun = Files.readAllLines(fixedOutput.toPath());

        ListIterator<Integer> inputs = Arrays.stream(fixedRun
                .get(fixedRun.size() - 1)
                .split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList())
                .listIterator();

        BoundedRandomness randomness = (i) -> {
            if (inputs.hasNext()) {
                return inputs.next();
            } else {
                throw new RuntimeException("OOPS");
            }
        };

        File tempFile = File.createTempFile("test-run", "txt");

        System.setOut(new PrintStream(tempFile));

        GameRunner.runGame(randomness);

        List<String> actualRun = Files.readAllLines(tempFile.toPath());

        assertThat(actualRun, is(fixedRun.subList(0, fixedRun.size()-1)));
    }

    @Test
    public void questionCategoryIsPopWhenPlayerPlaceIs0Modulo4() {
        Game game = new Game();

        List.of(0, 4, 8).forEach(place -> {
            Player one = new Player("player", place, 0);
            assertThat(game.currentCategory(one), is("Pop"));
        });
    }

    @Test
    public void questionCategoryIsScienceWhenPlayerPlaceIs1Modulo4() {
        Game game = new Game();

        List.of(1, 5, 9).forEach(place -> {
            Player one = new Player("player", place, 0);
            assertThat(game.currentCategory(one), is("Science"));
        });
    }

    @Test
    public void questionCategoryIsSportsWhenPlayerPlaceIs2Modulo4() {
        Game game = new Game();

        List.of(2, 6, 10).forEach(place -> {
            Player one = new Player("player", place, 0);
            assertThat(game.currentCategory(one), is("Sports"));
        });
    }

    @Test
    public void questionCategoryDefaultIsRock() {
        Game game = new Game();

        List.of(3, 7, 11).forEach(place -> {
            Player one = new Player("player", place, 0);
            assertThat(game.currentCategory(one), is("Rock"));
        });
    }
}
