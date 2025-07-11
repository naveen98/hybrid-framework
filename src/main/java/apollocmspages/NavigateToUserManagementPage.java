package apollocmspages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class NavigateToUserManagementPage {
	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;
	
	public NavigateToUserManagementPage(WebDriver driver) {
		this.wait=new webdriverwaitutils(driver);
		this.driver=driver;
		this.js=(JavascriptExecutor)driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
	private WebElement sidebarbtn;
	@FindBy(xpath = "//a[@id='menu-user-management']")private WebElement usermangementbtn;
	@FindBy(xpath = "//span[contains(text(),'Users')]")private WebElement usersbtn;
	@FindBy(xpath = "//h2[contains(text(),'Users')]")private WebElement userheaderdisplay;
	
	
	
	public void Navigatetouser() {
		
		try {
		wait.waitForVisibility(sidebarbtn).click();
		wait.waitForVisibility(usermangementbtn).click();
		wait.waitForVisibility(usersbtn).click();
		}catch (Exception e) {
			
			js.executeScript("arguments[0].click()", sidebarbtn);
			js.executeScript("arguments[0].click()", usermangementbtn);

			js.executeScript("arguments[0].click()", usersbtn);


		}

	}
	
	public boolean UserheaderDisplay() {
		
		try {
			WebElement text= wait.waitForVisibility(userheaderdisplay);
			return text.isDisplayed();
		
			
		}
		catch (Exception e) {
			System.out.println("Not Displayed");
                 return false;
                 
		}
	}

}
