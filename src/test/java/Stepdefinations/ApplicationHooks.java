package Stepdefinations;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qa.factory.DriverFactory;
import com.qa.utilities.ExtentReporter;
import com.qa.utilities.ReadConfig;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class ApplicationHooks {

    private DriverFactory driverfactory;
    private WebDriver driver;
    private ReadConfig readConfig;
    private Properties prop;

    // ExtentReports objects
    private static ExtentReports extent = ExtentReporter.initExtentReport();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Before(order = 0)
    public void getProperty(Scenario scenario) {
        this.readConfig = new ReadConfig();
        this.prop = readConfig.init_prop();

        // Start scenario in Extent Report
        ExtentTest extentTest = extent.createTest(scenario.getName());
        for (String tag : scenario.getSourceTagNames()) {
            extentTest.assignCategory(tag.replace("@", ""));
        }
        test.set(extentTest);
    }

    @Before(order = 1)
    public void launchBrowser() {
        String browserName = prop.getProperty("browser");
        driverfactory = new DriverFactory();
        driver = driverfactory.init_driver(browserName);
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");

            // Attach screenshot to Cucumber report
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);

            // Attach screenshot to Extent Report
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.get().fail("Scenario Failed: " + scenario.getName());
            test.get().addScreenCaptureFromBase64String(base64Screenshot, screenshotName);
        } else {
            test.get().pass("Scenario Passed: " + scenario.getName());
        }
    }

    @After(order = 0)
    public void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }

        // Flush Extent Report once after all tests
        extent.flush();
    }
}
