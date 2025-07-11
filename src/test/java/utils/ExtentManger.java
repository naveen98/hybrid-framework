package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentManger {
    public static void log(Status status, String message) {
        if (Reporting.getTest() != null) {
            Reporting.getTest().log(status, message);
        }
    }
    //log colored
    public static void logColored(Status status, String message, ExtentColor color) {
        if (Reporting.getTest() != null) {
            Reporting.getTest().log(status, MarkupHelper.createLabel(message, color));
        }
    }
    
    //methods
    public static void info(String message) {
        logColored(Status.INFO, message, ExtentColor.BLUE);
    }

    public static void pass(String message) {
        logColored(Status.PASS, message, ExtentColor.GREEN);
    }

    public static void fail(String message) {
        logColored(Status.FAIL, message, ExtentColor.RED);
    }

    public static void skip(String message) {
        logColored(Status.SKIP, message, ExtentColor.ORANGE);
    }

    public static void warning(String message) {
        logColored(Status.WARNING, message, ExtentColor.YELLOW);
    }
}
	 


