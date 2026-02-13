package com.amalitech.pages;

import com.amalitech.core.BasePage;
import com.amalitech.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPage extends BasePage {

    private static final By SUCCESS_TITLE =
            By.xpath("//h2[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'thanks')]");

    private static final By SUCCESS_EMAIL = By.id("success-email");

    public SuccessPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            WaitUtils.waitForVisibleBy(driver, SUCCESS_TITLE, 10);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitleText() {
        return WaitUtils.waitForVisibleBy(driver, SUCCESS_TITLE, 10).getText().trim();
    }

    public String getSuccessEmail() {
        return WaitUtils.waitForVisibleBy(driver, By.id("success-email"), 5).getText().trim();
    }

    private static final By DISMISS_BUTTON = By.id("dismiss");

    public NewsletterPage dismiss() {
        driver.findElement(DISMISS_BUTTON).click();
        return new NewsletterPage(driver);
    }
}