package com.utilites;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DynamicWebTableUtil {

	public WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;
	
	
	
	public DynamicWebTableUtil(WebDriver driver) {
		this.driver=driver;
		wait=new webdriverwaitutils(driver);
		js=(JavascriptExecutor)driver;
		
		
	}
	
	public int totalpagestext(WebElement pagenationtext) {
		
		String pagetext=wait.waitForVisibility(pagenationtext).getText();
		System.out.println("====pagenation text: "+ pagetext);
		
		int  totalpages=Integer.parseInt(pagetext.substring(pagetext.indexOf("Total") + 6,pagetext.lastIndexOf("Pages")-2));
		System.out.println("===Total Pages: "+ totalpages);
		
		return totalpages;

	}
	// Go to a specific page number by clicking the paginator

	public void gotopage(int pagenumber) {
	try {	
	WebElement pageElement=	driver.findElement(
            By.xpath("//span[@class='ui-paginator-pages']//*[text()='" + pagenumber + "']"));
	js.executeScript("arguments[0].scrollIntoView(true);", pageElement);
    wait. waitForClickability(pageElement).click();
    System.out.println("Navigated to page: " + pagenumber);

	} catch (Exception e) {
        System.out.println("Failed to navigate to page " + pagenumber + ": " + e.getMessage());
    }
	}
	
	
	public void pagenationselect(WebElement pagenationtext,WebElement tablebody) {
		
		int totalpages=totalpagestext(pagenationtext);
		for(int i=1;i<=totalpages;i++) {
			
		gotopage(i);
		List<WebElement> rows = tablebody.findElements(By.tagName("tr"));
        System.out.println("Page " + i + ":");
        
        for (WebElement row : rows) {
            System.out.println(row.getText());
        }
		
		}

	}
	
    public void clickNext(WebElement nextbtnloc) {
        try {
            WebElement nextBtn = wait.waitForVisibility(nextbtnloc);
            wait.waitForClickability(nextBtn).click();
        } catch (Exception e) {
            System.out.println("Next button not found or not clickable: " + e.getMessage());
        }
    }

    public void clickPrevious(WebElement prevbtnloc) {
        try {
            WebElement prevBtn = wait.waitForVisibility(prevbtnloc);
           wait. waitForClickability(prevBtn).click();
        } catch (Exception e) {
            System.out.println("Previous button not found or not clickable: " + e.getMessage());
        }
    }
}
