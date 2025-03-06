package analytics;

import java.io.File;
import java.time.Duration;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Analytics {
	private WebDriver driver;

	@BeforeMethod
	public void setUp() throws Throwable {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

		driver = new ChromeDriver(chromeOptions);

		driver.manage().window().maximize();

		driver.get("https://nyu.webwizardsusa.com/admin/login");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Updated this line
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Test
	public void analytics() throws Throwable {
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10)); // Updated this line
		wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
		driver.findElement(By.xpath("(//p[normalize-space()='Analytics'])[1]")).click();

		Actions footer = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Updated this line
		WebElement userchart = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='mb-2'])[1]")));
		footer.moveToElement(userchart).perform();
		Thread.sleep(3000);

		WebElement chart = driver.findElement(By.xpath("(//canvas[@id='users-activity'])[1]"));
		File screenshot = chart.getScreenshotAs(OutputType.FILE);

		try {
			FileHandler.copy(screenshot, new File("userchart_screenshot.png"));
			System.out.println("Screenshot saved successfully.");
		} catch (IOException e) {
			System.out.println("Failed to save screenshot: " + e.getMessage());
		}

		driver.findElement(By.xpath("(//button[normalize-space()='Rooms'])[1]")).click();
	}
}