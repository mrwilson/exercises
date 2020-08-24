package uk.co.probablyfine.exercises.sealed;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class TurnstileTest {

    sealed interface Turnstile permits Open, Closed {
        Turnstile push();
    }

    static final record Open() implements Turnstile {
        @Override
        public Turnstile push() {
            return new Closed();
        }
    }

    static final record Closed() implements Turnstile {
        @Override
        public Turnstile push() {
            return this;
        }
    }

    @Test
    public void pushingAClosedTurnstileDoesNothing() {
        Turnstile turnstile = new Closed();
        assertThat(turnstile.push(), instanceOf(Closed.class));
    }

    @Test
    public void pushingAnOpenTurnstileClosesIt() {
        Turnstile turnstile = new Open();
        assertThat(turnstile.push(), instanceOf(Closed.class));
    }

}
