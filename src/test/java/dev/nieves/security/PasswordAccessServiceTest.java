package dev.nieves.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests unitarios de {@link PasswordAccessService}.
 */
class PasswordAccessServiceTest {

    private static final String CORRECT_PASSWORD = "diario2026";
    private static final String WRONG_PASSWORD = "wrong-password";

    private InterfaceAccessService accessService;

    @BeforeEach
    void setUp() {
        accessService = new PasswordAccessService();
    }

    @Test
    void authenticate_withCorrectPassword_shouldReturnTrue() {
        boolean result = accessService.authenticate(CORRECT_PASSWORD);

        assertThat(result, is(true));
    }

    @Test
    void authenticate_withWrongPassword_shouldReturnFalse() {
        boolean result = accessService.authenticate(WRONG_PASSWORD);

        assertThat(result, is(false));
    }

    @Test
    void isLocked_beforeAnyAttempt_shouldReturnFalse() {
        assertThat(accessService.isLocked(), is(false));
    }

    @Test
    void isLocked_afterThreeFailedAttempts_shouldReturnTrue() {
        accessService.authenticate(WRONG_PASSWORD);
        accessService.authenticate(WRONG_PASSWORD);
        accessService.authenticate(WRONG_PASSWORD);

        assertThat(accessService.isLocked(), is(true));
    }

    @Test
    void authenticate_afterLocked_shouldThrowException() {
        accessService.authenticate(WRONG_PASSWORD);
        accessService.authenticate(WRONG_PASSWORD);
        accessService.authenticate(WRONG_PASSWORD);

        assertThrows(IllegalStateException.class, () -> accessService.authenticate(CORRECT_PASSWORD));
    }
}
