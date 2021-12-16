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
    private final By priceFromInput = By.xpath("//input[@aria-label='от']");
    private final By priceToInput = By.xpath("//input[@aria-label='до']");
    private final By markerForSearch = By.className("grid-product__title-inner");
    private final By markerForPrice = By.xpath("//div[@class='grid-product__price-value ec-price-item']");

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

    public List<WebElement> PriceRange(int from, int to){
        WebElement priceFromField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(priceFromInput));
        WebElement priceToField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(priceToInput));
        priceFromField.sendKeys(Integer.toString(from));
        priceToField.sendKeys(Integer.toString(to));
        priceToField.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(markerForPrice));
    }
}
