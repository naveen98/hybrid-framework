package adstestcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.utilites.Excelutils;

import adspages.AddBasicInformationDetailsPage;
import adspages.AddLedStylesPage;
import adspages.AdsAppselecctionpage;
import adspages.AdsDeleteCamapign;
import adspages.AdsEditCampaign;
import adspages.AdsPaginationsandcount;
import adspages.AdsTotalcountspages;
import adspages.NavigatetoLedCampaignPage;

public class AdsLedCreateCampaignTest extends AdsLognTest {

	@BeforeClass(alwaysRun = true)
	public void setupSummary() {
		Testcasecount.reset("AdsLedCreateCampaignTest");
	}

	@Test(priority = 0, groups = { "smoke", "Regression" })
	public void loginTest() {
		loginverify();
	}

	@Test(priority = 1, groups = { "smoke", "Regression" }, dependsOnMethods = { "loginTest" })
	public void Appselection() {

		boolean isTestPassed = true;

		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Appselection ======", ExtentColor.BLUE);

		try {
			AdsAppselecctionpage appselect = new AdsAppselecctionpage(driver);
			appselect.appselection();
			appselect.isTitleAsExpected("Dashboard || ads Application");

			if (isTestPassed) {
				isTestPassed = true;

				AdsExtentManger.logColored(Status.PASS, "App selection successful", ExtentColor.GREEN);
			} else {

				AdsExtentManger.logColored(Status.FAIL, "App selection failed", ExtentColor.RED);
			}
		} catch (Exception e) {
			isTestPassed = false;

			AdsExtentManger.logColored(Status.FAIL, "App selection failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail();
		}

	}

	@Test(priority = 2, groups = { "smoke", "Regression" }, dependsOnMethods = { "Appselection" })
	public void NavigationToLedCampaign() {

		boolean isTestPassed = true;

		AdsExtentManger.logColored(Status.INFO, "====== Test Started: NaviagtionToLedCampaign ======",
				ExtentColor.BLUE);

		try {
			NavigatetoLedCampaignPage navigation = new NavigatetoLedCampaignPage(driver);
			navigation.clickonledcampaign();
			navigation.istitleExpected("LED Campaigns || ads Application");

			if (isTestPassed) {

				AdsExtentManger.logColored(Status.PASS, "Navigation to LED Campaign successful", ExtentColor.GREEN);
			} else {

				AdsExtentManger.logColored(Status.FAIL, "Navigation to LED Campaign failed", ExtentColor.RED);
			}
		} catch (Exception e) {
			isTestPassed = false;

			AdsExtentManger.logColored(Status.FAIL, "Navigation  failed: " + e.getMessage(), ExtentColor.RED);
		}

	}

	@Test(priority = 3, groups = { "smoke" }, dependsOnMethods = { "NavigationToLedCampaign" })
	public void CreateCampaign() {

		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Create Campaign ======", ExtentColor.BLUE);

		try {
			// Page objects
			AddBasicInformationDetailsPage add = new AddBasicInformationDetailsPage(driver);
			AdsTotalcountspages counts = new AdsTotalcountspages(driver);
			AdsPaginationsandcount pgcounts = new AdsPaginationsandcount(driver);

			// Initial campaign and page count
			int beforeTotalCampaignCount = counts.getTotalCampaignCount();
			int beforePageTotalCount = pgcounts.getTotalPagesFromText();

			System.out.println("Before total campaign count: " + beforeTotalCampaignCount);
			System.out.println("Before total page count: " + beforePageTotalCount);

			// Read basic campaign data
			String basicData[][] = Excelutils.getcelldatas(
					"D:\\git-clone\\hybrid-framework\\src\\test\\resources\\adscreateandedit.xlsx", "Basic");

			boolean overallTestPass = true;

			for (int j = 0; j < basicData.length; j++) {
				try {
					String name = basicData[j][0];
					String startMonth = basicData[j][1];
					String startYear = basicData[j][2];
					String startDay = basicData[j][3];
					String endMonth = basicData[j][4];
					String endYear = basicData[j][5];
					String endDay = basicData[j][6];
					String startTime = basicData[j][7];
					String endTime = basicData[j][8];
					String displayText = basicData[j][9];

					// Fill campaign basic information
					add.addcreatebutton();
					add.entertext(name);
					add.startDate(startMonth, startYear, startDay);
					add.endDate(endMonth, endYear, endDay);
					add.setStartTime(startTime);
					add.setEndTime(endTime);
					add.displayText(displayText);
					add.clicknext();

					// Fill LED Style Details
					AddLedStylesPage led = new AddLedStylesPage(driver);
					String[][] ledData = Excelutils.getcelldatas(
							"D:\\git-clone\\hybrid-framework\\src\\test\\resources\\adsledstyle.xlsx", "LedStyle");

					for (int i = 0; i < ledData.length; i++) {
						try {
							String font = ledData[i][0];
							String fontSize = ledData[i][1];
							String animation = ledData[i][2];
							String stayTime = ledData[i][3];
							String style = ledData[i][4];
							String program = ledData[i][5];
							String dimension = ledData[i][6];

							led.Ledstyles(font, fontSize, animation, stayTime, style, program);
							led.clicknext();
							led.Preview(dimension);
							led.submitbutton();

							String toastMessage = led.GetToastmessage();
							boolean isSuccess = toastMessage.toLowerCase().contains("successfully")
									|| toastMessage.toLowerCase().contains("saved");

							if (isSuccess) {
								AdsExtentManger.logColored(Status.PASS, "Campaign saved: " + toastMessage,
										ExtentColor.GREEN);
							} else {
								overallTestPass = false;
								AdsExtentManger.logColored(Status.FAIL, "Campaign failed: " + toastMessage,
										ExtentColor.RED);
							}
						} catch (Exception ledEx) {
							overallTestPass = false;
							AdsExtentManger.logColored(Status.FAIL, "Exception in LED style: " + ledEx.getMessage(),
									ExtentColor.RED);
						}
					}

				} catch (Exception campaignEx) {
					overallTestPass = false;
					AdsExtentManger.logColored(Status.FAIL, "Exception in Basic Info: " + campaignEx.getMessage(),
							ExtentColor.RED);
				}
			}

			// After campaign and page count
			int afterTotalCampaignCount = counts.getTotalCampaignCount();
			int afterPageTotalCount = pgcounts.getTotalPagesFromText();

			System.out.println("After total campaign count: " + afterTotalCampaignCount);
			System.out.println("After page total count: " + afterPageTotalCount);

			AdsExtentManger.logColored(Status.INFO,
					"Before Campaigns: " + beforeTotalCampaignCount + ", After Campaigns: " + afterTotalCampaignCount,
					ExtentColor.BLUE);
			AdsExtentManger.logColored(Status.INFO,
					"Before Pages: " + beforePageTotalCount + ", After Pages: " + afterPageTotalCount,
					ExtentColor.BLUE);

			// Final result
			if (overallTestPass && afterTotalCampaignCount > beforeTotalCampaignCount) {
			} else {
				Assert.fail("campaigns failed count did not increase.");
			}

		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "Exception during Campaign creation: " + e.getMessage(),
					ExtentColor.RED);
			Assert.fail(e.getMessage());
		}

	}

