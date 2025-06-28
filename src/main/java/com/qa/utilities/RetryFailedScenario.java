package com.qa.utilities;

import com.aventstack.extentreports.Status;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
// import com.qa.utilities.ExtentTestManager;


public class RetryFailedScenario implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            ExtentTestManager.getTest().log(Status.WARNING,
                "Retrying scenario: " + result.getName() + " | Attempt " + retryCount);
            return true;
        }
        return false;
    }
}
