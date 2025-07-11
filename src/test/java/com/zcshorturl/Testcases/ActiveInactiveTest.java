package com.zcshorturl.Testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.zcshorturlpages.ActiveorInacrtivepage;
import com.zcshorturlpages.Appselectionpage;
import com.zcshorturlpages.NavigatetouserPage;

import utils.ExtentManger;
import utils.Testcasecounts;

public class ActiveInactiveTest extends ZcLogintest {
	
	@Test(priority=0)
	public void logintest() {
		logintverify();
	}
	@Test(priority = 1, dependsOnMethods = { "logintest" })
	public void appselection() {


		Testcasecounts tc = new Testcasecounts("appselection");

		try {
			Appselectionpage ap = new Appselectionpage(driver);
			ap.appselection();
			tc.totalcasesinc();

			if (ap.verifytextdisplayed()) {
				ExtentManger.logColored(Status.PASS, "search displayed.", ExtentColor.GREEN);
				tc.passedinc();
				Assert.assertTrue(true);
			} else {

				ExtentManger.logColored(Status.FAIL, "Search not displayed", ExtentColor.RED);
				tc.failedinc();
				Assert.fail("serach not displayed");
			}

		} catch (Exception e) {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "App selection failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail("App selection failed");
		}
		tc.logsummary();
	}

	@Test(priority = 2, dependsOnMethods = { "appselection" })
	public void navigatetouser() {
		Testcasecounts tc = new Testcasecounts("navigatetouser");

		try {

			NavigatetouserPage np = new NavigatetouserPage(driver);
			np.navigatetouser();
			tc.totalcasesinc();
			if(np.verifytextdisplayed()) {
				ExtentManger.logColored(Status.PASS, "users displayed.", ExtentColor.GREEN);
				Assert.assertTrue(true);
				tc.passedinc();

			} else {
				ExtentManger.logColored(Status.FAIL, "users not displayed", ExtentColor.RED);

				tc.failedinc();
				Assert.fail("failed to navigateurl");
			}

		} catch (Exception e) {
			tc.failedinc();

			ExtentManger.logColored(Status.FAIL, "Failed to navigate " + e.getMessage(), ExtentColor.RED);
			Assert.fail("navigation  failed. Stopping execution.");

		}
		tc.logsummary();
	}
	@Test(priority=3,dependsOnMethods = {"navigatetouser"})
	public void activeinactivetest() {
		Testcasecounts tc= new Testcasecounts("activeinactivetest");	
	try {
		ActiveorInacrtivepage ac= new ActiveorInacrtivepage(driver);
		ac.clickactiveandinactiveicon("Mr nag sri arjun");
		String validationmsg=ac.validationmessage();
		boolean issuccess=validationmsg.toLowerCase().contains("updated")||validationmsg.contains("successfully");
		if(issuccess) {
			tc.passedinc();
			ExtentManger.logColored(Status.PASS, "updated successfully." + validationmsg,
					ExtentColor.GREEN);
			Assert.assertTrue(true, "updated successful ");

		}else {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, " not updated " + validationmsg, ExtentColor.RED);
            Assert.fail(" not found");
		}
	}
	
	catch (Exception e) {
        System.out.println("exception" +e.getMessage());    
        
	  }
	tc.logsummary();
		
	}
  
}
