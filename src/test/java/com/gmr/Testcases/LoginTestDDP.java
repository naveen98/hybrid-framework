package com.gmr.Testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gmr.pageobjects.Loginpage;
import com.utilites.Excelutils;

public class LoginTestDDP extends Baseclass {
	String path = "E:\\naveen\\projects\\hybrid-framework\\src\\test\\resources\\logindata.xlsx";
	String sheet="Sheet1";

	@Test(dataProvider = "logincreditials")
	public void logintest(String uname, String pwd) throws Throwable {
       
	try {
		Loginpage lp = new Loginpage(driver);
		lp.setusername(uname);
		lp.setpassword(pwd);
		lp.clicklogin();
		
	
		String expectedtitle="Login || apollogmr Application";
		String currenttitle=driver.getTitle();

		if(currenttitle.equals(expectedtitle)) {
			
			System.out.println("Login Successfully : " + uname+ " " +pwd);
			Assert.assertTrue(true);
			lp.clicklogout();
			
			 if(lp.islogoutmsg()) {
				
				String loutmsg=lp.logoutmessage();
				System.out.println(loutmsg);
			}
			 
             Excelutils.setcelldata(path, sheet, 1, 2, "Login Successful");

		}
	
		
		else if(lp.isErrorDisplayed()) {
			
			String errmsg=lp.geterrormsg();
			System.out.println("Login Failed : "+ uname + " " +pwd +" error message "+ errmsg);
			capturescreen(driver, uname);
            Excelutils.setcelldata(path, sheet, 2, 2, "Failed "+ errmsg);
            Assert.assertTrue(false);
		}
		else {
			System.out.println("not matched ");
		}
		

	}
		catch(Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	
	@DataProvider(name = "logincreditials")
	String[][] getdata() throws Throwable {

		int rownum = Excelutils.getrowcount(path, sheet);
		
		int colcount = Excelutils.getcellcount(path, sheet, 1);
		
		String logindata[][] = new String[rownum][colcount];
		
		for (int i = 1; i <= rownum; i++) {

			for (int j = 0; j < colcount; j++) {
				
				logindata[i - 1][j] = Excelutils.getcelldata(path, sheet, i, j);
			
			}
		}
		return logindata;
	
		
	}
}
