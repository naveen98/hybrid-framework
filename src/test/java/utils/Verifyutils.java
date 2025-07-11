package utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.zcshorturlpages.ZcUsercreationpage;

public class Verifyutils {

	public static boolean verifyTitle(WebDriver driver, String expectedTitle, String successMsg, String failureMsg) {

		if (driver.getTitle().equalsIgnoreCase(expectedTitle)) {
			ExtentManger.logColored(Status.PASS, successMsg, ExtentColor.GREEN);
			return true;
		} else {
			ExtentManger.logColored(Status.FAIL, failureMsg + " Actual Title: " + driver.getTitle(), ExtentColor.RED);
			Assert.fail(failureMsg);
			return false;
		}
	}

	public static boolean verifyUrlEquals(WebDriver driver, String expectedUrl, String successMsg, String failureMsg) {
		String actualUrl = driver.getCurrentUrl();
		if (actualUrl.equals(expectedUrl)) {
			ExtentManger.logColored(Status.PASS, successMsg + " | URL: " + actualUrl, ExtentColor.GREEN);
			return true;
		} else {
			ExtentManger.logColored(Status.FAIL, failureMsg + " | Actual URL: " + actualUrl, ExtentColor.RED);
			return false;
		}

	}

	
}
