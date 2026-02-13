package com.amalitech.pages;

import com.amalitech.core.BasePage;
import com.amalitech.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsletterPage extends BasePage {

    private static final String URL = "https://lennox-owusu.github.io/newsletter-website/";

    public NewsletterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    // THIS is your real error element
    @FindBy(id = "error")
    private WebElement errorMessage;

    @FindBy(css = "main.container")
    private WebElement mainContainer;

    public boolean isFormVisible() {
        return mainContainer.isDisplayed();
    }

    /** Load page */
    public NewsletterPage open() {
        driver.get(URL);
        return this;
    }

    /** Type email into input */
    public NewsletterPage enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    /** Submit valid email → goes to success screen */
    public SuccessPage submit() {
        submitButton.click();
        return new SuccessPage(driver);
    }

    /**
     * Submit invalid email → remain on same page
     * Wait until JavaScript inserts error text.
     */
    public NewsletterPage submitInvalid() {
        submitButton.click();

        // Wait until <span id="error"> gets text injected
        WaitUtils.waitForNonEmptyText(driver, errorMessage, 2);

        return this;
    }

    /** Does error message exist? */
    public boolean isErrorDisplayed() {
        try {
            return !errorMessage.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /** Get error message text */
    public String getErrorMessage() {
        return errorMessage.getText().trim();
    }
}