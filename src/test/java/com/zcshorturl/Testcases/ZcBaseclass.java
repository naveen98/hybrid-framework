package com.zcshorturl.Testcases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class ZcBaseclass {
	public WebDriver driver;
	public String url;
	public String username;
	public String password;
	public static Logger log;


	@Parameters({"browser","env"})
	@BeforeClass
	public void setup(String br,String env) {

		log = LogManager.getLogger("ZcBaseclass");
		
		zcReadconfig readconfig= new zcReadconfig(env);
		url=readconfig.geturl();
		username=readconfig.getusername();
		password=readconfig.getpassword();
		
		
		switch (br) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "ie":
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("Invalid Browser");
			return;

		}

		log.info("Launching browser: " + br);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		log.info("Navigated to URL : " + url);

	}

	public String capturescreen(WebDriver driver, String tname) throws Throwable {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String filename = tname + "-" + timestamp + ".png";
		String filepath = System.getProperty("user.dir") + "/Screenshorts/" + filename;

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(filepath);
		FileUtils.copyFile(src, dest);
		System.out.println("Screenshot Taken");

		return filepath;
	}
	
	

	  @AfterClass public void teardown() {
	  log.info("========= close Browser ==============");
	  driver.quit();
	  
	  }

}
