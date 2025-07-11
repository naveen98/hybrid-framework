package apollocmspages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class Appselectionpage {
	WebDriver driver;
	webdriverwaitutils wait;
	public Appselectionpage(WebDriver driver) {
		
		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	}



	@FindBy(xpath="//div[@id='apollo']//div[@class='mngdescription']")private WebElement appselection;
	@FindBy(xpath = "//div[@title='Apollo admin Cms']")private WebElement textdisplay;
	
	
	public void appselection() {
		
		wait.waitForClickability(appselection).click();
		
	}
	
	public boolean isdisplayed() {
	try {
		WebElement text=wait.waitForVisibility(textdisplay);
		return text.isDisplayed();
	}catch (Exception e) {
		System.out.println("not displayed");
	}
	return false;
	
	}
	
	
}
