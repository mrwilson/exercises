package uk.co.probablyfine.exercises.stopwatch;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StopwatchTest {

    public static class TimeProvider {
        private long duration;

        public void advance(long seconds) {
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
            return "Current Time: 00:0" + (clock.time() - startTime);
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
        clock.advance(1L);

        assertThat(stopwatch.display(), is("Current Time: 00:01"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Second() {
        var clock = new TimeProvider();

        var stopwatch = new Stopwatch(clock);

        stopwatch.start();

        clock.advance(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:01"));

        clock.advance(1L);
        assertThat(stopwatch.display(), is("Current Time: 00:02"));
    }
}
