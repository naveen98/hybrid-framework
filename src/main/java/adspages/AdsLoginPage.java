package adspages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.utilites.webdriverwaitutils;

public class AdsLoginPage {

	private WebDriver driver;
	private webdriverwaitutils wait;
	private JavascriptExecutor js;

	// Constructor
	public AdsLoginPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.wait = new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	}

	// Web Elements
	@FindBy(id = "appUserName")
	private WebElement txtUsername;

	@FindBy(id = "appPassword")
	private WebElement txtPassword;

	@FindBy(id = "loginBtn")
	private WebElement LoginBtn;
	
	@FindBy(xpath = "(//strong[normalize-space()='Apollo Digital Signage'])[1]")
	private WebElement appselection;
	
	@FindBy(xpath = "//a[@id='userdroupdown']//img[@alt='logo']")private WebElement clickonprofile;
	
	@FindBy(xpath = "(//button[normalize-space()='Logout'])[1]")private WebElement clicklogout;
	
	//div[ (contains(@class,'toast-message') or contains(@class,'toast-title') or contains(@class,'modal-body')) ]
	//div[contains(@class, 'toast-message') and @aria-live='polite']
	By toastmsg= By.xpath("//div[(contains(@class,'toast-message') and @aria-live='polite') or contains(@class,'modal-body')]");
	//By toastMsg = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title') or (contains(@class,'modal-body') and (contains(text(),'Enter valid form data.') or contains(text(),'Invalid details')))]");

	@FindBy(xpath = "//button[@id='dialog-okay-btn']")private WebElement popokbtn;

	// Login method
	public void login(String username, String password) {
		try {
			wait.waitForVisibility(txtUsername).clear();
			txtUsername.sendKeys(username);

			wait.waitForVisibility(txtPassword).clear();
			txtPassword.sendKeys(password);

			System.out.println("Please enter CAPTCHA ");
			
			Thread.sleep(30000);
			
			WebElement lgbtn=wait.waitForVisibility(LoginBtn);
			if(lgbtn!=null&&lgbtn.isDisplayed()) {
				wait.waitForClickability(LoginBtn).click();
			}

		} catch (Exception e) {
			System.out.println("Exception during login: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public boolean handlepopupOK() {
	    try {
	        WebElement popup = wait.waitForVisibilityBy(toastmsg); 
	        if (popup != null && popup.isDisplayed()) {
	            WebElement okBtn = wait.waitForVisibility(popokbtn); 
	            if (okBtn != null && okBtn.isDisplayed()) {
	                try {
	                    okBtn.click(); 
	                } catch (Exception e) {
	                    js.executeScript("arguments[0].click();", okBtn); 
	                }
	                return true;
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Popup not handled: " + e.getMessage());
	    }
	    return false;
	}

	
	public String  gettoastmessage() {
		
		String message="";
		try {
			List<WebElement> messageElements = wait.waitForAllElementsVisible(toastmsg);
			if (!messageElements.isEmpty()) {
				for (WebElement msgelement : messageElements) {
					if (msgelement.isDisplayed()) {
						message = msgelement.getText().trim();
						System.out.println("Toast message: " + message);
						
						switch (message) {
						case "Invalid details":
							return message;
						case "Enter valid form data.":
							handlepopupOK();
							return message;
						default:
							return message;
						}
					}
				}
			} else {
				System.out.println("No toast message visible.");
			}
		} catch (Exception e) {
			System.out.println("Error in getvalidationmessage: " + e.getMessage());
		}

		return "No Message Displayed";
	}
		
  
	public void clicklogout() {
		try {
			WebElement prof=wait.waitForVisibility(clickonprofile);

		
		if(prof.isDisplayed()) {
			
			wait.waitForClickability(prof).click();
		}
		
		}catch (Exception e) {
			System.out.println("exception "+ e.getMessage());
		}
	

			try {
				WebElement logout=wait.waitForVisibility(clicklogout);

			
			if(logout.isDisplayed()) {
				
				wait.waitForClickability(logout).click();
			}
			
			}catch (Exception e) {
				System.out.println("exception "+ e.getMessage());
			}
		}

	
	 public void verifytitle(String expectedTitle) {
		 
	        try {
	        	
	        	WebElement app=wait.waitForVisibility(appselection);
	        	if(app!=null&&app.isDisplayed()) {
	        		
	            String actualTitle = driver.getTitle();
	            System.out.println("Actual Title " + actualTitle);
	            Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match after login!");
				System.out.println("Expected title "  +   expectedTitle);

	        	}
	        	else {
	        		System.out.println("not displayed");
	        	}
	        } catch (Exception e) {
	            System.out.println("Title verification failed: " +e.getMessage());
	            Assert.fail("Title verification failed: " + e.getMessage());
	        }
	    }
	
	 
	 public boolean verifytitlewithinvaliddata(String expectedTitle) {
		    try {
		        WebElement msg = wait.waitForPresence(toastmsg);
		        String actualTitle = driver.getTitle();
		        System.out.println("Actual Title: " + actualTitle);

		        if (actualTitle.equals(expectedTitle)) {
		            System.out.println("Page title matches expected: " + expectedTitle);
		            return true;
		        } else {
		            System.out.println("Page title mismatch! Expected: " + expectedTitle + " but was: " + actualTitle);
		            return false;
		        }
		    } catch (Exception e) {
		        System.out.println("Title verification failed due to exception: " + e.getMessage());
		        return false;
		    }
		}

	 
}
