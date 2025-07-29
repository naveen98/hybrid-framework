package adstestcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AdsInvalidcases  extends LoginInvalidTest{
	@BeforeClass(alwaysRun = true)
	public void setsummary() {
		Testcasecount.reset("AdsInvalidcases");
	}
	
@Test(priority = 0)
	public void Invalidlogin() {
	verifyInvalidLoginMultiple();

}

@Test(priority=1)
public void Logsummary() {
	TestcaseLogger.logSummary();
}
}
