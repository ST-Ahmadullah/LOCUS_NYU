package academicterms;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.helper.ConfigurationReader;
import com.base.BaseClass;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Create {
	private WebDriver driver;
	private WebDriverWait wait;

	private static final String EMAIL_INPUT = "email";
	private static final String PASSWORD_INPUT = "password";
	private static final String LOGIN_BUTTON = "//button[@type='submit']";
	private static final String MENU_BUTTON = "menuButton";
	private static final String ACADEMIC_TERMS_MENU = "(//p[normalize-space()='Academic Terms'])[1]";
	private static final String CREATE_TERM_BUTTON = "(//button[normalize-space()='Create New Term'])[1]";
	private static final String TERM_NAME_INPUT = "terms";
	private static final String START_DATE_INPUT = "start_date";
	private static final String END_DATE_INPUT = "end_date";
	private static final String MONTH_FROM = "(//option[@class='flatpickr-monthDropdown-month'])[7]";
	private static final String MONTH_TO = "(//span[@class='flatpickr-next-month'])[2]";
	private static final String SELECT_START_DATE = "(//span[@class='flatpickr-day'][normalize-space()='28'])[1]";
	private static final String SELECT_END_DATE = "(//span[@aria-label='August 27, 2025'])[1]";
	private static final String CREATE_BUTTON = "//span[text()='Create']";

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
	public void add() throws Throwable {

//		String termName = "AcademicTerm-" + System.currentTimeMillis(); // Dynamic name to avoid conflicts

		String termName = "AcademicTerm-2025";

		System.out.println("📌 Navigating to 'Academic Terms'...");
		wait.until(ExpectedConditions.elementToBeClickable(By.id(MENU_BUTTON))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ACADEMIC_TERMS_MENU))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_TERM_BUTTON))).click();

		System.out.println("📝 Entering academic term details...");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(TERM_NAME_INPUT))).sendKeys(termName);

		wait.until(ExpectedConditions.elementToBeClickable(By.id(START_DATE_INPUT))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(MONTH_FROM))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SELECT_START_DATE))).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id(END_DATE_INPUT))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(MONTH_TO))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SELECT_END_DATE))).click();

		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_BUTTON))).click();

		System.out.println("✅ Academic term '" + termName + "' was created successfully.");
	}

	@AfterMethod
	public void tearDown() {
		System.out.println("🔄 Closing the browser...");
		BaseClass.quitWebDriver();
		System.out.println("✅ Browser closed successfully.");
	}
}
