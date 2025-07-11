package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;

public class Testcasecounts {

	public int totalestcasecount = 0;
	public int totalpassedcount = 0;
	public int totalfailedcount = 0;
	public String testname;

	public Testcasecounts(String testname) {
		this.testname = testname;

	}

	public void totalcasesinc() {
		totalestcasecount++;

	}

	public void passedinc() {
		totalpassedcount++;

	}

	public void failedinc() {
		totalfailedcount++;

	}

	public void logsummary() {
		ExtentManger.logColored(Status.INFO, "===== Summary for " + testname + " =====", ExtentColor.BLUE);
		ExtentManger.logColored(Status.INFO, "Total: " + totalestcasecount, ExtentColor.BLUE);
		ExtentManger.logColored(Status.INFO, "Passed: " + totalpassedcount, ExtentColor.GREEN);
		ExtentManger.logColored(Status.INFO, "Failed: " + totalfailedcount, ExtentColor.RED);
	}

}
