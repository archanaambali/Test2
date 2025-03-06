package com.example.tests;

import com.example.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class SensorDataTests {
    protected WebDriver driver;
    protected Properties prop;

    @BeforeMethod
    public void setUp() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);

        String browser = prop.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (Boolean.parseBoolean(prop.getProperty("chromeHeadless"))) {
                options.addArguments("--headless=new");
            }
            driver = new ChromeDriver(options);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "sensorData")
    public Object[][] sensorData() {
        return new Object[][]{
            {"temperature", "25°C"},
            {"humidity", "50%"},
            {"pressure", "1013 hPa"},
            {"usage", "100 kWh"}
        };
    }

    @Test(dataProvider = "sensorData")
    public void testSensorDataProcessing(String sensorType, String value) {
        SensorDataPage sensorDataPage = new SensorDataPage(driver);
        sensorDataPage.retrieveSensorData(sensorType, value);
        sensorDataPage.processSensorData(sensorType, value);
        sensorDataPage.storeSensorData(sensorType, value);
        sensorDataPage.verifyProcessedDataAccuracy(sensorType, value);
        sensorDataPage.generateReport(sensorType, value);
    }
}

package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SensorDataPage {
    private WebDriver driver;

    private By temperatureDataField = By.id("temperature-data");
    private By humidityDataField = By.id("humidity-data");
    private By pressureDataField = By.id("pressure-data");
    private By usageDataField = By.id("usage-data");
    private By processButton = By.id("process-button");
    private By storeButton = By.id("store-button");
    private By verifyButton = By.id("verify-button");
    private By reportButton = By.id("report-button");

    public SensorDataPage(WebDriver driver) {
        this.driver = driver;
    }

    public void retrieveSensorData(String sensorType, String value) {
        switch (sensorType) {
            case "temperature":
                driver.findElement(temperatureDataField).sendKeys(value);
                break;
            case "humidity":
                driver.findElement(humidityDataField).sendKeys(value);
                break;
            case "pressure":
                driver.findElement(pressureDataField).sendKeys(value);
                break;
            case "usage":
                driver.findElement(usageDataField).sendKeys(value);
                break;
        }
    }

    public void processSensorData(String sensorType, String value) {
        driver.findElement(processButton).click();
    }

    public void storeSensorData(String sensorType, String value) {
        driver.findElement(storeButton).click();
    }

    public void verifyProcessedDataAccuracy(String sensorType, String value) {
        driver.findElement(verifyButton).click();
    }

    public void generateReport(String sensorType, String value) {
        driver.findElement(reportButton).click();
    }
}