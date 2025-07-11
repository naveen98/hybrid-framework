package cmstestcases;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.utilites.Excelutils;

import apollocmspages.Appselectionpage;
import apollocmspages.EditUserPage;
import apollocmspages.NavigateToUserManagementPage;
import apollocmspages.UserAddPage;
import apollocmspages.UserManagementActiveAndInactivePage;
import apollocmspages.UserManagementDelete;
import apollocmspages.UserManagementResetPasswordPage;
import apollocmspages.UserWorkFlowConfigUserManagementPage;
import apollocmspages.UsermanagementAccessType;
import utils.ExtentManger;
import utils.Testcasecounts;

public class UserMnagementUserTest extends CmsLoginTest {

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
			ExtentManger.logColored(Status.INFO, "Appselection Info", ExtentColor.BLUE);

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
	public void NavigateToUserMangement() {
		Testcasecounts Tc = new Testcasecounts("NavigateToUserMangement");
		Tc.totalcasesinc();
		try {
			NavigateToUserManagementPage np = new NavigateToUserManagementPage(driver);
			np.Navigatetouser();
			ExtentManger.logColored(Status.INFO, "Navigated to User Management", ExtentColor.BLUE);

			if (np.UserheaderDisplay()) {
				ExtentManger.logColored(Status.PASS, "Users Headers Text displayed.", ExtentColor.GREEN);
				Tc.passedinc();
			} else {
				ExtentManger.logColored(Status.FAIL, "Headers Text Not displayed", ExtentColor.RED);
				Tc.failedinc();
				Assert.fail("Header not displayed");
			}
		} catch (Exception e) {
			Tc.failedinc();
			ExtentManger.logColored(Status.FAIL, "Navigation Failed: " + e.getMessage(), ExtentColor.RED);
			Assert.fail("Navigation failed");
		}
		Tc.logsummary();
	}

	@Test(priority = 3, dependsOnMethods = { "NavigateToUserMangement" })
	public void CreateUser() throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Testcasecounts tc = new Testcasecounts("CreateUser");

		UserAddPage up = new UserAddPage(driver);
		up.addutton();

		String path = "D:\\hybrid-framework\\src\\test\\resources\\usermanagementadd.xlsx";
		String sheetname = "adduser";
		String[][] data = Excelutils.getcelldatas(path, sheetname);

		ExtentManger.logColored(Status.INFO, "User Creation Test Started", ExtentColor.BLUE);

		List<String> radioLabels = Arrays.asList("Enable Asset Request", "Upload Swachh in Vishwam",
				"Create New Drug Request", "Upload Apna Retro", "Enable Marketing Dashboard", "Champs Admin",
				"Retro Approval", "Retro QR", "APNA Survey", "Allow Global Ticket Creation", "Allow Ticket Tag");

		int radioStartIndex = 16;

		for (int i = 0; i < data.length; i++) {
			try {
				tc.totalcasesinc();

				// Form inputs
				String fname = data[i][0];
				String mname = data[i][1];
				String lname = data[i][2];
				String email = data[i][3];
				String phone = data[i][4];
				String userid = data[i][5];
				String region = data[i][6];
				String cluster = data[i][7];
				String location = data[i][8];
				String operatingUnit = data[i][9];
				String role = data[i][10];
				String department = data[i][11];
				String designation = data[i][12];
				String level = data[i][13];
				String password = data[i][14];
				String confirmPassword = data[i][15];

				up.Adduserform(fname, mname, lname, email, phone, userid, region, cluster, location, operatingUnit,
						role, department, designation, level, password, confirmPassword);

				// Handle radio buttons
				boolean allRadiosPassed = true;
				for (int j = 0; j < radioLabels.size(); j++) {
					String label = radioLabels.get(j);
					String option = data[i][radioStartIndex + j];

					try {
						boolean selected = up.selectRadioButtonOption(label, option);
						if (!selected) {
							allRadiosPassed = false;
						}

						if (label.equalsIgnoreCase("Allow Ticket Tag") && option.equalsIgnoreCase("Yes")) {
							try {
								String userInput = data[i][27];
								String expectedUser = data[i][28];
								up.addtickettaguser(userInput, expectedUser);
							} catch (Exception e) {
								allRadiosPassed = false;
							}
						}
					} catch (Exception e) {
						allRadiosPassed = false;
						up.closepoptag();
					}
				}

				// Submit the form
				up.clicksavebtn();

				// Validate response
				String msg = up.getvalidationmessage();
				boolean success = msg.toLowerCase().contains("successfully") || msg.toLowerCase().contains("created");

				if (success) {
					tc.passedinc();
					ExtentManger.logColored(Status.PASS, "User Created : " + msg, ExtentColor.GREEN);
					up.addutton();
				} else {
					js.executeScript("window.scrollTo(0, 0);");
					up.canceform();
					up.addutton();
					tc.failedinc();
					ExtentManger.logColored(Status.PASS, "User Creation Failed : " + msg, ExtentColor.RED);
				}

			} catch (Exception e) {
				ExtentManger.logColored(Status.FAIL, "Exception occurred: " + e.getMessage(), ExtentColor.RED);
				tc.failedinc();
			}
		}
		up.canceform();

