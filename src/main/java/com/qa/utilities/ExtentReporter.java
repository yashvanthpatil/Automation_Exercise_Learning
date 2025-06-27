package com.qa.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {

    private static ExtentReports extent;

    public static ExtentReports initExtentReport() {
        String path = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Automation Test Report");
        reporter.config().setDocumentTitle("Automation Exercise Project");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Yashvanth Patil");
        extent.setSystemInfo("Environment", "QA");

        return extent;
    }
}
