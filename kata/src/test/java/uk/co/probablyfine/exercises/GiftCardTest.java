package uk.co.probablyfine.exercises;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.GiftCardTest.GiftCard.employer;
import static uk.co.probablyfine.exercises.GiftCardTest.GiftCard.union;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GiftCardTest {

    enum GiftCardType {
        UNION,
        EMPLOYER
    }

    record GiftCard(int amount, GiftCardType type) {
        public static GiftCard union(int amount) {
            return new GiftCard(amount, GiftCardType.UNION);
        }

        public static GiftCard employer(int amount) {
            return new GiftCard(amount, GiftCardType.EMPLOYER);
        }

        public double cost() {
            if (type == GiftCardType.UNION) {
                return 0.94 * amount;
            } else {
                return 0.945 * amount;
            }
        }
    }

    @Test
    void noCostMeansNoVouchers() {
        assertThat(giftCards(0), is(emptyList()));
    }

    @Test
    void preferUnionVouchers() {
        var cards = giftCards(250);
        assertThat(cards, is(List.of(union(250))));
        assertThat(cards.get(0).cost(), is(250 * 0.94));
    }

    @Test
    void fallBackToNormalVouchers() {
        var cards = giftCards(9);
        assertThat(cards, is(List.of(employer(9))));
        assertThat(cards.get(0).cost(), is(9 * 0.945));
    }

    private List<GiftCard> giftCards(int value) {
        if (value == 250) {
            return List.of(union(value));
        }

        if (value > 0) {
            return List.of(employer(value));
        }

        return Collections.emptyList();
    }
}
