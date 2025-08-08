package adspages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utilites.Datepickutils;
import com.utilites.Dropdownutils;
import com.utilites.TimePickersUtil;
import com.utilites.webdriverwaitutils;

public class AdsEditCampaign {

	WebDriver driver;
	webdriverwaitutils wait;
	JavascriptExecutor js;
	Datepickutils dt;
	TimePickersUtil timepicker;

	  public AdsEditCampaign(WebDriver driver) {
	 	this.driver = driver;
		this.wait = new webdriverwaitutils(driver);
		this.js = (JavascriptExecutor) driver;
		this.dt = new Datepickutils(driver);
		this.timepicker = new TimePickersUtil(driver);
		PageFactory.initElements(driver, this);
	}

	// ==== Basic Elements ====
	@FindBy(xpath = "(//span[@class='zc-status'])[1]")
	private WebElement clickdraft;

	@FindBy(xpath = "//button[@id='btnEdit']")
	private WebElement editoption;

	@FindBy(xpath = "//input[@id='name']")
	private WebElement name;

	@FindBy(xpath = "//textarea[@id='display_text']")
	private WebElement textdisplay;

	@FindBy(xpath = "//input[@placeholder='Search by Name / Uploaded by / Status']")
	private WebElement searchinput;

	@FindBy(xpath = "(//button[@type='button'])[1]")
	private WebElement searchbar;

	// ==== Date Pickers ====
	private By startdateopen = By.xpath("(//i[@class='icon-calendar ui-state-default ng-star-inserted'])[1]");
	private By enddateopen = By.xpath("(//i[@class='icon-calendar ui-state-default ng-star-inserted'])[2]");
	private By Monthtextlocator = By.xpath("//button[@class='current ng-star-inserted']");
	private By yeartextlocator = By.xpath("//button[@class='current']");
	private By startnext = By.xpath("//button[@class='next']");
	private By startprevious = By.xpath("//button[@class='previous']");
	private By alldates = By.xpath("//td[@role='gridcell']");

	// ==== Time Pickers ====
	private By Starttime = By.xpath("(//input[@type='time'])[1]");
	private By Endtime = By.xpath("(//input[@type='time'])[2]");

	// ==== Dropdowns ====
	@FindBy(xpath = "(//p-dropdown[@placeholder='Select font'])[1]")
	private WebElement FontDropdown;

	@FindBy(xpath = "(//p-dropdown[@placeholder='Select font'])[2]")
	private WebElement Fontsizedropdown;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select animation speed']")
	private WebElement Animationspeeddropdown;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select stay time']")
	private WebElement staytimedropdown;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select style']")
	private WebElement Staystyledropdown;

	@FindBy(xpath = "//p-dropdown[@placeholder='Select program']")
	private WebElement programdropdown;

	@FindBy(xpath = "(//p-dropdown[@placeholder='Select dimensions'])[2]")
	private WebElement dimensiondropdown;

	private By Fontoptions = By.xpath("//li[@role='option']");
	private By FontSizeptions = By.xpath("//li[@role='option']");
	private By Animationoptions = By.xpath("//li[@role='option']");
	private By Staytimeoptions = By.xpath("//li[@role='option']");
	private By Styleoptions = By.xpath("//li[@role='option']");
	private By programoptions = By.xpath("//li[@role='option']");
	private By dimensionoption = By.xpath("//li[@role='option']");

	@FindBy(xpath = "(//button[@id='btnNext'])[2]")
	private WebElement ledstylenextbutton;

	@FindBy(xpath = "//button[@id='btnsubmit']")
	private WebElement Submitbutton;

	@FindBy(xpath = "//button[@id='btnUpdate']")
	private WebElement updatebutton;
	
	@FindBy(xpath = "//button[@id='btnCancel']")
	private WebElement cancelbutton;
	
	@FindBy(xpath = "//button[@id='dialog-okay-btn']")
	private WebElement popokbtn;
	
	@FindBy(xpath = "//button[@id='dialog-cancel-btn']")
	private WebElement popcancelbtn;

	// ==========Popup===========

