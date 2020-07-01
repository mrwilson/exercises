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
        private long lastTime;
        private State state;

        private enum State {
            RUNNING,
            STOPPED;
        }

        public Stopwatch(TimeProvider clock) {
            this.state = State.STOPPED;
            this.clock = clock;
        }

        public String display() {
            var secondsElapsed = switch(this.state) {
                case RUNNING -> (clock.time() - startTime);
                case STOPPED -> (lastTime - startTime);
            };

            var minutes = secondsElapsed / 60;
            var seconds = secondsElapsed % 60;

            return "Current Time: "+String.format("%02d:%02d", minutes, seconds);
        }

        public void start() {
            this.state = State.RUNNING;
            this.startTime = clock.time();
        }

        public void stop() {
            this.state = State.STOPPED;
            this.lastTime = clock.time();
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
}
