package gmail;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class creation {
	
		    public static void main(String[] args) throws Throwable {

		    	   // Step 1: Launch Chrome
		        WebDriver driver = new ChromeDriver();
		        driver.get("https://accounts.google.com/signup/v2/createaccount?flowName=GlifWebSignIn&flowEntry=SignUp");
		        driver.manage().window().maximize();
		        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		        // Step 2: Load Excel
		        FileInputStream fis = new FileInputStream("D:\\git-clone\\hybrid-framework\\src\\test\\resources\\gmail.xlsx");
		        Workbook wb = WorkbookFactory.create(fis);
		        Sheet sh = wb.getSheet("sheet1");

		        // Step 3: Loop through rows
		        int rowCount = sh.getLastRowNum();
		        for (int i = 0; i <= rowCount; i++) {
		            Row rowData = sh.getRow(i);

		            // Read data from Excel
		            String firstName = rowData.getCell(0).getStringCellValue();
		            int day = (int) rowData.getCell(1).getNumericCellValue();
		            int year = (int) rowData.getCell(2).getNumericCellValue();
		            String gender = rowData.getCell(3).getStringCellValue().trim();
		            String username=rowData.getCell(4).getStringCellValue().trim();
		            String password = rowData.getCell(5).getStringCellValue();
		            String confirmPassword = rowData.getCell(6).getStringCellValue();

		            // Step 4: Fill First Name
		            driver.findElement(By.id("firstName")).sendKeys(firstName);

		            // Step 5: Click "Next"
		            driver.findElement(By.xpath("//span[text()='Next']")).click();
		            Thread.sleep(3000);

		            // Step 6: Select Month (fixed as January)
		            driver.findElement(By.xpath("//div[@id='month']")).click();
		            Thread.sleep(1000);
		            driver.findElement(By.xpath("//ul[@role='listbox']//li[@data-value='3']")).click(); // January
		            Thread.sleep(1000);

		            // Step 7: Enter Day and Year
		            driver.findElement(By.id("day")).sendKeys(String.valueOf(day));
		            driver.findElement(By.id("year")).sendKeys(String.valueOf(year));
		            Thread.sleep(1000);

		            // Step 8: Handle Gender dropdown
		            driver.findElement(By.xpath("//div[@id='gender']")).click();
		            Thread.sleep(1000);

		            List<WebElement> genderOptions = driver.findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));
		            boolean genderFound = false;

		            for (WebElement option : genderOptions) {
		                String optionText = option.getText().trim();
		                if (optionText.equalsIgnoreCase(gender)) {
		                    option.click();
		                    genderFound = true;
		                    break;
		                }
		            }

		            if (!genderFound) {
		                System.out.println("Gender not found: " + gender);
		                driver.quit();
		                return;
		            }

		            Thread.sleep(1000);

		         
		            
		            // Step 9: Click Next
		            driver.findElement(By.xpath("//span[text()='Next']")).click();
		            Thread.sleep(3000);
		           
		            driver.findElement(By.xpath("//input[@name='Username']")).sendKeys(username);
		            Thread.sleep(3000);
		            
		            driver.findElement(By.xpath("(//button[@type='button'])[1]")).click();
		            
		          //  driver.findElement(By.xpath("(//input[@type='radio'])[1]")).click();
		            Thread.sleep(2000);
		            
		            // Step 10: Enter Password and Confirm Password
		            driver.findElement(By.name("Passwd")).sendKeys(password);
		           // Thread.sleep(1000);
		            driver.findElement(By.xpath("(//input[@type='password'])[2]")).sendKeys(confirmPassword);
		            Thread.sleep(1000);

		            // Step 11: Final "Next"
		            driver.findElement(By.xpath("//span[text()='Next']")).click();
		            Thread.sleep(3000);

		            // Step 12: Quit browser after form submission (optional)
		            driver.quit();
		        }

		        // Close workbook
		        wb.close();
		        fis.close();
		        driver.quit();
		    }
		}