	By msglocator = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'toast-title')]");

	By popupMessageLocator = By
			.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please fill mandatory fields");

	@FindBy(xpath = "//td[contains(text(),' No LED Campaigns Found ')]")private WebElement norecordfound;
	
	@FindBy(xpath = "//i[@class='icon-block7 ng-star-inserted']")private WebElement clickonledcampaignmain;
	
	
	
	// ==== Methods ====

	
	public void clickmainled() {
		
		try {
			WebElement mainled=wait.waitForVisibility(clickonledcampaignmain);
			if(mainled!=null&&mainled.isDisplayed()) {
				wait.waitForClickability(mainled).click();
			}
			
		}catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}
	}
	public boolean searchAndSelect(String search) {
	    try {
	        WebElement searchField = wait.waitForVisibility(searchinput);

	        if (searchField.isDisplayed()) {
	            wait.waitForEnterText(searchField, search);
	            wait.waitForClickability(searchbar).click();

	            System.out.println("Record found for: " + search);
	            return true;
	        } else {
	        	
	            System.out.println("Search field is not displayed.");
	            return false;
	        }

	    } catch (Exception e) {
	        System.out.println("Search exception: " + e.getMessage());
	        return false;
	    }
	}



	public void clickEditButton() {
	    try {
	        WebElement draftBtn = wait.waitForVisibility(clickdraft);
	        if (draftBtn != null && draftBtn.isDisplayed()) {
	            wait.waitForClickability(draftBtn).click();
	            System.out.println(" Draft button clicked successfully.");
	        } else {
	            System.out.println(" Draft button not visible.");
	        }

	        WebElement editBtn = wait.waitForVisibility(editoption);
	        if (editBtn != null && editBtn.isDisplayed()) {
	            wait.waitForClickability(editBtn).click();
	            System.out.println(" Edit button clicked successfully.");
	        } else {
	            System.out.println(" Edit button not visible.");
	        }

	    } catch (Exception e) {
	        System.out.println(" Edit click exception: " + e.getMessage());
	    }
	}


	public void editText(String text) {
		try {
			wait.waitForEnterText(name, text);
		} catch (Exception e) {
			System.out.println("Text edit exception: " + e.getMessage());
		}
	}

	public void startDate(String month, String year, String date) {
		try {
			wait.waitForClickabilityby(startdateopen).click();
			dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
		} catch (Exception e) {
			System.out.println("Error selecting start date: " + e.getMessage());
		}
	}

	public void endDate(String month, String year, String date) {
		try {
			wait.waitForClickabilityby(enddateopen).click();
			dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
		} catch (Exception e) {
			System.out.println("Error selecting end date: " + e.getMessage());
		}
	}

	public void setStartTime(String time) {
		timepicker.setTime(Starttime, time);
	}

	public void setEndTime(String time) {
		wait.waitForPresence(Endtime);
		timepicker.setTime(Endtime, time);
	}

	public void textDisplays(String text) {
		try {
			wait.waitForEnterText(textdisplay, text);
		} catch (Exception e) {
			System.out.println("Text display exception: " + e.getMessage());
		}
	}

	public void editLedStyles(String font, String fontSize, String animation, String stayTime, String style,
			String program, String dimension) {
		try {
			Dropdownutils.selectbyvisibletextlistretry(driver, FontDropdown, Fontoptions, font);
			Dropdownutils.selectbyvisibletextlistretry(driver, Fontsizedropdown, FontSizeptions, fontSize);
			Dropdownutils.selectbyvisibletextlistretry(driver, Animationspeeddropdown, Animationoptions, animation);
			Dropdownutils.selectbyvisibletextlistretry(driver, staytimedropdown, Staytimeoptions, stayTime);
			Dropdownutils.selectbyvisibletextlistretry(driver, Staystyledropdown, Styleoptions, style);
			Dropdownutils.selectbyvisibletextlistretry(driver, programdropdown, programoptions, program);
			wait.waitForVisibility(dimensiondropdown);
			Dropdownutils.selectbyvisibletextlistretry(driver, dimensiondropdown, dimensionoption, dimension);
		} catch (Exception e) {
			System.out.println("Dropdown selection exception: " + e.getMessage());
		}
	}
