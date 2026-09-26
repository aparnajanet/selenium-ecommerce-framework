package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Notice that we extend BaseTest to inherit the driver setup and teardown
public class SauceDemoTests extends BaseTest {

    @Test
    public void testSuccessfulLoginAndCartUpdate() {
        LoginPage loginPage = new LoginPage(driver);

        // 1. Log in using our fluent POM method and credentials from config.properties
        InventoryPage inventoryPage = loginPage.loginAs(
                ConfigReader.getProperty("validUser"),
                ConfigReader.getProperty("password")
        );

        // 2. Assert that we successfully landed on the inventory page
        Assert.assertTrue(inventoryPage.isPageLoaded(), "Inventory page failed to load after login.");

        // 3. Add items and verify the badge
        inventoryPage.addFirstNItemsToCart(2);
        Assert.assertEquals(inventoryPage.getCartItemCount(), 2, "Cart badge count is incorrect!");
    }

    @Test
    public void testPriceSortingLowToHigh() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.loginAs(
                ConfigReader.getProperty("validUser"),
                ConfigReader.getProperty("password")
        );

        // Sort the items using the dropdown
        inventoryPage.sortProductsBy("Price (low to high)");

        // Get the list of prices from the webpage (returned as Doubles from our POM)
        List<Double> actualPrices = inventoryPage.getAllItemPrices();

        // Create a copy of the list and use Java to sort it perfectly
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        // Assert that the website's sorting matches perfect Java sorting
        Assert.assertEquals(actualPrices, expectedPrices, "The price sorting on the website is broken!");
    }

    // A DataProvider runs the exact same test multiple times with different data inputs
    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidData() {
        return new Object[][] {
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"}
        };
    }

    // We link the test to the DataProvider above
    @Test(dataProvider = "invalidLoginData")
    public void testInvalidLoginScenarios(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();

        // Verify the exact error message appears
        Assert.assertTrue(actualError.contains(expectedError),
                "Expected error message not found. Actual was: " + actualError);
    }
}