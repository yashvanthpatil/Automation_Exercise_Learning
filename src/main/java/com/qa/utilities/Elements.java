package com.qa.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class Elements {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    public Elements(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // =================== Common Locator Handler ===================
    private WebElement resolve(Object locator) {
        if (locator instanceof By) {
            return driver.findElement((By) locator);
        } else if (locator instanceof WebElement) {
            return (WebElement) locator;
        } else {
            throw new IllegalArgumentException("Unsupported locator type: " + locator.getClass());
        }
    }

    // =================== Basic Actions ===================
    public void doclick(Object locator) {
        resolve(locator).click();
    }
    public WebElement getElement(Object locator) {
    	return resolve(locator);
    }

    public String GetTitle(){
        return driver.getTitle();
    }

    public void dosendKeys(Object locator, String value) {
        WebElement element = resolve(locator);
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    public boolean doisDisplayed(Object locator) {
        return resolve(locator).isDisplayed();
    }

    public String dogetText(Object locator) {
        return resolve(locator).getText();
    }

    // =================== Waits ===================
    public void dowaitForVisibility(Object locator) {
        if (locator instanceof By) {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By) locator));
        } else {
            wait.until(ExpectedConditions.visibilityOf((WebElement) locator));
        }
    }

    public void dowaitForClickable(Object locator) {
        if (locator instanceof By) {
            wait.until(ExpectedConditions.elementToBeClickable((By) locator));
        } else {
            wait.until(ExpectedConditions.elementToBeClickable((WebElement) locator));
        }
    }
    public void doWaitForPageLoadWithRetry(WebDriver driver, int maxRetries, int intervalMillis) {
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


    // =================== Dropdown ===================
    public void doselectByVisibleText(Object locator, String text) {
        Select dropdown = new Select(resolve(locator));
        dropdown.selectByVisibleText(text);
    }

    public void doselectByValue(Object locator, String value) {
        Select dropdown = new Select(resolve(locator));
        dropdown.selectByValue(value);
    }

    // =================== Actions ===================
    public void dohoverOver(Object locator) {
        actions.moveToElement(resolve(locator)).perform();
    }

    public void dodoubleClick(Object locator) {
        actions.doubleClick(resolve(locator)).perform();
    }

    // =================== JavaScript ===================
    public void doscrollIntoView(Object locator) {
        js.executeScript("arguments[0].scrollIntoView(true);", resolve(locator));
    }

    public void doclickByJS(Object locator) {
        js.executeScript("arguments[0].click();", resolve(locator));
    }

    // =================== Screenshots ===================
    public void docaptureScreenshot(String fileName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), new File("target/Screenshots" + fileName + ".png").toPath());
    }

    // =================== Flexible By (Optional Helper) ===================
    public By dogetBy(String type, String value) {
        switch (type.toLowerCase()) {
            case "id": return By.id(value);
            case "name": return By.name(value);
            case "xpath": return By.xpath(value);
            case "css": return By.cssSelector(value);
            case "class": return By.className(value);
            case "linktext": return By.linkText(value);
            case "partiallinktext": return By.partialLinkText(value);
            case "tag": return By.tagName(value);
            default: throw new IllegalArgumentException("Invalid locator type: " + type);
        }
    }

}

