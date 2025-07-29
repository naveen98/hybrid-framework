package adspages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Datepickutils;
import com.utilites.TimePickersUtil;
import com.utilites.webdriverwaitutils;

public class AddBasicInformationDetailsPage {

    WebDriver driver;
    webdriverwaitutils wait;
    JavascriptExecutor js;
    public Datepickutils dt;
    TimePickersUtil timepicker;
    

    public AddBasicInformationDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        this.dt = new Datepickutils(driver);
        this.timepicker=new TimePickersUtil(driver);
        
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//i[@class='icon-plus ng-star-inserted']") private WebElement addbutton;
    @FindBy(xpath = "//input[@id='name']") private WebElement txtname;
    
	@FindBy(xpath = "//li[@id='menu-li-led-campaigns']")
	private WebElement clickonledcampaign;
	
    @FindBy(xpath = "(//button[@type='button'])[1]") private WebElement clicknextbutton;
    
  

    private By Starttime=By.xpath("(//input[@type='time'])[1]");
  //  @FindBy(xpath = "(//input[@class='form-control ng-valid ng-star-inserted ng-touched ng-dirty'])[1]")private WebElement starttime;
    
    

    private By Endtime=By.xpath("(//input[@type='time'])[2]");

   // @FindBy(xpath = "(//input[@class='form-control ng-pristine ng-valid ng-star-inserted ng-touched'])[1]")private WebElement Endtime;
    @FindBy(xpath = "//textarea[@id='display_text']") private WebElement textdisplay;


  
    private By startdateopen = By.xpath("(//i[@class='icon-calendar ui-state-default ng-star-inserted'])[1]");
    private By enddateopen = By.xpath("(//i[@class='icon-calendar ui-state-default ng-star-inserted'])[2]");
    private By Monthtextlocator = By.xpath("//button[@class='current ng-star-inserted']");
    private By yeartextlocator = By.xpath("//button[@class='current']");
    private By startnext = By.xpath("//button[@class='next']");
    private By startprevious = By.xpath("//button[@class='previous']");
    private By alldates = By.xpath("//td[@role='gridcell']");
    
    @FindBy(xpath = "//div[@class='invalid-feedback ng-star-inserted']")private List<WebElement> validationmessages;
    
    

    // Action Method to Click Add Basic Info Button
    public void clickonledscreen() {
    	try {
    		WebElement ele=wait.waitForVisibility(clickonledcampaign);
    		ele.click();
    	}
    	catch (Exception e) {
           System.out.println("exception  "+e.getMessage());
    	}
    	
    }
    public void addcreatebutton() {
        try {
        	WebElement addbtn=wait.waitForVisibility(addbutton);
        	if(addbtn!=null&&addbtn.isDisplayed()) {
                wait.waitForClickability(addbtn).click();
        	}
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", addbutton);
        }
    }

    
    public void entertext(String name) {
    	try {
    		wait.waitForEnterText(txtname, name);
    		
    	}catch (Exception e) {
              System.out.println("Exception Enter Name:"+e.getMessage());

		}
    	
    	
    }
    // Start date Date Picker
    public void startDate(String month, String year, String date) {
        try {
            wait.waitForClickabilityby(startdateopen).click();
            dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error selecting start date: " + e.getMessage());
        }
    }

    //End date
    public void endDate(String month, String year, String date) {
        try {
            wait.waitForClickabilityby(enddateopen).click();
            dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error selecting end date: " + e.getMessage());
        }
    }    
    
    public void setStartTime(String time) {
        timepicker.setTime(Starttime, time);
    }

    public void setEndTime(String time) {
        timepicker.setTime(Endtime, time);
    }

    public void displayText(String text) {
        try {
            wait.waitForEnterText(textdisplay, text);
        } catch (Exception e) {
            System.out.println("Error entering display text: " + e.getMessage());
        }
    }
    
    public void clicknext() {
    	WebElement nextbtn=wait.waitForVisibility(clicknextbutton);
    	
    	try {
    		if(nextbtn!=null&&nextbtn.isDisplayed()) {
        		wait.waitForClickability(nextbtn).click();
    		}
    	}
    	catch (Exception e) {

                  System.out.println("Exception ");
		}
    }
    public List<String> getValidationMessages() {
        List<String> messages = new ArrayList<>();

        try {
            List<WebElement> elements = wait.waitForAllElementsVisible(validationmessages);

            for (WebElement el : elements) {
                if (el != null && el.isDisplayed()) {
                    messages.add(el.getText().trim());
                } else {
                    System.out.println("Validation message not displayed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while fetching validation messages: " + e.getMessage());
        }

        return messages;
    }

}
