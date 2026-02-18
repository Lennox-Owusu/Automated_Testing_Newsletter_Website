

# 📘 **README.md — Automated Testing: Newsletter Signup Website**

## 📌 Overview

This project is an automated UI testing framework built using **Selenium WebDriver**, **JUnit 5**, and the **Page Object Model (POM)**.  
It tests a simple *Newsletter Sign‑Up Website* hosted on GitHub Pages:

👉 **<https://lennox-owusu.github.io/newsletter-website/>**

The framework includes:

*   Selenium WebDriver automation
*   Page Object Model with PageFactory
*   Reusable wait utilities
*   JUnit 5 test suite
*   GitHub Actions CI pipeline
*   Slack & Email notifications
*   **Console logging using SLF4J **

***

## 🚀 **Technologies Used**

*   Java 21
*   Selenium WebDriver 4
*   JUnit 5
*   SLF4J (API + SimpleLogger)
*   Maven
*   GitHub Actions
*   ChromeDriver

***

## 📂 **Project Structure**

    src/
     └── main/java/com/amalitech
          ├── core/
          │    └── BasePage.java
          ├── pages/
          │    ├── NewsletterPage.java
          │    └── SuccessPage.java
          └── utils/
               └── WaitUtils.java

     └── test/java/com/amalitech/tests
          ├── BaseTest.java
          └── NewsletterSignupTest.java

***

# 📝 **Logging **

This project uses **SLF4J + slf4j-simple**, which prints logs **directly to the console** (NOT to files).

### ✔ Logging Libraries in `pom.xml`

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.12</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.12</version>
    <scope>runtime</scope>
</dependency>


### ✔ Logger Declaration (used in all classes)

java
private static final Logger log = LoggerFactory.getLogger(ClassName.class);


### ✔ Example Logging Output

The console will show messages such as:

    INFO  NewsletterPage - Opening Newsletter page...
    INFO  BaseTest        - Headless mode: true
    WARN  NewsletterPage  - Submitting invalid email...
    DEBUG WaitUtils       - Waiting for visibility of locator: id=error
    INFO  SuccessPage     - Success email displayed: test123@example.com

### ✔ What is logged?

*   Page loads
*   Element interactions (entering email, clicking submit)
*   Invalid input warnings
*   Wait events (page ready, element visible)
*   Test flow steps
*   Browser setup & teardown


## 🧪 **Test Scenarios Covered**

### 1. **Successful signup shows success screen**

*   Correct email is displayed
*   Success title appears

### 2. **Empty email shows error**

### 3. **Invalid email formats show error (Parameterized Test)**

### 4. **Dismiss button returns to form**

### 5. **Happy path navigation test**

***

## 🏗 **Continuous Integration (GitHub Actions)**

The CI workflow runs on **every push + pull request**.

### ✔ What CI does:

*   Sets up Java 17
*   Runs Selenium tests in **headless** mode
*   Uploads Surefire test reports
*   Sends Slack notifications (success/failure)
*   Sends an email notification on failure
*   Prints all **SLF4J console logs** to GitHub Actions logs

Workflow file:

    .github/workflows/ci.yml

***

## ▶️ **How to Run Tests Locally**

### **Run with UI**

    mvn test

### **Run in headless mode (same as CI)**

    mvn -Dheadless=true test

***

## 💡 **Troubleshooting**

### ❗ If SLF4J classes are not found:

Run:

    mvn clean install

Or reload Maven in IntelliJ.

***

## 📄 **License**

This project is for academic use under the Amalitech QA training module.

***







![CI](https://github.com/<Automated_Testing_Newsletter_Website Public>/actions/workflows/ci.yml/badge.svg)
CI tst run
