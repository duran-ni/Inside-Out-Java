package dev.nieves.view;

import dev.nieves.security.PasswordAccessService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests unitarios de {@link AccessView}.
 */
class AccessViewTest {

    private static final String CORRECT_PASSWORD = "diario2026";

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputCapture;

    @BeforeEach
    void setUp() {
        outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void login_withCorrectPasswordFirstTry_shouldReturnTrue() {
        boolean result = runLogin(CORRECT_PASSWORD + "\n");

        assertThat(result, is(true));
    }

    @Test
    void login_withWrongPasswordThenCorrect_shouldReturnTrue() {
        boolean result = runLogin("wrong\n" + CORRECT_PASSWORD + "\n");

        assertThat(result, is(true));
    }

    @Test
    void login_withThreeWrongAttempts_shouldReturnFalse() {
        boolean result = runLogin("wrong1\nwrong2\nwrong3\n");

        assertThat(result, is(false));
    }

    private boolean runLogin(String simulatedInput) {
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        AccessView accessView = new AccessView(new PasswordAccessService(), scanner);
        return accessView.login();
    }
}
