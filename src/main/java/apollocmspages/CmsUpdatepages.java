package apollocmspages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Dropdownutils;
import com.utilites.webdriverwaitutils;

public class CmsUpdatepages {

	WebDriver driver;
	webdriverwaitutils wait;

	public CmsUpdatepages(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@class='row ng-star-inserted']//i[@id='dropdownBasic1']")
	private WebElement actionsbtn;


	// @FindBy(xpath = "//div[contains(@class,'dropdown-menu') and
	// contains(@class,'show')]//span[contains(text(),'Update Issue Details')]")
	// private WebElement updateissuedetails;
	
	By updateissuedetails = By.xpath(
			"//div[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[contains(text(),'Update Issue Details')]");
	//div[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[contains(text(),'Update Issue Details')]

	@FindBy(xpath = "(//button[normalize-space()='Ok'])[1]")
	private WebElement popokbtn;

	@FindBy(xpath = "(//button[normalize-space()='Cancel'])[1]")
	private WebElement popcancelbtn;

	@FindBy(xpath = "//div[normalize-space()='Are you sure you want to change this pine lab issue status ?']")
	private WebElement popmsg;

	@FindBy(xpath = "//div[@class='user-menu actions-menu dropdown-menu show']//span[contains(text(),'View Issue Details')]")
	private WebElement viewissuedetails;

	@FindBy(xpath = "//textarea[@id='cmplnt_desc']")
	private WebElement issuedescriptionbox;

	@FindBy(xpath = "//input[@id='site_phone_no']")
	private WebElement storecontacttxt;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select category']")
	private WebElement drpcategory;

	By listoptions = By.xpath("//ul[@role='listbox']//span");
	// li[@role='option']//span //ul[@role='listbox']//li

	@FindBy(xpath = "(//button[@id='btn_update'])[1]")
	private WebElement updateissuebtn;

	@FindBy(xpath = "//div[contains(@class, 'toast-message') or contains(text(),'Successfully updated')]")
	private List<WebElement> toastMsg;

	@FindBy(xpath = "//label[@class='ng-star-inserted']//span[@class='zc-status'][normalize-space()='Closed']")
	private WebElement isclosed;

	@FindBy(xpath = "//span[normalize-space()='×']")
	private WebElement cancelform;

	@FindBy(xpath = "//input[@placeholder='Search for issue no, store, category, created by, status']")
	private WebElement searchboxtext;

	@FindBy(xpath = "(//button[@class='btn btn-primary filter-clear-btn mr-1 zc-global-search-btn ng-star-inserted'])[1]")
	private WebElement searchbar;

 @FindBy(xpath="(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")private WebElement searchclear;
 
 

	// Click update Issue Details
	public void clickupdatebutton(String issueno) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    try {
	        //  Search for the issue
	        WebElement searchBox = wait.waitForVisibility(searchboxtext);
	        searchBox.clear();
	        searchBox.sendKeys(issueno);

	        try {
	            wait.waitForClickability(searchbar).click();
	        }     catch (Exception e) {
	            js.executeScript("arguments[0].click();", searchbar);
	        }
	        

	        //clearing search 
	        try {
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
	        
	        
	        
	        
	        // Wait for actions button and safely click it
			try {
				wait.waitForClickability(actionsbtn).click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", actionsbtn);
			}
			
	        // Update Issue Details option
	        WebElement updateBtn = null;
	        try {
	            updateBtn = wait.waitForClickabilityby(updateissuedetails);
	        } catch (Exception e) {
	            //  normal click 
	            By UpdateLocator = By.xpath(
	                "//div[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[contains(text(),'Update Issue Details')]");
	            updateBtn = wait.waitForPresence(UpdateLocator);
	        }

	        if (updateBtn != null) {
	            try {
	                updateBtn.click();  
	            } catch (Exception e) {
	              
	                js.executeScript("arguments[0].click();", updateBtn);
	            }
	        } else {
	            System.out.println("Update Issue Details button not found.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error in clickupdatebutton: " + e.getMessage());
	    }
	}
	
	
	 public void selectCategory(String category) {
	        try {
	            Dropdownutils.selectbyvisibletextlistup(driver, drpcategory, listoptions, category);
	        } catch (Exception e) {
	            System.out.println("Failed to select category: " + category + " Error: " + e.getMessage());
	        }
	    }

	// Fill issue fields
	public void updateissuedetailsfornew(String storecontactno, String category, String issuedescription) {
		  try {
	            WebElement contactEle = wait.waitForVisibility(storecontacttxt);
	            contactEle.clear();
	            contactEle.sendKeys(storecontactno);

	            selectCategory(category);

	            WebElement descEle = wait.waitForVisibility(issuedescriptionbox);
	            descEle.clear();
	            descEle.sendKeys(issuedescription);

	        } catch (Exception e) {
	            System.out.println("Error in filling update details: " + e.getMessage());
	        }
	    }
	
	//update submit
	public void updatebtns() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			wait.waitForClickability(updateissuebtn).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", updateissuebtn);
			js.executeScript("arguments[0].click();", updateissuebtn);
		}
	}

	public String getvalidationmessage() {

		String message = "";
		try {

			List<WebElement> messageElements = wait.waitForAllElementsVisible(toastMsg);
			
			if (messageElements != null && !messageElements.isEmpty()) {
				for (WebElement msgelement : messageElements) {

					if (msgelement.isDisplayed())
						message = msgelement.getText();

					System.out.println("Toast message: " + message);

					switch (message) {

					case "Pine lab issue updated successfully":
						return message;

					case "Please fill mandatory fields":

						Thread.sleep(1000);

						handlepopup();

						return "Mandatory fields missing";

					default:
						return message;

					}
				}

			} else {
				System.out.println("Messge element not visible");
			}
		} catch (Exception e) {

			System.out.println("----------Error ------" + e.getMessage());
		}

		return "No Messege Displayed";
	}

	public boolean isstatus() {

		try {

			WebElement status = wait.waitForVisibility(isclosed);
			status.isDisplayed();
			return true;

		} catch (Exception e) {

			System.out.println("Not Displayed");
		}
		return false;

	}

	public boolean handlepopup() {
		try {
			WebElement popup = wait.waitForVisibility(popmsg);
			if (popup != null && popup.isDisplayed()) {
				popup.click();
				return true;
			}
		} catch (Exception e) {
			System.out.println("Popup not found: " + e.getMessage());
		}
		return false;
	}

	public String getPopupMessage() {
		try {
			WebElement popup = wait.waitForVisibility(popmsg);
			return popup.getText();

		} catch (Exception e) {
			return "";
		}
	}

	/*public void clickPopupOk() {
		try {
			wait.waitForClickability(popokbtn).click();
		} catch (Exception e) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", popokbtn);
		}
	}
*/
	/*public void safeClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			wait.waitForClickability(element).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("arguments[0].click();", element);
		}
	}*/

}
