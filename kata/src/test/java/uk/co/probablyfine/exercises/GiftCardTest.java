package uk.co.probablyfine.exercises;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.GiftCardTest.GiftCard.employer;
import static uk.co.probablyfine.exercises.GiftCardTest.GiftCard.union;

import java.util.ArrayList;
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
    }

    @Test
    void noCostMeansNoVouchers() {
        assertThat(giftCards(0), is(emptyList()));
    }

    @Test
    void preferUnionVouchers() {
        assertThat(giftCards(250), is(List.of(union(250))));
    }

    @Test
    void fallBackToNormalVouchers() {
        assertThat(giftCards(9), is(List.of(employer(9))));
    }

    @Test
    void combineVoucherTypes() {
        assertThat(giftCards(733), is(List.of(union(250), union(250), union(100), union(100), union(25), employer(8))));
    }

    private List<GiftCard> giftCards(int value) {
        return _giftCards(value, new ArrayList<>());
    }

    private List<GiftCard> _giftCards(Integer value, List<GiftCard> giftCards) {

        if (value == 0) {
            return giftCards;
        }

        var next = switch(value) {
            case Integer s when s >= 250 -> union(250);
            case Integer s when s >= 100 -> union(100);
            case Integer s when s >= 50 -> union(50);
            case Integer s when s >= 25 -> union(25);
            case Integer s when s >= 20 -> union(20);
            default -> employer(value);
        };

        giftCards.add(next);
        return _giftCards(value - next.amount(), giftCards);

    }
}
