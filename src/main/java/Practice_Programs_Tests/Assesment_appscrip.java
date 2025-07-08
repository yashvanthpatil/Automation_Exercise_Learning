package Practice_Programs_Tests;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assesment_appscrip {

	private static WebDriver driver;
	private static WebDriverWait wait;
	private static String searchTerm = "bicycles";

	public Assesment_appscrip(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public static void doWaitForPageLoadWithRetry(WebDriver driver, int maxRetries, int intervalMillis) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int attempt = 0;

		while (attempt < maxRetries) {
			String state = js.executeScript("return document.readyState").toString();
			if (state.equals("complete")) {
				System.out.println("Page loaded successfully after " + (attempt + 1) + " attempt(s).");
				return;
			}

			System.out.println("Page not fully loaded. Attempt: " + (attempt + 1));

			// Refresh after the first attempt
			if (attempt > 0) {
				driver.navigate().refresh();
				System.out.println("Page refreshed.");
			}

			try {
				Thread.sleep(intervalMillis); // wait before retrying
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			attempt++;
		}

		throw new RuntimeException("Page did not load after " + maxRetries + " retries, including refresh attempts.");
	}

	public static void launch_browser() {
		WebDriverManager.edgedriver().setup();

		EdgeOptions options = new EdgeOptions();
		options.addArguments("-inprivate");
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.geolocation", 2); // block location
		prefs.put("profile.default_content_setting_values.notifications", 2); // block notifications
		options.setExperimentalOption("prefs", prefs);

		driver = new EdgeDriver(options);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String url = "https://web.platform.kwibal.com/";

		driver.get(url);
		doWaitForPageLoadWithRetry(driver, 3, 30);

		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// System.out.println(driver.switchTo().alert().getText());
		// driver.switchTo().alert().dismiss();
	}

	public static void Searchproduct() {
		doWaitForPageLoadWithRetry(driver, 3, 30);
		WebElement searchbox = driver.findElement(By.xpath("//input[@name=\"search\"]"));

		wait.until(ExpectedConditions.visibilityOf(searchbox));
		searchbox.click();
		searchbox.clear();
		searchbox.sendKeys(searchTerm);

	}

	public static void clickSearchButton() {
		doWaitForPageLoadWithRetry(driver, 3, 30);
		WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(),'Search')]"));
		wait.until(ExpectedConditions.visibilityOf(searchButton));
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
	}

	public static void validateSearchResults() {
		doWaitForPageLoadWithRetry(driver, 3, 30);

		// Wait for the result summary (like "24 search results for "bicycles"")
		List<WebElement> resultHeader = driver.findElements(By.xpath("//div//h1"));
		for (WebElement headers : resultHeader) {
			String headersText = headers.getText().trim();
			if (headersText.contains("search results for")) {
				System.out.println("Result Count Text: " + headersText);

				// Extract numeric count
				int resultCount = 0;
				Pattern pattern = Pattern.compile("^(\\d+)");
				Matcher matcher = pattern.matcher(headersText);

				if (matcher.find()) {
					resultCount = Integer.parseInt(matcher.group(1));
				} else {
					System.out.println("⚠️ Could not extract result count from text: " + headersText);
				}

				// Check if 0 results — don't fail, just log and proceed
				if (resultCount == 0) {
					System.out.println("⚠️ No results found for search term: \"" + searchTerm + "\"");
				} else {
					System.out.println("✅ Found " + resultCount + " results for \"" + searchTerm + "\"");
				}
			}
		}

			// Now validate the header text like: Search results for "bicycles"
			WebElement searchResultLabel = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/ol/li[2]/span")));
			String actualMessage = searchResultLabel.getText().trim();
			String expectedMessage = "Search results for \"" + searchTerm + "\"";

			System.out.println("Actual Message   : " + actualMessage);
			System.out.println("Expected Message : " + expectedMessage);

			Assert.assertEquals("results are not matching",expectedMessage, actualMessage);
		
	}

	public static boolean searchButtonClickable() {
		doWaitForPageLoadWithRetry(driver, 3, 30);
		WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(),'Search')]"));
		doWaitForPageLoadWithRetry(driver, 3, 30);
		return searchButton.isEnabled();

	}

	public static void tearDown() {
		driver.quit();
	}

	public static void main(String[] args)  {
		launch_browser();
		searchButtonClickable();
		Searchproduct();
		clickSearchButton();
		validateSearchResults();
		tearDown();

	}
}
