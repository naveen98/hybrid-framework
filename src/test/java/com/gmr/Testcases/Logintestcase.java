package com.gmr.Testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gmr.pageobjects.DoctorPage;
import com.gmr.pageobjects.Loginpage;
import com.utilites.webdriverwaitutils;

public class Logintestcase extends Baseclass {
    webdriverwaitutils wait;
	@Test
	public void logintest() throws Throwable {
	    driver.get(baseurl);

		Loginpage lp = new Loginpage(driver);
		lp.setusername(username);
		
		lp.setpassword(password);
		lp.clicklogin();

	    String tit = driver.getTitle();
		System.out.println(tit);

		
		if (tit.equals("Login || apollogmr Applications")) {
			Assert.assertTrue(true);

		} else {
              
			capturescreen(driver, "tit");
			Assert.assertTrue(false);

		}
	
		
		
		
	}

}
