package cmstestcases;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;

import apollocmspages.CmsLoginPage;
import utils.ExtentManger;
import utils.Testcasecounts;

public class CmsLoginTest extends CmsBaseclass {
	public boolean loginSuccess = false;

	public void logintverify() {

		Testcasecounts tc = new Testcasecounts("logintverify");
		tc.totalcasesinc();
		try {

			CmsLoginPage lp = new CmsLoginPage(driver);
			ExtentManger.logColored(Status.INFO, "Starting login test", ExtentColor.BLUE);
			lp.logindata(username, password);
			ExtentManger.logColored(Status.INFO, "Navigated to login page", ExtentColor.BLUE);
			ExtentManger.logColored(Status.PASS, "Entered login credentials" + username + password, ExtentColor.GREEN);

			if (lp.cmsdisplayed()) {
				ExtentManger.logColored(Status.PASS, "Login successful - 'CMS' is displayed.",
						ExtentColor.GREEN);
				loginSuccess = true;
				tc.passedinc();
				Assert.assertTrue(true);

			} else {

				ExtentManger.logColored(Status.FAIL,
						"Login failed - 'CMS' is not displayed." + username + password, ExtentColor.RED);
				tc.failedinc();
				Assert.fail("Login failed - 'CMS' not visible.");

			}

		} catch (Exception e) {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "Exception during login" + e.getMessage(), ExtentColor.RED);
		}
    tc.logsummary();
	}

}
