package submitATicket;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateTicket {
	private WebDriver driver;

    @BeforeMethod
    public void setUp() throws Throwable {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        driver.get("https://nyu.webwizardsusa.com/admin/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Updated line
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void createusermanagement() throws Throwable {
        WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));  // Updated line
        wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
        driver.findElement(By.xpath("//p[normalize-space()='Submit a Ticket']")).click();
     // Wait for the iframe and switch to it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))); 
        driver.switchTo().frame(iframeElement); // Switch to the iframe

        // Wait for the dropdown and click using JavaScript
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='t-cf-1']/option[@value='1']")));
        option.click();

        // Interact with the input fields inside the iframe
        driver.findElement(By.xpath("(//input[@id='t-cf-3'])[1]")).sendKeys("www.test.com");
        driver.findElement(By.xpath("(//input[@id='id_subject'])[1]")).sendKeys("sdbfsdb");
        driver.findElement(By.xpath("(//textarea[@id='id_text'])[1]")).sendKeys("vbsvbudufusbfuhbsdusbdubsdbus");
        Thread.sleep(2000);
        Actions actions = new Actions(driver);
        WebElement addCC = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[normalize-space()='Add CC'])[1]")));
        actions.moveToElement(addCC).click().perform();
        driver.findElement(By.xpath("(//input[@id='id_cc'])[1]")).sendKeys("test1@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//a[normalize-space()='Add BCC'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input[@id='id_bcc'])[1]")).sendKeys("test2@gmail.com");
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input[@id='id_name'])[1]")).sendKeys("ABCD");
        driver.findElement(By.xpath("(//input[@id='id_email'])[1]")).sendKeys("abcd@gmail.com");
     // Wait for the iframe and switch to it
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement recaptchaIframe = wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@src, 'recaptcha')]")));
        driver.switchTo().frame(recaptchaIframe); // Switch to reCAPTCHA iframe
        Actions robot = new Actions(driver);
        // Locate the checkbox and click it
        WebElement recaptchaCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='recaptcha-checkbox-border']")));
        robot.moveToElement(recaptchaCheckbox).click().perform();

        // Switch back to the main content
        driver.switchTo().defaultContent();
        

    }
}
