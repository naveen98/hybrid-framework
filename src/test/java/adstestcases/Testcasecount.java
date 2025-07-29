package adstestcases;

public class Testcasecount {
	 public static int totalTestCaseCount = 0;
	    public static int passedTestCaseCount = 0;
	    public static int failedTestCaseCount = 0;
	    public static int skippedTestCaseCount = 0;

	    public static String testClassName = "";

	    public static void reset(String name) {
	        totalTestCaseCount = 0;
	        passedTestCaseCount = 0;
	        failedTestCaseCount = 0;
	        skippedTestCaseCount = 0;
	        testClassName = name;
	    }

	    public static void incrementTotal() {
	        totalTestCaseCount++;
	    }

	    public static void incrementPassed() {
	        passedTestCaseCount++;
	    }

	    public static void incrementFailed() {
	        failedTestCaseCount++;
	    }

	    public static void incrementSkipped() {
	        skippedTestCaseCount++;
	    }

	    public static int getTotal() {
	        return totalTestCaseCount;
	    }

	    public static int getPassed() {
	        return passedTestCaseCount;
	    }

	    public static int getFailed() {
	        return failedTestCaseCount;
	    }

	    public static int getSkipped() {
	        return skippedTestCaseCount;
	    }

	    public static String getTestClassName() {
	        return testClassName;
	    }

}
