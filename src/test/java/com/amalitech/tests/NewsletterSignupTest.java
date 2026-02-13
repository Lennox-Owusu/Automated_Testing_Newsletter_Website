package com.amalitech.tests;

import com.amalitech.pages.NewsletterPage;
import com.amalitech.pages.SuccessPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertEquals(email, success.getSuccessEmail(),
                "Success page should show the exact email submitted.");
    }

    @Test
    @DisplayName("Verify empty email shows required error")
    void testEmptyEmailError() {
        NewsletterPage page = new NewsletterPage(driver)
                .open()
                .enterEmail("")
                .submitInvalid();

        assertTrue(page.isErrorDisplayed());
        assertEquals("Valid email required!", page.getErrorMessage());
    }

    @Test
    @DisplayName("Verify invalid email should show error message and block submission")
    void testInvalidEmailShowsError() {

        NewsletterPage page = new NewsletterPage(driver)
                .open()
                .enterEmail("756..@abc")
                .submitInvalid();  // Stay on same page for invalid input

        assertTrue(page.isErrorDisplayed(),
                "Error message should be displayed for invalid email.");

        assertEquals("Please provide a valid e-mail address", page.getErrorMessage());
    }

    @Test
    @DisplayName("Dismiss button should return to the form screen")
    void testDismissReturnsToForm() {

        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail("dismiss@test.com")
                .submit();

        assertTrue(success.isLoaded());

        NewsletterPage backToForm = success.dismiss();

        // Assert form is visible again
        assertTrue(backToForm.isFormVisible(),
                "Form should be visible after dismissing success screen.");
    }

    @Test
    @DisplayName("Verify newsletter signup leads to success screen")
    void testNewsletterSignupSuccess() {

        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail("test+" + System.currentTimeMillis() + "@example.com")
                .submit();

        // Assert any of our robust success signals
        assertTrue(success.isLoaded(), "Success view did not load after submission.");

        // Optional: strengthen assertion if you want the exact heading
        String title = success.getTitleText().toLowerCase();
        if (!title.isBlank()) {
            assertTrue(title.contains("thanks") || title.contains("thank you"),
                    "Expected a 'Thanks/Thank you' heading but got: " + title);
        }
    }
}