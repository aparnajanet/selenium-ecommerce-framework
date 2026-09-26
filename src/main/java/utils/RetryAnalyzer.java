package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2; // It will try 2 additional times after the first failure

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (retryCount < maxRetryCount) {
                retryCount++;
                System.out.println("Flaky Test Detected! Retrying " + result.getName() +
                        " for the " + retryCount + " time.");
                return true; // Tells TestNG to run the test again
            }
        }
        return false; // Stop retrying if the limit is reached or the test passed
    }
}