package com.amalitech.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final long DEFAULT_TIMEOUT = 10;

    /** Wait for element (PageFactory) to become visible */
    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    /** Wait for element located by By to become visible */
    public static WebElement waitForVisibleBy(WebDriver driver, By locator, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait for an element's text to become non-empty */
    public static void waitForNonEmptyText(WebDriver driver, WebElement element, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> !element.getText().trim().isEmpty());
    }

    /** Wait for a class to appear on an element (optional, used for invalid input animation) */
    public static void waitForClass(WebDriver driver, WebElement element, String className, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> element.getAttribute("class") != null &&
                element.getAttribute("class").contains(className));
    }
}