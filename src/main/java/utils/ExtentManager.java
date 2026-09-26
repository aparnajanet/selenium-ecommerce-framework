package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Define where the HTML report will be saved
            String reportPath = System.getProperty("user.dir") + "/target/ExtentReports/Report.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            // Configure report UI
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("E-Commerce UI Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "QA Pipeline");
            extent.setSystemInfo("Framework", "Java/Selenium/TestNG");
        }
        return extent;
    }
}