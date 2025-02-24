package usermanagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class ViewUserManagement {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws Throwable {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        
        // Set an implicit wait globally
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://nyu.webwizardsusa.com/admin/login");

        // Using explicit wait for login process
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void viewusermanagement() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Navigate to User Management section
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
        driver.findElement(By.xpath("(//p[normalize-space()='User Management'])[1]")).click();
        
        // Search for user by email
        driver.findElement(By.xpath("(//input[@id='search'])[1]")).sendKeys("nyu@gmail.com");
        driver.findElement(By.xpath("(//button[normalize-space()='Search'])[1]")).click();
        
        // Wait for user row to appear and get the email text
        WebElement firstRowEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[normalize-space()='nyu@gmail.com'])[1]")));
        String emailText = firstRowEmail.getText();
        
        // Assert that the user exists in the search results
        Assert.assertEquals(emailText, "nyu@gmail.com", "User not found or email mismatch");
        
        // Optional: Print confirmation or other user details
        System.out.println("User found: " + emailText);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}