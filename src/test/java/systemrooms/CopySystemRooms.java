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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class CopySystemRooms {
	
	private WebDriver driver;

  
	@BeforeMethod
	public void setUp() throws Throwable {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		driver.get("https://nyu-design.webwizardsusa.com/admin/login");

		// Updated WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
	public void CopySystemRoom() throws Throwable {
		// Updated WebDriverWait
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
		driver.findElement(By.xpath("//p[normalize-space()='System Rooms']")).click();
		
		// Updated WebDriverWait
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-original-title='Copy Room'])[1]"))).click();
		driver.findElement(By.id("roomName")).sendKeys("CTEST001");
		driver.findElement(By.xpath("(//span[normalize-space()='Copy'])[1]")).click();
		
		// Updated WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement roomname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class='cl-title' and text()='CTEST001']")));
        String firstrow = roomname.getText();
        System.out.println(firstrow);
        
        // Assert the result instead of using System.out.println
        AssertJUnit.assertEquals(firstrow, "CTEST001", "Room copy failed!"); 
	}

	@AfterMethod
    public void tearDown() {
        System.out.println("Closing the browser...");
        if (driver != null) {
            driver.quit();
        }
    }
}