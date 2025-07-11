package com.zcshorturlpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class Resetpasswordpage {
	
	WebDriver driver;
	webdriverwaitutils wait;
	public Resetpasswordpage(WebDriver driver) {

		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);

	}
	
	@FindBy (xpath = "//table[@class='table-view']/tbody/tr")private List<WebElement> tablerows;  //table[@class='table-view']/tbody/tr/td//i
	@FindBy(xpath="//input[@id='newPassword']")private WebElement txtnewpassword;
	@FindBy(xpath="//input[@id='confirmPassword']")private WebElement txtconfirmpassword;
	@FindBy(xpath="//button[@id='btnSave']")private WebElement savebtn;
	@FindBy(xpath="//button[@id='btnCancel']")private WebElement cancelbtn;
	
	
	
	public void  clickresetpassword(String expectedusername,String newpassword,String cnfpassword) {
		List<WebElement> rows=wait.waitForAllElementsVisible(tablerows);
		for(WebElement row:rows)
		{
			String actusername=row.findElement(By.xpath("td[1]")).getText();
			
			if(actusername.equalsIgnoreCase(expectedusername)) {
				
			List<WebElement> actionicons=row.findElements(By.xpath(".//td[last()]//i"));
			if(actionicons.size()>=1) {
				
				WebElement reseticon=actionicons.get(0);
				
				wait.waitForClickability(reseticon).click();
				wait.waitForVisibility(txtnewpassword).sendKeys(newpassword);
				wait.waitForVisibility(txtconfirmpassword).sendKeys(cnfpassword);
                wait.waitForClickability(savebtn).click();
			}
			break;
		  }  
		}
	}
}
