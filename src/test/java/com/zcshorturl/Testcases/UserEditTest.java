package com.zcshorturl.Testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.utilites.Excelutils;
import com.zcshorturlpages.Appselectionpage;
import com.zcshorturlpages.NavigatetouserPage;
import com.zcshorturlpages.UserEditPage;

import utils.ExtentManger;
import utils.Testcasecounts;

public class UserEditTest extends ZcLogintest{
	
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
	public void edittest() throws Throwable {
		
		Testcasecounts tc = new Testcasecounts("edittest");
		UserEditPage up= new UserEditPage(driver);
		up.clickediticon("pavani");
		String path = "E:\\naveen\\projects\\hybrid-framework\\src\\test\\resources\\zceditform.xlsx";
		String sheetname = "editformzc";
		String[][] data = Excelutils.getcelldatas(path, sheetname);
		for(int i=0;i<data.length;i++) {
			tc.totalcasesinc();
			String salutration=data[i][0];
			String firstname=data[i][1];
			String middlename=data[i][2];
			String lastname=data[i][3];
			String email=data[i][4];
			String mobileno=data[i][5];
			String username=data[i][6];
			String role = data[i][7];
			try {
				
				up.editform(salutration, firstname, middlename, lastname, email, mobileno, username, role);
			 String validationmsg=up.validationmessage();
			 boolean issuccess=validationmsg.toLowerCase().contains("updated")||validationmsg.toLowerCase().contains("succesfully");
			 if(issuccess) {
				 tc.passedinc();
					ExtentManger.logColored(Status.PASS, "validation message : " + (i + 1) + validationmsg,
							ExtentColor.GREEN);
					Assert.assertTrue(issuccess, "Expected success message  found for row " + (i + 1));

				 
			 }
			 else {
				 tc.passedinc();
				 ExtentManger.logColored(Status.PASS, "validation message : " + (i + 1) + validationmsg,
							ExtentColor.RED);

				 
			 }
			 
			 
			}catch (Exception e) {
		    
				String errormessage = "Row" + (i + 1) + "  Error during form upating: " + e.getMessage();
				ExtentManger.logColored(Status.FAIL, errormessage, ExtentColor.RED);
				tc.failedinc();
			 }

			
		}



	}
}
