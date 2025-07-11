package apollocmspages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utilites.webdriverwaitutils;

public class UpadateIssueStatusClosed {
	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;
	public UpadateIssueStatusClosed(WebDriver driver) {
		this.driver=driver;
		this.wait=new webdriverwaitutils(driver);
		this.js=(JavascriptExecutor)driver;
		PageFactory.initElements(driver,this);
	
	}

	@FindBy(xpath = "(//i[@aria-haspopup='true'])[9]")private WebElement actionsbtn;
    @FindBy(xpath = "//div[@class=\"user-menu actions-menu dropdown-menu show\"]//span[contains(text(),\"Update Issue Status\")]")private WebElement updateissuestatus;

	@FindBy(xpath = "(//textarea[@id='feedback'])[1]")
	private WebElement txtfeedback;

	@FindBy(xpath = "(//button[@id='btnSubmit'])[1]")
	private WebElement submitbtn;
	
	@FindBy(xpath = "(//button[normalize-space()='Ok'])[1]")
	private WebElement popokbtn;

	@FindBy(xpath = "(//button[normalize-space()='Cancel'])[1]")
	private WebElement popcancelbtn;
	
	@FindBy(xpath = "//div[normalize-space()='Are you sure you want to change this pine lab issue status ?']")
	private WebElement popmsg;

	@FindBy(xpath = "//div[contains(@class, 'toast-message') or contains(text(),'Successfully updated')]")
	private List<WebElement> toastMsg;
	
	public By modalLocator = By.tagName("ngb-modal-window");
	
	public String statusclosed(String feedback) {
		try {
			
			wait.waitForVisibility(actionsbtn);
	        wait.waitForClickability(actionsbtn).click();
	        wait.waitForClickability(updateissuestatus).click();
	        wait.waitForEnterText(txtfeedback, feedback);
	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", submitbtn);

	        try {
	            wait.waitForClickability(submitbtn).click();
	        } catch (Exception e) {
	            js.executeScript("arguments[0].click();", submitbtn);
	        }

	        // Wait for and handle confirmation popup
	        if (wait.waitForVisibility(popmsg).isDisplayed()) {
	            try {
	                wait.waitForClickability(popokbtn).click();
	            } catch (Exception e) {
	                js.executeScript("arguments[0].click();", popokbtn);
	            }
	        }

	        Thread.sleep(1000); 
	        waitForModalToClose();

	    
	        WebDriverWait toastWait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        List<WebElement> messages = toastWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                By.xpath("//div[contains(@class,'toast-message')]")));

	        for (WebElement msg : messages) {
	            if (msg.isDisplayed()) {
	                String text = msg.getText();
	                System.out.println("Toast Message: " + text);
	                return text;
	            }
	        }

	    } catch (Exception e) {
	        System.out.println("Update Issue Status Failed: " + e.getMessage());
	    }

	    return "No toast message displayed.";
		
	}
	

	public String getPopupMessage() {
		try {
			WebElement popup = wait.waitForVisibility(popmsg);
			Thread.sleep(2000);
			return popup.getText();

		} catch (Exception e) {
			return "";
		}
	}
	

	public boolean handlePopupOK() {
		try {
			if (popokbtn != null && popokbtn.isDisplayed()) {
				popokbtn.click();
				return true;
			}
		} catch (Exception e) {
			System.out.println("Popup OK button not found: " + e.getMessage());
		}
		return false;
	}
	
	public void waitForModalToClose() {
		try {
			WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
			System.out.println("Modal closed successfully.");
		} catch (Exception e) {
			System.out.println("Modal not found or still open: " + e.getMessage());
		}
	}
}
