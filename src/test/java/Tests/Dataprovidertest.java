package Tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Dataprovidertest {
	
	@Test(dataProvider = "logintest")
	public void verifylogin(String username , String password) {
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		
		driver.get("https://practicetestautomation.com/practice-test-login/");
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='submit']")).click();

	}

	@DataProvider(name="logintest")
	public String [][] datasupplies(){
		
		String [][]data={ {"student","Password123"},
				{"incorrectUser ","Password123 "},
				{"student","incorrectPassword "}};
		

		return data;
		}
				
	
	
}
