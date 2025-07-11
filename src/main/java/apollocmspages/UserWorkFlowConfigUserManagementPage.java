package apollocmspages;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Dropdownutils;
import com.utilites.webdriverwaitutils;

public class UserWorkFlowConfigUserManagementPage {

	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;

	public UserWorkFlowConfigUserManagementPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	// Element Locators

	@FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='User Workflow Config']")
	private WebElement userWorkflowConfigOption;

	@FindBy(xpath = "(//button[@class='btn btn-primary ng-star-inserted'])[4]")
	private WebElement addButton;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select workflow entity']")
	private WebElement workflowEntityDropdown;

	private final By dropdownOptions = By.xpath("//li[@role='option']");

	@FindBy(xpath = "//button[@id='btnSubmit']")
	private WebElement submitButton;

	@FindBy(xpath = "(//button[@id='btnCancel'])[1]")
	private WebElement cancelButton;

	@FindBy(xpath = "//button[@id='dialog-okay-btn']")
	private WebElement popupOkButton;

	@FindBy(xpath = "//button[@id='dialog-cancel-btn']")
	private WebElement popupCancelButton;

	@FindBy(xpath = "//input[@placeholder='Search Name/Email/Mobile No./Reports to/User Type']")
	private WebElement searchField;

	@FindBy(xpath = "//button[@type='button']")
	private WebElement searchButton;

	@FindBy(xpath = "(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")
	private WebElement searchClearButton;

	@FindBy(xpath = "//div[contains(text(),'Please fill mandatory fields')]")
	private WebElement popupMessage;

	private final By toastMessages = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

	@FindBy(xpath = "(//button[@class='close'])[1]")
	private WebElement finalPopupClose;

	@FindBy(xpath = "(//button[@class='close'])[2]")
	private WebElement insidePopupClose;

	// Action Methods

	public void search(String userid) {
		try{
			WebElement ele= wait.waitForEnterText(searchField, userid);
		    ele.sendKeys(Keys.ENTER);
		
		
		} catch (Exception e) {
			System.out.println("search failed "+ e.getMessage());
		}
	}

	public void openUserWorkflowOption() {
		
		try {
			wait.waitForClickability(userWorkflowConfigOption).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", userWorkflowConfigOption);
		}
	}

	public void addUserWorkflowEntity(String workflowEntity) {
		try {
			wait.waitForClickability(addButton).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", addButton);
		}
		Dropdownutils.selectbyvisibletextlist(driver, workflowEntityDropdown, dropdownOptions, workflowEntity);
	}

	public void submit() {
		try {
			wait.waitForClickability(submitButton).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", submitButton);
		}
	}

	public void closeInsidePopup() {
		try {
			wait.waitForClickability(insidePopupClose).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", insidePopupClose);
		}
	}

	public void closeFinalPopup() {
		try {
			wait.waitForClickability(finalPopupClose).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", finalPopupClose);
		}
	}

	public String getToastMessage() {
	    String message = "";
 
	    try {
	        if (popupMessage != null && popupMessage.isDisplayed()) {
	            message = popupMessage.getText().trim();
	            System.out.println("Popup Message: " + message);

	            try {
	                popupOkButton.click();
	                insidePopupClose.click();
	                finalPopupClose.click();
	            } catch (Exception e) {
	                js.executeScript("arguments[0].click();", popupOkButton);
	                js.executeScript("arguments[0].click();", insidePopupClose);

	                js.executeScript("arguments[0].click();", finalPopupClose);

	            }

	            return message;
	        }
	    } catch (Exception e) {
	        System.out.println("Popup not present: " + e.getMessage());
	    }

	    // 2. Handle toast messages
	    try {
	        List<WebElement> messageElements = wait.waitForAllElementsVisible(toastMessages);

	        if (messageElements != null && !messageElements.isEmpty()) {
	            for (WebElement msgElement : messageElements) {
	                if (msgElement.isDisplayed()) {
	                    message = msgElement.getText().trim();
	                    System.out.println("Toast Message: " + message);

	                    switch (message) {
	                        case "Successfully saved":
	                            return "Successfully saved";

	                        case "Already Selected":
	                            return "Already Selected";

	                        case "Please fill mandatory fields":
	                        	
	                            handlePopup(); 
	                            return "Please fill mandatory fields";

	                        default:
	                            return message;
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error reading toast message: " + e.getMessage());
	    }

	    return message;
	}


	public boolean handlePopup() {
	    try {
	        WebElement popup = wait.waitForVisibility(popupMessage);
	        if (popup != null && popup.isDisplayed()) {

	            try {
	           
	                wait.waitForClickability(popupOkButton).click();
	                wait.waitForClickability(insidePopupClose).click();
	                wait.waitForClickability(finalPopupClose).click();
	            } catch (Exception e) {
	               
	                try {
	                    js.executeScript("arguments[0].click();", popupOkButton);
	                } catch (Exception ex) {
	                    System.out.println("Failed  click on OK button: " + ex.getMessage());
	                }

	                try {
	                    js.executeScript("arguments[0].click();", insidePopupClose);
	                } catch (Exception ex) {
	                    System.out.println("Failed  click on Inside Popup Close: " + ex.getMessage());
	                }

	                try {
	                    js.executeScript("arguments[0].click();", finalPopupClose);
	                } catch (Exception ex) {
	                    System.out.println("Failed  click on Final Popup Close: " + ex.getMessage());
	                }
	            }

	            return true;
	        }
	    } catch (Exception e) {
	        System.out.println("Popup not handled or not present: " + e.getMessage());
	    }

	    return false;
	}
}
