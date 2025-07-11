package apollocmspages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.utilites.webdriverwaitutils;

public class UserManagementDelete {

    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;

    public UserManagementDelete(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='Delete']")
    private WebElement deleteoption;

    @FindBy(xpath = "//input[@placeholder='Search Name/Email/Mobile No./Reports to/User Type']")
    private WebElement searchfield;

    @FindBy(xpath = "//button[contains(@class,'zc-global-search-btn')]")
    private WebElement searchbar;

    @FindBy(xpath = "(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")
    private WebElement searchclear;

    @FindBy(xpath = "//div[contains(text(),'Before deleting this user please check in Team mapping / Site')]")
    private WebElement popupmessage;

    @FindBy(id = "dialog-okay-btn")
    private WebElement popokbtn;

    @FindBy(id = "dialog-cancel-btn")
    private WebElement popcancelbtn;

    @FindBy(xpath = "//td[normalize-space()='No users found']")
    private WebElement norecordfound;

    By msglocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

    public boolean search(String name) {
        try {
            wait.waitForEnterText(searchfield, name);
            try {
                wait.waitForClickability(searchbar).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", searchbar);
               
            }
 
           return true;

          /*  if (norecordfound.isDisplayed()) {
                return false;
            }*/

        } catch (Exception e) {
            return false;
        }
    }

    public void clearsearch() {
        try {
            wait.waitForClickability(searchclear).click();
            wait.waitForClickability(searchbar).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", searchbar);
        }
    }

    public void Deleteoption() {
        try {
            js.executeScript("window.scrollTo(document.body.scrollWidth, 0);");
            wait.waitForClickability(deleteoption).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", deleteoption);
        }
    }

    public String handlepopup() {
        String message = "";
        try {
            WebElement popuptext = wait.waitForVisibility(popupmessage);
            if (popuptext != null && popuptext.isDisplayed()) {
                message = popuptext.getText().trim();
                System.out.println("Popup Message: " + message);
                try {
                    wait.waitForClickability(popokbtn).click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", popokbtn);
                }
            }
        } catch (Exception e) {
            System.out.println("Popup not found.");
        }
        return message;
    }

    public void cancelpopup() {
        try {
            wait.waitForClickability(popcancelbtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", popcancelbtn);
        }
    }

    public String getToastMessage() {
        String message = "";
        try {
            WebElement toast = wait.waitForVisibilityBy(msglocator);
            if (toast != null && toast.isDisplayed()) {
                message = toast.getText().trim();
                System.out.println("Toast Message: " + message);
            }
        } catch (Exception e) {
            System.out.println("Toast message not displayed.");
        }
        return message;
    }
}
