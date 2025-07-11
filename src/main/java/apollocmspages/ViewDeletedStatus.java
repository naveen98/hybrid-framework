package apollocmspages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class ViewDeletedStatus {
	webdriverwaitutils wait;
	WebDriver driver;
	JavascriptExecutor js;

	public ViewDeletedStatus(WebDriver driver) {

		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	// locators

	@FindBy(xpath = "(//button[@class='btn btn-primary filter-clear-btn mr-1 zc-global-search-btn ng-star-inserted'])[1]")
	private WebElement searchbar;

	@FindBy(xpath = "(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")
	private WebElement searchclear;
	@FindBy(xpath = "//input[@placeholder='Search for issue no, store, category, created by, status']")
	private WebElement searchboxtext;
	@FindBy(xpath = "(//td[normalize-space()='No pine lab issues found'])[1]")private WebElement issatus;
	
	
	
	
	
	
	public void checkdeletedissue(String issueno) {
		
		
		//clearing the search field
		try {
			wait.waitForClickability(searchclear).click();
			
		}catch (Exception e) {
            js.executeScript("arguments[0].click();", searchclear);
            
		}
		
		//Entering the issue Number and search it 
		  try {
		        //  Search for the issue
		        WebElement searchBox = wait.waitForVisibility(searchboxtext);
		        searchBox.sendKeys(issueno);

		        try {
		            wait.waitForClickability(searchbar).click();
		        }     catch (Exception e) {
		            js.executeScript("arguments[0].click();", searchbar);
		        }
		  }  catch (Exception e) {
                     System.out.println("searching Failed");
		       } 
		        
	}
	
	public boolean statusCheck() {
		
		try {
			if(wait.waitForVisibility(issatus).isDisplayed()) {
				System.out.println("Issue Deleted No data found  Displayed");

			return true;
					
			}
	
		}catch (Exception e) {
           System.out.println("Failed to Search");
		}
		return false;
		
	}
	

}
