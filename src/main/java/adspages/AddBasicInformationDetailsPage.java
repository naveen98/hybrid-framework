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
        this.timepicker = new TimePickersUtil(driver);
        PageFactory.initElements(driver, this);
    }

    // WebElements
    @FindBy(xpath = "//i[@class='icon-plus ng-star-inserted']")
    private WebElement addbutton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement txtname;

    @FindBy(xpath = "//li[@id='menu-li-led-campaigns']")
    private WebElement clickonledcampaign;

    @FindBy(xpath = "(//button[@type='button'])[1]")
    private WebElement clicknextbutton;

    @FindBy(xpath = "//textarea[@id='display_text']")
    private WebElement textdisplay;

    @FindBy(xpath = "//div[@class='invalid-feedback ng-star-inserted']")
    private List<WebElement> validationmessages;

    // Locators
    private By Starttime = By.xpath("(//input[@type='time'])[1]");
    private By Endtime = By.xpath("(//input[@type='time'])[2]");
    private By startdateopen = By.xpath("(//i[@class='icon-calendar ui-state-default ng-star-inserted'])[1]");
    private By enddateopen = By.xpath("(//i[@class='icon-calendar ui-state-default ng-star-inserted'])[2]");
    private By Monthtextlocator = By.xpath("//button[@class='current ng-star-inserted']");
    private By yeartextlocator = By.xpath("//button[@class='current']");
    private By startnext = By.xpath("//button[@class='next']");
    private By startprevious = By.xpath("//button[@class='previous']");
    private By alldates = By.xpath("//td[@role='gridcell']");

    // Click on LED Campaign section
    public void clickonledscreen() {
        try {
         	js.executeScript("window.scrollBy(0, -500);");
            WebElement ledsection = wait.waitForVisibility(clickonledcampaign);
            if (ledsection != null && ledsection.isDisplayed()) {
           
            	//js.executeScript("arguments[0].scrollIntoView(true);", ledsection);
                wait.waitForClickability(ledsection).click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", clickonledcampaign);
        }
    }

    // Click Add Button
    public void addcreatebutton() {
        try {
            WebElement addbtn = wait.waitForVisibility(addbutton);
            if (addbtn != null && addbtn.isDisplayed()) {
                wait.waitForClickability(addbtn).click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", addbutton);
        }
    }

    // Enter Campaign Name
    public void entertext(String name) {
        try {
            wait.waitForEnterText(txtname, name);
        } catch (Exception e) {
            System.out.println("Exception while entering name: " + e.getMessage());
        }
    }

    // Start Date Picker
    public void startDate(String month, String year, String date) {
        try {
            wait.waitForClickabilityby(startdateopen).click();
            dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
        } catch (Exception e) {
            System.out.println("Error selecting start date: " + e.getMessage());
        }
    }

    // End Date Picker
    public void endDate(String month, String year, String date) {
        try {
            wait.waitForClickabilityby(enddateopen).click();
            dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
          
        } catch (Exception e) {
            System.out.println("Error selecting end date: " + e.getMessage());
        }
    }

    // Set Start Time
    public void setStartTime(String time) {
        try {
            timepicker.setTime(Starttime, time);
        } catch (Exception e) {
            System.out.println("Error setting start time: " + e.getMessage());
        }
    }

    // Set End Time
    public void setEndTime(String time) {
        try {
            timepicker.setTime(Endtime, time);
        } catch (Exception e) {
            System.out.println("Error setting end time: " + e.getMessage());
        }
    }

    // Enter Display Text
    public void displayText(String text) {
        try {
            wait.waitForEnterText(textdisplay, text);
        } catch (Exception e) {
            System.out.println("Error entering display text: " + e.getMessage());
        }
    }

    // Click Next Button
    public void clicknext() {
        try {
            WebElement nextbtn = wait.waitForVisibility(clicknextbutton);
            if (nextbtn != null && nextbtn.isDisplayed()) {
                wait.waitForClickability(nextbtn).click();
            }
        } catch (Exception e) {
            System.out.println("Error clicking next button: " + e.getMessage());
        }
    }

    // Get all validation messages displayed
    public List<String> getValidationMessages() {
        List<String> messages = new ArrayList<>();
        try {
            if (validationmessages != null && !validationmessages.isEmpty()) {
                wait.waitForAllElementsVisible(validationmessages);

                for (WebElement el : validationmessages) {
                    if (el != null && el.isDisplayed()) {
                        String text = el.getText().trim();
                        if (!text.isEmpty()) {
                            messages.add(text);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching validation messages: " + e.getMessage());
        }
        return messages;
    }

}
