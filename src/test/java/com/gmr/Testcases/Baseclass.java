package com.gmr.Testcases;

import java.io.File;

import org.apache.commons.io.FileUtils;
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

import com.utilites.Readconfig;

public class Baseclass {
	Readconfig readconfig = new Readconfig();
	public String baseurl = readconfig.getapplicationurl();
	public String username = readconfig.getusername();
	public String password = readconfig.getpassword();
	public WebDriver driver;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {
		
	switch (br) {
	case"chrome":driver = new ChromeDriver();break;
	case"edge":driver = new EdgeDriver();break;
	case"firefox":driver = new FirefoxDriver();break;
	case"ie":driver = new InternetExplorerDriver();break;
	default:System.out.println("Invalid Browser");return;

	}
	    driver.manage().window().maximize();
	    driver.manage().deleteAllCookies();
		driver.get(baseurl);
	}

	/*@AfterClass
	public void teardown() {
		driver.quit();
	}*/

	public void capturescreen(WebDriver driver, String tname) throws Throwable {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/Screenshorts/" + tname + ".png");
		FileUtils.copyFile(src, dest);
		System.out.println("Screenshot Taken");

	}

}
