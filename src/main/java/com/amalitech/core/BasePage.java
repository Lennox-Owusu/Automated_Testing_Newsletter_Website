package com.amalitech.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Base page class for all Page Objects.
 * Provides shared functionality and initializes PageFactory elements.
 */
public abstract class BasePage {

    protected final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // initializes @FindBy elements
    }

    public String getTitle() {
        return driver.getTitle();
    }
}