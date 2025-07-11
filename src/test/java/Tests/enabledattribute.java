package Tests;

import org.testng.annotations.Test;

public class enabledattribute {
	@Test(priority = 0)
	public void launchapp() {
		
		System.out.println("Launch App Executed");
	}
@Test(priority = 1,enabled = false)
	public void navigationpage() {
		
		System.out.println("Navigated Executed");
	}

    @Test(priority = 2)
	public void closeapp() {
		
		System.out.println("closed App");
	}


}