		tc.logsummary();
	}

	@Test(priority = 4)//dependsOnMethods = {"CreateUser"}
	public void EditUser() throws Throwable {
	    Testcasecounts tc = new Testcasecounts("EditUser");
	    ExtentManger.logColored(Status.INFO, "Updating the User", ExtentColor.BLUE);

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    EditUserPage ed = new EditUserPage(driver);

	    String[][] data = Excelutils.getcelldatas(
	            "D:\\hybrid-framework\\src\\test\\resources\\usermanagementadd.xlsx", "Edituser");

	    List<String> radioLabels = Arrays.asList(
	            "Enable Asset Request", "Upload Swachh in Vishwam", "Create New Drug Request", "Upload Apna Retro",
	            "Enable Marketing Dashboard", "Champs Admin", "Retro Approval", "Retro QR", "APNA Survey",
	            "Allow Global Ticket Creation", "Allow Ticket Tag");

	    int radioStartIndex = 14;

	    for (int i = 0; i < data.length; i++) {
	        try {
	            tc.totalcasesinc();

	            ed.search(data[i][0]);
	            ed.clickeditoption();

	            ed.edituserdetails(
	                    data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5],
	                    data[i][6], data[i][7], data[i][8], data[i][9], data[i][10],
	                    data[i][11], data[i][12], data[i][13]);

	            boolean allRadiosPassed = true;
	            for (int j = 0; j < radioLabels.size(); j++) {
	                String label = radioLabels.get(j);
	                String option = data[i][radioStartIndex + j];

	                try {
	                    boolean isSelected = ed.selectRadioButtonOption(label, option);
	                    if (!isSelected) {
	                        allRadiosPassed = false;
	                    }

	                    if (label.equalsIgnoreCase("Allow Ticket Tag") && option.equalsIgnoreCase("Yes")) {
	                        try {
	                            ed.addtickettaguser(data[i][25], data[i][26]);
	                        } catch (Exception e) {
	                         //   ExtentManger.logColored(Status.PASS, "Ticket tag add failed", ExtentColor.RED);
	                            allRadiosPassed = false;
	                        }
	                    }
	                } catch (Exception e) {
	                  //  ExtentManger.logColored(Status.FAIL, "Radio select failed for label: " + label, ExtentColor.RED);
	                    allRadiosPassed = false;
	                    ed.closepoptag();
	                }
	            }

	            ed.clickupdatebutton();
	            String msg = ed.getvalidationmessage();

	            boolean success = msg.toLowerCase().contains("successfully") || msg.toLowerCase().contains("updated");

	            if (success) {
	                tc.passedinc();
	                ExtentManger.logColored(Status.PASS, msg , ExtentColor.GREEN);
	            } else {
	                js.executeScript("window.scrollTo(0, 0);");
	                ed.cancelform();
	                tc.failedinc();
	                ExtentManger.logColored(Status.PASS, msg, ExtentColor.RED);
	            }

	        } catch (Exception e) {
	            ExtentManger.logColored(Status.FAIL, "Exception: " + e.getMessage(), ExtentColor.RED);
	            tc.failedinc();
	        } finally {
	            try {
	                ed.clearserach(); // cleanup
	            } catch (Exception ex) {
	                ExtentManger.logColored(Status.INFO, "Clear search failed in finally", ExtentColor.ORANGE);
	            }
	     
	        }
		  

	    }
	    tc.logsummary();
	}
	@Test(priority = 5, dependsOnMethods = { "EditUser" })//, dependsOnMethods = { "EditUser" }
	public void DeleteUser() {
		Testcasecounts tc = new Testcasecounts("DeleteUser");
		tc.totalcasesinc();

		try {
			UserManagementDelete d = new UserManagementDelete(driver);
			String name = "P SUDARSANA BABU";

			ExtentManger.logColored(Status.INFO, "Searching user: " + name, ExtentColor.BLUE);

			boolean isSearched = d.search(name);

			if (isSearched) {

				d.Deleteoption();

				d.handlepopup();

				String toast = d.getToastMessage();

				if (toast.toLowerCase().contains("user")||toast.contains("deleted")) {
					ExtentManger.logColored(Status.PASS, toast +"  :  "+ name, ExtentColor.GREEN);
					tc.passedinc();
				} else {
					ExtentManger.logColored(Status.PASS, "Delete action ," + toast,ExtentColor.RED);
					tc.failedinc();
				}

			} else {
				ExtentManger.logColored(Status.FAIL, "No user found  : " + name, ExtentColor.RED);
				d.clearsearch();
				tc.failedinc();
			}

		} catch (Exception e) {
			ExtentManger.logColored(Status.FAIL, "Exception during delete : " + e.getMessage(), ExtentColor.RED);
			tc.failedinc();
		}

		tc.logsummary();
	}

	@Test(priority = 6,dependsOnMethods = {"DeleteUser"})
	public void ActiveOrInactive() {
	    Testcasecounts tc = new Testcasecounts("ActiveOrInactive");
	    tc.totalcasesinc();

	    UserManagementActiveAndInactivePage up = new UserManagementActiveAndInactivePage(driver);
	    String username = "bharath";

	    ExtentManger.logColored(Status.INFO, "Checking status for user: " + username, ExtentColor.BLUE);

	    try {
	        // Initial Status
	        String initialStatus = up.getCurrentStatus(username);
	        

	        // Status
	        if (initialStatus.equalsIgnoreCase("Active")) {
	            up.ToInactive();
	        } else if (initialStatus.equalsIgnoreCase("Inactive")) {
	            up.ToActive();
	        } else {
	            throw new Exception("Could not Found  current status.");
	        }

	        //  Toast
	        String toast = up.getToastMessage();
	        ExtentManger.logColored(Status.PASS, "Toast Message: " + toast, ExtentColor.GREEN);

	        //  Updated Status
	       // up.clearSearch();
	        String updatedStatus = up.getCurrentStatus(username);
	        ExtentManger.logColored(Status.INFO, "Updated Status: " + updatedStatus, ExtentColor.BLUE);

	        //  Validate
	        if (!initialStatus.equalsIgnoreCase(updatedStatus)) {
	            ExtentManger.logColored(Status.PASS, "Status changed from " + initialStatus + " to " + updatedStatus, ExtentColor.GREEN);
	            tc.passedinc();
	        } else {
	            ExtentManger.logColored(Status.FAIL, "Status did not change. : " + updatedStatus, ExtentColor.RED);
	            tc.failedinc();
	            Assert.fail("User status did not update.");
	        }

	    } catch (Exception e) {
	        ExtentManger.logColored(Status.FAIL, "Exception occurred: " + e.getMessage(), ExtentColor.RED);
	        tc.failedinc();
	        Assert.fail("Test failed due to exception: " + e.getMessage());
	    }

	    tc.logsummary();
	}

	@Test(priority = 7,dependsOnMethods = {"ActiveOrInactive"})
	public void ResetPassword() throws Throwable {

	    
	    Testcasecounts tc = new Testcasecounts("ResetPassword");
	    UserManagementResetPasswordPage rp = new UserManagementResetPasswordPage(driver);

	 
	    String[][] data = Excelutils.getcelldatas(
	        "D:\\hybrid-framework\\src\\test\\resources\\usermanagementresetpassword.xlsx", "reset");

	   
	    String username = "Naveen";
	    ExtentManger.logColored(Status.INFO, "Resetting password for user: " + username, ExtentColor.BLUE);

	    try {
	        for (int i = 0; i < data.length; i++) {
	            tc.totalcasesinc();

	            String newPassword = data[i][0];
	            String confirmPassword = data[i][1];

	            
	            rp.search(username);
	            rp.resetoption();

	            rp.enterPassword(newPassword, confirmPassword);
	            rp.savebtn();

	            String message = rp.getToastMessage();

	            if (message.toLowerCase().contains("success")) {
	                ExtentManger.logColored(Status.PASS,  message + " : " + username, ExtentColor.GREEN);
	                tc.passedinc();

	             /*  
	                try {
	                	rp.closePopBox();
	                } catch (Exception e) {
	                    System.out.println("Popbox not found ");
	                }
*/
	            } else {
	                ExtentManger.logColored(Status.PASS, message + " : " + username, ExtentColor.RED);
	                tc.failedinc();
	            }
            rp.closePopBox();
	            
	        }

	    } catch (Exception e) {
	        ExtentManger.logColored(Status.FAIL, "Exception : " + e.getMessage(), ExtentColor.RED);
	        tc.failedinc();
	    }

	    tc.logsummary();
	}

	@Test(priority = 8, dependsOnMethods = { "ResetPassword" })
	public void AccessType() {
		Testcasecounts tc = new Testcasecounts("AccessType");

		try {
			UsermanagementAccessType ac = new UsermanagementAccessType(driver);
			ExtentManger.logColored(Status.INFO, "Checking the Access Type Functionality", ExtentColor.BLUE);

			String uname = "sameera saam"; 
			ac.search(uname);

			String[][] data = Excelutils.getcelldatas("D:\\hybrid-framework\\src\\test\\resources\\Accesstypeusermanagement.xlsx", "Accesstype");

			for (int i = 0; i <= 0; i++) {
				tc.totalcasesinc();

				String accessType = data[i][0];
				ac.accessType(accessType);

				String validationMsg = ac.getToastMessage();

				if (validationMsg != null && (validationMsg.toLowerCase().contains("saved") || validationMsg.toLowerCase().contains("successfully"))) {
					ExtentManger.logColored(Status.PASS,  validationMsg + " : " + uname, ExtentColor.GREEN);
					tc.passedinc();
				} else if (validationMsg.equalsIgnoreCase("Already selected")) {
					ExtentManger.logColored(Status.PASS, "Access type already selected : " + uname, ExtentColor.GREEN);
					tc.failedinc();
				} else {
					ExtentManger.logColored(Status.PASS, validationMsg + "  Already Selected  :  " + uname, ExtentColor.RED);
					tc.failedinc();
				}
				
			}
			
                //ac.closeinsidepop();
      
		} catch (Exception e) {
			ExtentManger.logColored(Status.FAIL, "Exception in Access Type Functionality: " + e.getMessage(), ExtentColor.RED);
			System.out.println("Exception: " + e.getMessage());
		}

		tc.logsummary();

	}

	
	@Test(priority = 9)
	public void UserWorkFlowConfig() {
	    Testcasecounts tc = new Testcasecounts("UserWorkFlowConfig");
	    UserWorkFlowConfigUserManagementPage us = new UserWorkFlowConfigUserManagementPage(driver);

	    ExtentManger.logColored(Status.INFO, "Checking the User Work Flow Functionality", ExtentColor.BLUE);

	    String Userid = "ganesh";
	    us.search(Userid);

	    try {
	        String[][] data = Excelutils.getcelldatas("D:\\hybrid-framework\\src\\test\\resources\\userworkflowconfig.xlsx", "userworkflow");

	        for (int k = 0; k < data.length; k++) {
	            tc.totalcasesinc();

	            String entity = data[k][0];
	            us.openUserWorkflowOption();
	            us.addUserWorkflowEntity(entity);
	            us.submit();

	            String validationmsg = us.getToastMessage();
	            System.out.println("Validation message: " + validationmsg);

	            if (validationmsg != null) {
	                switch (validationmsg.trim()) {
	                    case "Successfully saved":
	                        tc.passedinc();
	                        ExtentManger.logColored(Status.PASS, validationmsg + " | " + Userid, ExtentColor.GREEN);
	                        break;

	                    case "Please fill mandatory fields":
	                        tc.failedinc();
	                        ExtentManger.logColored(Status.FAIL, "Validation failed: " + validationmsg + " | " + Userid, ExtentColor.RED);
	                        break;

	                    default:
	                        tc.failedinc();
	                        ExtentManger.logColored(Status.FAIL, "Unexpected message: " + validationmsg + " | " + Userid, ExtentColor.RED);
	                        break;
	                }
	            } else {
	                tc.failedinc();
	                ExtentManger.logColored(Status.FAIL, "No toast message captured for " + Userid, ExtentColor.RED);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Exception: " + e.getMessage());
	        ExtentManger.logColored(Status.FAIL, "Exception in test: " + e.getMessage(), ExtentColor.RED);
	    }

	    tc.logsummary();
	}
}