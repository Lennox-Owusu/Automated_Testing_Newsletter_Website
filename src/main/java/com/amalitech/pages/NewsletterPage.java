package com.amalitech.pages;

import com.amalitech.core.BasePage;
import com.amalitech.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsletterPage extends BasePage {

    private static final String URL = "https://lennox-owusu.github.io/newsletter-website/";
    private static final Logger log = LoggerFactory.getLogger(NewsletterPage.class);

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "error")
    private WebElement errorMessage;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = "main.container")
    private WebElement formContainer;

    public NewsletterPage(WebDriver driver) {
        super(driver);
    }

    public NewsletterPage open() {
        log.info("Opening Newsletter page: {}", URL);
        driver.get(URL);

        log.debug("Waiting for form container to become visible");
        WaitUtils.waitForPageReady(driver, 5);
        WaitUtils.waitForVisible(driver, formContainer);

        return this;
    }

    public boolean isFormVisible() {
        try {
            boolean visible = formContainer.isDisplayed();
            log.info("Form visibility check: {}", visible);
            return visible;
        } catch (Exception e) {
            log.error("Error checking form visibility", e);
            return false;
        }
    }

    public NewsletterPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        WaitUtils.waitForVisible(driver, emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    // VALID email → success screen
    public SuccessPage submit() {
        log.info("Submitting valid email...");
        submitButton.click();

        log.debug("Waiting for success screen to appear");
        WaitUtils.waitForHidden(driver, By.cssSelector("main.container"), 5);

        return new SuccessPage(driver);
    }

    // INVALID email → stays on form
    public NewsletterPage submitInvalid() {
        log.warn("Submitting invalid email...");
        submitButton.click();

        log.debug("Waiting for error message to appear");
        WaitUtils.waitForVisible(driver, errorMessage);
        WaitUtils.waitForNonEmptyText(driver, errorMessage, 5);

        return this;
    }

    public boolean isErrorDisplayed() {
        try {
            WaitUtils.waitForVisible(driver, errorMessage);
            boolean displayed = !errorMessage.getText().trim().isEmpty();
            log.info("Error message displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            log.warn("Error message not displayed");
            return false;
        }
    }

    public String getErrorMessage() {
        String text = errorMessage.getText().trim();
        log.info("Validation error message: {}", text);
        return text;
    }
}