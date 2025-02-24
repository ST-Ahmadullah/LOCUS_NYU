package systemrooms;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class CreateSystemRooms {
    private WebDriver driver;

   
	@BeforeMethod
    public void setUp() throws Throwable {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        driver.get("https://nyu-design.webwizardsusa.com/admin/login");

        // Updated WebDriverWait with Duration
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void CreateSystemRoom() throws Throwable {
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
        driver.findElement(By.xpath("//p[normalize-space()='System Rooms']")).click();
        driver.findElement(By.xpath("//button[text()=' Create New Room ']")).click();
        driver.findElement(By.id("roomName")).sendKeys("TEST001");
        driver.findElement(By.xpath("//span[normalize-space()='Create']")).click();

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='New Row']"))).click();
        
        // Select Room and Seat
        driver.findElement(By.xpath("//div[@id='0_0']")).click();
        driver.findElement(By.xpath("//div[@id='69_0']")).click();
        driver.findElement(By.xpath("//button[@id='curvePoint']//span[@class='button-text'][contains(text(),'Select')]")).click();
        driver.findElement(By.xpath("(//div[@id='32_9'])[1]")).click();
        driver.findElement(By.id("seat")).sendKeys("5");
        driver.findElement(By.xpath("//span[text()='Save & Exit']")).click();

        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Generate Seat Numbers']"))).click();
        driver.findElement(By.xpath("//span[text()='Create']")).click();
        Thread.sleep(3000);
        WebElement dropdownElement = driver.findElement(By.xpath("//select[@class='form-select section-select text-truncate']"));
        dropdownElement.click();
        Thread.sleep(3000);
        Select attendanceDropdown = new Select(dropdownElement);
        attendanceDropdown.selectByValue("1");
        dropdownElement.click();
        Thread.sleep(2000);
        WebDriverWait verify = new WebDriverWait(driver, Duration.ofSeconds(10));
        verify.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
        driver.findElement(By.xpath("//p[normalize-space()='System Rooms']")).click();
        Thread.sleep(2000);
        // Assertion: Verify if the room TEST001 has been created successfully
        WebElement roomName = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'TEST001')]")));
        AssertJUnit.assertNotNull(roomName);
    }

 
	@AfterMethod
    public void tearDown() {
        System.out.println("Closing the browser...");
        if (driver != null) {
            driver.quit();
        }
    }
}