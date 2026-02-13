package com.amalitech.pages;

import com.amalitech.core.BasePage;
import com.amalitech.utils.WaitUtils;
import org.openqa.selenium.*;

public class SuccessPage extends BasePage {

    private static final By SUCCESS_SCREEN = By.id("success-screen");
    private static final By SUCCESS_TITLE = By.id("success-title");
    private static final By SUCCESS_EMAIL = By.id("success-email");
    private static final By DISMISS_BUTTON = By.id("dismiss");
    private static final By FORM_CONTAINER = By.cssSelector("main.container");

    public SuccessPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        WebElement screen = WaitUtils.waitForVisibleBy(driver, SUCCESS_SCREEN, 5);
        return screen.isDisplayed();
    }

    public String getTitleText() {
        return WaitUtils.waitForVisibleBy(driver, SUCCESS_TITLE, 5).getText().trim();
    }

    public String getSuccessEmail() {
        return WaitUtils.waitForVisibleBy(driver, SUCCESS_EMAIL, 5).getText().trim();
    }

    public NewsletterPage dismiss() {
        WaitUtils.waitForVisibleBy(driver, DISMISS_BUTTON, 5).click();

        // success-screen becomes hidden and form becomes visible
        WaitUtils.waitForHidden(driver, SUCCESS_SCREEN, 5);
        WaitUtils.waitForVisibleBy(driver, FORM_CONTAINER, 5);

        return new NewsletterPage(driver);
    }
}