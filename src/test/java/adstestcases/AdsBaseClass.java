package adstestcases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.ie.InternetExplorerDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class AdsBaseClass {

    public WebDriver driver;
    public String url;
    public String username;
    public String password;
    public static Logger log;

    @Parameters({"browser", "env"})
    @BeforeClass(alwaysRun = true)
    public void setup(String br, String env) {
        log = LogManager.getLogger("AdsBaseClass");

        try {
            Adsconfiguration config = new Adsconfiguration(env);
            url = config.geturl();
            username = config.getusername();
            password = config.getpassword();

            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<>();

                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--disable-infobars");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--disable-notifications");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    firefoxOptions.addPreference("dom.push.enabled", false);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "ie":
                    driver = new InternetExplorerDriver();
                    break;

                default:
                    log.error("Invalid browser specified: " + br);
                    throw new IllegalArgumentException("Unsupported browser: " + br);
            }

            log.info("Launching browser: " + br);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.get(url);
            log.info("Navigated to URL: " + url);

        } catch (Exception e) {
            log.error("Browser setup failed: " + e.getMessage(), e);
            driver = null;
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    public String captureScreenshot(WebDriver driver, String testName) throws Exception {
        if (driver == null) {
            log.error("WebDriver is null. Cannot take screenshot.");
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String fileName = testName + "-" + timestamp + ".png";
        String filePath = System.getProperty("user.dir") + "/Screenshots/" + fileName;

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(filePath);
        FileUtils.copyFile(src, dest);

        log.info("Screenshot saved at: " + filePath);
        return filePath;
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            log.info("=========== Closing Browser ===========");
            driver.quit();
        } else {
            log.warn("WebDriver is null during teardown.");
        }
    }
}