	@Test(priority = 4, groups = { "smoke" }, dependsOnMethods = { "CreateCampaign" })
	public void EditCampaign() {

		try {
			AdsEditCampaign ad = new AdsEditCampaign(driver);

			String[][] data = Excelutils.getcelldatas(
					"D:\\git-clone\\hybrid-framework\\src\\test\\resources\\adscreateandedit.xlsx", "edit");

			boolean isTestPassed = true;

			for (int i = 0; i < data.length; i++) {
				String name = data[i][0];
				String startMonth = data[i][1];
				String startYear = data[i][2];
				String startDay = data[i][3];
				String endMonth = data[i][4];
				String endYear = data[i][5];
				String endDay = data[i][6];
				String startTime = data[i][7];
				String endTime = data[i][8];
				String displayText = data[i][9];
				String font = data[i][10];
				String fontSize = data[i][11];
				String animation = data[i][12];
				String stayTime = data[i][13];
				String style = data[i][14];
				String program = data[i][15];
				String dimension = data[i][16];
				String campaignType = data[i][17];
				String updateStatus = data[i][18];
				String status = data[i][19];

				boolean recordFound = ad.searchAndSelect(name);

				if (!recordFound) {
					AdsExtentManger.logColored(Status.INFO, "No record found for campaign: " + name,
							ExtentColor.ORANGE);
					continue;
				}

				ad.clickEditButton();
				ad.editText(name + " Features");
				ad.startDate(startMonth, startYear, startDay);
				ad.endDate(endMonth, endYear, endDay);
				ad.setStartTime(startTime);
				ad.setEndTime(endTime);
				ad.textDisplays(displayText);

				ad.editLedStyles(font, fontSize, animation, stayTime, style, program, dimension);

				List<List<String>> radioSelect = new ArrayList<>();
				radioSelect.add(Arrays.asList("Campaign Type", campaignType));
				radioSelect.add(Arrays.asList("Do you want to update status?", updateStatus));
				radioSelect.add(Arrays.asList("Status", status));

				for (List<String> entry : radioSelect) {
					String label = entry.get(0);
					String option = entry.get(1);
					boolean selected = ad.selectRadioButtonOption(label, option);
					Assert.assertTrue(selected, "Failed to select '" + option + "' for '" + label + "'");
				}

				ad.clickUpdateButton();
				String msg = ad.getvalidationmessageok();

				if (msg.toLowerCase().contains("successfully") || msg.toLowerCase().contains("updated")) {
					AdsExtentManger.logColored(Status.PASS, "Campaign updated: " + msg, ExtentColor.GREEN);
				} else {
					isTestPassed = false;
					AdsExtentManger.logColored(Status.FAIL, "Update failed: " + msg, ExtentColor.RED);
					Assert.fail();
				}
			}

			Assert.assertTrue(isTestPassed, " campaign updates failed.");

		} catch (Exception e) {
			Assert.fail("EditCampaign test failed due to exception: " + e.getMessage());
		}

	}

