package com.amalitech.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final long DEFAULT_TIMEOUT = 10;

    /**
     * ORIGINAL: Wait for visibility using driver + element
     */
    public static void waitForVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /** Wait for visibility using driver + locator */
    public static WebElement waitForVisibleBy(WebDriver driver, By locator, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for hidden (By locator)
     */
    public static void waitForHidden(WebDriver driver, By locator, long seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for text to be non-empty
     */
    public static void waitForNonEmptyText(WebDriver driver, WebElement element, long seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));

        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(d -> {
                    String txt = element.getText();
                    return txt != null && !txt.trim().isEmpty() ? txt.trim() : null;
                });
    }

    /** Wait for page JS readyState complete */
    public static void waitForPageReady(WebDriver driver, long seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until((ExpectedCondition<Boolean>)
                        d -> {
                            assert d != null;
                            return "complete".equals(
                                    ((JavascriptExecutor) d).executeScript("return document.readyState"));
                        }
                );
    }
}