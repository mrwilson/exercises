package uk.co.probablyfine.exercises.password;

import static java.util.function.Predicate.not;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class PasswordCheckerTest {

    @Test
    void failsNullInput() {
        assertFalse(isValidPassword(null));
    }

    @Test
    void failsForSizeLessThan10() {
        rangeClosed(0, 9)
                .mapToObj("a"::repeat)
                .map(this::enforcePasswordRules)
                .forEach(result -> assertFalse(result.lengthRequired));
    }

    @Test
    void passesForSizeMoreThan10() {
        IntStream.range(10, 1000)
                .parallel()
                .mapToObj("a"::repeat)
                .map(this::enforcePasswordRules)
                .forEach(result -> assertTrue(result.lengthRequired));
    }

    @Test
    void failsIfNoNumberPresent() {
        var result = enforcePasswordRules("a");

        assertFalse(result.atLeastOneNumber());
    }

    @Test
    void passesIfAtLeastOneNumberPresent() {
        var result = enforcePasswordRules("1");

        assertTrue(result.atLeastOneNumber());
    }

    @Test
    void failsIfNoLetterPresent() {
        var result = enforcePasswordRules("1");

        assertFalse(result.atLeastOneLetter());
    }

    @Test
    void passesIfAtLeastOneLetterPresent() {
        var result = enforcePasswordRules("a");

        assertTrue(result.atLeastOneLetter());
    }

    @Test
    void failsForSizeLessThan16WhenAdmin() {
        rangeClosed(0, 15)
                .mapToObj("a"::repeat)
                .map(password -> enforcePasswordRules(password, true))
                .forEach(result -> assertFalse(result.lengthRequired));
    }

    @Test
    void passesForSizeMoreThan16WhenAdmin() {
        IntStream.range(16, 1000)
                .parallel()
                .mapToObj("a"::repeat)
                .map(password -> enforcePasswordRules(password, true))
                .forEach(result -> assertTrue(result.lengthRequired));
    }

    @Test
    void failsIfNoAlphanumericCharPresentForAdmin() {
        var result = enforcePasswordRules("a", true);

        assertFalse(result.atLeastOneNonAlphanumeric());
    }

    @Test
    void passesIfAlphanumericCharPresentForAdmin() {
        var result = enforcePasswordRules("!", true);

        assertTrue(result.atLeastOneNonAlphanumeric());
    }

    private boolean isValidPassword(String password) {
        return enforcePasswordRules(password).lengthRequired();
    }

    private PasswordCheckResult enforcePasswordRules(String password) {
        return enforcePasswordRules(password, false);
    }

    record PasswordCheckResult(
            boolean lengthRequired,
            boolean atLeastOneNumber,
            boolean atLeastOneLetter,
            boolean atLeastOneNonAlphanumeric) {}

    private PasswordCheckResult enforcePasswordRules(String password, boolean admin) {

        if (password == null) {
            return new PasswordCheckResult(false, false, false, false);
        }

        return new PasswordCheckResult(
                admin ? password.length() >= 16 : password.length() >= 10,
                password.chars().filter(Character::isDigit).count() >= 1,
                password.chars().filter(Character::isLetter).count() >= 1,
                password.chars().boxed().filter(not(Character::isLetterOrDigit)).count() >= 1);
    }
}
