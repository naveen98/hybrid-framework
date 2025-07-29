package adstestcases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class AdsReporting extends TestListenerAdapter {

    private ExtentSparkReporter sparkReporter; // UI of the report
    private ExtentReports extent;              // Common report object
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("======= Extent Report Initialization Started =======");

        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "Test-Report-" + timestamp + ".html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + reportName);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // You can make these dynamic later based on env
        extent.setSystemInfo("Computer Name", "localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Naveen");
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Browser", "Edge");
    }

    @Override
    public void onTestStart(ITestResult result) {
        Testcasecount.incrementTotal();
        System.out.println("====== Test Started: " + result.getMethod().getMethodName() + " ======");
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Testcasecount.incrementPassed();

        extentTest.get().log(Status.PASS, MarkupHelper.createLabel("Test Passed: " + result.getName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Testcasecount.incrementFailed();

        extentTest.get().log(Status.FAIL, MarkupHelper.createLabel("Test Failed: " + result.getName(), ExtentColor.RED));
        extentTest.get().log(Status.FAIL, MarkupHelper.createLabel("Exception: " + result.getThrowable(), ExtentColor.RED));

        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof AdsBaseClass) {
                WebDriver driver = ((AdsBaseClass) testInstance).driver;
                if (driver != null) {
                    String screenshotPath = ((AdsBaseClass) testInstance).captureScreenshot(driver, result.getMethod().getMethodName());
                    extentTest.get().addScreenCaptureFromPath(screenshotPath);
                } else {
                    extentTest.get().log(Status.WARNING, "WebDriver is null. Screenshot not captured.");
                }
            }
        } catch (Throwable e) {
            extentTest.get().log(Status.WARNING, "Exception while capturing screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Testcasecount.incrementSkipped();
        extentTest.get().log(Status.SKIP, MarkupHelper.createLabel("Test Skipped: " + result.getName(), ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        System.out.println("======= Extent Report Generation Completed =======");
    }

    // Static accessor for external usage
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
