package com.gmr.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class DoctorPage {
	public WebDriver driver;
	public webdriverwaitutils wait;

	public DoctorPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new webdriverwaitutils(driver);

	}
	// locators

	@FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
	WebElement sidebar;
	@FindBy(xpath = "(//li[@id='menu-li-users-doctors']//span[normalize-space()='Doctors'])[1]")
	WebElement doctormodule;
	@FindBy(xpath = "(//li[@id='menu-li-users-doctors']//span[normalize-space()='Doctors'])[2]")
	WebElement submoduledoctor;
	@FindBy(xpath = "(//button[@title='Add Doctor'])[1]")
	WebElement adddoctor;
	@FindBy(xpath = "//i[@class='icon-home-1 ng-star-inserted']")
	WebElement home;

	public void clickhome() {

		wait.waitForClickability(home).click();
	}

	public void Navigatetodoctorpage() {
		wait.waitForClickability(sidebar).click();
		wait.waitForClickability(doctormodule).click();
		wait.waitForClickability(submoduledoctor).click();
	}

	public void adddoctor() {

		wait.waitForClickability(adddoctor).click();
	}

}
