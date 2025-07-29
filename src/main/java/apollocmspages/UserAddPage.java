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

import com.utilites.Dropdownutils;
import com.utilites.webdriverwaitutils;

public class UserAddPage {
    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;

    public UserAddPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'Add')]")
    private WebElement Addusersbtn;

    @FindBy(xpath = "(//input[@id='first_name'])[1]")
    private WebElement TxtFirstname;

    @FindBy(xpath = "(//input[@id='middle_name'])[1]")
    private WebElement TxtMiddlename;

    @FindBy(xpath = "(//input[@id='last_name'])[1]")
    private WebElement TxtLastname;

    @FindBy(xpath = "(//input[@id='email'])[1]")
    private WebElement TxtEmail;

    @FindBy(xpath = "(//input[@id='phone'])[1]")
    private WebElement TxtPhoneNumber;

    @FindBy(xpath = "//input[@id='login_unique']")
    private WebElement useridtxt;

    @FindBy(xpath = "//p-dropdown[@placeholder='Select region']")
    private WebElement regiondrp;
    By regionoptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//p-dropdown[@id='formly_38_select_cluster_6']")
    private WebElement clusterdrp;
    By clusteroptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//p-dropdown[@placeholder='Select location']")
    private WebElement locationdrp;
    By locatiooptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//p-dropdown[@placeholder='Select operating unit']")
    private WebElement operatingunitdrp;
    By operatingoptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//p-dropdown[@placeholder='Select role']")
    private WebElement roledrp;
    By roleoptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//p-dropdown[@placeholder='Select department']")
    private WebElement depatmentdrp;
    By depatoptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//p-dropdown[@placeholder='Select designation']")
    private WebElement designationdrp;
    By designoptions = By.xpath("//li[@role='option']");

    @FindBy(xpath = "(//input[@id='password'])[1]")
    private WebElement txtpassword;

    @FindBy(xpath = "(//input[@id='confirm_password'])[1]")
    private WebElement txtcnfpassword;

    @FindBy(xpath = "(//p-dropdown[@placeholder='Select'])[2]")
    private WebElement leveldr;
    By leveloption = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//button[@id='btnSave']")
    private WebElement savebtn;

    @FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
    private WebElement okbtn;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement cancelform;

    @FindBy(xpath = "(//span[contains(text(),'Add')])[2]")
    private WebElement tickettaguseradd;

    @FindBy(xpath = "(//input[@role='combobox'])[5]")
    private WebElement tickettagusersearch;
    
    By tickettaguseroption = By.xpath("//li[@role='option']");

    @FindBy(xpath = "(//span[contains(text(),'Add')])[3]")
    private WebElement addtikettaguserbtn;

    @FindBy(xpath = "(//button[@class='close'])[2]")
    private WebElement closetagpoptable;

    //By msglocator = By.xpath("//div[contains(@class,'toast-message') and (contains(text(),'Employee ID') or contains(text(),'Mobile No') or contains(text(),'saved') or contains(text(),'match') or contains(text(),'Password') or contains(text(),'exists'))]");

    By msglocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");
    By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please fill mandatory fields')]");

    public void addutton() {
    	try {
        wait.waitForClickability(Addusersbtn).click();
    }
    	catch (Exception e) {
			js.executeScript("arguments[0].click();", Addusersbtn);
		}
    }
    public void closepoptag() {
        wait.waitForClickability(closetagpoptable).click();
    }

    public void Adduserform(String fname, String mname, String lname, String email, String phone, String userid,
                            String region, String cluster, String location, String operatingUnit,
                            String role, String department, String designation, String level,
                            String password, String confirmPassword) {

        wait.waitForVisibility(TxtFirstname);
        wait.waitForEnterText(TxtFirstname, fname);
        wait.waitForEnterText(TxtMiddlename, mname);
        wait.waitForEnterText(TxtLastname, lname);
        wait.waitForEnterText(TxtEmail, email);
        wait.waitForEnterText(TxtPhoneNumber, phone);
        wait.waitForEnterText(useridtxt, userid);

        Dropdownutils.selectbyvisibletextlist(driver, regiondrp, regionoptions, region);
        wait.waitForVisibility(clusterdrp);
        Dropdownutils.selectbyvisibletextlistretry(driver, clusterdrp, clusteroptions, cluster);
        Dropdownutils.selectbyvisibletextlistretry(driver, locationdrp, locatiooptions, location);
        Dropdownutils.selectbyvisibletextlistretry(driver, operatingunitdrp, operatingoptions, operatingUnit);
        Dropdownutils.selectbyvisibletextlistretry(driver, roledrp, roleoptions, role);
        Dropdownutils.selectbyvisibletextlistretry(driver, depatmentdrp, depatoptions, department);
        Dropdownutils.selectbyvisibletextlistretry(driver, designationdrp, designoptions, designation);
        Dropdownutils.selectbyvisibletextlistretry(driver, leveldr, leveloption, level);

        wait.waitForEnterText(txtpassword, password);
        wait.waitForEnterText(txtcnfpassword, confirmPassword);
    }

    public void clicksavebtn() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        wait.waitForClickability(savebtn).click();
    }

    public void canceform() {
        wait.waitForClickability(cancelform).click();
    }

    public boolean selectRadioButtonOption(String labelText, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
        	//label 
            String labelxpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText + "')]]";
            WebElement radioGroup = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelxpath)));

            List<WebElement> options = radioGroup.findElements(By.xpath(".//label[contains(@class,'custom-radio')]"));
            for (WebElement option : options) {
                String label = option.getText().trim();
                if (label.equalsIgnoreCase(optionText.trim())) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                    WebElement radiobtn = option.findElement(By.tagName("input"));
                    if (!radiobtn.isSelected()) {
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                        } catch (Exception e) {
                            js.executeScript("arguments[0].click();", option);
                        }
                    }
                    return radiobtn.isSelected();
                }
            }
            System.err.println("Radio option '" + optionText + "' not found under label '" + labelText + "'");
        } catch (Exception e) {
            System.err.println("Exception in selecting radio [" + labelText + " : " + optionText + "]: " + e.getMessage());
        }
        return false;
    }

    public void addtickettaguser(String input, String Expected) {
        try {
            wait.waitForClickability(tickettaguseradd).click();
            Dropdownutils.selectFromAutoSuggest(driver, tickettagusersearch, tickettaguseroption, input, Expected);
            try {
                wait.waitForClickability(addtikettaguserbtn).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", addtikettaguserbtn);
            }
        } catch (Exception e) {
            System.out.println("Failed to interact with addticketusertag");
        }
    }

    public boolean handlepopup() {
        try {
            WebElement popbtn = wait.waitForVisibilityBy(popupMessageLocator);
            if (popbtn != null && popbtn.isDisplayed()) {
                WebElement popokbtn = wait.waitForClickability(okbtn);
                try {
                    popokbtn.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", popokbtn);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }

    public String getvalidationmessage() {
        String message = "";
        try {
            // Check for popup
            List<WebElement> popups = driver.findElements(popupMessageLocator);
            
            if (!popups.isEmpty()) {
                for (WebElement popup : popups) {
                    if (popup.isDisplayed()) {
                        message = popup.getText().trim();
                        handlepopup(); // close it
                        System.out.println("Popup message: " + message);
                        return message;
                    }
                }
            }

            // Check for toast
            List<WebElement> messageElements = wait.waitForAllElementsVisible(msglocator);
            if (!messageElements.isEmpty()) {
                for (WebElement msgelement : messageElements) {
                    if (msgelement.isDisplayed()) {
                        message = msgelement.getText().trim();
                        System.out.println("Toast message: " + message);
                        switch (message) {
                            case "User saved Successfully":
                                return message;
                            case "Mobile No. Already Exists":
                                return message;
                            case "Employee ID Already exists":
                                return message;
                            case "New Password and Confirm Password did not match, please verify.":
                                return message;
                            case "Password should be between 6 and 25 characters, 1 Min Upper Case, 1 Min Lower Case, 1 Min Digit, 1 Min Special Chars.":
                                return message;
                            case "Please fill mandatory fields":
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
}
