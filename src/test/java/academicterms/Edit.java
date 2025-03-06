package academicterms;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.helper.ConfigurationReader;
import com.base.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class Edit {
	private WebDriver driver;
	private WebDriverWait wait;

	private static final String EMAIL_INPUT = "email";
	private static final String PASSWORD_INPUT = "password";
	private static final String LOGIN_BUTTON = "//button[@type='submit']";
	private static final String MENU_BUTTON = "menuButton";
	private static final String ACADEMIC_TERMS_MENU = "//p[normalize-space()='Academic Terms']";
	private static final String EDIT_ICON = "//*[name()='svg' and contains(@class, 'fa-pencil')]";
	private static final String STATUS_DROPDOWN = "status-select";
	private static final String UPDATE_BUTTON = "//span[text()='Update']";

	@BeforeMethod
	public void setUp() {
		System.out.println("🚀 Initializing WebDriver...");
		driver = BaseClass.initializeWebDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		ConfigurationReader config = ConfigurationReader.getInstance();
		String baseUrl = config.getUrl();
		String email = config.getEmail();
		String password = config.getPassword();

		System.out.println("🔗 Navigating to login page: " + baseUrl + "/admin/login");
		driver.get(baseUrl + "/admin/login");

		wait.until(ExpectedConditions.urlContains("/admin/login"));
		Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "/admin/login", "❌ Expected URL is not opened");
		System.out.println("✅ Login page loaded successfully.");

		try {
			System.out.println("🔑 Entering login credentials...");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_INPUT))).sendKeys(email);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PASSWORD_INPUT))).sendKeys(password);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LOGIN_BUTTON))).click();
			System.out.println("✅ Login successful!");
		} catch (Exception e) {
			Assert.fail("❌ Login failed: " + e.getMessage());
		}
	}

	@Test
	public void edit() throws Throwable {
		System.out.println("📌 Navigating to 'Academic Terms'...");
		wait.until(ExpectedConditions.elementToBeClickable(By.id(MENU_BUTTON))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ACADEMIC_TERMS_MENU))).click();

		System.out.println("✏ Editing academic term...");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(EDIT_ICON))).click();

		Thread.sleep(2000);
		WebElement statusDropdown = driver.findElement(By.id(STATUS_DROPDOWN));
		Select select = new Select(statusDropdown);
		select.selectByVisibleText("Inactive");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(UPDATE_BUTTON))).click();
		System.out.println("✅ Academic term status updated successfully.");

		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(EDIT_ICON))).click();

		Thread.sleep(2000);
		WebElement updatedStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(STATUS_DROPDOWN)));
		Select updatedSelect = new Select(updatedStatus);
		String selectedOption = updatedSelect.getFirstSelectedOption().getText();

		Assert.assertEquals(selectedOption, "Inactive", "The status was not updated successfully!");

		System.out.println("✅ SUCCESS: Academic term status has been correctly updated and verified!");

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		System.out.println("🔄 Closing the browser...");
		BaseClass.quitWebDriver();
		System.out.println("✅ Browser closed successfully.");
	}
}
