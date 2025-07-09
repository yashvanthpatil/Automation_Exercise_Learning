package TestRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",       // You can keep specific file too
    				glue = {"Stepdefinations"},                    // Stepdefs and hooks package
    	plugin = {
    	"pretty",                                   // Clean console output
        "html:target/cucumber-reports/cucumber.html", // Cucumber default HTML report
        "json:target/cucumber-reports/cucumber.json", // For ExtentReport adapter or CI
        "junit:target/cucumber-reports/cucumber.xml", // JUnit format for Jenkins
        "rerun:target/failedrerun.txt"                // Capture failed scenarios
    }
   // monochrome = true,         // Makes console output readable
   // dryRun = false,            // Set true to validate steps without execution
   // tags = "@Regression"       // Optional: run only tagged scenarios
)
public class AllTestRunner {
    // This class will run all feature files matching the criteria.
	///contactUsForm.feature
}
