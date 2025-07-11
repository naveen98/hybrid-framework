package apollocmspages;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Dropdownutils;
import com.utilites.webdriverwaitutils;

public class EditUserPage {

    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;

    public EditUserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // =================== WebElements ===================

    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='Edit']")
    private WebElement editoption;

    @FindBy(xpath = "//input[@placeholder='Search Name/Email/Mobile No./Reports to/User Type']")
    private WebElement searchfiels;

    @FindBy(xpath = "//button[@class='btn btn-primary filter-clear-btn mr-1 zc-global-search-btn ng-star-inserted']")
    private WebElement searchbar;

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

    @FindBy(xpath = "//p-dropdown[@placeholder='Select cluster']")
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

    @FindBy(xpath = "(//p-dropdown[@placeholder='Select'])[2]")
    private WebElement leveldr;
    By leveloption = By.xpath("//li[@role='option']");

    @FindBy(xpath = "//button[@id='btnUpdate']")
    private WebElement updatebtn;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement cancelform;

    // toast and popup messages
    By msglocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");
    By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please fill mandatory fields')]");
    @FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
    private WebElement okbtn;

    // Ticket Tag
    @FindBy(xpath = "(//span[contains(text(),'Add')])[2]")
    private WebElement tickettaguseradd;

    @FindBy(xpath = "(//input[@role='combobox'])[5]")
    private WebElement tickettagusersearch;

    By tickettaguseroption = By.xpath("//li[@role='option']");

    @FindBy(xpath = "(//span[contains(text(),'Add')])[3]")
    private WebElement addtikettaguserbtn;

    @FindBy(xpath = "(//button[@class='close'])[2]")
    private WebElement closetagpoptable;

    // ============== Methods ===================

    public void search(String keyword) {
        try {
            WebElement searchBox = wait.waitForVisibility(searchfiels);
            searchBox.clear();
            searchBox.sendKeys(keyword);
            wait.waitForClickability(searchbar).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", searchbar);
        }
    }

 
    public void clickeditoption() {
        try {
            wait.waitForVisibility(editoption);
            js.executeScript("arguments[0].scrollIntoView(true);", editoption);
            wait.waitForClickability(editoption).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", editoption);
        }
    }

    public void edituserdetails(String fname, String mname, String lname, String email, String phone, String userid,
                                String region, String cluster, String location, String operatingUnit, String role,
                                String department, String designation, String level) {

        wait.waitForEnterText(TxtFirstname, fname);
        wait.waitForEnterText(TxtMiddlename, mname);
        wait.waitForEnterText(TxtLastname, lname);
        wait.waitForEnterText(TxtEmail, email);
        wait.waitForEnterText(TxtPhoneNumber, phone);
        wait.waitForEnterText(useridtxt, userid);

        Dropdownutils.selectbyvisibletextlistretry(driver, regiondrp, regionoptions, region);
        Dropdownutils.selectbyvisibletextlistretry(driver, clusterdrp, clusteroptions, cluster);
        Dropdownutils.selectbyvisibletextlistretry(driver, locationdrp, locatiooptions, location);
        Dropdownutils.selectbyvisibletextlistretry(driver, operatingunitdrp, operatingoptions, operatingUnit);
        Dropdownutils.selectbyvisibletextlistretry(driver, roledrp, roleoptions, role);
        Dropdownutils.selectbyvisibletextlistretry(driver, depatmentdrp, depatoptions, department);
        Dropdownutils.selectbyvisibletextlistretry(driver, designationdrp, designoptions, designation);
        Dropdownutils.selectbyvisibletextlistretry(driver, leveldr, leveloption, level);
    }

    public boolean selectRadioButtonOption(String labelText, String optionText) {
        try {
            String labelxpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText + "')]]";
            WebElement radioGroup = driver.findElement(By.xpath(labelxpath));
            List<WebElement> options = radioGroup.findElements(By.xpath(".//label[contains(@class,'custom-radio')]"));

            for (WebElement option : options) {
                String label = option.getText().trim();
                if (label.equalsIgnoreCase(optionText.trim())) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                    WebElement radioBtn = option.findElement(By.tagName("input"));
                    if (!radioBtn.isSelected()) {
                        try {
                            radioBtn.click();
                        } catch (Exception e) {
                            js.executeScript("arguments[0].click();", option);
                        }
                    }
                    return radioBtn.isSelected();
                }
            }
        } catch (Exception e) {
            System.out.println("Radio error: " + e.getMessage());
        }
        return false;
    }

    public void clickupdatebutton() {
        wait.waitForClickability(updatebtn).click();
    }

    public void cancelform() {
        try {
            wait.waitForClickability(cancelform).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", cancelform);
        }
    }

    public void addtickettaguser(String input, String expected) {
        try {
            wait.waitForClickability(tickettaguseradd).click();
            Dropdownutils.selectFromAutoSuggest(driver, tickettagusersearch, tickettaguseroption, input, expected);
            wait.waitForClickability(addtikettaguserbtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", addtikettaguserbtn);
        }
    }

    public void closepoptag() {
        wait.waitForClickability(closetagpoptable).click();
    }

    public boolean handlepopup() {
        try {
            WebElement popbtn = wait.waitForVisibilityBy(popupMessageLocator);
            if (popbtn != null && popbtn.isDisplayed()) {
                WebElement popokbtn = wait.waitForClickability(okbtn);
                popokbtn.click();
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    public String getvalidationmessage() {
        try {
            List<WebElement> popups = driver.findElements(popupMessageLocator);
            for (WebElement popup : popups) {
                if (popup.isDisplayed()) {
                    handlepopup();
                    return popup.getText().trim();
                }
            }

            List<WebElement> messageElements = wait.waitForAllElementsVisible(msglocator);
            for (WebElement msg : messageElements) {
                if (msg.isDisplayed()) {
                    return msg.getText().trim();
                }
            }
        } catch (Exception e) {
            System.out.println("Validation message error: " + e.getMessage());
        }
        return "No Message Displayed";
    }
    public void clearserach() {
        try {
            WebElement searchBox = wait.waitForVisibility(searchfiels);
            searchBox.clear();
            searchBox.sendKeys(Keys.BACK_SPACE);
            wait.waitForClickability(searchbar).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].value='';", searchfiels);
            js.executeScript("arguments[0].click();", searchbar);
        }
    }

}
