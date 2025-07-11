package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;


public class Dependsonmethods {
	
	@Test
	public void launchapp() {
		
		Assert.assertEquals("online tutorial", "online tutorial");
		System.out.println("Launch App Executed");
	}
@Test(dependsOnMethods = {"launchapp"})
	public void navigationpage() {
		
		Assert.assertEquals("navigate demo", "navigated demo");
		System.out.println("Navigated Executed");
	}

    @Test(dependsOnMethods = {"navigationpage"},alwaysRun = true)
	public void closeapp() {
		
		System.out.println("closed App");
	}

	
}
