package apollocmspages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class Deleteissuepages {
	WebDriver driver;
	webdriverwaitutils wait;
	public Deleteissuepages(WebDriver driver) {
		
		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		PageFactory.initElements(driver,this);
	}

	
	@FindBy(xpath = "(//i[@id='dropdownBasic1'])[1]")private WebElement actionsbtn;
	
	@FindBy(xpath = "(//button[@class='dropdown-item ng-star-inserted'])[48]")private WebElement deletebtn;

	@FindBy(xpath = "(//button[normalize-space()='Ok'])[1]")private WebElement popokbtn;
	
	@FindBy(xpath = "(//button[normalize-space()='Cancel'])[1]")private WebElement cancelbtn;
	@FindBy(xpath = "(//span[normalize-space()='Delete Issue'])[1]")private WebElement deleteoption;
	

		
  //  @FindBy(xpath="//div[contains(@class, 'toast-title') or normalize-space()='Do you want to delete this issue?']")private WebElement popmsg;
	@FindBy(xpath = "//div[contains(@class, 'toast-message') and contains(text(),'deleted successfully')]")
	private WebElement toastMsg;
	
	@FindBy(xpath = "//div[normalize-space()='Do you want to delete this issue?']") 
	private WebElement popmsg;
	
	@FindBy(xpath = "//input[@placeholder='Search for issue no, store, category, created by, status']")
	private WebElement searchboxtext;


	 @FindBy(xpath="(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")private WebElement searchclear;
	 
	 @FindBy(xpath = "(//button[@type='button'])[1]")
		private WebElement searchbar;
	 @FindBy(xpath = "(//td[normalize-space()='No pine lab issues found'])[1]")private WebElement noissuefound;
	
	public void clearseach() {
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		   //clearing search 
        try {
        	wait.waitForVisibility(searchclear);
        	wait.waitForClickability(searchclear).click();
        
        }
        catch (Exception e) {
			js.executeScript("arguments[0].click();", searchclear);
        }
        
        //click on search 
        try {
        	wait.waitForClickability(searchbar).click();
        }
        catch (Exception e) {
            js.executeScript("arguments[0].click();", searchbar);
        }
        
  
	}
	public boolean  Deleteissue(String issueno) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    try {
	        // Search for the issue
	        WebElement searchBox = wait.waitForVisibility(searchboxtext);
	        searchBox.clear();
	        searchBox.sendKeys(issueno);

	        try {
	            wait.waitForClickability(searchbar).click();
	        } catch (Exception e) {
	            js.executeScript("arguments[0].click();", searchbar);
	        }

	        Thread.sleep(2000); // allow time for search results to update

	        // Check if issue is not found (already deleted)
	        try {
	            if (noissuefound.isDisplayed()) {
	                System.out.println("Issue " + issueno + " already deleted or not found.");
	                return false;
	            }
	        } catch (Exception e) {
	            System.out.println("Exception");
	        }

	        // Click on actions button
	        try {
	            wait.waitForClickability(actionsbtn).click();
	        } catch (Exception e) {
	            js.executeScript("arguments[0].click();", actionsbtn);
	        }

	        // Click on delete option
	        try {
	            wait.waitForClickability(deleteoption).click();
	        } catch (Exception e) {
	            js.executeScript("arguments[0].click();", deleteoption);
	        }

	        return true; 
	    } catch (Exception e) {
	        System.out.println("Failed to Delete: " + e.getMessage());
	        return false;
	    }
	
	    }
	
	
	public String handlepopup() {
		String message ="";
		try {
			WebElement popupText = wait.waitForVisibility(popmsg);
			if (popupText != null && popupText.isDisplayed()) {
				Thread.sleep(2000);
				message = popupText.getText().trim();
				System.out.println("Popup Message: " + message);

				try {
					wait.waitForClickability(popokbtn).click();
				} catch (Exception e) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", popokbtn);
				}
				return message;
			}
		} catch (Exception e) {
			System.out.println("Popup not found.");
		}
		return message;
	}

		
		public String getPopupMessage() {
			String message = "";
			try {
				WebElement popupmsg = wait.waitForVisibility(popmsg);
				if (popupmsg != null && popupmsg.isDisplayed()) {
					message = popupmsg.getText().trim();
					System.out.println("Popup Text: " + message);
				}
			} catch (Exception e) {
				System.out.println("Popup Message not found.");
			}
			return message;
		}
		
		public void cancelpopup() {
			try {
				wait.waitForClickability(cancelbtn).click();
			} catch (Exception e) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", cancelbtn);
			}
		}
		
		public String getToastMessage() {
			try {
				WebElement toast = wait.waitForVisibility(toastMsg);
				if (toast != null && toast.isDisplayed()) {
					Thread.sleep(2000);
					String msg = toast.getText().trim();
					System.out.println("Toast Message: " + msg);
					return msg;
				}
			} catch (Exception e) {
				System.out.println("Toast message not displayed.");
			}
			return "";
		}

	
	
	
}
