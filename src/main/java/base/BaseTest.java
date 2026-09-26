package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class BaseTest {

    // Protected so child test classes can inherit and use the driver
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // GitHub Actions automatically sets the "GITHUB_ACTIONS" environment variable to "true"
        String githubActions = System.getenv("GITHUB_ACTIONS");
        boolean isCI = githubActions != null && githubActions.equals("true");

        if (isCI) {
            System.out.println("Executing in CI Pipeline: Enabling Headless Mode");
            // The "new" headless mode is required for modern Selenium 4.x
            options.addArguments("--headless=new");
            // Mandatory arguments to prevent crashes in Linux CI environments
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            System.out.println("Executing Locally: Running in Headed Mode");
            options.addArguments("--start-maximized");
        }

        // Initialize driver (Selenium 4's built-in Selenium Manager automatically downloads ChromeDriver)
        driver = new ChromeDriver(options);

        // Set standard explicit/implicit wait baselines
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Failsafe maximize for local execution
        if (!isCI) {
            driver.manage().window().maximize();
        }
        // Open the application URL
        driver.get(ConfigReader.getProperty("baseUrl"));
    }
    public String captureScreenshot(String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Save screenshots in the same folder as the Extent Report
        String destPath = System.getProperty("user.dir") + "/target/ExtentReports/screenshots/" + testName + ".png";

        try {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/target/ExtentReports/screenshots/"));
            Files.copy(srcFile.toPath(), new File(destPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
        return destPath; // Return the path so ExtentReports can attach it
    }
    @AfterMethod
    public void tearDown() {
        // Clean up the session after every individual test
        if (driver != null) {
            driver.quit();
        }
    }
}