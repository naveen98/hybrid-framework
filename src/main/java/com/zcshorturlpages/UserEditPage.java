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

public class UserEditPage {
	
	WebDriver driver;
	webdriverwaitutils wait;
	public UserEditPage(WebDriver driver) {
		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	
	}
	@FindBy (xpath = "//table[@class='table-view']/tbody/tr")private List<WebElement> tablerows;  //table[@class='table-view']/tbody/tr/td//i
	@FindBy(xpath = "//p-dropdown[@placeholder='Select']//label")
	private WebElement Salutrationdropdown;
	By salutationOptions = By.xpath("//li[@role='option']");
	@FindBy(xpath = "//input[@id='first_name']")
	private WebElement txtfirstname;
	@FindBy(xpath = "//input[@placeholder='Enter middle name']")
	private WebElement txtmiddilename;
	@FindBy(xpath = "//input[@id='last_name']")
	private WebElement txtlastname;
	@FindBy(xpath = "(//input[@id='phone'])[1]")
	private WebElement txtmobilenumber;
	@FindBy(xpath = "//input[@id='email']")
	private WebElement txtemail;
	@FindBy(xpath = "//input[@id='login_unique']")
	private WebElement txtusername;
	@FindBy(xpath = "//p-dropdown[@placeholder='Select role']//label")
	private WebElement roledropdown;
	By roleoptions = By.xpath("//li[@role='option']");
	@FindBy(xpath = "//p-dropdown[@placeholder='Select reports to']")
	private WebElement reportstodropdown;
	@FindBy(xpath = "//span[contains(@class,'ui-dropdown-filter-icon')]")
	private List<WebElement> reportoptions;
	@FindBy(xpath = "//div[contains(@class, 'toast-title')] | //div[normalize-space()='Please fill mandatory fields']")
	private WebElement msglocator;
	@FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
	private WebElement popokbutton;
	@FindBy(xpath = "//button[@id='btnUpdate']")private WebElement updatebtn;
	@FindBy(xpath ="//button[@id='btnCancel']")private WebElement cancelbtn;
	@FindBy(xpath = "//button[@class='close']")
	private WebElement closeform;
	
	
	
	public void editform(String salutration,String fname,String mname,String lname,String email,String mobile,String uname,String role) {
		
		Dropdownutils.selectbyvisibletextlist(driver, Salutrationdropdown, salutationOptions, salutration);
		wait.waitForEnterText(txtfirstname, fname);
		wait.waitForEnterText(txtmiddilename, mname);
		wait.waitForEnterText(txtlastname, lname);

		wait.waitForEnterText(txtemail, email);
		wait.waitForEnterText(txtmobilenumber, mobile);

		wait.waitForEnterText(txtusername, uname);
		Dropdownutils.selectbyvisibletextlist(driver, roledropdown, roleoptions, role);
		wait.waitForClickability(updatebtn).click();

	}
	public void clickediticon(String expectedusername) {
		
		List<WebElement>rows=wait.waitForAllElementsVisible(tablerows);
		for(WebElement row:rows) {
			String actusername=row.findElement(By.xpath("td[1]")).getText();
			if(actusername.equalsIgnoreCase(expectedusername)) {
				List<WebElement>actionsicons=row.findElements(By.xpath(".//td[last()]//i"));
				if(actionsicons.size()>=2) {
					WebElement editoption=actionsicons.get(1);
					
					wait.waitForClickability(editoption).click();
					
				}
				break;
			}
		}
		
	}
	public void clickcancel() {
		wait.waitForClickability(cancelbtn).click();
		
	}
	
	public boolean handlepopup() {
		try {
			WebElement popbtn=wait.waitForClickability(popokbutton);
			if(popbtn.isDisplayed())
			{
			try {	popbtn.click();
				}catch (Exception e) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("argumentys[0].click();", popbtn);

				}
			return true;
			
				}
			}
		catch (Exception e) {
          System.out.println("no popup displayed");		
          }
		return false;
		
	}
	public String validationmessage() {
		String message="";
		try {
			WebElement messagelocator=wait.waitForVisibility(msglocator);
			if(messagelocator!=null&&messagelocator.isDisplayed()) {
				message=messagelocator.getText();
				System.out.println(message);
				switch (message) {
				case "User Updated successfully":
					return"updated succesfully";
				case "Please fill mandatory fields":
					handlepopup();
					
					return"Mandatory fields missing";
				case "The information you entered will be lost. Do you want to continue?":
					handlepopup();
					return "infrormation you entered will be lost";
			
				default:
					return message;
				}
			}
		}catch (Exception e) {
         System.out.println("error"+e.getMessage());
		}
		return "no message disaplayed";
	}
}
