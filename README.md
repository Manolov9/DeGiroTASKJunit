# DeGiroTASKJunit
Automation QA Engineer Assignment

Overview
SauceDemoTest is a Selenium-based automation test suite for testing the functionalities of the SauceDemo website. This project includes test cases for login, sorting products, cart operations, and more.

Prerequisites
Before you begin, ensure you have the following installed on your machine:

Java Development Kit (JDK) 11 or later
Apache Maven (for managing dependencies and running tests)
Microsoft Edge WebDriver (Edge browser must also be installed)

Setup
1. Clone the Repository
--code:
git clone https://github.com/Manolov9/DeGiroTASKJunit.git
cd DeGiroTASKJunit.git

2. Install Dependencies
Ensure that all dependencies are included in your pom.xml file. Use Maven to install them:

--code
mvn clean install

3. WebDriver Configuration
Download the Microsoft Edge WebDriver and place it in a known directory. Update the webdriver.edge.driver system property in the setUp method of your test class with the correct path to the WebDriver executable.

Example:

java
--code:
System.setProperty("webdriver.edge.driver", "D:\\WebDrivers\\edgedriver_win64\\msedgedriver.exe");
Running the Tests
1. Open the Project in Your IDE
You can use any IDE that supports Maven projects, such as IntelliJ IDEA or Eclipse.

2. Navigate to the Test Class
In your IDE, navigate to the SauceDemoTest class located in the src/test/java directory.

3. Run the Tests
You can run the tests directly from your IDE by right-clicking on the SauceDemoTest class and selecting "Run". Alternatively, you can use Maven to run the tests from the command line:


--code:
mvn test

Test Descriptions
testLoginWithAllUsers
This test case verifies the login functionality for multiple user types.

testSortProducts
This test case checks the product sorting feature on the inventory page.

testNavigateToCartAndValidateEmptyCart
This test case navigates to the cart page and validates if the cart is empty.

testAddToCartHighestAndLowestPriceAndCheckout
This test case adds the products with the highest and lowest prices to the cart and performs checkout.

testAddAllProductsToCartAndValidate
This test case adds all available products to the cart and validates the cart's contents.

Helper Methods
navigateToCart
Navigates to the cart page.

login
Performs login using the specified username and password.

logout
Performs logout from the application.

sortBy
Sorts products based on the specified sorting option.

isSorted
Verifies if the products are sorted correctly based on the specified order.

isLoginSuccessful
Checks if the login was successful.

isLogoutSuccessful
Checks if the logout was successful.

getTotalProductsFromBadge
Retrieves the total number of products in the cart from the cart badge.

validateAllProductsAddedToCart
Validates that the number of products in the cart matches the total number of products added.

Logging
Errors and important actions are logged using Java's Logger class for easy debugging and traceability.

Troubleshooting
Common Issues
WebDriverException
Ensure that the path to the WebDriver executable is correct and the WebDriver version is compatible with your browser version.

NoSuchElementException
Verify that the element locators (IDs, classes, XPaths) in the test methods match the elements on the SauceDemo website.
