package com.gmr.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class Loginpage {
	WebDriver driver;
	public webdriverwaitutils wait;

	public Loginpage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new webdriverwaitutils(driver);

	}

	@FindBy(xpath = "//input[@id='appUserName']")WebElement txtusername;
	@FindBy(xpath = "//input[@id='appPassword']")WebElement txtpassword;
	@FindBy(xpath = "//button[@id='loginBtn']")	WebElement btnlogin;
	@FindBy(xpath = "//div[contains(@class, 'toast-error')]//div[contains(@class, 'toast-title')]")WebElement errorMsg;
	@FindBy(xpath = "(//img[@alt='logo'])[1]")WebElement clickprofile;
	@FindBy(xpath = "//button[@id='logout']")WebElement btnlogout;
    @FindBy(xpath = "//div[contains(@class,'toast-title')]|//div[normalize-space()='Sucessfully logged out']")WebElement logoutmsg;
    @FindBy(xpath="//div[@id='his']//div[@class='mngdescription']")WebElement hisclcik;

	// enter login details
	public void setusername(String uname) {

		wait.waitForEnterText(txtusername, uname);
	}

	public void setpassword(String pwd) {
		wait.waitForEnterText(txtpassword, pwd);
	}

	public void clicklogin() {
		wait.waitForClickability(btnlogin).click();

	}

	// Logout
	public void clicklogout() {

		wait.waitForClickability(clickprofile).click();
		wait.waitForClickability(btnlogout).click();

	}
	
	public void hisclick() {
		
		wait.waitForClickability(hisclcik).click();
	}

	// error message displays
	public boolean isErrorDisplayed() {
		try {
			return wait.waitForVisibility(errorMsg).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// capture error message
	public String geterrormsg() {

		try {
			String toastmsg = wait.waitForVisibility(errorMsg).getText();

			System.out.println("Failed message" + toastmsg);
			return toastmsg;
		} catch (Exception e) {
			return "error message not found";
		}
	}

	// logout displays
	public boolean islogoutmsg() {
		try {
			return wait.waitForVisibility(logoutmsg).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// capture message
	public String logoutmessage() {
		try {
			String logmsg = wait.waitForVisibility(logoutmsg).getText();

			System.out.println("logout Message  " + logmsg);
			return logmsg;
		} catch (Exception e) {
			return "no message displayed";

		}
	}
}
