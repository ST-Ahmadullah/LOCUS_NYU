package rolesandpermission;

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

public class CreateRolesandPermission {
	
	private WebDriver driver;

    @BeforeMethod
	public void setUp() throws Throwable {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		driver.get("https://nyu.webwizardsusa.com/admin/login");

		// Updated WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("noreply@locus.online");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void createusermanagement() throws Throwable {
		// Updated WebDriverWait
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='menuButton']"))).click();
		driver.findElement(By.xpath("(//p[normalize-space()='Roles & Permissions'])[1]")).click();
		driver.findElement(By.xpath("(//button[normalize-space()='Create New Role'])[1]")).click();
		driver.findElement(By.xpath("(//input[@id='Role Name'])[1]")).sendKeys("TestingRole");

		// Updated WebDriverWait
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class, 'form-check')]//input[@type='checkbox'])[1]"))).click();

		WebElement checkbox1 = new WebDriverWait(driver, Duration.ofSeconds(10))
		        .until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id='__BVID__241744___BV_form-check__'])[1]")));
		checkbox1.click();
    }
}