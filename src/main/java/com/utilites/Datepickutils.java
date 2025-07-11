package com.utilites;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Datepickutils {
	public WebDriver driver;
	public webdriverwaitutils wait;

	public Datepickutils(WebDriver driver) {
		this.driver = driver;
		this.wait = new webdriverwaitutils(driver);

	}

	public void datepickers(By monthyearLocator, By nextButton, By allDates, String expMonth, String expYear,String expDate) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			// select month
			while (true) {

				String monthyeartext = wait.waitForVisibilityBy(monthyearLocator).getText();

				String[] monthyear = monthyeartext.split(" ");

				String currentmonth = monthyear[0];

				String currentyear = monthyear[1];

				System.out.println("Current Month: " + currentmonth + " " + "currentyear :" + currentyear);

				if (currentmonth.equals(expMonth) && currentyear.equals(expYear)) {

				      
				// next
				WebElement nextbtn = wait.waitForVisibilityBy(nextButton);
				js.executeScript("arguments[0].click();", nextbtn);

			}
			
		}  
		}catch (Exception e) {
			System.out.println("Month year invalid selection  :" );

		}

		
		// select the date
		try {
			
			List<WebElement> dates = wait.waitForAllElementsVisible(allDates);

			for (WebElement d : dates) {

				String datemsg = d.getText();

				String parts[] = datemsg.split("\\s+"); 
				String date = parts[0];
				
				// String price = parts[1];

				System.out.println(date);

				if (date.equals(expDate)) {
					
					js.executeScript("arguments[0].click();", d);
					
					break;
					
				} else {
					
					System.out.println("Invalid Date");
				}
			}
			
		} catch (Exception e) {

			System.out.println("Invalid Date Selection" + e.getMessage());
		}

	}

	
	//both cases can handle
		
	public void datepickers(By monthlocator ,By yearLocator ,By Prevbtn, By nextButton, By allDates, String expMonth, String expYear,String expDate) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
	    String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		try {
			// select month
			while (true) {

				String currentmonthloc = wait.waitForVisibilityBy(monthlocator).getText();  //may
				String currentyearloc = wait.waitForVisibilityBy(yearLocator).getText();
                  
				
				int currentyearnum=Integer.parseInt(currentyearloc);
				int expyearnum=Integer.parseInt(expYear);
				
				int currentmonthindex =Arrays.asList(monthList).indexOf(currentmonthloc);
				int expmonthindex= Arrays.asList(monthList).indexOf(expMonth);
				
				
				
			  System.out.println("Current Month: " + currentmonthloc + " " + "currentyear :" + currentyearnum +"expMonth:" +expMonth +" "+expyearnum  );
           
				if (currentmonthloc.equalsIgnoreCase(expMonth) && currentyearloc.equalsIgnoreCase(expYear)) {
					break;

				    //previous
				//	WebElement prevbtns = wait.waitForClickabilityby(Prevbtn);
					
				//	js.executeScript("arguments[0].click();", prevbtns);
					

				}
				else if(currentyearnum > expyearnum || (currentyearnum == expyearnum && currentmonthindex > expmonthindex)) {

					
					    WebElement prevbtns = wait.waitForVisibilityBy(Prevbtn);
					
						js.executeScript("arguments[0].click();", prevbtns);
				}else {
					
			    // next	
				WebElement nextbtn = wait.waitForVisibilityBy(nextButton);
				js.executeScript("arguments[0].click();", nextbtn);
				
				}
			}
	
		}   catch (Exception e) {
			System.out.println("Month year invalid selection  :" );

		}

		
		// select the date
		try {
			
			List<WebElement> dates = wait.waitForAllElementsVisible(allDates);

			for (WebElement d : dates) {

				String datemsg = d.getText();

	
				System.out.println(datemsg);

				if (datemsg.equals(expDate)) {
					
					js.executeScript("arguments[0].click();", d);
					System.out.println(expDate);
					
					break;
					
				}
			}
			
		} catch (Exception e) {

			System.out.println("Invalid Date Selection" + e.getMessage());
		}

	}

}

	



