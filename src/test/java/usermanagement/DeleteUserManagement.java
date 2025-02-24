package usermanagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class DeleteUserManagement {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws Throwable {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        driver.get("https://nyu.webwizardsusa.com/admin/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void deleteusermanagement() throws Throwable {
        WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();

        driver.findElement(By.xpath("(//p[normalize-space()='User Management'])[1]")).click();

        // Search for the user by email
        driver.findElement(By.xpath("(//input[@id='search'])[1]")).sendKeys("nyu@gmail.com");
        driver.findElement(By.xpath("(//button[normalize-space()='Search'])[1]")).click();

        // Wait for the delete button to be visible and click it
        WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait6.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-original-title='Delete']"))).click();

        // Click on the confirmation delete button (Fixed the XPath typo)
        driver.findElement(By.xpath("//button[@class='btn-primary'])[1]")).click();
        
        // Optionally, add an assertion or wait for confirmation that the user is deleted
        WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'User deleted successfully')]"))); // Adjust this locator based on the actual confirmation message
        Assert.assertFalse(driver.getPageSource().contains("nyu@gmail.com"), "User still exists in the system");
    }
}