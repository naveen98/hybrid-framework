package com.zcshorturlpages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class Appselectionpage {
	
	WebDriver driver;
	public webdriverwaitutils wait;
	
    public Appselectionpage(WebDriver driver) {
		
		this.driver= driver;
		PageFactory.initElements(driver,this);
		this.wait=new webdriverwaitutils(driver);

    }
		@FindBy(xpath="//div[@id='administration']//div[@class='mngdescription']")private WebElement appselction;
		@FindBy(xpath = "//span[normalize-space()='Search']")
		private WebElement searchtext;

		@FindBy(xpath = "//div[contains(@class, 'toast-title')] | //div[normalize-space()='Please fill mandatory fields']")
		private WebElement msglocator;
		@FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
		private WebElement popokbutton;
		@FindBy(xpath = "//button[@class='close']")private WebElement cancelbtn;
		
		
		public void appselection() {
			wait.waitForClickability(appselction).click();

		}
		
		public boolean handlepopup() {
			try {
				WebElement popbtn=wait.waitForClickability(popokbutton);
				if(popbtn.isDisplayed()) {
					try {
						popbtn.click();
					}catch(Exception e) {
						JavascriptExecutor js=(JavascriptExecutor)driver;
						js.executeScript("arguments[0].click();", popbtn);
					}
					return true;
					
				}
			}catch (Exception e) {
           System.out.println("No popup displayed");
			}
			return false;
			
		}

		//validation messages
		
		public String validationmessages() {
			String Message="";
			try {
				WebElement messagelocator=wait.waitForVisibility(msglocator);
				if(messagelocator!=null&&messagelocator.isDisplayed()) {
					Message=messagelocator.getText();
					System.out.println(Message);
					switch (Message) {
					case "Password updated successfully":
						return "password update successfully";
						
					case "Password should be between 6 and 25 characters, 1 Min Upper Case, 1 Min Lower Case, 1 Min Digit, 1 Min Special Chars.":
						return "password should strong use all cases";
						
					case "New Password and Confirm Password did not match, please verify.,Password should be between 6 and 25 characters, 1 Min Upper Case, 1 Min Lower Case, 1 Min Digit, 1 Min Special Chars.":
						return "password did not match check new and confirm password";
						
					case"Your new password can not be same as last 3 passwords, please choose a new password":
						return"Your new password can not be same as last 3 passwords, please choose a new password";
					case"Please fill mandatory fields":
						handlepopup();
						
						return"Mandatory fields missing";
					
					default:
						return Message;
						
					}
				}
				
				}
			catch (Exception e) {
				System.out.println("Error" +e.getMessage());
			}
			return "no message displayed";
		}
		public boolean verifytextdisplayed() {

			try {
				WebElement text = wait.waitForVisibility(searchtext);
				return text.isDisplayed();
			} catch (Exception e) {
				System.out.println("Not displayed");
				return false;
			}
		}
}
