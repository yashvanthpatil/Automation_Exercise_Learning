package com.qa.factory;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver init_driver(String browser) {

        System.out.println("browser value is: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // 🔒 Preferences to disable popups and password prompts
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2); // Block notifications
            prefs.put("profile.default_content_setting_values.geolocation", 2); // Block location
            prefs.put("profile.default_content_setting_values.media_stream_camera", 2); // Block camera
            prefs.put("profile.default_content_setting_values.media_stream_mic", 2); // Block mic
            prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
            prefs.put("profile.default_content_setting_values.popups", 0);

            // ❌ Disable save password prompts
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs", prefs);

            // 🕵️ Run Chrome in Incognito mode
           // options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--ignore-certificate-errors");
            // Optional: Start maximized or headless
            options.addArguments("--start-maximized");

            tlDriver.set(new ChromeDriver(options));

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();

            EdgeOptions options = new EdgeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.default_content_setting_values.geolocation", 2);
            prefs.put("profile.default_content_setting_values.media_stream_camera", 2);
            prefs.put("profile.default_content_setting_values.media_stream_mic", 2);
            prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
            prefs.put("profile.default_content_setting_values.popups", 0);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--disable-notifications");
            options.addArguments("--ignore-certificate-errors");
          //  options.addArguments("-inprivate"); // Edge incognito mode
            options.addArguments("--start-maximized");

            tlDriver.set(new EdgeDriver(options));

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("safari")) {
            tlDriver.set(new SafariDriver());
        } else {
            System.out.println("Please pass a valid browser name: " + browser);
        }

        getDriver().manage().deleteAllCookies();
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
}
