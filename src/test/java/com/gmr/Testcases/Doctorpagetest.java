package com.gmr.Testcases;

import org.testng.annotations.Test;

import com.gmr.pageobjects.DoctorPage;
import com.gmr.pageobjects.Loginpage;
import com.utilites.webdriverwaitutils;

public class Doctorpagetest extends Baseclass{
	webdriverwaitutils wait;
	
  @Test
 public void doctorpage() {
	  
	  Loginpage lp= new Loginpage(driver);
	  lp.setusername(username);
	  lp.setpassword(password);
	  lp.clicklogin();
	  lp.hisclick();
	  
	  
	  DoctorPage dp= new DoctorPage(driver);
	  dp.adddoctor();
	  
	 
	 
 }
	

}
