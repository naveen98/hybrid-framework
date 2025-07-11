package Tests;

import org.testng.annotations.Test;

public class TestwithAttributes {
	
	@Test(priority = 2,description  = "Iam executed decpition method")
	public void descriptiontest() {
		
		System.out.println("Description test passed");
	}
	
	@Test(priority = 1)
	public void testpriority() {
		
		System.out.println("iam executing test 3 ");
	} 
	
	@Test(timeOut = 1000)
	public void timeouts() {
		System.out.println("test meesage for timeouts");
	}

	
	

}
