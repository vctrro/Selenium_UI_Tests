import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.SearchPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchPageTests {
    private static final String chromeWebDriver = "src/webDriver/chromedriver";
    private static final String siteURL = "https://buy-in-10-seconds.company.site";

    WebDriver webDriver;
    SearchPage searchPage;


    @BeforeAll
    static void InitDriverPath(){
        System.setProperty("webdriver.chrome.driver", chromeWebDriver);
    }

    @BeforeEach
    public void InitTest(){
        webDriver = new ChromeDriver();
        searchPage = new SearchPage(webDriver);
        webDriver.navigate().to(siteURL + searchPage.pageURN);
    }

    @DisplayName("Поиск по ключевому слову:")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"Товар", "1", "5"})
    public void SearchTest(String keyword){
        List<WebElement> searchResult = searchPage.Search(keyword);

        keyword = keyword.toLowerCase();
        for (WebElement element :
                searchResult) {
            String productName = element.getText().toLowerCase();
            assertTrue(productName.contains(keyword),
                    String.format("\n\"%s\" не содержит \"%s\"", productName, keyword));
        }
    }

    @DisplayName("Поиск по диапазону цен:")
    @ParameterizedTest(name = "от {0} до {1}")
    @CsvSource({
            "0, 1",
            "1, 5",
            "2, 4"
    })
    public void PriceRangeTest(int from, int to){
        List<WebElement> searchResult = searchPage.PriceRange(from, to);

        for (WebElement element :
                searchResult) {
            float price = Float.parseFloat(element.getText().substring(1));
            assertFalse(price > to || price < from,
                    String.format("\nЦена \"%f\" не соответствует диапазону \"%d\" - \"%d\"", price, from, to));
        }
    }

    @DisplayName("Показать товары в наличии")
    @Test
    public void InStockTest(){
        List<WebElement> searchResults = searchPage.InStock();

        for (WebElement element :
                searchResults) {
            assertFalse(element.getText().toLowerCase().contains("распродано"),
                    "\nНе все показанные товары есть в наличии");
        }
    }

    @AfterEach
    public void CloseDriver(){
        webDriver.close();
        webDriver = null;
    }
}
