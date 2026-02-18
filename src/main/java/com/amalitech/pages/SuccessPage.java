package com.amalitech.pages;

import com.amalitech.core.BasePage;
import com.amalitech.utils.WaitUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuccessPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(SuccessPage.class);

    private static final By SUCCESS_SCREEN = By.id("success-screen");
    private static final By SUCCESS_TITLE = By.id("success-title");
    private static final By SUCCESS_EMAIL = By.id("success-email");
    private static final By DISMISS_BUTTON = By.id("dismiss");
    private static final By FORM_CONTAINER = By.cssSelector("main.container");

    public SuccessPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        log.info("Checking if success page is loaded...");
        WebElement screen = WaitUtils.waitForVisibleBy(driver, SUCCESS_SCREEN, 5);
        boolean loaded = screen.isDisplayed();
        log.info("Success page loaded: {}", loaded);
        return loaded;
    }

    public String getTitleText() {
        String text = WaitUtils.waitForVisibleBy(driver, SUCCESS_TITLE, 5).getText().trim();
        log.info("Success page title: {}", text);
        return text;
    }

    public String getSuccessEmail() {
        String value = WaitUtils.waitForVisibleBy(driver, SUCCESS_EMAIL, 5).getText().trim();
        log.info("Success email displayed: {}", value);
        return value;
    }

    public NewsletterPage dismiss() {
        log.info("Clicking Dismiss button...");
        WaitUtils.waitForVisibleBy(driver, DISMISS_BUTTON, 5).click();

        log.debug("Waiting for success screen to hide and form to reappear");
        WaitUtils.waitForHidden(driver, SUCCESS_SCREEN, 5);
        WaitUtils.waitForVisibleBy(driver, FORM_CONTAINER, 5);

        return new NewsletterPage(driver);
    }
}