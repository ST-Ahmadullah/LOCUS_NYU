package studentAttendanceReport;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Absent {
	
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
    public void downloadAttendanceReport() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Navigate through the system
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menuButton"))).click();
        driver.findElement(By.xpath("//p[normalize-space()='Reports']")).click();
        driver.findElement(By.xpath("//p[normalize-space()='Student Attendance']")).click();
        
        // Scroll Down to ensure all elements are visible
        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
        System.out.println("Page scrolled down.");
        
        // Select Attendance Type
        driver.findElement(By.xpath("//select[@id='attendance-select']/option[text()='Absent']")).click();
        driver.findElement(By.xpath("//select[@id='attendance-con-select']/option[text()='<=']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Number of Days']")).sendKeys("2");
        driver.findElement(By.xpath("//select[@id='term-select']/option[text()='Winter 2023']")).click();
        
        // Click Generate Report
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='Generate']")).click();
        
        // Wait and Click Download Button
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[contains(@class, 'download-button')]")).click();
        
     // 5️⃣ Handle "Save As" Dialog
        saveFileInUbuntu();
        
        System.out.println("✅ File saved successfully!");
        driver.quit();
    }

    public static void saveFileInUbuntu() throws AWTException, InterruptedException {
        Robot robot = new Robot();

        // 1️⃣ Wait for Save As dialog
        Thread.sleep(3000);

        // 2️⃣ Set file path in clipboard
        String filePath = "/home/sumanas/Downloads"; // Change as needed
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        // 3️⃣ Paste file path using Ctrl + V
        pasteFromClipboard(robot);
        Thread.sleep(500);  // Wait for text to appear

        // 4️⃣ Navigate to Save button (Press TAB multiple times)
        pressTabKey(robot);  // Move focus to Save button
        Thread.sleep(500);

        // 5️⃣ Press 'Enter' to save
        pressEnterKey(robot);
    }

    private static void pasteFromClipboard(Robot robot) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    private static void pressEnterKey(Robot robot) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private static void pressTabKey(Robot robot) {
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }
}
