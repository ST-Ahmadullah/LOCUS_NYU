package systemrooms;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class EditSystemRooms {
    private WebDriver driver;

    
	@BeforeMethod
    public void setUp() throws Throwable {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        driver.get("https://nyu-design.webwizardsusa.com/admin/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void editsystemrooms() throws Throwable {
        WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
        driver.findElement(By.xpath("//p[normalize-space()='System Rooms']")).click();

        // Edit System Room CTEST001
        WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class='cl-title' and text()='TEST001']"))).click();
        driver.findElement(By.xpath("//span[text()='New Row']")).click();

        WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='0_16']")));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        driver.findElement(By.xpath("//div[@id='0_14']")).click();
        driver.findElement(By.xpath("//div[@id='77_14']")).click();
        driver.findElement(By.id("seat")).sendKeys("5");
        Thread.sleep(2000);

        // Save changes
        WebDriverWait save = new WebDriverWait(driver, Duration.ofSeconds(10));
        save.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Save & Exit']"))).click();
        Thread.sleep(2000);
       
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Generate Seat Numbers']"))).click();
        driver.findElement(By.xpath("//span[text()='Create']")).click();
        
        
    }

    
	@AfterMethod
    public void tearDown() {
        System.out.println("Closing the browser...");
        if (driver != null) {
            driver.quit();
        }
    }
}