	@Test(priority = 5, groups = { "smoke" }, dependsOnMethods = { "EditCampaign" })
	public void DeleteCampaign() {

		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Delete Campaign ======", ExtentColor.BLUE);

		AdsDeleteCamapign del = new AdsDeleteCamapign(driver);
		String campaignToDelete = "Luminous";

		try {

			del.clickmainled();
			del.searchAndSelect(campaignToDelete);

			if (del.Norecordfound()) {
				AdsExtentManger.logColored(Status.PASS, "No record found for campaign: " + campaignToDelete,
						ExtentColor.GREEN);
				return;
			}

			del.clickDelete();

			boolean confirmed = del.confirmDeletePopUp();
			if (!confirmed) {
				AdsExtentManger.logColored(Status.FAIL, "Delete confirmation popup did not appear.", ExtentColor.RED);
				return;
			}

			String msg = del.getDeleteValidationMessage();
			boolean success = msg.toLowerCase().contains("successfully") || msg.toLowerCase().contains("deleted");

			if (success) {
				AdsExtentManger.logColored(Status.PASS, "Campaign deleted successfully: " + msg, ExtentColor.GREEN);
			} else {
				AdsExtentManger.logColored(Status.FAIL, "Campaign delete failed: " + msg, ExtentColor.RED);
				Assert.fail();
			}

		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "Exception  campaign deletion: " + e.getMessage(), ExtentColor.RED);
		}

	}

	@Test(priority = 6, groups = { "smoke" }, dependsOnMethods = { "DeleteCampaign" })
	public void Paginations() {

		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Paginations ======", ExtentColor.BLUE);

		try {
			AdsPaginationsandcount ads = new AdsPaginationsandcount(driver);

			String[][] data = Excelutils.getcelldatas(
					"D:\\git-clone\\hybrid-framework\\src\\test\\resources\\paginations.xlsx", "paginations");

			for (int i = 0; i < data.length; i++) {
				String action = data[i][0].trim().toLowerCase(); // it will get data from excel like click /next
				int expectedPage = Integer.parseInt(data[i][1].trim()); // it will get data from excel like 1 /2/3

				switch (action) {
				case "goto":
					ads.navigateToPage(expectedPage);
					int currentPage = ads.getCurrentPageNumber();
					Assert.assertEquals(currentPage, expectedPage, "Failed to navigate to page " + expectedPage);
					AdsExtentManger.logColored(Status.PASS, "Navigated to page " + expectedPage + " successfully.",
							ExtentColor.GREEN);
					break;

				case "next":
					ads.clickNextPage();
					int nextPage = ads.getCurrentPageNumber();
					Assert.assertEquals(nextPage, expectedPage,
							"Next page navigation failed. Expected: " + expectedPage + ", but was: " + nextPage);
					AdsExtentManger.logColored(Status.PASS, "Clicked Next. Now on page " + nextPage, ExtentColor.GREEN);
					break;

				case "prev":
					ads.clickPreviousPage();
					int prevPage = ads.getCurrentPageNumber();
					Assert.assertEquals(prevPage, expectedPage,
							"Previous page navigation failed. Expected: " + expectedPage + ", but was: " + prevPage);
					AdsExtentManger.logColored(Status.PASS, "Clicked Previous. Now on page " + prevPage,
							ExtentColor.GREEN);
					break;

				case "grid":
					ads.Gridrefresh();
					AdsExtentManger.logColored(Status.PASS, "Grid Refresherd ", ExtentColor.GREEN);
					break;

				default:
					AdsExtentManger.logColored(Status.WARNING, "Unknown action: " + action, ExtentColor.ORANGE);
					break;
				}
			}

		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "Pagination test failed: " + e.getMessage(), ExtentColor.RED);
		}
	}

	@Test(priority = 7, groups = {"Regression"}, dependsOnMethods = {"NavigationToLedCampaign"})
	public void CreatewithInvalid() {
	    AdsExtentManger.logColored(Status.INFO, "====== Test Started: Create Campaign With Invalid data ======", ExtentColor.BLUE);

	    try {
	        // Page object setup
	        AddBasicInformationDetailsPage add = new AddBasicInformationDetailsPage(driver);
	        AdsTotalcountspages counts = new AdsTotalcountspages(driver);
	        AdsPaginationsandcount pgcounts = new AdsPaginationsandcount(driver);

	        int beforeCampaignCount = counts.getTotalCampaignCount();
	        int beforePageCount = pgcounts.getTotalPagesFromText();

	        // Read Excel test data
	        String[][] testData = Excelutils.getcelldatas("D:\\git-clone\\hybrid-framework\\src\\test\\java\\adstestcases\\AdsInvalidcases.java", "Sheet1");

	        // Store results
	        List<String[]> results = new ArrayList<>();

	        // Loop through Excel rows
	        for (int i = 0; i < testData.length; i++) {
	            String message = "Row " + (i + 1) + ": ";
	            boolean skipToNext = false;

	            try {
	                add.addcreatebutton();  

	                // STEP 1: Basic Info
	                add.entertext(testData[i][0]);
	                add.startDate(testData[i][1], testData[i][2], testData[i][3]);
	                add.endDate(testData[i][4], testData[i][5], testData[i][6]);
	                add.setStartTime(testData[i][7]);
	                add.setEndTime(testData[i][8]);
	                add.displayText(testData[i][9]);
	                add.clicknext();

	                List<String> basicErrors = add.getValidationMessages();
	                if (!basicErrors.isEmpty()) {
	                    message += "Basic Info Errors: ";
	                    for (String error : basicErrors) {
	                        AdsExtentManger.logColored(Status.INFO, "Basic Info Error: " + error, ExtentColor.RED);
	                        message += error + " | ";
	                    }
	                    results.add(new String[]{String.valueOf(i + 1), "Basic Info Failed", message});
	                    add.clickonledscreen();  
	                    continue;
	                }

	                // STEP 2: LED Styles
	                AddLedStylesPage led = new AddLedStylesPage(driver);
	                led.Ledstyles(testData[i][10], testData[i][11], testData[i][12], testData[i][13], testData[i][14], testData[i][15]);
	                led.clicknext();

	                List<String> ledErrors = led.getValidationMessages();
	                if (!ledErrors.isEmpty()) {
	                    message += "LED Style Errors: ";
	                    for (String error : ledErrors) {
	                        AdsExtentManger.logColored(Status.INFO, "LED Style Error: " + error, ExtentColor.RED);
	                        message += error + " | ";
	                    }
	                    results.add(new String[]{String.valueOf(i + 1), "LED Style Failed", message});
	                    add.clickonledscreen();
	                    continue;
	                }

	                // STEP 3: Submit
	                led.Preview(testData[i][16]);
	                led.submitbutton();

	                String toastMessage = led.GetToastmessage();
	                boolean isSuccess = toastMessage.toLowerCase().contains("successfully") || toastMessage.toLowerCase().contains("saved");

	                if (isSuccess) {
	                    AdsExtentManger.logColored(Status.FAIL, "Unexpected success: " + toastMessage, ExtentColor.RED);
	                    message += "Unexpected Success: " + toastMessage;
	                    results.add(new String[]{String.valueOf(i + 1), "Unexpected Pass", message});
	                } else {
	                    AdsExtentManger.logColored(Status.PASS, "Failed as expected: " + toastMessage, ExtentColor.GREEN);
	                    message += "Failed as Expected: " + toastMessage;
	                    results.add(new String[]{String.valueOf(i + 1), "Valid Failure", message});
	                    add.clickonledscreen(); 
	                }

	            } catch (Exception e) {
	                AdsExtentManger.logColored(Status.FAIL, "Exception: " + e.getMessage(), ExtentColor.RED);
	                results.add(new String[]{String.valueOf(i + 1), "Exception", e.getMessage()});
	            }
	        }

	        // Final counts after test
	        int afterCampaignCount = counts.getTotalCampaignCount();
	        int afterPageCount = pgcounts.getTotalPagesFromText();

	        AdsExtentManger.logColored(Status.INFO, "Before Campaigns: " + beforeCampaignCount + ", After: " + afterCampaignCount, ExtentColor.BLUE);
	        AdsExtentManger.logColored(Status.INFO, "Before Pages: " + beforePageCount + ", After: " + afterPageCount, ExtentColor.BLUE);

	      /*  // Print result summary to console
	        System.out.println("========= Validation Summary =========");
	        for (String[] result : results) {
	            System.out.println("Row: " + result[0] + " | Status: " + result[1] + " | Message: " + result[2]);
	        }*/

	    } catch (Exception e) {
	        AdsExtentManger.logColored(Status.FAIL, "Fatal Error: " + e.getMessage(), ExtentColor.RED);
	        Assert.fail(e.getMessage());
	    }
	}

	@Test(priority = 8, groups = { "smoke", "Regression" })
	public void logSummary() {
		TestcaseLogger.logSummary();
	}

}
