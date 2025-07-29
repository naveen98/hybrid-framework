package adspages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Datepickutils;
import com.utilites.Dropdownutils;
import com.utilites.TimePickersUtil;
import com.utilites.webdriverwaitutils;

public class AddLedStylesPage {
    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;
    public Datepickutils dt;
    TimePickersUtil timepicker;

    public AddLedStylesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        this.dt = new Datepickutils(driver);
        this.timepicker = new TimePickersUtil(driver);
        PageFactory.initElements(driver, this);
    }

    // Dropdown elements
    @FindBy(xpath = "(//p-dropdown[@placeholder='Select font'])[1]") private WebElement FontDropdown;
    @FindBy(xpath = "(//p-dropdown[@placeholder='Select font'])[2]") private WebElement Fontsizedropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select animation speed']") private WebElement Animationspeeddropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select stay time']") private WebElement satytimedropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select style']") private WebElement Staystyledropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select program']") private WebElement programdropdown;

    // Buttons
    @FindBy(xpath = "(//button[@id='btnNext'])[2]") private WebElement ledstylenextbutton;
    @FindBy(xpath = "//button[@id='btnsubmit']") private WebElement Submitbutton;

    // Toast messages
    By toastmsg = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

    
    By popupMessageLocator = By
			.xpath("//div[contains(@class,'modal-body') and contains(text(),'Data will be removed. do you want to continue?");

    
    @FindBy(xpath="(//button[@id='btnBack'])[1]")private WebElement Backbutton;
    @FindBy(xpath = "//button[@id='dialog-okay-btn']")private WebElement popokbtn;
    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")private WebElement popcancelbtn;


    // Drop down option locators
    private By Fontoptions = By.xpath("//li[@role='option']");
    private By FontSizeptions = By.xpath("//li[@role='option']");
    private By Animationoptions = By.xpath("//li[@role='option']");
    private By Staytimeoptions = By.xpath("//li[@role='option']");
    private By Styleoptions = By.xpath("//li[@role='option']");
    private By programoptions = By.xpath("//li[@role='option']");

    //  validation message locator
    private By validationMessages = By.xpath("//div[contains(@class,'invalid-feedback')]");

    // Preview section
    @FindBy(xpath = "//p-dropdown[@placeholder='Select dimensions']") private WebElement dimensiondropdown;
    private By dimensionoption = By.xpath("//li[@role='option']");

    //Filling 
    public void Ledstyles(String font, String fontsize, String animation, String staytime, String style, String program) {
        try {
            Dropdownutils.selectbyvisibletextlistretry(driver, FontDropdown, Fontoptions, font);
            Dropdownutils.selectbyvisibletextlistretry(driver, Fontsizedropdown, FontSizeptions, fontsize);
            Dropdownutils.selectbyvisibletextlistretry(driver, Animationspeeddropdown, Animationoptions, animation);
            Dropdownutils.selectbyvisibletextlistretry(driver, satytimedropdown, Staytimeoptions, staytime);
            Dropdownutils.selectbyvisibletextlistretry(driver, Staystyledropdown, Styleoptions, style);
            Dropdownutils.selectbyvisibletextlistretry(driver, programdropdown, programoptions, program);
        } catch (Exception e) {
            System.out.println("Exception in Ledstyles " + e.getMessage());
        }
    }


    
    
    //  Toast Message 
    public String GetToastmessage() {
        String message = "";
        try {
            List<WebElement> msg = wait.waitForAllElementsVisible(toastmsg);
            for (WebElement msgelement : msg) {
                if (msgelement != null && msgelement.isDisplayed()) {
                    message = msgelement.getText().trim();
                    System.out.println("Toast Message: " + message);
                    return message;
                }
            }
        } catch (Exception e) {
            System.out.println("Toast not found: " + e.getMessage());
        }
        return "no message displayed";
    }

    // Capture All Validation Messages 
    public List<String> getValidationMessages() {
        List<String> messages = new ArrayList<>();

        try {
            List<WebElement> validations = wait.waitForAllElementsVisible(validationMessages);

            for (WebElement msg : validations) {
                if (msg != null && msg.isDisplayed()) {
                    messages.add(msg.getText().trim());
                    System.out.println("Validation: " + msg.getText().trim());
                }
            }

        } catch (Exception e) {
            System.out.println("No validation messages found: " + e.getMessage());
        }

        return messages;
    }


    // click next
    public void clicknext() {
    	WebElement nextbtn=wait.waitForVisibility(ledstylenextbutton);
        try {
        	if(nextbtn!=null&&nextbtn.isDisplayed()) {
                wait.waitForClickability(nextbtn).click();
        	}
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", ledstylenextbutton);
        }
    }

    //  Preview 
    public void Preview(String Dimension) {
        try {
            Dropdownutils.selectbyvisibletextlistretry(driver, dimensiondropdown, dimensionoption, Dimension);
        } catch (Exception e) {
            System.out.println("Exception in Preview(): " + e.getMessage());
        }
    }

    // Submit Button 
    public void submitbutton() {
    	WebElement submitbtn=wait.waitForVisibility(Submitbutton);
    	
        try {
        	if(submitbtn!=null&&submitbtn.isDisplayed()) {
        		
                wait.waitForClickability(submitbtn).click();
        	}
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", Submitbutton);
        }
    }
    
    public boolean handlepopupOK() {
		try {
			WebElement popbtn = wait.waitForVisibilityBy(popupMessageLocator);
			if (popbtn != null && popbtn.isDisplayed()) {
				WebElement popok = wait.waitForClickability(popokbtn);
				try {
					popok.click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", popok);
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println("Popup not displayed: " + e.getMessage());
		}
		return false;
	}
	
}
