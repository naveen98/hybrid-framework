package apollocmspages;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import com.utilites.webdriverwaitutils;

public class ViewIssuePage {
	WebDriver driver;
	webdriverwaitutils wait;
	public ViewIssuePage(WebDriver driver) {

		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	
	}
	
	@FindBy(xpath = "//div[@class='ui-clickable d-inline-block dropdown']//i")private WebElement actionsbtn;
    @FindBy(xpath = "//div[@class='user-menu actions-menu dropdown-menu show']//span[contains(text(),'View Issue Details')]")private WebElement viewissuedetails;
    @FindBy(xpath="//label[@class='ng-star-inserted']//span[@class='zc-status'][normalize-space()='New']")private   WebElement isstaus;
    @FindBy(xpath = "//button[@class='close']")private WebElement cancelbtn;
    
	

    public void viewdetails() {
    	JavascriptExecutor js= (JavascriptExecutor)driver;
    	
        try{
        	//click on action button 
        	wait.waitForVisibility(actionsbtn);
        	wait.waitForClickability(actionsbtn).click();
        }catch (Exception e) {
        	//System.out.println("actions failed: "+ e.getMessage());
		      js.executeScript("arguments[0].click();", actionsbtn);

       }

        try {
            // Click on the View Issue Details option
            wait.waitForClickability(viewissuedetails).click();
        } catch (Exception e) {
         //   System.out.println("View Issue Details click failed: " + e.getMessage());
            js.executeScript("arguments[0].click();", viewissuedetails);
        }
    }
    public boolean viewstatus(String expected) {
    	 try {
    	        // Wait until the status element is visible
    	        WebElement statusElement = wait.waitForVisibility(isstaus);
    	        
    	        if (statusElement == null) {
    	            System.out.println("Status element not found or not visible.");
    	            return false;
    	        }

    	        // Extract and compare the status text
    	        String currentStatus = statusElement.getText().trim();
    	        expected = expected.trim();

    	        if (currentStatus.equalsIgnoreCase(expected)) {
    	            System.out.println("Status matched: " + currentStatus);
    	            return true;
    	        } else {
    	            System.out.println("Status mismatch: expected = '" + expected + "', actual = '" + currentStatus + "'");
    	            return false;
    	        }
    	    } catch (Exception e) {
    	        System.out.println("Error in viewstatus(): " + e.getMessage());
    	        return false;
    	    }
    	}
    
    
	 public void cancel() {
		    try {
		        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
		                .withTimeout(Duration.ofSeconds(40))
		                .pollingEvery(Duration.ofMillis(500))
		                .ignoring(NoSuchElementException.class)
		                .ignoring(ElementClickInterceptedException.class)
		                .ignoring(StaleElementReferenceException.class);

		        fluentWait.until(new Function<WebDriver, Boolean>() {
		            public Boolean apply(WebDriver driver) {
		                try {
		                    if (cancelbtn.isDisplayed() && cancelbtn.isEnabled()) {
		                        cancelbtn.click();
		                        return true;
		                    }
		                } catch (Exception e) {
		                    return false;
		                }
		                return false;
		            }
		        });

		        System.out.println("Clicked cancel button using FluentWait.");
		    } catch (Exception e) {
		        try {
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelbtn);
		        } catch (Exception jsEx) {
		            System.out.println("Failed to click cancel button even using JavaScript. Exception: " + jsEx.getMessage());
		        }
		    }
	    }
	
	 public void canceled() {
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    try {
		    	wait.waitForVisibility(cancelbtn);
		        wait.waitForClickability(cancelbtn).click();
		    } catch (Exception e) {
	            js.executeScript("arguments[0].click();", cancelbtn);
	            System.out.println("cancel not "+e.getMessage());
		    }
	         
		    }
		
	 public boolean isCancelButtonDisplayed() {
	        try {
	            WebElement status = wait.waitForVisibility(cancelbtn);
	            return status.isDisplayed();
	        } catch (Exception e) {
	            System.out.println("Cancel button not visible: " + e.getMessage());
	            return false;
	        }
	    }
	
	
}
