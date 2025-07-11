package com.gmr.Testcases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.zcshorturl.Testcases.ZcBaseclass;

public class Reporting  extends TestListenerAdapter{
	
	public ExtentSparkReporter sparkrepoter; //ui of the  report
	public ExtentReports extents;  //common info report
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	
	 public  void onStart(ITestContext context) {
		 
		 
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repname=" Test Reportss -"+ timestamp + ".html";
		 
		sparkrepoter=new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/"+repname); //report generation location
		 
		sparkrepoter.config().setDocumentTitle("Automation Report"); //title of report
		sparkrepoter.config().setReportName("Functional Testing"); //name of report 
		sparkrepoter.config().setTheme(Theme.STANDARD);  //visible of black or white
		extents=new ExtentReports();
		extents.attachReporter(sparkrepoter);
		
		extents.setSystemInfo("Computer name", "localhost");
		extents.setSystemInfo("Environment", "QA");
		extents.setSystemInfo("TesterName", "NaveeN");
		extents.setSystemInfo("OS", "Windows");
		extents.setSystemInfo("Browsername", "Chrome");

		
		  }
	 
	  public void onTestStart(ITestResult result) {
			ExtentTest test = extents.createTest(result.getName());

			extentTest.set(test);
	  }
	
	 public void onTestSuccess(ITestResult result) {
	        extentTest.get().log(Status.PASS, "Test case passed: " + result.getName());
		   
		  }
	 public void onTestFailure(ITestResult result) {
		     extentTest.get().log(Status.FAIL, "Test case failed: " + result.getName());
	        extentTest.get().log(Status.FAIL, "Exception: " + result.getThrowable());



	        try {
	            WebDriver driver = ((ZcBaseclass) result.getInstance()).driver;
	            String screenshotPath = ((ZcBaseclass) result.getInstance()).capturescreen(driver, result.getMethod().getMethodName());
	            extentTest.get().addScreenCaptureFromPath(screenshotPath);
	        } catch (Throwable e) {  
	            e.printStackTrace();
	        }
	    } 		  
	 
	    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test case skipped: " + result.getName());
		   		  
	 }

	 public void onFinish(ITestContext context) {
		  extents.flush();
	 }

	
	
	


}
