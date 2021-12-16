import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.SearchPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchPageTests {
    private static final String chromeWebDriver = "src/webDriver/chromedriver";
    private static final String siteURL = "https://buy-in-10-seconds.company.site/";

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

    @AfterEach
    public void CloseDriver(){
        webDriver.close();
        webDriver = null;
    }
}
