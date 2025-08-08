package adstestcases;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;

public class TestcaseLogger {
	
	public static void logSummary(ExtentTest test) {
        AdsExtentManger.logColored(Status.INFO, "====== Final Test Summary ======", ExtentColor.BLUE);
        AdsExtentManger.logColored(Status.INFO, "Test Class: " + Testcasecount.getTestClassName(), ExtentColor.BLUE);
        AdsExtentManger.logColored(Status.INFO, "Total Test Cases: " + Testcasecount.getTotal(), ExtentColor.BLUE);
        AdsExtentManger.logColored(Status.INFO, "Passed: " + Testcasecount.getPassed(), ExtentColor.GREEN);
        AdsExtentManger.logColored(Status.INFO, "Failed: " + Testcasecount.getFailed(), ExtentColor.RED);
        AdsExtentManger.logColored(Status.INFO, "Skipped: " + Testcasecount.getSkipped(), ExtentColor.ORANGE);
    }

}
