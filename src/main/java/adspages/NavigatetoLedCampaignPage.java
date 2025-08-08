package adspages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.utilites.webdriverwaitutils;

public class NavigatetoLedCampaignPage {
	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;

	public NavigatetoLedCampaignPage(WebDriver driver) {

		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//li[@id='menu-li-led-campaigns']")
	private WebElement clickonledcampaign;

	@FindBy(xpath = "//formly-group[@class='row dashboard-counts-row zc-fg-no-margin mx-0 ng-star-inserted']")
	private WebElement rowdislayed;

	public void clickonledcampaign() {

		try {
			WebElement ledcamp=wait.waitForVisibility(clickonledcampaign);
			if(ledcamp!=null&&ledcamp.isDisplayed()) {
			wait.waitForClickability(ledcamp).click();
			}
			
		} catch (Exception e) {

			js.executeScript("arguments[0].click();", clickonledcampaign);

		}
	}

	public void istitleExpected(String expectedtitle) {
		try {
			WebElement row = wait.waitForVisibility(rowdislayed);
			if (row != null && row.isDisplayed()) {

				String actualtitle = driver.getTitle();
				System.out.println("Actual title : " + actualtitle);

				Assert.assertEquals(actualtitle, expectedtitle, " Page title does not match!");
				System.out.println("Expected title "  +  expectedtitle);

			} else {
				System.out.println("not diaplayed");
				Assert.fail();
			}
		} catch (Exception e) {
			System.out.println("Exception while verifying title: " + e.getMessage());

			Assert.fail("Title verification failed due to an exception.");
		}

	}

}
