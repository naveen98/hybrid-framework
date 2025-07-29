package adspages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Datepickutils;
import com.utilites.TimePickersUtil;
import com.utilites.Totalcounts;
import com.utilites.webdriverwaitutils;

public class AdsTotalcountspages {
	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;
	Datepickutils dt;
	TimePickersUtil timepicker;
	Totalcounts tc;
	

	public AdsTotalcountspages(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
		this.tc=new Totalcounts(driver);
		
	}
	By totalcampaign = By.xpath("//label[text()='Campaigns']/following-sibling::span");
	By totaldraft = By.xpath("//label[text()='Draft']/following-sibling::span");
	By totalpending = By.xpath("//label[text()='Pending']/following-sibling::span");
	By totalapproved = By.xpath("//label[text()='Approved']/following-sibling::span");
	By totalrejected = By.xpath("//label[text()='Rejected']/following-sibling::span");

	@FindBy(xpath="(//div[@class='dashboard-counts-div'])[1]")private WebElement totalcampaigns;
	

     
	public int getTotalCampaignCount() {
	    try {
	        WebElement totalCounts = wait.waitForVisibility(totalcampaigns);

	        if (totalCounts != null && totalCounts.isDisplayed()) {
	            return tc.getcount(totalcampaign);
	        } else {
	            System.out.println("Total campaigns  not visible.");
	            return 0;
	        }

	    } catch (Exception e) {
	        System.out.println(" Exception while getting total campaign count: " + e.getMessage());
	        return 0;
	    }
	}


	public int getDraftCount() {
	    try {
	        WebElement draftCountElement = wait.waitForVisibilityBy(totaldraft);

	        if (draftCountElement != null && draftCountElement.isDisplayed()) {
	            return tc.getcount(totaldraft);
	        } else {
	            System.out.println(" Draft count element is not visible.");
	            return 0;
	        }

	    } catch (Exception e) {
	        System.out.println("Exception while getting draft count: " + e.getMessage());
	        return 0;
	    }
	}

	public int getPendingCount() {
	    try {
	        WebElement pendingCountElement = wait.waitForVisibilityBy(totalpending);

	        if (pendingCountElement != null && pendingCountElement.isDisplayed()) {
	            return tc.getcount(totalpending);
	        } else {
	            System.out.println(" Pending count element is not visible.");
	            return 0;
	        }

	    } catch (Exception e) {
	        System.out.println(" Exception while getting pending count: " + e.getMessage());
	        return 0;
	    }
	}


    public int getApprovedCount() {
        return tc.getcount(totalapproved);
    }

    public int getRejectedCount() {
        return tc.getcount(totalrejected);
    }
	
	
	
	
}
