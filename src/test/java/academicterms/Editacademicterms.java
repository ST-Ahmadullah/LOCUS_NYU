package academicterms;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Editacademicterms {
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement editIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='svg' and contains(@class, 'fa-pencil')]")));
        editIcon.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('status-select').value='inactive';");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='Update']")).click();
        System.out.println("Academic term - Term2025 status was changed");
        
        
        
    }
 
	@AfterMethod
    public void tearDown() {
        System.out.println("Closing the browser...");
        if (driver != null) {
            driver.quit();
        }
       
        
        

}
}
