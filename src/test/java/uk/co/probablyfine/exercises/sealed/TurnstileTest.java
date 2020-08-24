package uk.co.probablyfine.exercises.sealed;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class TurnstileTest {

    sealed interface Turnstile permits Open, Closed {
        Turnstile push();
        Turnstile insertCoin();
    }

    static final record Open() implements Turnstile {
        @Override
        public Turnstile push() {
            return new Closed();
        }

        @Override
        public Turnstile insertCoin() {
            return this;
        }
    }

    static final record Closed() implements Turnstile {
        @Override
        public Turnstile push() {
            return this;
        }

        @Override
        public Turnstile insertCoin() {
            return new Open();
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

    @Test
    public void insertingACoinIntoAClosedTurnstileOpensIt() {
        Turnstile turnstile = new Closed();
        assertThat(turnstile.insertCoin(), instanceOf(Open.class));
    }

    @Test
    public void insertingACoinIntoAClosedTurnstileDoesNothing() {
        Turnstile turnstile = new Open();
        assertThat(turnstile.insertCoin(), instanceOf(Open.class));
    }

}
