package uk.co.probablyfine.exercises.stopwatch;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StopwatchTest {

    public static record Stopwatch() {

        public String display() {
            return "Current Time: 00:00";
        }
    }

    @Test
    public void displayWithoutRunning() {
        var stopwatch = new Stopwatch();

        assertThat(stopwatch.display(), is("Current Time: 00:00"));
    }
}
