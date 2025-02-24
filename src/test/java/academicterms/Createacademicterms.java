package academicterms;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Createacademicterms {
	 private WebDriver driver;

	  
		@BeforeMethod
	    public void setUp() throws Throwable {
	        WebDriverManager.chromedriver().setup();
	        ChromeOptions chromeOptions = new ChromeOptions();
	        chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

	        driver = new ChromeDriver(chromeOptions);
	        driver.manage().window().maximize();

	        driver.get("https://nyu.webwizardsusa.com/admin/login");

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
	        driver.findElement(By.xpath("(//p[normalize-space()='Academic Terms'])[1]")).click();
	        driver.findElement(By.xpath("(//button[normalize-space()='Create New Term'])[1]")).click();
	        driver.findElement(By.xpath("(//input[@id='terms'])[1]")).sendKeys("Term2025");
	        WebDriverWait icon = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement calender = icon.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='end_date']")));
	        calender.click();
	        driver.findElement(By.xpath("(//span[@class='flatpickr-next-month'])[2]")).click();
	        driver.findElement(By.xpath("(//span[@class='flatpickr-day' and text()='18'])[2]")).click();
	        driver.findElement(By.xpath("//span[text()='Create']")).click();
	        System.out.println("Academic term - Term2025 was created successfully");
	        
	        
	        
	    }
	   
		@AfterMethod
	    public void tearDown() {
	        System.out.println("Closing the browser...");
	        if (driver != null) {
	            driver.quit();
	        }
	    }
}
