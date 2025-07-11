package com.zcshorturl.Testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.utilites.Excelutils;
import com.zcshorturlpages.Appselectionpage;
import com.zcshorturlpages.NavigatetouserPage;
import com.zcshorturlpages.ZcUsercreationpage;

import utils.ExtentManger;
import utils.Testcasecounts;
import utils.Verifyutils;

//@Listeners(utils.Reporting.class)
public class ZcusercreationLogintest extends ZcLogintest {

	@Test(priority = 0)
	public void logintest() {

		logintverify();

	}

	@Test(priority = 1, dependsOnMethods = { "logintest" })
	public void appselection() {
		boolean isappseclected = false;

		Testcasecounts tc = new Testcasecounts("appselection");

		try {
			Appselectionpage ap=new Appselectionpage(driver);
			ap.appselection();
			tc.totalcasesinc();

			

			if (ap.verifytextdisplayed()) {
				ExtentManger.logColored(Status.PASS, "search displayed.", ExtentColor.GREEN);
				isappseclected = true;
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

			NavigatetouserPage np=new NavigatetouserPage(driver);
			np.navigatetouser();
			tc.totalcasesinc();
			boolean isnavigated = Verifyutils.verifyUrlEquals(driver,
					"https://zcshorturl.v37.dev.zeroco.de/administration/users/list", "successfully navigated",
					"Navigated failed");

			if (isnavigated) {

				tc.passedinc();

			} else {

				tc.failedinc();
			}

		} catch (Exception e) {
			tc.failedinc();

			ExtentManger.logColored(Status.FAIL, "Failed to navigate " + e.getMessage(), ExtentColor.RED);
			Assert.fail("navigation  failed. Stopping execution.");

		}
		tc.logsummary();

	}

	@Test(priority = 3, dependsOnMethods = { "navigatetouser" })
	public void usercreationtest() throws Throwable {
		Testcasecounts tc = new Testcasecounts("usercreationtest");

		ZcUsercreationpage up = new ZcUsercreationpage(driver);
		up.clickaddagain();
		String path = "E:\\naveen\\projects\\hybrid-framework\\src\\test\\resources\\zcusercreation.xlsx";
		String sheetname = "Zcuser";
		String[][] data = Excelutils.getcelldatas(path, sheetname);

		for (int i = 0; i < data.length; i++) {

			tc.totalcasesinc();
			String salutration = data[i][0];
			String firstname = data[i][1];
			String middilename = data[i][2];
			String lastname = data[i][3];
			String email = data[i][4];
			String mobileno = data[i][5];
			String username = data[i][6];
			String role = data[i][7];
			String reportto = data[i][8];
			String password = data[i][9];
			String confirmpassword = data[i][10];

			try {

				up.userformcreation(salutration, firstname, middilename, lastname, email, mobileno, username, role,
						password, confirmpassword);
				String validationmsg = up.getvalidationmessage();

				boolean isSuccess = validationmsg.toLowerCase().contains("saved")
						|| validationmsg.toLowerCase().contains("succesfully");
				if (isSuccess) {
					tc.passedinc();
					ExtentManger.logColored(Status.PASS, "validation message : " + (i + 1) + validationmsg,
							ExtentColor.GREEN);

					Assert.assertTrue(isSuccess, "Expected success message  found for row " + (i + 1));

					up.clickaddagain();
					Thread.sleep(3000);
				} else {

					tc.passedinc();
					ExtentManger.logColored(Status.PASS, "validation message : " + (i + 1) + validationmsg,
							ExtentColor.RED);

					Thread.sleep(2000);
					up.clickcancel();
					Thread.sleep(3000);
					up.clickaddagain();
				}

				Excelutils.writecelldatas(path, sheetname, i + 1, 11, validationmsg);
				Excelutils.writecelldatas(path, sheetname, i + 1, 12, isSuccess ? "PASS" : "FAIL");

			} catch (Exception c) {

				String errormessage = "Row" + (i + 1) + "  Error during form submission: " + c.getMessage();
				ExtentManger.logColored(Status.FAIL, errormessage, ExtentColor.RED);
				tc.failedinc();
				
			}

		}

		tc.logsummary();
	}

}
