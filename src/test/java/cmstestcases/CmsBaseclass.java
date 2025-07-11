package cmstestcases;

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

public class CmsBaseclass {
	
	
	  public WebDriver driver;
	    public String url;
	    public String username;
	    public String password;
	    public static Logger log;

	    @Parameters({"browser", "env"})
	    @BeforeClass
	    public void setup(String br, String env) {
	        log = LogManager.getLogger("CmsBaseClass");

	        try {
	            CmsReadconfig readconfig = new CmsReadconfig(env);
	            url = readconfig.geturl();
	            username = readconfig.getusername();
	            password = readconfig.getpassword();

	            switch (br.toLowerCase()) {
	                case "chrome":
	                	 ChromeOptions chromeOptions = new ChromeOptions();
	                	    Map<String, Object> prefs = new HashMap<>();

	                	    //Disable password manager completely
	                	    prefs.put("credentials_enable_service", false);                  
	                	    prefs.put("profile.password_manager_enabled", false);         
	                	 // Block site notifications
	                	    prefs.put("profile.default_content_setting_values.notifications", 2); 

	                	    chromeOptions.setExperimentalOption("prefs", prefs);
	                	  //  chromeOptions.addArguments("--incognito");
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
	                    log.error("Invalid Browser: " + br);
	                    throw new IllegalArgumentException("Invalid Browser: " + br);
	            }

	            log.info("Launching browser: " + br);
	            driver.manage().window().maximize();
	            driver.manage().deleteAllCookies();
	            driver.get(url);
	            log.info("Navigated to URL : " + url);

	        } catch (Exception e) {
	            log.error("Browser setup failed: " + e.getMessage());
	            driver = null;
	            throw new RuntimeException("Failed to initialize WebDriver", e);
	        }
	    }

	    public String capturescreen(WebDriver driver, String tname) throws Throwable {
	        if (driver == null) {
	            log.error("WebDriver is null, cannot capture screenshot.");
	        }
	        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	        String filename = tname + "-" + timestamp + ".png";
	        String filepath = System.getProperty("user.dir") + "/Screenshorts/" + filename;

	        TakesScreenshot ts = (TakesScreenshot) driver;
	        File src = ts.getScreenshotAs(OutputType.FILE);
	        File dest = new File(filepath);
	        FileUtils.copyFile(src, dest);
	        log.info("Screenshot Taken: " + filepath);

	        return filepath;
	    }

	    @AfterClass
	    public void teardown() {
	        if (driver != null) {
	            log.info("========= Closing Browser ==============");
	            driver.quit();
	        } else {
	            log.warn("WebDriver was null at teardown, nothing to quit.");
	        }
	    }
	}