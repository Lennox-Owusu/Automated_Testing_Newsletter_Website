package com.amalitech.pages;

import com.amalitech.core.BasePage;
import com.amalitech.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class NewsletterPage extends BasePage {

    private static final String URL = "https://lennox-owusu.github.io/newsletter-website/";

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
        driver.get(URL);
        WaitUtils.waitForPageReady(driver, 5);
        WaitUtils.waitForVisible(driver, formContainer);
        return this;
    }

    public boolean isFormVisible() {
        try {
            return formContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public NewsletterPage enterEmail(String email) {
        WaitUtils.waitForVisible(driver, emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    // VALID email path → success screen appears
    public SuccessPage submit() {
        submitButton.click();

        // main.container becomes hidden
        WaitUtils.waitForHidden(driver, By.cssSelector("main.container"), 5);
        return new SuccessPage(driver);
    }

    //INVALID email path → stay on form and error appears
    public NewsletterPage submitInvalid() {
        submitButton.click();
        WaitUtils.waitForVisible(driver, errorMessage);
        WaitUtils.waitForNonEmptyText(driver, errorMessage, 5);
        return this;
    }

    public boolean isErrorDisplayed() {
        try {
            WaitUtils.waitForVisible(driver, errorMessage);
            return !errorMessage.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessage.getText().trim();
    }
}