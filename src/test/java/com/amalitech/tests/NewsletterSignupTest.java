package com.amalitech.tests;

import com.amalitech.pages.NewsletterPage;
import com.amalitech.pages.SuccessPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
public class NewsletterSignupTest extends BaseTest {

    @Test
    @DisplayName("Verify Success page should display the submitted email")
    void testSuccessPageShowsCorrectEmail() {
        String email = "test+" + System.currentTimeMillis() + "@example.com";

        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail(email)
                .submit();

        assertTrue(success.isLoaded(), "Success page should appear.");
        assertEquals(email, success.getSuccessEmail(), "Success page should show the exact email submitted.");
    }

    @Test
    @DisplayName("Verify empty email shows required error")
    void testEmptyEmailError() {
        NewsletterPage page = new NewsletterPage(driver)
                .open()
                .enterEmail("")
                .submitInvalid();

        assertTrue(page.isErrorDisplayed(), "Error should be visible for empty email.");
        assertTrue(page.getErrorMessage().toLowerCase().contains("valid"),
                "Expected a validation message, but got: " + page.getErrorMessage());
    }

    @ParameterizedTest(name = "Invalid email should show error: {0}")
    @ValueSource(strings = {"756..@abc", "user@@domain", "user@", "plainaddress", "a@b"})
    void testInvalidEmailShowsError(String badEmail) {
        NewsletterPage page = new NewsletterPage(driver)
                .open()
                .enterEmail(badEmail)
                .submitInvalid();

        assertTrue(page.isErrorDisplayed(), "Error message should be displayed for invalid email.");
        assertFalse(page.getErrorMessage().trim().isEmpty(), "Error text should be empty.");
    }

    @Test
    @DisplayName("Dismiss button should return to the form screen")
    void testDismissReturnsToForm() {
        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail("dismiss@test.com")
                .submit();

        assertTrue(success.isLoaded(), "Success page should load first.");
        NewsletterPage backToForm = success.dismiss();
        assertTrue(backToForm.isFormVisible(), "Form should be visible after dismissing success screen.");
    }

    @Test
    @DisplayName("Verify newsletter signup leads to success screen")
    void testNewsletterSignupSuccess() {
        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail("test+" + System.currentTimeMillis() + "@example.com")
                .submit();

        assertTrue(success.isLoaded(), "Success view did not load after submission.");
        String title = success.getTitleText().toLowerCase();
        if (!title.isBlank()) {
            assertTrue(title.contains("thanks") || title.contains("thank you"),
                    "Expected a 'Thanks/Thank you' heading but got: " + title);
        }
    }
}