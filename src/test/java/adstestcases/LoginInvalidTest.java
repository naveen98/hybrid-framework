package adstestcases;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.utilites.Excelutils;

import adspages.AdsLoginPage;

public class LoginInvalidTest extends AdsBaseClass {

   
    public void verifyInvalidLoginMultiple() {
        try {
            AdsLoginPage lp = new AdsLoginPage(driver);

            String excelPath = "D:\\git-clone\\hybrid-framework\\src\\test\\resources\\Adsinvalidlogintest.xlsx";
            String sheetName = "invalid";
            String expectedTitle = "Login || ads Application";

            String[][] testData = Excelutils.getcelldatas(excelPath, sheetName);

            for (String[] row : testData) {
                String username = row[0];
                String password = row[1];

                AdsExtentManger.logColored(Status.INFO, "Starting login test:", ExtentColor.BLUE);

                lp.login(username, password);
             //   AdsExtentManger.logColored(Status.INFO, "Entered credentials: " + username + " : " + password, ExtentColor.GREEN);

                String validationMsg = lp.gettoastmessage();

                try {
                    switch (validationMsg) {
                        case "Enter valid form data.":
                        case "Invalid details":
                        case "Invalid captcha / Captcha expired":
                            AdsExtentManger.logColored(Status.PASS, "Displayed error message: " + validationMsg, ExtentColor.GREEN);
                            break;
                        default:
                            AdsExtentManger.logColored(Status.FAIL, "Unexpected error message: " + validationMsg, ExtentColor.RED);
                            Assert.fail("Unexpected toast message: " + validationMsg);
                    }
                } catch (Exception e) {
                    AdsExtentManger.logColored(Status.FAIL, "Exception while checking toast message: " + e.getMessage(), ExtentColor.RED);
                }

                // Verify Title
                boolean isTitleCorrect = lp.verifytitlewithinvaliddata(expectedTitle);
                if (isTitleCorrect) {
                    AdsExtentManger.logColored(Status.PASS, "Page title verified: " + expectedTitle, ExtentColor.GREEN);
                } else {
                    AdsExtentManger.logColored(Status.FAIL, "Page title mismatch. Expected: " + expectedTitle, ExtentColor.RED);
                    Assert.fail("Page title mismatch. Expected: " + expectedTitle);
                }

            }

        } catch (Exception e) {
            AdsExtentManger.logColored(Status.FAIL, "Test failed due to exception: " + e.getMessage(), ExtentColor.RED);
            Assert.fail(" exception: " + e.getMessage());
        }
    }
}
