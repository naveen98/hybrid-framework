package apollocmspages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import com.utilites.webdriverwaitutils;

public class CmsSatuspage {

	WebDriver driver;
	webdriverwaitutils wait;

	public CmsSatuspage(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);

	}

	private By pagestext = By.xpath("//div[contains(text(),'Total') and contains(text(),'Pages')]");
	                                                    //div[contains(text(),'Total')]
	@FindBy(xpath = "//table[@class='table-view']/tbody/tr")
	private List<WebElement> rowsstatus;
	@FindBy(xpath = "(//table[@class='table-view'])[1]")
	private WebElement tabledisplay;
	@FindBy(xpath = "(//span[@class='zc-status'][normalize-space()='New'])[1]")private WebElement Statusceck;
	

	@FindBy(xpath = "//div[@class='row ng-star-inserted']//i[@id='dropdownBasic1']")
	private WebElement actionsbtn;
    @FindBy(xpath = "//div[@class='user-menu actions-menu dropdown-menu show']//span[contains(text(),'View Issue Details')]")private WebElement viewissuedetails;

	@FindBy(xpath = "//input[@placeholder='Search for issue no, store, category, created by, status']")
	private WebElement searchboxtext;

	@FindBy(xpath = "(//button[@class='btn btn-primary filter-clear-btn mr-1 zc-global-search-btn ng-star-inserted'])[1]")
	private WebElement searchbar;
	
	@FindBy(xpath = "(//span[@class='zc-status'][normalize-space()='Closed'])[2]")private WebElement isclosedstatus;
    @FindBy(xpath = "//button[@class='close']")private WebElement cancelbtn;



	public boolean isTableDisplayedStatus(String expected ) {
		
		        try {
		            WebElement status = wait.waitForVisibility(Statusceck);
		            if (status != null && status.isDisplayed()) {
		                String actualText = status.getText().trim();
		                if (actualText.equalsIgnoreCase(expected.trim())) {
		                    System.out.println("Status matched: " + actualText);
		                    return true;
		                } else {
		                    System.out.println("Status mismatch: Expected = " + expected + ", Actual = " + actualText);
		                }
		            }
		        } catch (Exception e) {
		            System.out.println("Status check failed: " + e.getMessage());
		        }
		        return false;
	}
	
	public void viewclosedstatuscheck(String issueno) {
		
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		  
		 
		    try {
		    	
		        //  Search for the issue
		        WebElement searchBox = wait.waitForVisibility(searchboxtext);
		      //  searchBox.clear();
		        searchBox.sendKeys(issueno);

		        try {
		            wait.waitForClickability(searchbar).click();
		        }     catch (Exception e) {
		            js.executeScript("arguments[0].click();", searchbar);
		        }

		        // Wait for actions button and safely click it
				try {
					wait.waitForClickability(actionsbtn).click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", actionsbtn);
				}
	
				try {
					wait.waitForClickability(viewissuedetails).click();
					
				}catch (Exception e) {
                           js.executeScript("arguments[0].click();", viewissuedetails);
                  
				}
		    }	catch (Exception e) {
                       System.out.println("----view closed status -------error ---");
		    
		    }
    	
    }
	
	
    public boolean viewstatus(String expected) {
   	 try {
   	        // Wait until the status element is visible
   	        WebElement statusElement = wait.waitForVisibility(isclosedstatus);
   	        
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

		/*try {
		
			WebElement table = wait.waitForVisibility(tabledisplay);
			if (table != null && table.isDisplayed()) {
				System.out.println(" Table is displayed.");
				return true;
		  }
		} catch (Exception e) {
			System.out.println("Table not displayed: " + e.getMessage());
		
		}
		return false;
	}*/
	
}
