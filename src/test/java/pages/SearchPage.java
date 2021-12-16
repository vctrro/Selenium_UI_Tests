package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage {
    private final WebDriver webDriver;
    private final WebDriverWait webDriverWait;

    public final String pageURN = "/search";

    private final By searchInput = By.name("keyword");
    private final By markerForSearch = By.className("grid-product__title-inner");

    public SearchPage(WebDriver driver){
        webDriver = driver;
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }

    public List<WebElement> Search(String keyword){
        WebElement searchField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchField.sendKeys(keyword);
        searchField.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(markerForSearch));
    }
}
