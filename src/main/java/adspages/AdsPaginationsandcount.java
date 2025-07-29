package adspages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utilites.Paginations;
import com.utilites.webdriverwaitutils;

public class AdsPaginationsandcount {

    WebDriver driver;
    webdriverwaitutils wait;
    Paginations pagination;

    By paginationTextLocator = By.xpath("//div[contains(text(),'Total')]"); 
    By nextButton = By.xpath("//a[contains(@class,'ui-paginator-next')]");
    By previousButton = By.xpath("//a[contains(@class,'ui-paginator-prev')]");

    @FindBy(xpath="//a[@title='Reload']")private WebElement gridrefresh;
    
    public AdsPaginationsandcount(WebDriver driver) {
        this.driver = driver;
        this.wait = new webdriverwaitutils(driver);
        this.pagination = new Paginations(driver);
        PageFactory.initElements(driver, this);
    }

    public int getTotalPagesFromText() {
        try {
            WebElement paginationTextElement = wait.waitForVisibilityBy(paginationTextLocator);

            if (paginationTextElement != null && paginationTextElement.isDisplayed()) {
                String paginationText = paginationTextElement.getText();
                System.out.println(" Pagination Text: " + paginationText);

                int totalPages = Paginations.extractTotalCountFromPaginationText(paginationText);
                System.out.println(" Extracted Total Pages: " + totalPages);

                return totalPages;
            } else {
                System.out.println(" Pagination text element not visible.");
                return 0;
            }

        } catch (Exception e) {
            System.out.println(" Exception while getting total pages: " + e.getMessage());
            return 0;
        }
    }


    public WebElement getNextButton() {
        try {
            WebElement nextBtn = wait.waitForVisibilityBy(nextButton);
            if (nextBtn != null && nextBtn.isDisplayed()) {
                return nextBtn;
            } else {
                System.out.println("Next button not visible.");
                return null;
            }
        } catch (Exception e) {
            System.out.println(" Exception clicking Next button: " + e.getMessage());
            return null;
        }
    }

    public WebElement getPreviousButton() {
        try {
            WebElement prevBtn = wait.waitForVisibilityBy(previousButton);
            if (prevBtn != null && prevBtn.isDisplayed()) {
                return prevBtn;
            } else {
                System.out.println(" Previous button not visible.");
                return null;
            }
        } catch (Exception e) {
            System.out.println(" Exception clicking Previous button: " + e.getMessage());
            return null;
        }
    }


    // Navigate to specific page number
    public void navigateToPage(int pageNumber) {
         pagination.goToPage(pageNumber);
         
    }

    // Click next page
    public void clickNextPage() {
        pagination.clickNext(getNextButton());
    }

    // Click previous page
    public void clickPreviousPage() {
        pagination.clickPrevious(getPreviousButton());
    }
    
    public int getCurrentPageNumber() {
        try {
            WebElement activePage = driver.findElement(
            By.xpath("//a[contains(@class,'ui-paginator-page') and contains(@class,'ui-state-active')]"));
            return Integer.parseInt(activePage.getText());
        } catch (Exception e) {
            System.out.println("Could not find current active page: " + e.getMessage());
            return -1;
        }
    }
    
    public void Gridrefresh() {
    	WebElement grid=wait.waitForVisibility(gridrefresh);
    	
    	try {
    		if(grid!=null&&grid.isDisplayed()) {
        		wait.waitForClickability(gridrefresh).click();
    		}else {
    			System.out.println("Grid Not clickable");
    		}
    	
    	}
    	catch (Exception e) {
			System.out.println("Exception "+e.getMessage());
		}
    }
    
    
    
    
}
