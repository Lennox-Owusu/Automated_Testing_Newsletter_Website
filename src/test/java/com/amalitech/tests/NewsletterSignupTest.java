package com.amalitech.tests;

import com.amalitech.pages.NewsletterPage;
import com.amalitech.pages.SuccessPage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("ui")
public class NewsletterSignupTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(NewsletterSignupTest.class);

    @Test
    @DisplayName("Verify Success page should display the submitted email")
    void testSuccessPageShowsCorrectEmail() {

        String email = "test+" + System.currentTimeMillis() + "@example.com";
        log.info("Generated test email: {}", email);

        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail(email)
                .submit();

        log.info("Asserting success screen is loaded...");
        assertTrue(success.isLoaded());

        log.info("Asserting success email matches submitted value...");
        assertEquals(email, success.getSuccessEmail());
    }

    @Test
    @DisplayName("Verify empty email shows required error")
    void testEmptyEmailError() {

        log.info("Testing empty email submission...");

        NewsletterPage page = new NewsletterPage(driver)
                .open()
                .enterEmail("")
                .submitInvalid();

        assertTrue(page.isErrorDisplayed(), "Expected validation error.");
    }

    @ParameterizedTest(name = "Verify invalid email should show error: {0}")
    @ValueSource(strings = {"756..@abc", "user@@domain", "user@", "plainaddress", "a@b"})
    void testInvalidEmailShowsError(String badEmail) {

        log.info("Testing invalid email: {}", badEmail);

        NewsletterPage page = new NewsletterPage(driver)
                .open()
                .enterEmail(badEmail)
                .submitInvalid();

        assertTrue(page.isErrorDisplayed(), "Expected error for invalid email.");
    }

    @Test
    @DisplayName("Verify dismiss button should return to the form screen")
    void testDismissReturnsToForm() {

        log.info("Testing dismiss button from success screen...");

        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail("dismiss@test.com")
                .submit();

        assertTrue(success.isLoaded());

        NewsletterPage backToForm = success.dismiss();
        assertTrue(backToForm.isFormVisible());
    }

    @Test
    @DisplayName("Verify newsletter signup leads to success screen")
    void testNewsletterSignupSuccess() {

        log.info("Testing successful newsletter signup...");

        SuccessPage success = new NewsletterPage(driver)
                .open()
                .enterEmail("test+" + System.currentTimeMillis() + "@example.com")
                .submit();

        assertTrue(success.isLoaded(), "Success screen should appear");

        String title = success.getTitleText().toLowerCase();
        log.info("Success page title text: {}", title);

        if (!title.isBlank()) {
            assertTrue(title.contains("thanks") || title.contains("thank you"));
        }
    }
}