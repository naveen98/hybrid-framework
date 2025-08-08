package adstestcases;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
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


	@Test(priority = 0, groups = { "smoke", "Regression", "both" })
	public void loginTest() {
		loginverify();
	}

	@Test(priority = 1, groups = {"smoke", "Regression", "both" }, dependsOnMethods = { "loginTest" })
	public void Appselection() {
		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Appselection ======", ExtentColor.BLUE);

		try {
			AdsAppselecctionpage appselect = new AdsAppselecctionpage(driver);
			appselect.appselection();
			appselect.isTitleAsExpected("Dashboard || ads Application");

			AdsExtentManger.logColored(Status.PASS, "App selection successful", ExtentColor.GREEN);
		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "App selection failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail("App selection failed: " + e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "smoke", "Regression", "both" }, dependsOnMethods = { "Appselection" })
	public void NavigationToLedCampaign() {
		AdsExtentManger.logColored(Status.INFO, "====== Test Started: NavigationToLedCampaign ======",
				ExtentColor.BLUE);

		try {
			NavigatetoLedCampaignPage navigation = new NavigatetoLedCampaignPage(driver);
			navigation.clickonledcampaign();
			navigation.istitleExpected("LED Campaigns || ads Application");

			AdsExtentManger.logColored(Status.PASS, "Navigation to LED Campaign successful", ExtentColor.GREEN);
		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "Navigation failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail("Navigation to LED Campaign failed: " + e.getMessage());
		}
	}

	@Test(priority = 3, groups = { "smoke" }, dependsOnMethods = { "NavigationToLedCampaign" })
	public void CreateCampaign() {
		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Create Campaign ======", ExtentColor.BLUE);

		try {
			AddBasicInformationDetailsPage add = new AddBasicInformationDetailsPage(driver);
			AdsTotalcountspages counts = new AdsTotalcountspages(driver);
			AdsPaginationsandcount pgcounts = new AdsPaginationsandcount(driver);

			int beforeTotalCampaignCount = counts.getTotalCampaignCount();
			int beforePageTotalCount = pgcounts.getTotalPagesFromText();

			String basicData[][] = Excelutils.getcelldatas(
					"D:\\Selenium\\hybrid-framework\\src\\test\\resources\\adscreateandedit.xlsx", "Basic");

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

					add.addcreatebutton();
					add.entertext(name);
					add.startDate(startMonth, startYear, startDay);
					add.endDate(endMonth, endYear, endDay);
					add.setStartTime(startTime);
					add.setEndTime(endTime);
					add.displayText(displayText);
					add.clicknext();

					AddLedStylesPage led = new AddLedStylesPage(driver);
					String[][] ledData = Excelutils.getcelldatas(
							"D:\\Selenium\\hybrid-framework\\src\\test\\resources\\adsledstyle.xlsx", "LedStyle");

					for (int i = 0; i < ledData.length; i++) {
						try {
							String font = ledData[i][0];
							String fontSize = ledData[i][1];
							String animation = ledData[i][2];
							String stayTime = ledData[i][3];
							String style = ledData[i][4];
							String program = ledData[i][5];
							String dimension = ledData[i][6];

							led.fillLedStyles(font, fontSize, animation, stayTime, style, program);
							led.clickNext();

							led.preview(dimension);
							led.clickSubmit();

							String toastMessage = led.getToastMessage();
							boolean isSuccess = toastMessage.toLowerCase().contains("successfully")
									|| toastMessage.toLowerCase().contains("saved");

							if (isSuccess) {
								AdsExtentManger.logColored(Status.PASS, "Campaign saved: " + toastMessage,
										ExtentColor.GREEN);
							} else {
								overallTestPass = false;
								AdsExtentManger.logColored(Status.FAIL, "Campaign failed: " + toastMessage,
										ExtentColor.RED);
								Assert.fail();
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

			int afterTotalCampaignCount = counts.getTotalCampaignCount();
			int afterPageTotalCount = pgcounts.getTotalPagesFromText();

			AdsExtentManger.logColored(Status.INFO,
					"Before Campaigns: " + beforeTotalCampaignCount + ", After Campaigns: " + afterTotalCampaignCount,
					ExtentColor.BLUE);
			AdsExtentManger.logColored(Status.INFO, "Before Total page count : " + beforePageTotalCount
					+ ", afterPageTotalCount: " + afterPageTotalCount, ExtentColor.BLUE);

			if (!(overallTestPass && afterTotalCampaignCount > beforeTotalCampaignCount)) {
				Assert.fail("Campaign creation failed or count did not increase.");
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
					"D:\\Selenium\\hybrid-framework\\src\\test\\resources\\adscreateandedit.xlsx", "edit");
			boolean isTestPassed = true;

			for (String[] row : data) {
				String name = row[0];
				String startMonth = row[1];
				String startYear = row[2];
				String startDay = row[3];
				String endMonth = row[4];
				String endYear = row[5];
				String endDay = row[6];
				String startTime = row[7];
				String endTime = row[8];
				String displayText = row[9];
				String font = row[10];
				String fontSize = row[11];
				String animation = row[12];
				String stayTime = row[13];
				String style = row[14];
				String program = row[15];
				String dimension = row[16];
				String campaignType = row[17];
				String updateStatus = row[18];
				String status = row[19];

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

				List<List<String>> radioSelect = Arrays.asList(Arrays.asList("Campaign Type", campaignType),
						Arrays.asList("Do you want to update status?", updateStatus), Arrays.asList("Status", status));

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
					isTestPassed = true;
				} else {
					isTestPassed = false;
					AdsExtentManger.logColored(Status.FAIL, "Update failed: " + msg, ExtentColor.RED);
					Assert.fail();
				}
			}
		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "EditCampaign test failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail();

		}
	}

	@Test(priority = 5, dependsOnMethods = { "CreateCampaign" }, groups = { "smoke" })
	public void DeleteCampaign() {
		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Delete Campaign ======", ExtentColor.BLUE);

		AdsDeleteCamapign del = new AdsDeleteCamapign(driver);
		String campaignToDelete = "Luminous";

		try {
			del.clickmainled();
			del.searchAndSelect(campaignToDelete);
			del.clickDelete();

			if (!del.confirmDeletePopUp()) {
				AdsExtentManger.logColored(Status.FAIL, "Delete confirmation popup did not appear.", ExtentColor.RED);
				return;
			}

			String msg = del.getDeleteValidationMessage();
			if (msg.toLowerCase().contains("successfully") || msg.toLowerCase().contains("deleted")) {
				AdsExtentManger.logColored(Status.PASS, "Campaign deleted successfully: " + msg, ExtentColor.GREEN);
			} else {
				AdsExtentManger.logColored(Status.FAIL, "Campaign delete failed: " + msg, ExtentColor.RED);
				Assert.fail();
			}

		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "Exception during campaign deletion: " + e.getMessage(),
					ExtentColor.RED);
			Assert.fail();

		}
	}

	@Test(priority = 6, groups = { "smoke" }, dependsOnMethods = { "DeleteCampaign" })
	public void Paginations() {
		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Paginations ======", ExtentColor.BLUE);

		try {
			AdsPaginationsandcount ads = new AdsPaginationsandcount(driver);
			String[][] data = Excelutils.getcelldatas(
					"D:\\Selenium\\hybrid-framework\\src\\test\\resources\\paginations.xlsx", "paginations");

			for (String[] row : data) {
				String action = row[0].trim().toLowerCase();
				int expectedPage = Integer.parseInt(row[1].trim());
				
				 switch (action) {
			        case "goto":
			            ads.navigateToPage(expectedPage);
			            if (ads.getCurrentPageNumber() != expectedPage) {
			                AdsExtentManger.logColored(Status.FAIL, "Expected page: " + expectedPage + ", but found: " + ads.getCurrentPageNumber(), ExtentColor.RED);
			                Assert.fail("Failed to navigate to page " + expectedPage);
			            }
			            AdsExtentManger.logColored(Status.PASS, "Navigated to page " + expectedPage, ExtentColor.GREEN);
			            break;

			        case "next":
			            ads.clickNextPage();
			            if (ads.getCurrentPageNumber() != expectedPage) {
			                AdsExtentManger.logColored(Status.FAIL, "Expected page after next: " + expectedPage + ", but found: " + ads.getCurrentPageNumber(), ExtentColor.RED);
			                Assert.fail("Failed to click Next to reach page " + expectedPage);
			            }
			            AdsExtentManger.logColored(Status.PASS, "Clicked Next. Now on page " + expectedPage, ExtentColor.GREEN);
			            break;

			        case "prev":
			            ads.clickPreviousPage();
			            if (ads.getCurrentPageNumber() != expectedPage) {
			                AdsExtentManger.logColored(Status.FAIL, "Expected page after prev: " + expectedPage + ", but found: " + ads.getCurrentPageNumber(), ExtentColor.RED);
			                Assert.fail("Failed to click Previous to reach page " + expectedPage);
			            }
			            AdsExtentManger.logColored(Status.PASS, "Clicked Previous. Now on page " + expectedPage, ExtentColor.GREEN);
			            break;

			        case "grid":
			            ads.Gridrefresh();
			            AdsExtentManger.logColored(Status.PASS, "Grid Refreshed", ExtentColor.GREEN);
			            break;

			        default:
			            AdsExtentManger.logColored(Status.WARNING, "Unknown action: " + action, ExtentColor.ORANGE);
			            break;
				}
			}

		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, "Pagination test failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail();

		}
	}

	@Test(priority = 7, groups = { "Regression" }, dependsOnMethods = { "NavigationToLedCampaign" })
	public void CreatewithInvalid() {
		AdsExtentManger.logColored(Status.INFO, "====== Test Started: Create Campaign ======", ExtentColor.BLUE);

		try {
			AddBasicInformationDetailsPage add = new AddBasicInformationDetailsPage(driver);
			AdsTotalcountspages counts = new AdsTotalcountspages(driver);
			AdsPaginationsandcount pgcounts = new AdsPaginationsandcount(driver);

			int beforeTotalCampaignCount = counts.getTotalCampaignCount();
			int beforePageTotalCount = pgcounts.getTotalPagesFromText();

			String[][] testData = Excelutils.getcelldatas(
					"D:\\Selenium\\hybrid-framework\\src\\test\\resources\\createadsinvalid.xlsx", "Sheet1");

			for (int i = 0; i < testData.length; i++) {
				AdsExtentManger.logColored(Status.INFO, "=====Executing Row: " + (i + 1) + " =====", ExtentColor.BLUE);

				try {
					add.addcreatebutton();

					// Step 1: Fill Basic Info
					String name = testData[i][0];
					String startMonth = testData[i][1];
					String startYear = testData[i][2];
					String startDay = testData[i][3];
					String endMonth = testData[i][4];
					String endYear = testData[i][5];
					String endDay = testData[i][6];
					String startTime = testData[i][7];
					String endTime = testData[i][8];
					String displayText = testData[i][9];

					add.entertext(name);
					add.startDate(startMonth, startYear, startDay);
					add.endDate(endMonth, endYear, endDay);
					add.setStartTime(startTime);
					add.setEndTime(endTime);
					add.displayText(displayText);
					add.clicknext();

					// Step 1 Validation
					List<String> basicValidationMessages = add.getValidationMessages();
					if (!basicValidationMessages.isEmpty()) {
						AdsExtentManger.logColored(Status.PASS, "Basic Info Validation Errors at Row ",
								ExtentColor.RED);
						for (String msg : basicValidationMessages) {
							AdsExtentManger.logColored(Status.INFO, msg, ExtentColor.RED);
						}
						add.clickonledscreen();
						continue;
					}

					// Step 2: Fill LED Styles
					String font = testData[i][10];
					String fontSize = testData[i][11];
					String animation = testData[i][12];
					String stayTime = testData[i][13];
					String style = testData[i][14];
					String program = testData[i][15];

					AddLedStylesPage led = new AddLedStylesPage(driver);
					led.fillLedStyles(font, fontSize, animation, stayTime, style, program);
					led.clickNext();

					// Step 2 Validation
					List<String> ledValidationMessages = led.getValidationMessages();
					if (!ledValidationMessages.isEmpty()) {
						AdsExtentManger.logColored(Status.PASS, "LED Style Validation Errors  ", ExtentColor.RED);
						for (String msg : ledValidationMessages) {
							AdsExtentManger.logColored(Status.INFO, msg, ExtentColor.RED);
						}
						add.clickonledscreen();
						continue;
					}

					// Step 3: Handle Dimension
					AddLedStylesPage dim = new AddLedStylesPage(driver);

					String dimension = testData[i][16];
					dim.preview(dimension);
					dim.clickSubmit();

					// Toast Message Check
					String toast = led.getToastMessage();

					if (toast.toLowerCase().contains("Successfully") || toast.toLowerCase().contains("saved")) {
						AdsExtentManger.logColored(Status.FAIL, "Expected Failure" + ": " + toast, ExtentColor.RED);
						Assert.fail();

					} else {
						AdsExtentManger.logColored(Status.PASS, "Successfully showed Error Message  " + ": " + toast,
								ExtentColor.RED);
						add.clickonledscreen();

					}

				} catch (Exception e) {
					AdsExtentManger.logColored(Status.FAIL, "Exception  " + ": " + e.getMessage(), ExtentColor.RED);
					Assert.fail();
				}
			}

			// Final Counts
			int afterTotalCampaignCount = counts.getTotalCampaignCount();
			int afterPageTotalCount = pgcounts.getTotalPagesFromText();

			AdsExtentManger.logColored(Status.INFO, "Total Campaign Count - Before: " + beforeTotalCampaignCount
					+ " | After: " + afterTotalCampaignCount, ExtentColor.BLUE);
			AdsExtentManger.logColored(Status.INFO,
					"Total Page Count - Before: " + beforePageTotalCount + " | After: " + afterPageTotalCount,
					ExtentColor.BLUE);

		} catch (Exception e) {
			AdsExtentManger.logColored(Status.FAIL, " Exception: " + e.getMessage(), ExtentColor.RED);
		}
	}

	@Test(priority = 8, groups = { "Regression" })
	public void InvalidLoginCheck() {
		LoginInvalidTest.verifyInvalidLoginMultiple();

	}

}