//--------------------------------radiobuttons------------------------------------------------
	public boolean selectRadioButtonOption(String labelText, String optionText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		try {
			// label groups
			String labelxpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText+ "')]]";
			WebElement radioGroup = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(labelxpath)));

			List<WebElement> options = radioGroup.findElements(By.xpath(".//label[contains(@class,'custom-radio')]"));
			for (WebElement option : options) {
				String label = option.getText().trim();
				if (label.equalsIgnoreCase(optionText.trim())) {
					js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
					WebElement radiobtn = option.findElement(By.tagName("input"));
					if (!radiobtn.isSelected()) {
						try {
							wait.until(ExpectedConditions.elementToBeClickable(option)).click();
						} catch (Exception e) {
							js.executeScript("arguments[0].click();", option);
						}
					}
					return radiobtn.isSelected();
				}
			}
			System.out.println("Radio option '" + optionText + "' not found under label '" + labelText + "'");
		} catch (Exception e) {
			System.out.println(
					"Exception in selecting radio [" + labelText + " : " + optionText + "]: " + e.getMessage());
		}
		return false;
	}

	public boolean handlepopupOK() {
		try {
			WebElement popbtn = wait.waitForVisibilityBy(popupMessageLocator);
			if (popbtn != null && popbtn.isDisplayed()) {
				WebElement popok = wait.waitForClickability(popokbtn);
				try {
					popok.click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", popok);
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println("Popup not displayed: " + e.getMessage());
		}
		return false;
	}
	

	public boolean handlepopupCancel() {
		try {
			WebElement popbtn = wait.waitForVisibilityBy(popupMessageLocator);
			if (popbtn != null && popbtn.isDisplayed()) {
				WebElement popcancel = wait.waitForClickability(popcancelbtn);
				try {
					popcancel.click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", popcancel);
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println("Popup not displayed: " + e.getMessage());
		}
		return false;
	}

//---------------------ok---------------------------------------
	public String getvalidationmessageok() {
		String message = "";
		try {
			// Check for popup
			List<WebElement> popups = driver.findElements(popupMessageLocator);

			if (!popups.isEmpty()) {
				for (WebElement popup : popups) {
					if (popup.isDisplayed()) {
						message = popup.getText().trim();
						handlepopupOK();
						System.out.println("Popup message: " + message);
						return message;
					}
				}
			}
			
			List<WebElement> messageElements = wait.waitForAllElementsVisible(msglocator);
			if (!messageElements.isEmpty()) {
				for (WebElement msgelement : messageElements) {
					if (msgelement.isDisplayed()) {
						message = msgelement.getText().trim();
						System.out.println("Toast message: " + message);
						switch (message) {
						case "Successfully updated":
							return message;
						case "Please fill mandatory fields":
							return message;
						default:
							return message;
						}
					}
				}
			} else {
				System.out.println("No toast message visible.");
			}
		} catch (Exception e) {
			System.out.println("Error in getvalidationmessage: " + e.getMessage());
		}

		return "No Message Displayed";
	}
	
	
	//------------------------------cancel-------------------------------
		
			public String getvalidationmessagecancel() {
				String message = "";
				try {
					// Check for popup
					List<WebElement> popups = driver.findElements(popupMessageLocator);

					if (!popups.isEmpty()) {
						for (WebElement popup : popups) {
							if (popup.isDisplayed()) {
								message = popup.getText().trim();
								handlepopupCancel();
								System.out.println("Popup message: " + message);
								return message;
							}
						}
				
			
				}
				
			// Check for toast
			List<WebElement> messageElements = wait.waitForAllElementsVisible(msglocator);
			if (!messageElements.isEmpty()) {
				for (WebElement msgelement : messageElements) {
					if (msgelement.isDisplayed()) {
						message = msgelement.getText().trim();
						System.out.println("Toast message: " + message);
						switch (message) {
						case "Successfully updated":
							return message;
						case "Please fill mandatory fields":
							return message;
						default:
							return message;
						}
					}
				}
			} else {
				System.out.println("No toast message visible.");
			}
		} catch (Exception e) {
			System.out.println("Error in getvalidationmessage: " + e.getMessage());
		}

		return "No Message Displayed";
	}
//----------------------------update -----------------------------------------
	public void clickUpdateButton() {
		WebElement updatebtn=wait.waitForVisibility(updatebutton);
		try {
			if(updatebtn!=null&&updatebtn.isDisplayed()) {
			wait.waitForClickability(updatebtn).click();
			}
			
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}
	}

}
