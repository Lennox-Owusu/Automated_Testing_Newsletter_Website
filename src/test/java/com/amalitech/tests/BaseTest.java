package com.amalitech.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BaseTest {
    protected WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);


    @BeforeEach
    void setUp() {
        log.info("Configuring SSL/TLS settings...");
        // Add SSL/TLS configuration BEFORE creating ChromeDriver
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.3");
        System.setProperty("jsse.enableSNIExtension", "true");

        ChromeOptions options = new ChromeOptions();

        // Read headless from system property
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        log.info("Headless mode: {}", headless);

        if (headless) {
            log.info("Adding headless Chrome arguments... = {}", headless);
            log.info("Launching browser with options...");
            log.info("Navigating to base URL: {}", System.getProperty("app.baseUrl"));

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

        log.info("Launching ChromeDriver...");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);

        if (!headless) {
            log.info("Maximizing window...");
            driver.manage().window().maximize();
        }

        System.setProperty("app.baseUrl",
                System.getProperty("app.baseUrl", "https://lennox-owusu.github.io/newsletter-website/"));
    }


    @AfterEach
    void tearDown() {
        log.info("Closing browser session...");
        if (driver != null) {
            driver.quit();
        }
    }
}