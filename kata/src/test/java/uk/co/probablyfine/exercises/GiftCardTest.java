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
        assertThat(giftCards(733), is(List.of(union(250), union(250), employer(233))));
    }

    private List<GiftCard> giftCards(int value) {
        return _giftCards(value, new ArrayList<>());
    }

    private List<GiftCard> _giftCards(int value, List<GiftCard> giftCards) {
        if (value >= 250) {
            giftCards.add(union(250));
            return _giftCards(value - 250, giftCards);
        }

        if (value > 0) {
            giftCards.add(employer(value));
            return _giftCards(0, giftCards);
        }

        return giftCards;

    }
}
