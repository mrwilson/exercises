package uk.co.probablyfine.exercises;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.GiftCardTest.GiftCard.employer;
import static uk.co.probablyfine.exercises.GiftCardTest.GiftCard.union;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GiftCardTest {

    enum GiftCardType {
        UNION,
        EMPLOYER
    }

    record GiftCardResult(List<GiftCard> giftCards, List<GiftCard> balance) {}

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
        assertThat(giftCards(0).balance(), is(emptyList()));
    }

    @Test
    void preferUnionVouchers() {
        assertThat(giftCards(250).balance(), is(List.of(union(250))));
    }

    @Test
    void fallBackToNormalVouchers() {
        assertThat(giftCards(9).balance(), is(List.of(employer(9))));
    }

    @Test
    void combineVoucherTypes() {
        assertThat(
                giftCards(733).balance(),
                is(
                        List.of(
                                union(250),
                                union(250),
                                union(100),
                                union(100),
                                union(25),
                                employer(8))));
    }

    private GiftCardResult giftCards(int value) {
        return _giftCards(value, new ArrayList<>(), new ArrayList<>());
    }

    private GiftCardResult _giftCards(
            Integer value, List<GiftCard> giftCards, List<GiftCard> balance) {

        if (value == 0) {
            return new GiftCardResult(giftCards, balance);
        }

        var next =
                switch (value) {
                    case Integer s when s >= 250 -> union(250);
                    case Integer s when s >= 100 -> union(100);
                    case Integer s when s >= 50 -> union(50);
                    case Integer s when s >= 25 -> union(25);
                    case Integer s when s >= 20 -> union(20);
                    default -> employer(value);
                };

        int totalBalance = balance.stream().mapToInt(GiftCard::amount).sum();
        if (totalBalance + next.amount() <= 2000) {
            balance.add(next);
        } else {
            giftCards.add(next);
        }

        return _giftCards(value - next.amount(), giftCards, balance);
    }
}
