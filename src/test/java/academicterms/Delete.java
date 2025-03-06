package academicterms;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.helper.ConfigurationReader;

import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Delete {
	private WebDriver driver;
	private WebDriverWait wait;

	private static final String EMAIL_INPUT = "email";
	private static final String PASSWORD_INPUT = "password";
	private static final String LOGIN_BUTTON = "//button[@type='submit']";
	private static final String MENU_BUTTON = "menuButton";
	private static final String ACADEMIC_TERMS_MENU = "(//p[normalize-space()='Academic Terms'])[1]";
	private static final String DELETE_BUTTON = "(//button[@data-original-title='Delete'])[1]";
	private static final String CONFIRM_DELETE_BUTTON = "//button[.//span[text()='Delete']]";

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
	public void delete() {
		try {
			System.out.println("📌 Navigating to 'Academic Terms'...");
			wait.until(ExpectedConditions.elementToBeClickable(By.id(MENU_BUTTON))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ACADEMIC_TERMS_MENU))).click();

			String termToDelete = "AcademicTerm-2025";

			System.out.println("🗑️ Deleting an academic term...");
			WebElement deleteButton = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELETE_BUTTON)));
			deleteButton.click();

			WebElement confirmDelete = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(CONFIRM_DELETE_BUTTON)));
			confirmDelete.click();

			Thread.sleep(2000);
			List<WebElement> deletedTerm = driver
					.findElements(By.xpath("//tr[td[contains(text(),'" + termToDelete + "')]]"));

			Assert.assertTrue(deletedTerm.isEmpty(),
					"❌ Academic Term '" + termToDelete + "' still exists after deletion!");

			System.out.println("✅ Academic Term '" + termToDelete + "' was successfully deleted.");

		} catch (Exception e) {
			Assert.fail("❌ Deletion failed: " + e.getMessage());
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		System.out.println("🔄 Closing the browser...");
		BaseClass.quitWebDriver();
		System.out.println("✅ Browser closed successfully.");
	}
}