package uk.co.probablyfine.exercises.aoc25;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public abstract class BaseTest<INPUT, OUTPUT> {

    protected final INPUT testData;
    protected final INPUT realData;
    private final int day;

    public BaseTest(int day) {
        this.testData = parse(data("/day" + day + "-test.txt"));
        this.realData = parse(data("/day" + day + ".txt"));
        this.day = day;
    }

    abstract INPUT parse(String input);

    abstract OUTPUT part1(INPUT input);

    abstract OUTPUT part2(INPUT input);

    @Benchmark
    public void part1(Blackhole blackhole) {
        blackhole.consume(part1(realData));
    }

    @Benchmark
    public void part2(Blackhole blackhole) {
        blackhole.consume(part2(realData));
    }

    @Test
    public void benchmarks() throws RunnerException {
        Assumptions.assumeTrue(System.getProperty("benchmark") != null);
        var options =
                new OptionsBuilder()
                        .warmupIterations(3)
                        .measurementIterations(10)
                        .mode(Mode.AverageTime)
                        .forks(1)
                        .timeUnit(TimeUnit.SECONDS)
                        .include(this.getClass().getName())
                        .build();

        new Runner(options)
                .run()
                .forEach(
                        x -> {
                            try {
                                Files.writeString(
                                        new File("bm-" + day + ".txt").toPath(),
                                        x.getAggregatedResult().toString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
    }

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
