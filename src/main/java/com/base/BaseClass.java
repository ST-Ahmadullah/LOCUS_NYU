package com.base;

import java.util.Collections;
import com.helper.ConfigurationReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseClass {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initializeWebDriver() {
        if (driver.get() != null) {
            return driver.get(); // Return existing WebDriver instance
        }

        WebDriver webDriver = null;

        // Read browser configuration from properties file
        ConfigurationReader config = ConfigurationReader.getInstance();
        String browser = config.getBrowser();
        boolean headless = config.isHeadless();

        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    webDriver = new ChromeDriver(getChromeOptions(headless));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriver = new FirefoxDriver(getFirefoxOptions(headless));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    webDriver = new EdgeDriver(getEdgeOptions(headless));
                    break;
                default:
                    throw new IllegalArgumentException("❌ Browser not supported: " + browser);
            }

            webDriver.manage().window().maximize();
            driver.set(webDriver);
            System.out.println("✅ WebDriver initialized: " + browser + " | Headless: " + headless);

        } catch (Exception e) {
            handleException("❌ WebDriver initialization error", e);
        }

        return driver.get();
    }

    public static void quitWebDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
            System.out.println("✅ WebDriver quit successfully.");
        }
    }

    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");
        if (headless) options.addArguments("--headless");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-popup-blocking");
        if (headless) options.addArguments("--headless");
        return options;
    }

    private static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.setCapability("ms:edgeOptions",
                Collections.singletonMap("args", Collections.singletonList("disable-popup-blocking")));
        if (headless) options.addArguments("--headless");
        return options;
    }

    private static void handleException(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }
}
