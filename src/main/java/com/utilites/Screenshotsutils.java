package com.utilites;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Screenshotsutils {
	public  WebDriver driver;

	public Screenshotsutils(WebDriver driver) {
		this.driver = driver;
	}

	public void capture(String filepath) throws Throwable {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);

			File dest = new File(filepath);
			FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			System.out.println("not captured");
		}
	}

	public void capturespecific(String loc, String filename) throws IOException {

		try {
			WebElement ele = driver.findElement(By.xpath(loc));
			File src = ele.getScreenshotAs(OutputType.FILE);
			File dest = new File(System.getProperty("user.dir") + "\\screenshots" + filename);
			FileUtils.copyFile(src, dest);

			System.out.println("suceesfully captured");

		} catch (Exception e) {

			System.out.println("Not captured" + e.getMessage());
		}

	}

}
