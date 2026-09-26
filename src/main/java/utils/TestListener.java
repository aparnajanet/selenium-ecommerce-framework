package utils;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private ExtentReports extent = ExtentManager.getInstance();
    private ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new entry in the report for this test
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed. Exception: " + result.getThrowable().getMessage());

        // Grab the instance of BaseTest to access the driver and screenshot method
        Object testClass = result.getInstance();
        BaseTest baseTest = (BaseTest) testClass;

        // Take the screenshot and attach it to the report
        String screenshotPath = baseTest.captureScreenshot(result.getMethod().getMethodName());
        test.addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush writes everything to the HTML file
        extent.flush();
    }
}