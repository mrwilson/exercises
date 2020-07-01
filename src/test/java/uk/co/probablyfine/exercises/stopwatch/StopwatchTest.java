package uk.co.probablyfine.exercises.stopwatch;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
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
            var previousTotalLaps = this.laps.stream().reduce(0L, Long::sum);

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
                    .mapToObj(this::formatLapTime)
                    .collect(Collectors.joining("\n"));
        }

        private String formatLapTime(int lapNumber) {
            return format(
                    "Lap %d: %s", (lapNumber + 1), formatMinutesAndSeconds(laps.get(lapNumber)));
        }

        private String formatMinutesAndSeconds(long secondsElapsed) {
            return format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60);
        }

        public void start() {
            this.state = State.RUNNING;
            this.startTime = clock.time();
        }

        public void stop() {
            if (this.state == State.RUNNING) {
                this.timeElapsed += (clock.time() - startTime);
            }

            this.state = State.STOPPED;
        }

        public void reset() {
            this.state = State.NEW;
            this.timeElapsed = 0;
            this.laps.clear();
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
        assertThat(stopwatch, hasCurrentTime("00:00"));
    }

    @Test
    public void displayAfterRunningFor1Second() {

        stopwatch.start();

        clock.advanceSeconds(1L);

        assertThat(stopwatch, hasCurrentTime("00:01"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Second() {

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:02"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsAfter1Minute() {
        stopwatch.start();

        clock.advanceSeconds(60L);
        assertThat(stopwatch, hasCurrentTime("01:00"));
    }

    @Test
    public void displayAfterTakingMultipleMeasurementsFromAStoppedStopwatch() {

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));

        stopwatch.stop();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));
    }

    @Test
    public void displayAfterGoingBackInTime() {
        stopwatch.start();

        clock.advanceSeconds(2L);
        assertThat(stopwatch, hasCurrentTime("00:02"));

        clock.advanceSeconds(-1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));
    }

    @Test
    public void shouldResumeCountingIfStartedAgain() {
        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));

        stopwatch.stop();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));

        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:02"));
    }

    @Test
    public void shouldBeAbleToReset() {
        stopwatch.start();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, hasCurrentTime("00:01"));

        stopwatch.reset();

        assertThat(stopwatch, hasCurrentTime("00:00"));
    }

    @Test
    public void shouldBeAbleToTakeLaps() {
        stopwatch.start();

        clock.advanceSeconds(1L);
        stopwatch.lap();

        clock.advanceSeconds(1L);
        assertThat(stopwatch, allOf(hasCurrentTime("00:02"), hasLap(1, "00:01")));

        clock.advanceSeconds(1L);
        stopwatch.lap();

        assertThat(
                stopwatch, allOf(hasCurrentTime("00:03"), hasLap(1, "00:01"), hasLap(2, "00:02")));
    }

    @Test
    public void shouldAccumulateCurrentTimeOnStop_NotReset() {

        stopwatch.start();

        clock.advanceSeconds(1L);
        stopwatch.lap();

        clock.advanceSeconds(1L);
        stopwatch.lap();

        stopwatch.stop();

        clock.advanceSeconds(2L);
        stopwatch.start();
        clock.advanceSeconds(1);

        stopwatch.lap();
        stopwatch.stop();

        assertThat(
                stopwatch,
                allOf(
                        hasCurrentTime("00:03"),
                        hasLap(1, "00:01"),
                        hasLap(2, "00:01"),
                        hasLap(3, "00:01")));
    }

    @Test
    public void shouldClearLapsWhenResetIsCalled() {

        stopwatch.start();

        clock.advanceSeconds(1L);
        stopwatch.lap();

        assertThat(stopwatch, allOf(hasCurrentTime("00:01"), hasLap(1, "00:01")));

        stopwatch.reset();

        assertThat(stopwatch, allOf(hasCurrentTime("00:00"), not(hasLap(1, "00:01"))));
    }

    @Test
    public void shouldDoNothingWhenStopIsCalledMultipleTimes() {
        stopwatch.start();

        clock.advanceSeconds(1L);

        stopwatch.stop();

        assertThat(stopwatch, hasCurrentTime("00:01"));

        stopwatch.stop();

        assertThat(stopwatch, hasCurrentTime("00:01"));
    }

    @Test
    public void shouldDoNothingWhenStopIsCalledBeforeStart() {
        clock.advanceSeconds(1L);

        stopwatch.stop();

        assertThat(stopwatch, hasCurrentTime("00:00"));
    }

    private static Matcher<Stopwatch> hasLap(int lap, String time) {
        return hasLineInDisplay("Lap " + lap + ": " + time);
    }

    private static Matcher<Stopwatch> hasCurrentTime(String currentTime) {
        return hasLineInDisplay("Current Time: " + currentTime);
    }

    private static Matcher<Stopwatch> hasLineInDisplay(String line) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(Stopwatch item, Description mismatchDescription) {
                var matches = Arrays.asList(item.display().split("\n")).contains(line);

                if (!matches) {
                    mismatchDescription.appendText("is a Stopwatch with display ["+item.display()+"]");
                }

                return matches;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(
                        " a Stopwatch with display containing the line [" + line + "]");
            }
        };
    }
}
