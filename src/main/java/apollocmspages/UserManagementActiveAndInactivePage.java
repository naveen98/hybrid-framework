package apollocmspages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class UserManagementActiveAndInactivePage {

    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;

    public UserManagementActiveAndInactivePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // Static locators
    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='Inactive']")
    private WebElement clickInactiveOption;

    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='Active']")
    private WebElement clickActiveOption;

    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement popOkBtn;

    @FindBy(xpath = "//input[@placeholder='Search Name/Email/Mobile No./Reports to/User Type']")
    private WebElement searchField;

    @FindBy(xpath = "//button[contains(@class,'zc-global-search-btn')]")
    private WebElement searchButton;

    @FindBy(xpath = "(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")
    private WebElement searchClear;

    @FindBy(xpath = "//div[contains(text(),'Before Inactivating') or contains(text(),'Do you want to make user active?')]")
    private WebElement popupMessage;

    By toastLocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");
    By statusLocator = By.xpath("//span[@title='Active' or @title='Inactive']");

    // --- Methods ---

    public void searchUser(String username) {
        try {
            wait.waitForEnterText(searchField, username);
            wait.waitForClickability(searchButton).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", searchButton);
        }
    }

    public String getCurrentStatus(String username) {
        searchUser(username);
        WebElement statusElement = wait.waitForVisibilityBy(statusLocator);
        return statusElement.getAttribute("title").trim();  // returns "Active" or "Inactive"
    }

    public void ToInactive() {
        try {
            wait.waitForClickability(clickInactiveOption).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", clickInactiveOption);
        }
        handlePopup();
    }

    public void ToActive() {
        try {
            wait.waitForClickability(clickActiveOption).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", clickActiveOption);
        }
        handlePopup();
    }

    public void handlePopup() {
        try {
            WebElement popup = wait.waitForVisibility(popupMessage);
            System.out.println("Popup Message: " + popup.getText().trim());

            try {
                wait.waitForClickability(popOkBtn).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", popOkBtn);
            }
        } catch (Exception e) {
            System.out.println("No confirmation popup displayed.");
        }
    }

    public String getToastMessage() {
        String message = "";
        try {
            WebElement toast = wait.waitForVisibilityBy(toastLocator);
            if (toast != null && toast.isDisplayed()) {
                message = toast.getText().trim();
            }
        } catch (Exception e) {
            System.out.println("Toast message not found.");
        }
        return message;
    }

  /*  public void clearSearch() {
        try {
            wait.waitForClickability(searchClear).click();
            wait.waitForClickability(searchButton).click(); // Refreshes the list
        } catch (Exception e) {
            System.out.println("Clear search failed: " + e.getMessage());
        }
    }*/
}
