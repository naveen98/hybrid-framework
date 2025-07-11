package com.zcshorturlpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class ActiveorInacrtivepage {
	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;
	public ActiveorInacrtivepage(WebDriver driver) {
		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		this.js=(JavascriptExecutor)driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath="//table[@class='table-view']/tbody/tr")private List<WebElement> tablerows;
	@FindBy(xpath="(//button[@type='button'])[3]")private WebElement popcancelbtn;
	@FindBy(xpath = "(//button[normalize-space()='Ok'])[1]")private  WebElement popokbtn;
	@FindBy(xpath = "//div[contains(@class, 'toast-title')] | //div[normalize-space()='Please fill mandatory fields']")
	private WebElement msglocator;
	
	
	
	public void clickactiveandinactiveicon(String expectedname) {
		
		List<WebElement>rows=wait.waitForAllElementsVisible(tablerows);
		for(WebElement row:rows) 
		{
			String actualusername=row.findElement(By.xpath("td[1]")).getText();
			if(actualusername.equalsIgnoreCase(expectedname)) {
				List<WebElement>actionicon=row.findElements(By.xpath(".//td[last()]//i"));
				if(actionicon.size()>=3) {
					WebElement actinactive=actionicon.get(2);
					wait.waitForClickability(actinactive).click();
					wait.waitForClickability(popokbtn).click();
					
				}
				break;
			}
		}
	}
	
	public boolean handlepopup() {
		try {
			WebElement popbtn=wait.waitForClickability(popokbtn);
			if(popbtn.isDisplayed()) {
				try {
					popbtn.click();
					
				}catch (Exception e) {
					js.executeScript("arguments[0].click();", popbtn);
				}
				return true;
				
			}
		}catch (Exception e) {

		System.out.println("no popup displayed");
		}
		return false;
		
	}
	public String validationmessage() {
		String message="";
		try{
			WebElement messagelocator=wait.waitForVisibility(msglocator);
		if(messagelocator!=null&&messagelocator.isDisplayed()) {
			message=messagelocator.getText();
			System.out.println(message);
			switch (message) {
			case "Do you want to make nag inactive?":
				handlepopup();
				return "doy you want to make nag inactive:";
				
			case "Status updated successfully":
			return "status updated succesfully";
			
			default:
				return message;
			}
		}
		
	}catch (Exception e) {
		System.out.println("not displayed");
	}
		return "no message";
		
	}
}
