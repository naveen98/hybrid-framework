package apollocmspages;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.utilites.webdriverwaitutils;

public class UserManagementResetPasswordPage {

    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;

    public UserManagementResetPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='Reset Password']")
    private WebElement resetPasswordIcon;

    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")
    private WebElement popOkBtn;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popCancelBtn;

    @FindBy(xpath = "//input[@placeholder='Search Name/Email/Mobile No./Reports to/User Type']")
    private WebElement searchField;

    @FindBy(xpath = "//button[@class='btn btn-primary filter-clear-btn mr-1 zc-global-search-btn ng-star-inserted']")
    private WebElement searchButton;

    @FindBy(xpath = "(//div[normalize-space()='Please fill mandatory fields'])[1]")
    private WebElement popupMessage;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement newPassword;

    @FindBy(xpath = "//input[@id='confirmPassword']")
    private WebElement confirmPassword;

    @FindBy(xpath = "(//button[@type='button'])[3]")
    private WebElement saveBtn;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement popbox;

    By toastLocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

    public void search(String username) {
        try {
            wait.waitForVisibility(searchField);
            searchField.clear();
            searchField.sendKeys(username);
            wait.waitForClickability(searchButton).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", searchButton);
        }
    }

    public void resetoption() {
        try {
            wait.waitForClickability(resetPasswordIcon).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", resetPasswordIcon);
        }
    }

    public void enterPassword(String newPass, String confirmPass) {
        try {
            wait.waitForTextToBePresentsendkeys(newPassword, newPass);
            wait.waitForTextToBePresentsendkeys(confirmPassword, confirmPass);
        } catch (Exception e) {
            System.out.println("Password entry failed: " + e.getMessage());
        }
    }

    public void savebtn() {
        try {
            wait.waitForClickability(saveBtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", saveBtn);
        }
    }

    public String getToastMessage() {
        String message = "";
        try {
            List<WebElement> toastMessages = wait.waitForAllElementsVisible(toastLocator);
            if (toastMessages != null && !toastMessages.isEmpty()) {
                for (WebElement toast : toastMessages) {
                    if (toast.isDisplayed()) {
                        message = toast.getText().trim();
                        System.out.println("Toast Message: " + message);

                        switch (message) {
                            case "Password updated sucessfully":
                            case "Your new password can not be same as last 3 passwords, please choose a new password":
                            case "New Password and Confirm Password did not match, please verify.Password should be between 6 and 25 characters, 1 Min Upper Case, 1 Min Lower Case, 1 Min Digit, 1 Min Special Chars.":
                            case "Password should be between 6 and 25 characters, 1 Min Upper Case, 1 Min Lower Case, 1 Min Digit, 1 Min Special Chars.":
                                return message;

                            case "Please fill mandatory fields":
                                handlePopup();
                                return "Please fill mandatory fields";

                            default:
                                return message;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error toast message: " + e.getMessage());
        }
        return message;
    }

    public boolean handlePopup() {
        try {
            WebElement popup = wait.waitForVisibility(popupMessage);
            if (popup != null && popup.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(popOkBtn);
                WebElement cancelBox = wait.waitForClickability(popbox);
                try {
                    okBtn.click();
                    cancelBox.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", okBtn);
                    js.executeScript("arguments[0].click();", cancelBox);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }

    public void closePopBox() {
        try {
            if (popbox.isDisplayed()) {
                wait.waitForClickability(popbox).click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", popbox);
        }
    }
}
