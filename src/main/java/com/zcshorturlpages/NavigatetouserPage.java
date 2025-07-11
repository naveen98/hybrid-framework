package com.zcshorturlpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class NavigatetouserPage {
	
	WebDriver driver;
	webdriverwaitutils wait;
	public NavigatetouserPage(WebDriver driver) {
		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//a[@class='icon-angle-right sidebar-toggle d-flex']")private WebElement extractclick;

	@FindBy(xpath="(//span[contains(@class,'nav-link-text')][normalize-space()='Users'])[1]")private WebElement userdrp;

	@FindBy(xpath="(//span[contains(@class,'nav-link-text')][normalize-space()='Users'])[2]")private WebElement users;
	@FindBy(xpath="//h2[normalize-space()='Users']")private WebElement userstext;

	public  void navigatetouser() {

		wait.waitForClickability(extractclick).click();
		wait.waitForClickability(userdrp).click();
		wait.waitForClickability(users).click();
		
	}
	public boolean verifytextdisplayed() {

		try {
			WebElement text = wait.waitForVisibility(userstext);
			return text.isDisplayed();
		} catch (Exception e) {
			System.out.println("Not displayed");
			return false;
		}

}
}
