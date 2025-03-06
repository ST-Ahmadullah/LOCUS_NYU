package myclasses;

import org.openqa.selenium.By;
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

import java.time.Duration;

public class ClassEditAndDelete {
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
	public void editAndDelete() throws Throwable {
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
		driver.findElement(By.xpath("(//a[@class='router-link-active router-link-exact-active active'])[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[contains(@class, 'create-search-button')])[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//input[contains(@class, 'offering-create')])[1]")).sendKeys("NYUTest");

		WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait7.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//select[@id='academic-term-select']/option[text()='new terms']")))
				.click();

		driver.findElement(By.xpath("//button[contains(@class, 'add-button')]")).click();
		driver.findElement(By.xpath("//li[@class='fd__item']//a[text()='Barker Orla (1585)']")).click();
		driver.findElement(By.xpath("//input[@type='checkbox' and @value='Tue']")).click();
		driver.findElement(By.xpath("//input[@type='checkbox' and @value='Wed']")).click();
		driver.findElement(By.xpath("//input[@type='checkbox' and @value='Thu']")).click();
		driver.findElement(By.xpath("(//button[@class='red btn-primary'])[1]")).click();
		Thread.sleep(2000);

		WebDriverWait menu = new WebDriverWait(driver, Duration.ofSeconds(10));
		menu.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
		Thread.sleep(2000);

		WebDriverWait myclasses = new WebDriverWait(driver, Duration.ofSeconds(20));
		myclasses.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//a[@class='router-link-active router-link-exact-active active'])[1]"))).click();
		Thread.sleep(2000);

		Actions edit = new Actions(driver);
		WebElement icon = driver.findElement(By.xpath("(//*[name()='svg'][@role='img'])[29]"));
		edit.moveToElement(icon).perform();
		icon.click();
	}
}