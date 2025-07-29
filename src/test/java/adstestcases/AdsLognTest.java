package adstestcases;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;

import adspages.AdsLoginPage;

public class AdsLognTest extends AdsBaseClass {

	 public void loginverify() {
	    

	        try {
	            AdsLoginPage lp = new AdsLoginPage(driver);
	            AdsExtentManger.logColored(Status.INFO, "Starting login test", ExtentColor.BLUE);

	            lp.login(username, password);
	            AdsExtentManger.logColored(Status.PASS, "Entered login credentials: " + username, ExtentColor.GREEN);

	            // Title verification 
	            String expectedTitle = "App Selection || ads Application";
	            lp.verifytitle(expectedTitle);
	         
	            
	            AdsExtentManger.logColored(Status.PASS, "Page title verified successfully", ExtentColor.GREEN);
	            
	            boolean isTestPassed = true;
	        
	            if (isTestPassed) {
	                Assert.assertTrue(true);
	                AdsExtentManger.logColored(Status.PASS, "Login Test Passed", ExtentColor.GREEN);
	            } else {
	                AdsExtentManger.logColored(Status.FAIL, "Login Test Failed", ExtentColor.RED);
	            }
	            
	        } catch (Exception ae) {
	            AdsExtentManger.logColored(Status.FAIL, "Assertion failed: " + ae.getMessage(), ExtentColor.RED);
	            System.out.println("Assertion failed: " + ae.getMessage());
	            Assert.fail();
	        }

	    }
	}
