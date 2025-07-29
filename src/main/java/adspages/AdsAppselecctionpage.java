package adspages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.utilites.webdriverwaitutils;

public class AdsAppselecctionpage {

	WebDriver driver;
	webdriverwaitutils wait;

	public AdsAppselecctionpage(WebDriver driver) {

		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//strong[normalize-space()='Apollo Digital Signage'])[1]")
	private WebElement appselection;
	@FindBy(xpath = "(//span[contains(text(),'Dashboard')])[1]")
	private WebElement dashboardtext;
	
	public void appselection() {
		WebElement appselect=wait.waitForVisibility(appselection);
		try {
			if(appselect!=null&&appselect.isDisplayed()) {
				
				wait.waitForClickability(appselect).click();

			}
		}
		catch (Exception e) {
			System.out.println("Exception "+ e.getMessage());
		}
	}

	public void isTitleAsExpected(String expectedTitle) {
		
		try {
			WebElement text=wait.waitForVisibility(dashboardtext);
			if(text!=null&&text.isDisplayed()) {
	
			String actualTitle = driver.getTitle();
			System.out.println("Actual page title: " + actualTitle);
			System.out.println(" Expected title " +   expectedTitle);
			Assert.assertEquals(actualTitle, expectedTitle, "Page Title Does Not match!");
			}
			else {
				System.out.println("Not displayed");
			}
		} catch (Exception e) {
            System.out.println("excepton while verify title"+e.getMessage());
    		Assert.fail("Title verification failed due to an exception.");

		}
	}
}
