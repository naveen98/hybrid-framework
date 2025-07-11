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

public class UsermanagementAccessType {

	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;

	public UsermanagementAccessType(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@title='Access Type']")
	private WebElement accessTypeOption;

	@FindBy(xpath = "//button[@id='dialog-okay-btn']")
	private WebElement popOkBtn;

	@FindBy(xpath = "//input[@placeholder='Search Name/Email/Mobile No./Reports to/User Type']")
	private WebElement searchField;

	@FindBy(xpath = "//button[@class='btn btn-primary filter-clear-btn mr-1 zc-global-search-btn ng-star-inserted']")
	private WebElement searchButton;

	@FindBy(xpath = "(//span[@class='filter-searchclear icon icon-close ng-star-inserted'])[1]")
	private WebElement searchClear;

	@FindBy(xpath = "//div[contains(text(),'All data will be lost. Do you want to continue?')]")
	private WebElement popupMessage;

	@FindBy(xpath = "(//span[contains(text(),'Add')])[2]")
	private WebElement accessTypeAddBtn;

	@FindBy(xpath = "//p-multiselect[@placeholder='Select Access Type']")
	private WebElement accessTypeDropdown;

	By dropdownOptions = By.xpath("//div[contains(@class,'ui-multiselect-panel')]//li[contains(@class,'ui-multiselect-item')]");
	By selectedOptions = By.xpath("//div[contains(@class,'ui-multiselect-panel')]//li[contains(@class,'ui-state-highlight')]");

	@FindBy(xpath = "//button[@id='btnAdd']")
	private WebElement clickAddSubmit;

	@FindBy(xpath = "(//button[@class='close'])[1]")
	private WebElement finalPopupClose;

	@FindBy(xpath = "(//button[@class='close'])[2]")
	private WebElement insidePopupClose;

	By toastMsgLocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

	// Methods 
	

	public void closeinsidepop() {
		try {
			wait.waitForClickability(insidePopupClose).click();
			wait.waitForClickability(finalPopupClose).click();
			
		}catch (Exception e) {
			js.executeScript("arguments[0].click();", insidePopupClose);
			js.executeScript("arguments[0].click();", finalPopupClose);
		}
	}

	public void search(String username) {
	
		try {
			wait.waitForEnterText(searchField, username);
            wait.waitForClickability(searchButton).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", searchButton);
		}
	}

	public void accessType(String dropdownValue) {
	try {
		WebElement icon = wait.waitForVisibility(accessTypeOption);
		wait.waitForClickability(icon).click();
	} catch (Exception e) {
		js.executeScript("arguments[0].click();",accessTypeOption);
	}

		wait.waitForClickability(accessTypeAddBtn).click();

		try {
			wait.waitForClickability(accessTypeDropdown).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", accessTypeDropdown);
		}

		// Check if already selected
		List<WebElement> selected = driver.findElements(selectedOptions);
		
		if (selected != null && !selected.isEmpty()) {
			System.out.println("Access Type already selected, closing popup.");
			try {
				wait.waitForClickability(insidePopupClose).click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", insidePopupClose);
			}
			try {
				wait.waitForClickability(finalPopupClose).click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", finalPopupClose);
			}
			return;
		}

		try {
			Dropdownutils.selectCheckboxFromDropdown(driver, accessTypeDropdown, dropdownOptions, dropdownValue);
		} catch (Exception e) {
			System.out.println("Dropdown selection failed: " + e.getMessage());
		}

		try {
			wait.waitForClickability(clickAddSubmit).click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", clickAddSubmit);
		}

		wait.waitForVisibility(finalPopupClose);
		wait.waitForClickability(finalPopupClose).click();
	}

	public String getToastMessage() {
		String message = "";
		try {
			List<WebElement> toastElements = wait.waitForAllElementsVisible(toastMsgLocator);
			for (WebElement msgElement : toastElements) {
				if (msgElement.isDisplayed()) {
					message = msgElement.getText().trim();
					System.out.println("Toast message: " + message);

					if (message.contains("saved successfully")) {
						return message;
					} else if (message.contains("All data will be lost")) {
						js.executeScript("window.scrollTo(0, 0);");
						handlePopup();
						return "Mandatory fields missing";
					} else {
						return message;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Toast fetch error: " + e.getMessage());
		}
		return "No Message Displayed";
	}

	public boolean handlePopup() {
		try {
			WebElement popup = wait.waitForVisibility(popupMessage);
			if (popup != null && popup.isDisplayed()) {
				try {
					wait.waitForClickability(popOkBtn).click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", popOkBtn);
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println("Popup not displayed: " + e.getMessage());
		}
		return false;
	}
}
