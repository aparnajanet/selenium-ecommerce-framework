package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private WebDriver driver;

    // Locators
    private By productsHeader = By.cssSelector(".title");
    private By addToCartButtons = By.cssSelector(".btn_inventory");
    private By cartBadge = By.className("shopping_cart_badge");
    private By sortDropdown = By.className("product_sort_container");
    private By itemPrices = By.className("inventory_item_price");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public boolean isPageLoaded() {
        return driver.findElement(productsHeader).isDisplayed();
    }

    public void addFirstNItemsToCart(int count) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int i = 0; i < count && i < buttons.size(); i++) {
            buttons.get(i).click();
        }
    }

    public int getCartItemCount() {
        String badgeText = driver.findElement(cartBadge).getText();
        return Integer.parseInt(badgeText);
    }

    public void sortProductsBy(String visibleText) {
        Select dropdown = new Select(driver.findElement(sortDropdown));
        dropdown.selectByVisibleText(visibleText);
    }

    /**
     * Reads all price elements, strips the "$" sign, converts them to Doubles,
     * and returns them as a List for our test assertions.
     */
    public List<Double> getAllItemPrices() {
        return driver.findElements(itemPrices)
                .stream()
                .map(element -> Double.parseDouble(element.getText().replace("$", "")))
                .collect(Collectors.toList());
    }
}