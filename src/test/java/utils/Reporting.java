package utils;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import cmstestcases.CmsBaseclass;

public class Reporting  extends TestListenerAdapter{
	
	public ExtentSparkReporter sparkrepoter; //ui of the  report
	public ExtentReports extent;  //common info report
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	
	 public  void onStart(ITestContext context) {
		 
		
		System.out.println("=======Extent Report Intialization started===========");

		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportname=" Test Reports-"+ timestamp + ".html";
		 
		sparkrepoter=new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" +  reportname); //report generation location
		 
		sparkrepoter.config().setDocumentTitle("Automation Report");   //title of report
		sparkrepoter.config().setReportName("Functional Testing");     //name of report 
		sparkrepoter.config().setTheme(Theme.STANDARD);                //visible of black or white
		
		extent=new ExtentReports();
		
		extent.attachReporter(sparkrepoter);
		
		extent.setSystemInfo("Computer name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester", "Naveen");
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Browser", "Edge");

		
		  }
	 
	  public void onTestStart(ITestResult result) {
		  
		    System.out.println(" ======Test started ======: " + result.getMethod().getMethodName());

		    ExtentTest test = extent.createTest(result.getMethod().getMethodName());
	        extentTest.set(test);
	  }
	
	 public void onTestSuccess(ITestResult result) {
	        extentTest.get().log(Status.PASS, MarkupHelper.createLabel("===Test case passed: " + result.getName(),ExtentColor.GREEN));
		   
		  }
	 public void onTestFailure(ITestResult result) {
	        extentTest.get().log(Status.FAIL,MarkupHelper.createLabel("===Test Failed: " + result.getName(), ExtentColor.RED));
	        extentTest.get().log(Status.FAIL, MarkupHelper.createLabel("Exception: " + result.getThrowable(),ExtentColor.RED));
	        try {
	            Object testInstance = result.getInstance();
	            if (testInstance instanceof CmsBaseclass) {
	                WebDriver driver = ((CmsBaseclass) testInstance).driver;
	                if (driver != null) {
	                    String screenshotPath = ((CmsBaseclass) testInstance).capturescreen(driver, result.getMethod().getMethodName());
	                    extentTest.get().addScreenCaptureFromPath(screenshotPath);
	                } else {
	                    extentTest.get().log(Status.WARNING, "WebDriver is null, cannot capture screenshot.");
	                }
	            }
	        } catch (Throwable e) {
	            e.printStackTrace();
	        }
	 }

	 
	    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP,MarkupHelper.createLabel("====Test case skipped: " + result.getName(),ExtentColor.ORANGE));
		   		  
	 }

	 public void onFinish(ITestContext context) {
		  extent.flush();
	        System.out.println("=== Extent Report Generated Finished ===");

	 }

	   // Allow access to extentTest in other utilities
	    public static ExtentTest getTest() {
	        return extentTest.get();
	    }
	
	


}
