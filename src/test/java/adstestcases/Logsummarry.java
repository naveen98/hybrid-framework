package adstestcases;

import org.testng.annotations.AfterSuite;

public class Logsummarry {
	

    @AfterSuite(alwaysRun = true)
    public void logFinalSummary() {
        System.out.println("Final summary logged from @AfterSuite");
        TestcaseLogger.logSummary();
    }

}
