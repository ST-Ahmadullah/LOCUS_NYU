package myclasses;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Attendance {
	private WebDriver driver;

	@BeforeMethod
	public void setUp() throws Throwable {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		driver.get("https://nyu.webwizardsusa.com/admin/login");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Test
	public void attendance() throws Throwable {
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
		driver.findElement(By.xpath("(//a[@class='router-link-active router-link-exact-active active'])[1]")).click();

		WebDriverWait calender = new WebDriverWait(driver, Duration.ofSeconds(10));
		calender.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(@class, 'calender-hover')])[2]")))
				.click();

		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait6.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//select[contains(@class, 'attendance-select')]/option[text()='Present']"))).click();

		WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait7.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//select[contains(@class, 'attendance-select')]/option[text()='Clear all']"))).click();
		WebElement clear = driver.findElement(By.xpath("//span[contains(text(), 'Yes, Clear')]"));
		clear.click();
		WebDriverWait wait8 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait8.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//select[contains(@class, 'attendance-select')]/option[text()='Absent']"))).click();
		// driver.findElement(By.xpath("//span[contains(text(), 'Yes,
		// Clear')]")).click();

		WebDriverWait wait9 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait9.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']"))).sendKeys("ALEA");
		driver.findElement(By.xpath("//button[text()='Present']")).click();
		driver.findElement(By.xpath("//span[text()='Export Attendance']")).click();
		String parentWindow = driver.getWindowHandle();
		driver.switchTo().window(parentWindow);
		driver.navigate().refresh();

		WebDriverWait present = new WebDriverWait(driver, Duration.ofSeconds(10));
		present.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Present'])[2]")))
				.click();

		WebDriverWait tardy = new WebDriverWait(driver, Duration.ofSeconds(10));
		tardy.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Tardy'])[3]"))).click();
		Thread.sleep(2000);

		WebDriverWait absent = new WebDriverWait(driver, Duration.ofSeconds(20));
		absent.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Absent'])[4]"))).click();
		// driver.findElement(By.xpath("//span[text()='Export Attendance']")).click();
		driver.navigate().refresh();
		driver.findElement(By.xpath("//span[text()='Seating Chart']")).click();

		WebDriverWait room = new WebDriverWait(driver, Duration.ofSeconds(10));
		room.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Auto Assign']"))).click();
		driver.findElement(By.xpath("//input[@id='random']")).click();
		driver.findElement(By.xpath("//button[contains(@class, 'red')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Create PDF']")).click();
		Thread.sleep(1000);

		driver.close();
	}
}