package com.zcshorturlpages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class ZcLoginPage {
	WebDriver driver;
	public webdriverwaitutils wait;

	public ZcLoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new webdriverwaitutils(driver);

	}

	// locators
	@FindBy(xpath = "//button[@class='btn login-btn']")
	private WebElement loginPageBtn;
	@FindBy(xpath = "//input[@id='appUserName']")
	private WebElement txtusername;

	@FindBy(xpath = "//input[@id='appPassword']")
	private WebElement txtpassword;

	@FindBy(xpath = "//span[contains(text(),'Log in')]")
	private WebElement btnlogin;

	@FindBy(xpath = "//strong[normalize-space()='Administration']")
	private WebElement textadmin;

	public void navigateloginpage(String uname, String password) {

		try {
			WebElement loginButton = wait.waitForVisibility(loginPageBtn);
			wait.waitForClickability(loginPageBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);

			wait.waitForVisibility(txtusername).sendKeys(uname);
			wait.waitForVisibility(txtpassword).sendKeys(password);
			wait.waitForClickability(btnlogin).click();

		} catch (Exception e) {

			System.out.println("Failed to navigate loginpage" + e.getMessage());

		}

	}

	public boolean isadminstratordisplayed() {
		try {
			WebElement text = wait.waitForVisibility(textadmin);
			return text.isDisplayed();

		} catch (Exception e) {
			System.out.println("not diaplayed");
		}
		return false;
	}
}