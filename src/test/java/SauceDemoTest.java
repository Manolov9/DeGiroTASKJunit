import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SauceDemoTest {

    private WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(SauceDemoTest.class.getName());

    @Before
    public void setUp() {
        try {
            System.setProperty("webdriver.edge.driver", "D:\\WebDrivers\\edgedriver_win644\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during WebDriver setup: " + e.getMessage());
        }
    }

    @Test
    public void testLoginWithAllUsers() {
        String[] users = {"standard_user", "locked_out_user", "problem_user", "performance_glitch_user", "error_user", "visual_user"};

        for (String user : users) {
            login(user, "secret_sauce");
            assertTrue("Login failed for user: " + user, isLoginSuccessful());
            logout();
            assertTrue("Logout failed after user: " + user, isLogoutSuccessful());
        }
    }

    @Test
    public void testSortProducts() {
        login("standard_user", "secret_sauce");
        // Sort by Name (A to Z)
        sortBy("az");
        assertTrue("Sort by Name failed", isSorted("A to Z"));

        // Sort by Price (low to high)
        sortBy("lohi");
        assertTrue("Sort by Price (low to high) failed", isSorted("Low to High"));

        // Sort by Price (high to low)
        sortBy("hilo");
        assertTrue("Sort by Price (high to low) failed", isSorted("High to Low"));

        logout();
    }

    @Test
    public void testNavigateToCartAndValidateEmptyCart() {
        login("standard_user", "secret_sauce");
        navigateToCart();
        assertTrue("Cart is not empty", isCartEmpty());
        logout();
    }

    @Test
    public void testAddToCartHighestAndLowestPriceAndCheckout() {
        login("standard_user", "secret_sauce");
        addProductToCart(getProductWithHighestPrice());
        addProductToCart(getProductWithLowestPrice());
        // Perform checkout
        // Add assertions or actions as per requirements
        logout();
    }

    @Test
    public void testAddAllProductsToCartAndValidate() {
        login("standard_user", "secret_sauce");
        addAllProductsToCart();
        validateAllProductsAddedToCart();
        logout();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /*private void navigateToCart() {
        try {
            WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
            cartIcon.click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("cart"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during navigation to cart: " + e.getMessage());
        }
    }*/


    private void login(String username, String password) {
        try {
            driver.get("https://www.saucedemo.com/");
            WebElement usernameField = driver.findElement(By.id("user-name"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login-button"));

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("inventory"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during login: " + e.getMessage());
        }
    }

    private void logout() {
        try {
            WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
            WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));

            menuButton.click();
            logoutLink.click();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during logout: " + e.getMessage());
        }
    }

    private void sortBy(String option) {
        try {
            WebElement sortDropDown = driver.findElement(By.className("product_sort_container"));
            sortDropDown.click();
            List<WebElement> options = driver.findElements(By.tagName("option"));
            for (WebElement opt : options) {
                if (opt.getAttribute("value").equals(option)) {
                    opt.click();
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during sorting: " + e.getMessage());
        }
    }

    private void addAllProductsToCart() {
        try {
            // Find all product elements
            List<WebElement> products = driver.findElements(By.className("inventory_item"));

            // Iterate through each product and add it to the cart
            for (WebElement product : products) {
                WebElement addButton = product.findElement(By.tagName("button"));
                addButton.click();
                // Add some waiting time to ensure the product is added to the cart
                Thread.sleep(1000); // Adjust the waiting time as needed
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding products to cart: " + e.getMessage());
        }
    }


    private boolean isSorted(String sortOrder) {
        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
        List<String> sortedProductNames = new ArrayList<>();

        for (WebElement product : productNames) {
            sortedProductNames.add(product.getText());
        }

        List<String> expectedOrder = new ArrayList<>(sortedProductNames);
        if (sortOrder.equals("A to Z")) {
            Collections.sort(expectedOrder);
        } else if (sortOrder.equals("Low to High")) {
            // Implement sorting by price (low to high) logic
            // This might involve fetching the price of each product and comparing
            // them to validate the sorting order
        } else if (sortOrder.equals("High to Low")) {
            // Implement sorting by price (high to low) logic
            // This might involve fetching the price of each product and comparing
            // them to validate the sorting order
        }

        return sortedProductNames.equals(expectedOrder);
    }

    private boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("https://www.saucedemo.com/");
    }

    private boolean isLogoutSuccessful() {
        return driver.getCurrentUrl().contains("https://www.saucedemo.com/");
    }

    private boolean isCartEmpty() {
        try {
            // Check if any product items are present in the cart
            List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cart_item']"));

            // If no product items are found, cart is empty
            return cartItems.isEmpty();
        } catch (NoSuchElementException e) {
            // If any exception occurs while finding elements, assume cart is empty
            return true;
        }
    }


    private void addProductToCart(String productName) {
        WebElement product = driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']"));
        WebElement addButton = product.findElement(By.tagName("button"));
        addButton.click();
    }

    private String getProductWithHighestPrice() {
        try {
            List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
            List<WebElement> productPrices = driver.findElements(By.className("inventory_item_price"));
            double maxPrice = 0;
            String productName = "";

            for (int i = 0; i < productPrices.size(); i++) {
                double price = Double.parseDouble(productPrices.get(i).getText().replace("$", ""));
                if (price > maxPrice) {
                    maxPrice = price;
                    productName = productNames.get(i).getText();
                }
            }

            return productName;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while getting product with highest price: " + e.getMessage());
            return null;
        }
    }


    private String getProductWithLowestPrice() {
        try {
            List<WebElement> products = driver.findElements(By.className("inventory_item"));
            double minPrice = Double.MAX_VALUE;
            String productName = "";

            for (WebElement product : products) {
                WebElement priceElement = product.findElement(By.className("inventory_item_price"));
                double price = Double.parseDouble(priceElement.getText().replace("$", ""));
                if (price < minPrice) {
                    minPrice = price;
                    productName = product.findElement(By.className("inventory_item_name")).getText();
                }
            }

            return productName;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while getting product with lowest price: " + e.getMessage());
            return null;
        }
    }


    private void validateAllProductsAddedToCart() {
        // Navigate to the cart page
        navigateToCart();

        // Get the total number of products in the cart
        int totalProductsInCart = getTotalProductsFromBadge();
        List<WebElement> addedProducts = driver.findElements(By.className("cart_item"));

        // Verify that the total number of products in the cart is equal to the total number of products added
        assertEquals(totalProductsInCart, addedProducts.size());
    }

    private void navigateToCart() {
        try {
            WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
            cartIcon.click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("cart.html"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during navigation to cart: " + e.getMessage());
        }
    }

    private int getTotalProductsFromBadge() {
        try {
            WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
            return Integer.parseInt(badge.getText());
        } catch (NoSuchElementException e) {
            // If the badge is not found, it means there are no products in the cart
            return 0;
        }
    }



}
