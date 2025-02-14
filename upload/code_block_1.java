package com.example.tests;

import com.example.pages.DashboardPage;
import com.example.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Properties;

public class ResourceConsumptionTests {
    private WebDriver driver;
    private Properties prop;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"manager1"},
                {"manager2"},
                {"manager3"},
                {"manager4"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testDashboardRealTimeData(String username) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Step 1: Log in as manager
        loginPage.login(username, "password");

        // Step 2: Navigate to the dashboard
        dashboardPage.navigateToDashboard();

        // Step 3: Verify real-time resource consumption data
        Assert.assertTrue(dashboardPage.isRealTimeDataDisplayed(), "Real-time data is not displayed");

        // Step 4: Check data refresh rate
        Assert.assertTrue(dashboardPage.isDataRefreshingEveryMinute(), "Data is not refreshing every minute");

        // Step 5: Verify data accuracy
        Assert.assertTrue(dashboardPage.isDataAccurate(), "Data does not match actual resource consumption");
    }

    @Test(dataProvider = "loginData")
    public void testCoffeeBeansLowAlert(String username) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Step 1: Log in as manager
        loginPage.login(username, "password");

        // Step 2: Navigate to the dashboard
        dashboardPage.navigateToDashboard();

        // Step 3: Simulate coffee beans level dropping to 10%
        dashboardPage.simulateCoffeeBeansLevel(10);

        // Step 4: Verify alert generation
        Assert.assertTrue(dashboardPage.isCoffeeBeansAlertGenerated(), "Alert for low coffee beans is not generated");

        // Step 5: Check alert details
        Assert.assertTrue(dashboardPage.isCoffeeBeansAlertDetailsCorrect(), "Alert details for low coffee beans are incorrect");
    }

    @Test(dataProvider = "loginData")
    public void testWaterLevelLowAlert(String username) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Step 1: Log in as manager
        loginPage.login(username, "password");

        // Step 2: Navigate to the dashboard
        dashboardPage.navigateToDashboard();

        // Step 3: Simulate water level dropping to 15%
        dashboardPage.simulateWaterLevel(15);

        // Step 4: Verify alert generation
        Assert.assertTrue(dashboardPage.isWaterAlertGenerated(), "Alert for low water level is not generated");

        // Step 5: Check alert details
        Assert.assertTrue(dashboardPage.isWaterAlertDetailsCorrect(), "Alert details for low water level are incorrect");
    }

    @Test(dataProvider = "loginData")
    public void testMilkLevelLowAlert(String username) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Step 1: Log in as manager
        loginPage.login(username, "password");

        // Step 2: Navigate to the dashboard
        dashboardPage.navigateToDashboard();

        // Step 3: Simulate milk level dropping to 20%
        dashboardPage.simulateMilkLevel(20);

        // Step 4: Verify alert generation
        Assert.assertTrue(dashboardPage.isMilkAlertGenerated(), "Alert for low milk level is not generated");

        // Step 5: Check alert details
        Assert.assertTrue(dashboardPage.isMilkAlertDetailsCorrect(), "Alert details for low milk level are incorrect");
    }

    @Test(dataProvider = "loginData")
    public void testDashboardResourceLevels(String username) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Step 1: Log in as manager
        loginPage.login(username, "password");

        // Step 2: Navigate to the dashboard
        dashboardPage.navigateToDashboard();

        // Step 3: Verify coffee beans level
        Assert.assertTrue(dashboardPage.isCoffeeBeansLevelDisplayed(), "Coffee beans level is not displayed");

        // Step 4: Verify water level
        Assert.assertTrue(dashboardPage.isWaterLevelDisplayed(), "Water level is not displayed");

        // Step 5: Verify milk level
        Assert.assertTrue(dashboardPage.isMilkLevelDisplayed(), "Milk level is not displayed");

        // Step 6: Verify data accuracy for all resources
        Assert.assertTrue(dashboardPage.isAllResourceDataAccurate(), "Data for all resources does not match actual consumption");
    }
}