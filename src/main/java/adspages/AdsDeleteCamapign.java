package adspages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class AdsDeleteCamapign {

	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;

	public AdsDeleteCamapign(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;

		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[@placeholder='Search by Name / Uploaded by / Status']")
	private WebElement searchinput;

	@FindBy(xpath = "(//button[@type='button'])[1]")
	private WebElement searchbar;

	@FindBy(xpath = "(//span[@class='zc-status'])[1]")
	private WebElement clickcampaign;

	@FindBy(xpath = "//button[@id='btnDelete']")
	private WebElement Deleteoption;

	By deleteconfirmmsg = By
			.xpath("//div[contains(@class,'modal-body') and contains(text(),'Do you want to delete?')]");

	@FindBy(xpath = "//button[@id='dialog-okay-btn']")
	private WebElement popokbtn;
	
	@FindBy(xpath = "//button[@id='dialog-cancel-btn']")
	private WebElement popcancelbtn;

	By msglocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

	@FindBy(xpath = "//i[@class='icon-block7 ng-star-inserted']")
	private WebElement clickonledcampaignmain;
	
	@FindBy(xpath = "//td[contains(text(),' No LED Campaigns Found ')]")private WebElement norecordfound;


	public void clickmainled() {
    WebElement mainled=wait.waitForVisibility(clickonledcampaignmain);
		try {
			if(mainled.isDisplayed()) {
			wait.waitForClickability(mainled).click();
			}
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}
	}

	public boolean searchAndSelect(String search) {
	    try {
	        WebElement searchField = wait.waitForVisibility(searchinput);

	        if (searchField != null && searchField.isDisplayed()) {
	            wait.waitForEnterText(searchField, search);
	            wait.waitForClickability(searchbar).click();

	            System.out.println(" Record found for: " + search);
	            return true;
	        } else {
	        	
	            System.out.println(" Search input field is not displayed.");
	            return false;
	            
	        }

	    } catch (Exception e) {
	        System.out.println(" Search exception: " + e.getMessage());
	        return false;
	    }
	}
	public void clickDelete() {
	    try {
	        WebElement clickCampa = wait.waitForVisibility(clickcampaign);
	        if (clickCampa != null && clickCampa.isDisplayed()) {
	            wait.waitForClickability(clickCampa).click();
	            System.out.println(" Campaign clicked.");
	        } else {
	            System.out.println("Campaign element not visible.");
	        }
	    } catch (Exception e) {
	        System.out.println(" Exception while clicking campaign: " + e.getMessage());
	    }

	    try {
	        WebElement delete = wait.waitForVisibility(Deleteoption);
	        if (delete != null && delete.isDisplayed()) {
	            wait.waitForClickability(delete).click();
	            System.out.println("Delete button clicked.");
	        } else {
	            System.out.println("Delete option not visible.");
	        }
	    } catch (Exception e) {
	        System.out.println("Exception while clicking delete: " + e.getMessage());
	    }
	}


	public boolean confirmDeletePopUp() {
		try {
			WebElement confirmMsg = wait.waitForVisibilityBy(deleteconfirmmsg);
			if (confirmMsg != null && confirmMsg.isDisplayed()) {
				System.out.println("Confirmation message displayed: " + confirmMsg.getText());
				wait.waitForClickability(popokbtn).click();
				return true;
			}
		} catch (Exception e) {
			System.out.println("Delete confirmation failed  " + e.getMessage());
		}
		return false;
	}

	public void cancelDelete() {
		try{
			WebElement cancel=wait.waitForVisibility(popcancelbtn);
		
		if(cancel.isDisplayed()) {
		wait.waitForClickability(cancel).click();
		}
	    	}catch (Exception e) {
				System.out.println("Excepton "+ e.getMessage());
			}
		
	}

	// Get toast
	public String getDeleteValidationMessage() {
		String message = "";
		try {
			WebElement msg = wait.waitForVisibilityBy(msglocator);
			message = msg.getText();

			return message;

		} catch (Exception e) {
			return "No message displayed";
		}
	}
	
	public boolean Norecordfound() {
		try {
			WebElement norecords=wait.waitForVisibility(norecordfound);
			if(norecords.isDisplayed()) {
				wait.waitForVisibility(norecordfound).isDisplayed();
				return true;
			}
			else {
				System.out.println("no record found not displayed");
				return false;
			}
			
		}
		catch (Exception e) {
			System.out.println("exception :" +e.getMessage());
			return false;
		}
	}

}
