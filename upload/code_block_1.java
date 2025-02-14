package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class ProductCategoryTest {
    private WebDriver driver;
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void verifyProductCategories() {
        driver.get("https://www.example.com/home");
        Assert.assertTrue(homePage.isDisplayed(), "Homepage is not displayed");
        
        homePage.clickBrowseProducts();
        Assert.assertTrue(productPage.isDisplayed(), "Product browsing page is not displayed");

        Assert.assertTrue(productPage.areCategoriesDisplayed(new String[]{"Electronics", "Clothing", "Books"}), "Product categories are not displayed correctly");
        
        Assert.assertTrue(productPage.areProductsDisplayedUnderCategory("Electronics", new String[]{"Smartphone", "Laptop"}), "Electronics products are not displayed correctly");
        Assert.assertTrue(productPage.areProductsDisplayedUnderCategory("Clothing", new String[]{"T-shirt", "Jeans"}), "Clothing products are not displayed correctly");
        Assert.assertTrue(productPage.areProductsDisplayedUnderCategory("Books", new String[]{"Fiction", "Non-fiction"}), "Books products are not displayed correctly");

        Assert.assertTrue(productPage.isProductCountCorrect("Electronics", 10), "Product count for Electronics is not correct");
        Assert.assertTrue(productPage.isProductCountCorrect("Clothing", 20), "Product count for Clothing is not correct");
        Assert.assertTrue(productPage.isProductCountCorrect("Books", 15), "Product count for Books is not correct");

        Assert.assertTrue(productPage.areProductDetailsCorrect(), "Product details are not displayed correctly");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}