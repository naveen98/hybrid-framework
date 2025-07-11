package apollocmspages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.webdriverwaitutils;

public class CmsNavigatetoPineLabsIssuePages {

	WebDriver driver;
	webdriverwaitutils wait;

	public CmsNavigatetoPineLabsIssuePages(WebDriver driver) {

		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
	private WebElement sidebarbtn;
	@FindBy(xpath = "//span[normalize-space()='Pine Lab Issues']")
	private WebElement pinelabissue;
	@FindBy(xpath = "//h2[normalize-space()='Pine Lab Issues']")
	private WebElement pinelabtextdisplay;

	public void navigatetopinelab() {
		wait.waitForClickability(sidebarbtn).click();
		wait.waitForClickability(pinelabissue).click();

	}

	public boolean istextdisplayed() {

		try {
			WebElement text = wait.waitForVisibility(pinelabtextdisplay);
			return text.isDisplayed();

		} catch (Exception e) {
			System.out.println("Not displayed");

		}
		return false;

	}

}
