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
        // Add SSL/TLS configuration BEFORE creating ChromeDriver
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("jsse.enableSNIExtension", "true");

        ChromeOptions options = new ChromeOptions();

        // Read headless from system property
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
        }

        // Stable defaults for CI Linux + SSL fixes
        options.addArguments(
                "--window-size=1920,1080",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-ssl-key-logging",  // Security best practice
                "--allow-insecure-localhost"   // If testing on localhost
        );

        // Accept all certificates (for GitHub Pages and self-signed certs)
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);

        if (!headless) {
            driver.manage().window().maximize();
        }

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