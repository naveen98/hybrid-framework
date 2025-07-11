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

public class CreateNewIssuePage {
	WebDriver driver;
	webdriverwaitutils wait;

	public CreateNewIssuePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new webdriverwaitutils(driver);

	}

	@FindBy(xpath = "//button[@title='Create New Pine Lab Issue']")
	private WebElement createnewticketbtn;
	
	@FindBy(xpath = "//p-autocomplete//input[@role='combobox']")
	private WebElement storenamedrp;
	
	By storeoptions = By.xpath("//p-autocomplete//ul//li[@role='option']");

	@FindBy(xpath = "//p-dropdown[@placeholder='Select category']")
	private WebElement categorydrp;
	
	By categoryoptions = By.xpath("//ul[@role='listbox']//li");
	
	@FindBy(xpath = "//textarea[@id='cmplnt_desc']")
	private WebElement textboxdrscrpition;
	
	@FindBy(xpath = "//button[@id='btn_save']")
	private WebElement createissuebtn;
	
	@FindBy(xpath = "//p-dropdown[@placeholder='Select device TID']")
	private WebElement deviceiddrp;
	
	By diveoption = By.xpath("//li[@role='option']");
	
    private  By msglocator = By.xpath("//div[contains(@class,'toast-title') or text()='Please fill mandatory fields' or contains(text(),'Pine lab issue created successfully')]");
	//By msglocator = By.xpath("//div[contains(@class, 'toast-title')] | //div[normalize-space()='Please fill mandatory fields']");
    // @FindBy(xpath="//div[contains(@class,'toast-title') or contains(text(),'Please fill mandatory fields')]")private List<WebElement> msglocator;
 
    
	@FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
	private WebElement popokbutton;
	
    By pagestext = By.xpath("//div[contains(text(),'Total') and contains(text(),'Pages')]");

	public void clickbuuton() {
		wait.waitForClickability(createnewticketbtn).click();
		
	}

	public void createnewissue(String storeInput, String expectedStoreText, String category, String deviceId,
			String description) {

		Dropdownutils.selectFromAutoSuggest(driver, storenamedrp, storeoptions, storeInput, expectedStoreText);
		Dropdownutils.selectbyvisibletextlist(driver, categorydrp, categoryoptions,category);
		Dropdownutils.selectbyvisibletextlist(driver, deviceiddrp, diveoption, deviceId);
		wait.waitForEnterText(textboxdrscrpition, description);	
	}
	public void createsubmitbtn() {

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    try {
	        // Scroll to bottom of the page
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

	        wait.waitForClickability(createissuebtn).click();
	    } catch (Exception e) {
	        // If normal click fails, use JS click
	        js.executeScript("arguments[0].click();", createissuebtn);
	    }
	}

	public boolean handlepopup() {

		try {
			WebElement popbtn = wait.waitForClickability(popokbutton);
			if (popbtn!=null&&popbtn.isDisplayed()) {
				try {
	                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", popbtn);

					popbtn.click();
				} catch (Exception e) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", popbtn);

				}
				return true;

			}
		} catch (Exception c) {

			System.out.println("no popup ");

		}
		return false;

	}

	public String getvalidationmessage() {

		String message = "";
		try {
	
			List<WebElement> messageElements = wait.waitForAllElementsVisible(msglocator);
			if (messageElements != null && !messageElements.isEmpty()) {
            for(WebElement msgelement:messageElements) {
            	
            if(msgelement.isDisplayed())
				message =msgelement.getText();

				System.out.println("Toast message: " + message);

				switch (message) {

				case "Pine lab issue created successfully":
					return message;

				case "Username Already exists":
					return "username already exists";

				case "Pine lab issue updated successfully":
					return message;
					

				case "Please fill mandatory fields":
					//scroll up and handle 
					((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

					handlepopup();

					return "Mandatory fields missing";

				default:
					return message;

				}
            }
			
				
			}else {
				System.out.println("Messge element not visible");
			}
		} catch (Exception e) {

			System.out.println("----------Error ------" + e.getMessage());
		}

		return "No Messege Displayed";
	}
	
	public int gettotalcount() {
		
		try {
			WebElement pageinfotext=wait.waitForVisibilityBy(pagestext);
			String  text=pageinfotext.getText();
			String total=text.split("Total")[1].split(",")[0].trim();
			
			return Integer.parseInt(total);
		}
		catch (Exception e) {
	        System.out.println(" total count: " + e.getMessage());
			return -1;
		}
			
		
	}

}
