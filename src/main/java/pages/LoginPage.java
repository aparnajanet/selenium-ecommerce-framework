package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // 1. Locators (Encapsulated as private so tests can't mess with them)
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    // 2. Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // 3. Action Methods
    public void enterUsername(String user) {
        driver.findElement(usernameField).sendKeys(user);
    }

    public void enterPassword(String pass) {
        driver.findElement(passwordField).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    /**
     * Fluent Page Object approach:
     * Since logging in successfully navigates to the Inventory Page,
     * this method returns a new instance of the InventoryPage automatically.
     */
    public InventoryPage loginAs(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
        return new InventoryPage(driver);
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}