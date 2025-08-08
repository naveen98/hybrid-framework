package adspages;

import java.util.ArrayList;
import java.util.List;

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
    @FindBy(xpath = "(//p-dropdown[@placeholder='Select font'])[1]") private WebElement fontDropdown;
    @FindBy(xpath = "(//p-dropdown[@placeholder='Select font'])[2]") private WebElement fontSizeDropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select animation speed']") private WebElement animationSpeedDropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select stay time']") private WebElement stayTimeDropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select style']") private WebElement stayStyleDropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select program']") private WebElement programDropdown;
    @FindBy(xpath = "//p-dropdown[@placeholder='Select dimensions']") private WebElement dimensionDropdown;

    // Buttons
    @FindBy(xpath = "(//button[@id='btnNext'])[2]") private WebElement ledStyleNextButton;
    @FindBy(xpath = "//button[@id='btnsubmit']") private WebElement submitButton;
    @FindBy(xpath = "(//button[@id='btnBack'])[1]") private WebElement backButton;
    @FindBy(xpath = "//button[@id='dialog-okay-btn']") private WebElement popupOkButton;
    @FindBy(xpath = "//button[@id='dialog-cancel-btn']") private WebElement popupCancelButton;

    // Locators
    private By toastMsg = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");
    private By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Data will be removed')]");
    private By validationMessages = By.xpath("//div[contains(@class,'invalid-feedback')]");
    private By dropdownOptions = By.xpath("//li[@role='option']");

    // Fill dropdowns
    public void fillLedStyles(String font, String fontSize, String animation, String stayTime, String style, String program) {
        try {
            Dropdownutils.selectbyvisibletextlistretry(driver, fontDropdown, dropdownOptions, font);
            Dropdownutils.selectbyvisibletextlistretry(driver, fontSizeDropdown, dropdownOptions, fontSize);
            Dropdownutils.selectbyvisibletextlistretry(driver, animationSpeedDropdown, dropdownOptions, animation);
            Dropdownutils.selectbyvisibletextlistretry(driver, stayTimeDropdown, dropdownOptions, stayTime);
            Dropdownutils.selectbyvisibletextlistretry(driver, stayStyleDropdown, dropdownOptions, style);
            Dropdownutils.selectbyvisibletextlistretry(driver, programDropdown, dropdownOptions, program);
        } catch (Exception e) {
            System.out.println("Exception in fillLedStyles(): " + e.getMessage());
        }
    }

    // Select dimension 
    public void preview(String dimension) {
        try {
            Dropdownutils.selectbyvisibletextlistretry(driver, dimensionDropdown, dropdownOptions, dimension);
        } catch (Exception e) {
            System.out.println("Exception in preview(): " + e.getMessage());
        }
    }

    // Click Next button
    public void clickNext() {
        try {
            WebElement nextBtn = wait.waitForVisibility(ledStyleNextButton);
            if (nextBtn != null && nextBtn.isDisplayed()) {
                wait.waitForClickability(nextBtn).click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", ledStyleNextButton);
        }
    }

    // Click Submit button
    public void clickSubmit() {
        try {
            WebElement submitBtn = wait.waitForVisibility(submitButton);
            if (submitBtn != null && submitBtn.isDisplayed()) {
                wait.waitForClickability(submitBtn).click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", submitButton);
        }
    }

    // Get Toast Message
    public String getToastMessage() {
        try {
            List<WebElement> messages = wait.waitForAllElementsVisible(toastMsg);
            for (WebElement msg : messages) {
                if (msg != null && msg.isDisplayed()) {
                    String toast = msg.getText().trim();
                    System.out.println("Toast Message: " + toast);
                    return toast;
                }
            }
        } catch (Exception e) {
            System.out.println("Toast not found: " + e.getMessage());
        }
        return "no message displayed";
    }

    // Get Validation Messages
    public List<String> getValidationMessages() {
        List<String> messages = new ArrayList<>();
        try {
            List<WebElement> validationElements = wait.waitForAllElementsVisible(validationMessages);
            for (WebElement element : validationElements) {
                if (element != null && element.isDisplayed()) {
                    String msg = element.getText().trim();
                    messages.add(msg);
                    System.out.println("Validation: " + msg);
                }
            }
        } catch (Exception e) {
            System.out.println("No validation messages found: " + e.getMessage());
        }
        return messages;
    }

    // Handle popup clicking OK
    public boolean handlePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(popupMessageLocator);
            if (popupMsg != null && popupMsg.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(popupOkButton);
                try {
                    okBtn.click();
                } catch (Exception ex) {
                    js.executeScript("arguments[0].click();", popupOkButton);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }
}
