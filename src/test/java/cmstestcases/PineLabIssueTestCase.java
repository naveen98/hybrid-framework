package cmstestcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.utilites.Excelutils;
import com.utilites.webdriverwaitutils;

import apollocmspages.Appselectionpage;
import apollocmspages.CmsNavigatetoPineLabsIssuePages;
import apollocmspages.CmsSatuspage;
import apollocmspages.CmsUpdatepages;
import apollocmspages.CreateNewIssuePage;
import apollocmspages.Deleteissuepages;
import apollocmspages.UpadateIssueStatusClosed;
import apollocmspages.ViewDeletedStatus;
import apollocmspages.ViewIssuePage;
import utils.ExtentManger;
import utils.Testcasecounts;

public class PineLabIssueTestCase  extends CmsLoginTest{
	public webdriverwaitutils wait;

	@Test(priority = 0)
	public void LoginTest() {
		logintverify();
	}

	@Test(priority = 1, dependsOnMethods = { "LoginTest" })
	public void AppSelectionPage() {
		Testcasecounts tc = new Testcasecounts("AppSelectionPage");
		tc.totalcasesinc();
		try {
			Appselectionpage ap = new Appselectionpage(driver);
			ap.appselection();
			ExtentManger.logColored(Status.INFO, "Appselection Info ", ExtentColor.BLUE);

			if (ap.isdisplayed()) {
				ExtentManger.logColored(Status.PASS, "CMS Admin displayed.", ExtentColor.GREEN);
				tc.passedinc();
			} else {
				ExtentManger.logColored(Status.FAIL, "CMS Admin not displayed", ExtentColor.RED);
				tc.failedinc();
				Assert.fail("CMS Admin not displayed");
			}
		} catch (Exception e) {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "App selection failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail("App selection failed");
		}
		tc.logsummary();
	}
	@Test(priority = 2, dependsOnMethods = { "AppSelectionPage" })
	public void NavigationToPineLabIssue() {
		Testcasecounts tc = new Testcasecounts("NavigationToPineLabIssue");
		tc.totalcasesinc();
		try {
			CmsNavigatetoPineLabsIssuePages np = new CmsNavigatetoPineLabsIssuePages(driver);
			np.navigatetopinelab();
			ExtentManger.logColored(Status.INFO, "Navigated to Pine Lab Issue", ExtentColor.BLUE);

			if (np.istextdisplayed()) {
				ExtentManger.logColored(Status.PASS, "Pine Labs Issue displayed.", ExtentColor.GREEN);
				tc.passedinc();
			} else {
				ExtentManger.logColored(Status.FAIL, "Pine Labs Issue not displayed", ExtentColor.RED);
				tc.failedinc();
				Assert.fail();
			}
		} catch (Exception e) {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "Navigation failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail("Navigation failed");
		}
		tc.logsummary();
	}
	@Test(priority = 3, dependsOnMethods = { "NavigationToPineLabIssue" })
	public void CreateNewIssue() throws Throwable {
		Testcasecounts tc = new Testcasecounts("CreateNewIssue");
		CreateNewIssuePage cc = new CreateNewIssuePage(driver);
		cc.clickbuuton();

		String[][] data = Excelutils.getcelldatas("D:\\hybrid-framework\\src\\test\\resources\\createissuecms.xlsx",
				"createissue");

		for (int i = 0; i <= 0; i++) {
			tc.totalcasesinc();

			try {
				
				cc.createnewissue(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4]);
				cc.createsubmitbtn();
				ExtentManger.logColored(Status.INFO, "Clicking create New Issue ", ExtentColor.BLUE);

				String msg = cc.getvalidationmessage();
				boolean success = msg.toLowerCase().contains("created") || msg.toLowerCase().contains("successfully");
				if (success) {
					tc.passedinc();
					ExtentManger.logColored(Status.PASS, "  Created Success: " + msg, ExtentColor.GREEN);
				} else {
					tc.failedinc();
					ExtentManger.logColored(Status.FAIL,  " Validation Failed: " + msg,	ExtentColor.RED);
				}

				// cc.clickbuuton();
				// Thread.sleep(1000);
			} catch (Exception e) {
				tc.failedinc();
				ExtentManger.logColored(Status.FAIL, " Error: " + e.getMessage(), ExtentColor.RED);
			}
		}
		tc.logsummary();
	}
	@Test(priority = 4, dependsOnMethods = { "CreateNewIssue" })
	public void TablevieListStatus() {
		Testcasecounts tc = new Testcasecounts("TablevieListStatus");
		CmsSatuspage cm = new CmsSatuspage(driver);
		try {
			tc.totalcasesinc();
			if (cm.isTableDisplayedStatus("New")) {
				ExtentManger.logColored(Status.INFO, "Status Of Table View  Pine Lab Status ", ExtentColor.BLUE);

				ExtentManger.logColored(Status.PASS, "Status New Displayed ", ExtentColor.GREEN);
				tc.passedinc();
			} else {
				ExtentManger.logColored(Status.FAIL, "Expected Status Not displayed.", ExtentColor.RED);
				tc.failedinc();
			}
		} catch (Exception e) {
			tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "Table View " + e.getMessage(), ExtentColor.RED);

		}
		tc.logsummary();

	}
	@Test(priority = 5, dependsOnMethods = { "TablevieListStatus" })
	public void ViewIssueDetails() {

		Testcasecounts tc = new Testcasecounts("ViewIssueDetails");
		ViewIssuePage v = new ViewIssuePage(driver);

		try {
			tc.totalcasesinc();
		    v.viewdetails();
		    ExtentManger.logColored(Status.INFO, "Checking the Status of Pine Lab ", ExtentColor.BLUE);

		    if (v.viewstatus("New")) {
		        ExtentManger.logColored(Status.PASS, " New Status Displayed.", ExtentColor.GREEN);
		        tc.passedinc();
		    } else {
		        ExtentManger.logColored(Status.FAIL, "Expected Status not displayed.", ExtentColor.RED);
		        tc.failedinc();
		        Assert.fail("Status not visible.");
		    }

		   v.canceled(); 

		} catch (Exception e) {
		    tc.failedinc();
		    ExtentManger.logColored(Status.FAIL, "View Test Error: " + e.getMessage(), ExtentColor.RED);
		}
		tc.logsummary();
	}
	@Test(priority = 6, dependsOnMethods = { "ViewIssueDetails" })
	public void UpdateIssue() throws Throwable {
		Testcasecounts tc = new Testcasecounts("UpdateIssue");
		 CmsUpdatepages up = new CmsUpdatepages(driver);
		 
		   up.clickupdatebutton("AP-10000-CHE-10000299"); 
          String [][]data=Excelutils.getcelldatas("D:\\hybrid-framework\\src\\test\\resources\\createissuecms.xlsx", "updateissue");
          for(int i=0 ;i<=0;i++) {
        	  tc.totalcasesinc();
        	    try {
                   String issueNo = "AP-10000-CHE-10000299"; 
                   ExtentManger.logColored(Status.INFO, "Updating Functionality: " + issueNo, ExtentColor.BLUE);

        	    	    String contactNo = data[i][0];
        	            String category = data[i][1];
        	            String description = data[i][2];

                    // 2. Fill details and click update
                    up.updateissuedetailsfornew(contactNo, category, description);
                    up.updatebtns(); 

                    // 3. Get validation message
                    String Msg = up.getvalidationmessage(); 
                    boolean isUpdate = Msg != null && (Msg.toLowerCase().contains("updated") || Msg.toLowerCase().contains("successfully"));

                    if (isUpdate) {
                        ExtentManger.logColored(Status.PASS, " Updated Succesfully: " + Msg, ExtentColor.GREEN);
                        tc.passedinc();
                    } else {
                        ExtentManger.logColored(Status.FAIL," Validation Failed: " + Msg, ExtentColor.RED);
                        tc.failedinc();
                    }
                } catch (Exception e) {
                    tc.failedinc();
                    ExtentManger.logColored(Status.FAIL, " Exception: " + e.getMessage(), ExtentColor.RED);
                }
            }

            tc.logsummary();
        
   }
	@Test(priority = 7, dependsOnMethods = { "UpdateIssue" })
	public void AgaincheckViewIssueDetails() {


	    Testcasecounts tc = new Testcasecounts("AgaincheckViewIssueDetails");
	    ViewIssuePage v = new ViewIssuePage(driver);

	    //  Check updated status
	    try {
	        tc.totalcasesinc();
	        v.viewdetails(); 
	        ExtentManger.logColored(Status.INFO, "Checking status of the Pine Lab issue after update", ExtentColor.BLUE);

	        if (v.viewstatus("New")) {
	        	
	            ExtentManger.logColored(Status.PASS, "Expected Status is displayed: New", ExtentColor.GREEN);
	            tc.passedinc();
	        } else {
	            ExtentManger.logColored(Status.FAIL, "Expected status 'Updated' not displayed.", ExtentColor.RED);
	            tc.failedinc();
	            Assert.fail("Expected status not visible.");
	        }

	    //  Cancel the View Form
	        v.cancel();
	  

	    } catch (Exception e) {
	        tc.failedinc();
	        ExtentManger.logColored(Status.FAIL, "Cancel Test Error: " + e.getMessage(), ExtentColor.RED);
	    }

	   
	    tc.logsummary();
	}
	@Test(priority=8,dependsOnMethods = {"AgaincheckViewIssueDetails"})
	public void UpdateStatusClose() {
		
		Testcasecounts tc = new Testcasecounts("UpdateStatusClose");
		tc.totalcasesinc();
		UpadateIssueStatusClosed up = new UpadateIssueStatusClosed(driver);
		String message = up.statusclosed("Closing issue");

		if (message.toLowerCase().contains("successfully") || message.toLowerCase().contains("updated")) {
			ExtentManger.logColored(Status.PASS, "Issue Closed Successfully: " + message, ExtentColor.GREEN);
			tc.passedinc();
		} else {
			ExtentManger.logColored(Status.FAIL, "Issue Close Failed: " + message, ExtentColor.RED);
			tc.failedinc();
			Assert.fail("Toast not received or Failed");
		}
		tc.logsummary();
	}
	
	@Test(priority = 9,dependsOnMethods = {"UpdateStatusClose"})
	public void ClosedStatusCheck() {
		
		Testcasecounts tc= new Testcasecounts("ClosedStatusCheck");
		CmsSatuspage cm= new CmsSatuspage(driver);
		String issueNo = "AP-10000-CHE-10000322"; 
        ExtentManger.logColored(Status.INFO, "Closed Functionality: " + issueNo, ExtentColor.BLUE);
		cm.viewclosedstatuscheck(issueNo);

        try {
			tc.totalcasesinc();
		   
		    ExtentManger.logColored(Status.INFO, "Checking the Status", ExtentColor.BLUE);

		    if (cm.viewstatus("Closed")) {
		        ExtentManger.logColored(Status.PASS, " Status Displayed:Closed", ExtentColor.GREEN);
		        tc.passedinc();
		    } else {
		        ExtentManger.logColored(Status.FAIL, "Expected Status Not displayed.", ExtentColor.RED);
		        tc.failedinc();
		        Assert.fail("Status Not visible.");
		    }

		   cm.cancel();

		} catch (Exception e) {
		    tc.failedinc();
		    ExtentManger.logColored(Status.FAIL, "View Test Error: " + e.getMessage(), ExtentColor.RED);
		}
		tc.logsummary();
	}
	@Test(priority = 10, dependsOnMethods = { "ClosedStatusCheck" })
	public void DeleteTest() {
		Testcasecounts tc = new Testcasecounts("DeleteTest");

		try {
			Deleteissuepages dl = new Deleteissuepages(driver);
			dl.clearseach();

			String issueNo = "AP-10000-CHE-10000332";
			String AgainIssueNo = "AP-10000-CHE-10000333";

			ExtentManger.logColored(Status.INFO, "Delete Functionality: " + issueNo, ExtentColor.BLUE);

			boolean isDeleted = dl.Deleteissue(issueNo);

			if (!isDeleted) {
				dl.clearseach();
				ExtentManger.logColored(Status.INFO, "Deleting Functionality: " + AgainIssueNo, ExtentColor.BLUE);
				isDeleted = dl.Deleteissue(AgainIssueNo);
			}

			if (isDeleted) {
				String popmsg = dl.handlepopup();

				if (!popmsg.isEmpty()) {
					Assert.assertTrue(popmsg.contains("Do you want to"), "Popup message does not match expected.");
					String toast = dl.getToastMessage();
					Assert.assertTrue(toast.contains("deleted successfully"), "Toast message does not confirm deletion.");
					ExtentManger.logColored(Status.PASS, "Delete successful ", ExtentColor.GREEN);
					tc.passedinc();
				} else {
					ExtentManger.logColored(Status.FAIL, "Popup not handled correctly.", ExtentColor.RED);
					tc.failedinc();
					Assert.fail("Popup not displayed.");
				}
			} else {
				ExtentManger.logColored(Status.FAIL, "No valid issue found to delete.", ExtentColor.RED);
				tc.failedinc();
				Assert.fail("No issue found.");
			}

		} catch (Exception e) {
			ExtentManger.logColored(Status.FAIL, "Exception in delete test: " + e.getMessage(), ExtentColor.RED);
			System.out.println("Exception found: " + e.getMessage());
			tc.failedinc();
		}

		tc.logsummary();
	}

	@Test(priority=11,dependsOnMethods = "DeleteTest")
	public void DeletedStatusCheck() {
		
		Testcasecounts tc= new Testcasecounts("DeletedStatusCheck");
		tc.totalcasesinc();
		ViewDeletedStatus d= new ViewDeletedStatus(driver);
		ExtentManger.logColored(Status.INFO, "Checking Delete Functionality",ExtentColor.BLUE);
		
		String issueno="AP-10000-CHE-10000332";
		ExtentManger.logColored(Status.INFO, "Selected Functionality" + issueno ,ExtentColor.BLUE);
		d.checkdeletedissue(issueno);
		
		boolean isDeleted=d.statusCheck();
		
		if (isDeleted) {
			tc.passedinc();
	        ExtentManger.logColored(Status.PASS, "Issue " + issueno + "  Deleted and Not Found.", ExtentColor.GREEN);
	    } else {
	    	tc.failedinc();
	        ExtentManger.logColored(Status.FAIL, "Issue " + issueno + " still Displayed ", ExtentColor.RED);
	    }
		tc.logsummary();
	}
	
		
	
}
