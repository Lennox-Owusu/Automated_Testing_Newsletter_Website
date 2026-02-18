package com.amalitech.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base page class for all Page Objects.
 * Provides shared functionality and initializes PageFactory elements.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        log.info("Initializing PageFactory for {}", getClass().getSimpleName());
        PageFactory.initElements(driver, this);
    }
}