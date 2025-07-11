package com.zcshorturl.Testcases;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.zcshorturlpages.ZcLoginPage;

import utils.ExtentManger;
import utils.Testcasecounts;

public class ZcLogintest extends ZcBaseclass {

	public boolean loginSuccess = false;

	public void logintverify() {

		Testcasecounts tc = new Testcasecounts("logintverify");
		try {
			tc.totalcasesinc();
			ExtentManger.logColored(Status.INFO, "Starting login test", ExtentColor.BLUE);
			ZcLoginPage lp = new ZcLoginPage(driver);
			lp.navigateloginpage(username, password);
			ExtentManger.logColored(Status.INFO, "Navigated to login page", ExtentColor.BLUE);
			// enter login credintials
			ExtentManger.logColored(Status.PASS, "Entered login credentials" + username + password, ExtentColor.GREEN);

			// validate login
			if (lp.isadminstratordisplayed()) {
				ExtentManger.logColored(Status.PASS, "Login successful - 'Administration' is displayed.",
						ExtentColor.GREEN);
				loginSuccess = true;
				tc.passedinc();
				Assert.assertTrue(true);

			} else {

				ExtentManger.logColored(Status.FAIL,
						"Login failed - 'Administration' is not displayed." + username + password, ExtentColor.RED);
				tc.failedinc();
				Assert.fail("Login failed - 'Administrator' not visible.");

			}

		} catch (Exception e) {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "Exception during login" + e.getMessage(), ExtentColor.RED);
		}
		tc.logsummary();

	}

}
