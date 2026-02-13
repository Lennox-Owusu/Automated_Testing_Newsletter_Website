package com.amalitech.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Read headless from system property: -Dheadless=true (default: false for local dev)
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
        }

        // Stable defaults for CI Linux
        options.addArguments(
                "--window-size=1920,1080",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );

        driver = new ChromeDriver(options);

        // maximize is redundant in headless; keeping only for local non-headless convenience
        if (!headless) {
            driver.manage().window().maximize();
        }

        // Optional: expose base URL as a JVM property used by pages (fallback to your current URL)
        System.setProperty("app.baseUrl",
                System.getProperty("app.baseUrl", "https://lennox-owusu.github.io/newsletter-website/"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
