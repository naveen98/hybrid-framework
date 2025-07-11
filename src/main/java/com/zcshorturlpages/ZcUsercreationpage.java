package com.zcshorturlpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Dropdownutils;
import com.utilites.webdriverwaitutils;

public class ZcUsercreationpage {
	WebDriver driver;
	public webdriverwaitutils wait;

	public ZcUsercreationpage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new webdriverwaitutils(driver);

	}

	@FindBy(xpath = "//i[@class='icon-plus-1 ng-star-inserted']")
	private WebElement addbutton;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select']//label")
	private WebElement Salutrationdropdown;

	By salutationOptions = By.xpath("//li[@role='option']");

	@FindBy(xpath = "//input[@id='first_name']")
	private WebElement txtfirstname;
	@FindBy(xpath = "//input[@placeholder='Enter middle name']")
	private WebElement txtmiddilename;
	@FindBy(xpath = "//input[@id='last_name']")
	private WebElement txtlastname;
	@FindBy(xpath = "//input[@id='email']")
	private WebElement txtemail;
	@FindBy(xpath = "(//input[@id='phone'])[1]")
	private WebElement txtmobilenumber;
	@FindBy(xpath = "//input[@id='login_unique']")
	private WebElement txtusername;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select role']//label")
	private WebElement roledropdown;

	By roleoptions = By.xpath("//li[@role='option']");

	@FindBy(xpath = "//p-dropdown[@placeholder='Select reports to']")
	private WebElement reportstodropdown;
	@FindBy(xpath = "//span[contains(@class,'ui-dropdown-filter-icon')]")
	private List<WebElement> reportoptions;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement txtpassword;
	@FindBy(xpath = "//input[@id='confirm_password']")
	private WebElement txtconfirmpassword;
	@FindBy(xpath = "//button[@id='btnSave']")
	private WebElement btnsave;

	@FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
	private WebElement popokbutton;

	@FindBy(xpath = "//div[contains(@class, 'toast-title')] | //div[normalize-space()='Please fill mandatory fields']")
	private WebElement msglocator;

	@FindBy(xpath = "//button[@class='close']")
	private WebElement cancelbtn;

 
	// Actions

	public void clickaddagain() {
		wait.clickElement(addbutton);

	}

	public void userformcreation(String salutration, String fname, String mname, String lname, String email,
			String mobile, String username, String role, String password, String cnfpassword) {

		Dropdownutils.selectbyvisibletextlist(driver, Salutrationdropdown, salutationOptions, salutration);

		wait.waitForEnterText(txtfirstname, fname);
		wait.waitForEnterText(txtmiddilename, mname);
		wait.waitForEnterText(txtlastname, lname);

		wait.waitForEnterText(txtemail, email);
		wait.waitForEnterText(txtmobilenumber, mobile);

		wait.waitForEnterText(txtusername, username);

		Dropdownutils.selectbyvisibletextlist(driver, roledropdown, roleoptions, role);

		// Dropdownutils.selectbyvisibletextlist(driver, reportstodropdown,
		// reportoptions, reports);

		wait.waitForEnterText(txtpassword, password);
		wait.waitForEnterText(txtconfirmpassword, cnfpassword);
		wait.clickElement(btnsave);

	}

	public void clickcancel() {

		wait.waitForClickability(cancelbtn).click();
	}

	public boolean handlepopup() {

		try {
			WebElement popbtn = wait.waitForClickability(popokbutton);
			if (popbtn.isDisplayed()) {
				try {
					popbtn.click();
				} catch (Exception e) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("argumentys[0].click();", popbtn);

				}
				return true;

			}
		} catch (Exception c) {

			System.out.println("no popup ");

		}
		return false;

	}



	// validation messages
	public String getvalidationmessage() {

		String message = "";
		try {

			WebElement messagelocator = wait.waitForVisibility(msglocator);

			if (messagelocator != null && messagelocator.isDisplayed()) {

				message = messagelocator.getText();

				System.out.println(message);

				switch (message) {

				case "User saved successfully":
					return "user saved succesfully";

				case "Username Already exists":
					return "username already exists";

				case "Username Already exists,Email ID Already Exists,Mobile No. Already Exists":
					return "username/emailid/mobileno already exist";

				case "New Password and Confirm Password did not match, please verify.,Username Already exists,Email ID Already Exists":
					return "New password confirm password did not match";

				case "Password should be between 6 and 25 characters, 1 Min Upper Case, 1 Min Lower Case, 1 Min Digit, 1 Min Special Chars.":
					return "password should between 6 and 25 characters , special characters ";

				case "Please fill mandatory fields":

					handlepopup();

					return "Mandatory fields missing";

				default:
					return message;

				}
			}
		} catch (Exception e) {

			System.out.println("----------error -----------" + e.getMessage());
		}

		return "no messege displayed";
	}

}
