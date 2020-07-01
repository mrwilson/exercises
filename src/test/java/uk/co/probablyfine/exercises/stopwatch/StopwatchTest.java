package uk.co.probablyfine.exercises.stopwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public class StopwatchTest {

    public static class TimeProvider {
        private long duration;

        public void advanceSeconds(long seconds) {
            this.duration += seconds;
        }

        public long time() {
            return this.duration;
        }
    }

    public static class Stopwatch {
        private final TimeProvider clock;
        private final List<Long> laps;
        private long startTime;
        private long timeElapsed;
        private State state;

        public void lap() {
            var previousTotalLaps = this.laps.stream().mapToLong(x -> x).sum();

            this.laps.add(secondsElapsed() - previousTotalLaps);
        }

        private enum State {
            NEW,
            RUNNING,
            STOPPED
        }

        public Stopwatch(TimeProvider clock) {
            this.state = State.NEW;
            this.clock = clock;
            this.timeElapsed = 0;
            this.laps = new ArrayList<>();
        }

        public String display() {
            var currentTime = "Current Time: " + formatMinutesAndSeconds(secondsElapsed());

            if (this.laps.isEmpty()) {
                return currentTime;
            } else {
                return currentTime + "\n" + lapTimes();
            }
        }

        private String lapTimes() {
            return IntStream.range(0, this.laps.size())
                    .mapToObj(
                            i ->
                                    String.format(
                                            "Lap %d: %s",
                                            (i + 1), formatMinutesAndSeconds(laps.get(i))))
                    .collect(Collectors.joining("\n"));
        }

        private String formatMinutesAndSeconds(long secondsElapsed) {
            return String.format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60);
        }

        public void start() {
            this.state = State.RUNNING;
            this.startTime = clock.time();
        }

        public void stop() {
            this.state = State.STOPPED;
            this.timeElapsed = (clock.time() - startTime);
        }

        public void reset() {
            this.state = State.NEW;
            this.timeElapsed = 0;
        }

        private long secondsElapsed() {
            return switch (this.state) {
                case RUNNING -> this.timeElapsed + (clock.time() - startTime);
                case STOPPED -> this.timeElapsed;
                case NEW -> 0;
            };
        }
    }

    private final TimeProvider clock = new TimeProvider();
    private final Stopwatch stopwatch = new Stopwatch(clock);

    @Test
    public void displayWithoutRunning() {
        assertThat(stopwatch.display(), is("Current Time: 00:00"));
    }

    @Test
    public void displayAfterRunningFor1Second() {

        stopwatch.start();

        clock.advanceSeconds(1L);

        assertThat(stopwatch.display(), is("Current Time: 00:01"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Second() {

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:02"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Minute() {
        stopwatch.start();

        clock.advanceSeconds(60L);
        assertThat(stopwatch.display(), is("Current Time: 01:00"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsFromAStoppedStopwatch() {

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        stopwatch.stop();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));
    }

    @Test
    public void displayAfterGoingBackInTime() {
        stopwatch.start();

        clock.advanceSeconds(2L);
        assertThat(stopwatch.display(), is("Current Time: 00:02"));

        clock.advanceSeconds(-1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));
    }

    @Test
    public void shouldResumeCountingIfStartedAgain() {
        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        stopwatch.stop();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:02"));
    }

    @Test
    public void shouldBeAbleToReset() {
        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        stopwatch.reset();

        assertThat(stopwatch.display(), is("Current Time: 00:00"));
    }

    @Test
    public void shouldBeAbleToTakeLaps() {
        stopwatch.start();

        clock.advanceSeconds(1L);
        stopwatch.lap();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:02\nLap 1: 00:01"));

        clock.advanceSeconds(1L);
        stopwatch.lap();

        assertThat(stopwatch.display(), is("Current Time: 00:03\nLap 1: 00:01\nLap 2: 00:02"));
    }
}
