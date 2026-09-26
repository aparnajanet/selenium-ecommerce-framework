# 🛒 Selenium E-Commerce Automation Pipeline

[![CI/CD Pipeline](https://github.com/[Your-Username]/selenium-ecommerce-framework/actions/workflows/maven.yml/badge.svg)](https://github.com/[Your-Username]/selenium-ecommerce-framework/actions)
[![Java Version](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.x-green.svg)](https://www.selenium.dev/)

An end-to-end automated testing framework designed to validate critical user journeys on standard e-commerce platforms (Target: SauceDemo). This project demonstrates enterprise-level QA practices, including strict separation of concerns, dynamic data handling, and continuous integration.

![Automation Demo](link-to-your-10-second-demo.gif)
*(Above: Headed execution of the checkout pipeline)*

## 🛠️ Tech Stack & Architecture

* **Language:** Java
* **Automation Tool:** Selenium WebDriver (v4)
* **Test Runner / Assertion:** TestNG
* **Build Management:** Maven
* **Design Pattern:** Page Object Model (POM)
* **Reporting:** ExtentReports (with automatic failure screenshots)
* **CI/CD:** GitHub Actions

## 🧪 Automated Scenarios

| Module | Scenario | Validation Strategy |
| :--- | :--- | :--- |
| **Authentication** | Data-Driven Login | Validates successful login, locked-out user alerts, and invalid credential errors using TestNG `@DataProvider`. |
| **Inventory** | Array Sorting | Changes filter to "Price: Low to High" and programmatically verifies the resulting array is in ascending order. |
| **Checkout Pipeline** | End-to-End Purchase | Simulates adding multiple items to the cart, verifying cart badge counts, filling out checkout forms, and asserting the final success screen. |

## 🏗️ Framework Structure

```text
src/
├── main/java/
│   ├── base/           # BaseTest (Driver initialization & teardown)
│   ├── pages/          # Page Object Classes (Locators & Actions)
│   └── utils/          # ConfigReader, ExtentReportManager, Listeners
├── test/java/
│   └── tests/          # TestNG Test Classes
└── test/resources/
    └── config.properties # URLs, browser selection, environment data