package apollocmspages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class CmsLoginPage {
	WebDriver driver;
	webdriverwaitutils wait;

	public CmsLoginPage(WebDriver driver) {

		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[@id='appUserName']")
	private WebElement txtusername;
	@FindBy(xpath = "//input[@id='appPassword']")
	private WebElement txtpassword;
	@FindBy(xpath = "//button[@id='loginBtn']")
	private WebElement btnlogin;
	@FindBy(xpath = "//strong[normalize-space()='CMS']")
	private WebElement cmsdisplays;

	public void logindata(String username, String password) {

		try {
			wait.waitForVisibility(txtusername).sendKeys(username);
			wait.waitForVisibility(txtpassword).sendKeys(password);
			wait.waitForClickability(btnlogin).click();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());

		}

	}

	public boolean cmsdisplayed() {

		try {
			WebElement Text = wait.waitForVisibility(cmsdisplays);
			return Text.isDisplayed();

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());

		}
		return false;

	}
}
