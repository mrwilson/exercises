package uk.co.probablyfine.exercises.stopwatch;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

        private long startTime;

        public Stopwatch(TimeProvider clock) {
            this.clock = clock;
        }

        public String display() {
            var secondsElapsed = (clock.time() - startTime);

            var minutes = secondsElapsed / 60;
            var seconds = secondsElapsed % 60;

            return "Current Time: "+String.format("%02d:%02d", minutes, seconds);
        }

        public void start() {
            this.startTime = clock.time();
        }
    }

    @Test
    public void displayWithoutRunning() {
        var stopwatch = new Stopwatch(new TimeProvider());

        assertThat(stopwatch.display(), is("Current Time: 00:00"));
    }

    @Test
    public void displayAfterRunningFor1Second() {
        var clock = new TimeProvider();

        var stopwatch = new Stopwatch(clock);

        stopwatch.start();
        clock.advanceSeconds(1L);

        assertThat(stopwatch.display(), is("Current Time: 00:01"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Second() {
        var clock = new TimeProvider();

        var stopwatch = new Stopwatch(clock);

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        clock.advanceSeconds(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:02"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Minute() {
        var clock = new TimeProvider();
        var stopwatch = new Stopwatch(clock);

        stopwatch.start();

        clock.advanceSeconds(60L);
        assertThat(stopwatch.display(), is("Current Time: 01:00"));
    }
}
